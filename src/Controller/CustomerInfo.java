package Controller;

import DBConnection.DBConnection;
import validation.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomerInfo implements Initializable {
    @FXML
    private Label error_CMND;

    @FXML
    private Label error_dob;

    @FXML
    private Label error_gender;

    @FXML
    private Label error_nameKH;

    @FXML
    private Label error_phoneN;

    @FXML
    private Label error_que;

    @FXML
    private Label error_quocTich;

    @FXML
    private DatePicker datePickerDob;

    @FXML
    private TextField textFieldHometown;

    @FXML
    private TextField textFieldId;

    @FXML
    private TextField textFieldIdCard;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldNationality;

    @FXML
    private TextField textFilePhoneNumber;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ToggleGroup rbGender;

    @FXML
    private RadioButton rbMale;

    private String gender;

    @FXML
    void handleButtonSave(ActionEvent event) {
        insertCustomer();
    }

    @FXML
    private void getGender(ActionEvent event) {
        if(rbMale.isSelected()){
            gender = rbMale.getText();
        } else {
            gender = rbFemale.getText();
        }

    }

    private boolean checkErrorText(){
        boolean isDobNotEmpty=Validation.isDatePickerNotEmpty(datePickerDob,error_dob,"Vui lòng chọn ngày sinh.");
        boolean isCMNDNotEmpty=Validation.isTextFieldNotEmpty(textFieldIdCard,error_CMND,"Vui lòng nhập số CMND.");
        boolean isHomeNotEmpty=Validation.isTextFieldNotEmpty(textFieldHometown,error_que,"Vui lòng nhập quê quán.");
        boolean isNameNotEmpty=Validation.isTextFieldNotEmpty(textFieldName,error_nameKH,"Vui lòng nhập tên KH.");
        boolean isGenderNotEmpty=Validation.isGenderNotEmpty(rbMale,rbFemale,error_gender,"Vui lòng chọn giới tính.");
        boolean isPhoneNNotEmpty=Validation.isTextFieldNotEmpty(textFilePhoneNumber,error_phoneN,"Vui lòng nhập số ĐT.");
        boolean isNationalityNotEmpty=Validation.isTextFieldNotEmpty(textFieldNationality,error_quocTich,"Vui lòng nhập quốc tịch.");
        boolean phoneNIsNumber=false;
        boolean CMNDisNumber=false;
        if(isCMNDNotEmpty){
            CMNDisNumber=Validation.isNumber(textFieldIdCard,error_CMND,"CMND phải là 1 dãy các chữ số");
        }
        if(isPhoneNNotEmpty){
            phoneNIsNumber=Validation.isNumber(textFilePhoneNumber,error_phoneN,"Số đt phải là 1 dãy các chữ số");
        }
        if(isDobNotEmpty && isCMNDNotEmpty && isHomeNotEmpty && isNameNotEmpty && isGenderNotEmpty && isPhoneNNotEmpty && isNationalityNotEmpty && phoneNIsNumber && CMNDisNumber){
            return true;
        }
        return false;
    }

    private void insertCustomer(){
        if(checkErrorText()) {
            String query = "INSERT INTO HotelManager.dbo.KhachHang VALUES ( N'" + textFieldName.getText() + "', N'"
                    + datePickerDob.getValue().toString() + "', N'" + gender + "', N'" + textFieldIdCard.getText() + "', N'" + textFilePhoneNumber.getText() + "', N'" + textFieldHometown.getText() + "', N'" + textFieldNationality.getText() + "')";
            System.out.println(query);
            DBConnection databaseConnection = new DBConnection();
            Connection cn = databaseConnection.getConnection();
            Statement st;
            try {
                st = cn.createStatement();
                st.executeUpdate(query);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Đã thêm");
                alert.show();
                resetText();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Mã khách hàng đã tồn tại");
                alert.show();
            }
        }
    }
    private void resetText(){
        textFieldId.setText(getMKH());
        textFieldName.setText("");
        datePickerDob.setValue(null);
        textFieldHometown.setText("");
        textFilePhoneNumber.setText("");
        textFieldIdCard.setText("");
        textFieldNationality.setText("");
        rbFemale.setSelected(false);
        rbMale.setSelected(false);
    }
    private String getMKH() {
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT MAX(maKH) FROM KhachHang";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                return "KH" + String.format("%02d", rs.getInt(1) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldId.setText(getMKH());
    }
}
