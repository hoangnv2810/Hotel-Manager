package Controller;
import DBConnection.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Account {
    @FXML
    private Label labelAddress;

    @FXML
    private Label labelDob;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelGender;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPhone;

    @FXML
    private Label labelUsername;

    @FXML
    private TextField tfDiaChi;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfGioiTinh;

    @FXML
    private TextField tfHoTen;

    @FXML
    private TextField tfNgaySinh;

    @FXML
    private TextField tfSoDienThoai;

    @FXML
    private TextField tfUsername;

    @FXML
    private ImageView iconCancel;

    @FXML
    private ImageView iconSave;

    @FXML
    void mouseClickedIcon(MouseEvent event) {
        iconCancel.setVisible(true);
        iconSave.setVisible(true);
        tfUsername.setVisible(true);
        tfHoTen.setVisible(true);
        tfNgaySinh.setVisible(true);
        tfGioiTinh.setVisible(true);
        tfSoDienThoai.setVisible(true);
        tfEmail.setVisible(true);
        tfDiaChi.setVisible(true);
        tfUsername.setText(labelUsername.getText());
        tfHoTen.setText(labelName.getText());
        tfNgaySinh.setText(labelDob.getText());
        tfGioiTinh.setText(labelGender.getText());
        tfSoDienThoai.setText(labelPhone.getText());
        tfEmail.setText(labelEmail.getText());
        tfDiaChi.setText(labelAddress.getText());
//        labelUsername.setVisible(false);
    }


    @FXML
    void mouseClickedIconCancel(MouseEvent event) {
        iconCancel.setVisible(false);
        iconSave.setVisible(false);
        tfUsername.setVisible(false);
        tfHoTen.setVisible(false);
        tfNgaySinh.setVisible(false);
        tfGioiTinh.setVisible(false);
        tfSoDienThoai.setVisible(false);
        tfEmail.setVisible(false);
        tfDiaChi.setVisible(false);
    }

    @FXML
    void mouseClickedIconSave(MouseEvent event) {
        String query = "UPDATE [User] SET username = '" + tfUsername.getText() + "', tenNV = N'" + tfHoTen.getText() + "', ngaySinh = N'" + tfNgaySinh.getText() + "', gioiTinh = N'" + tfGioiTinh.getText() + "', soDT = '" + tfSoDienThoai.getText() + "', email = '" + tfEmail.getText() + "', queQuan = N'" + tfDiaChi.getText() + "' WHERE username = '" + labelUsername.getText() + "'";
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        try {
            Statement st = cn.createStatement();
            st.executeUpdate(query);
            setInfor(tfUsername.getText());
            mouseClickedIconCancel(event);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật thành công");
            alert.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setInfor(String username) {
        DBConnection db = new DBConnection();
        Connection cn = db.getConnection();
        String query = "SELECT * FROM [User] WHERE username = '" + username + "'";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                labelUsername.setText(rs.getString("username"));
                labelName.setText(rs.getString("tenNV"));
                labelDob.setText(String.valueOf(rs.getDate("ngaySinh")));
                labelGender.setText(rs.getString("gioiTinh"));
                labelPhone.setText(rs.getString("soDT"));
                labelEmail.setText(rs.getString("email"));
                labelAddress.setText(rs.getString("queQuan"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
