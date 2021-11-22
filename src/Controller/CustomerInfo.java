package Controller;

import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class CustomerInfo {
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
    private void goBack(ActionEvent e) throws IOException {
        //Lay Stage
        FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        loader.setLocation(getClass().getResource("../View/MenuBarBorderPane.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setHeight(575);
        stage.setScene(scene);
    }


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

    private void insertCustomer(){
        String query = "INSERT INTO HotelManager.dbo.KhachHang VALUES (N'" + textFieldId.getText() + "', N'" + textFieldName.getText() + "', N'"
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
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Mã khách hàng đã tồn tại");
            alert.show();
        }
    }

}
