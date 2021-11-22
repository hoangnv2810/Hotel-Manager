package Controller;
import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;


    public void loginButtonOnAction(ActionEvent e){
        if(usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            DBConnection connect = new DBConnection();
            Connection connectDB = connect.getConnection();
            String sql = "SELECT count(1) FROM dbo.[User] where username = '" + usernameTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(sql);
                while (queryResult.next()){
                    if(queryResult.getInt(1) == 1){
                        goToHomePage(e);
                    } else {
                        loginMessageLabel.setText("Sai mật khẩu hoặc tài khoản");
                    }
                }
            } catch (Exception ec){
                ec.printStackTrace();
            }
        } else {
            loginMessageLabel.setText("Vui lòng thử lại");
        }
    }

    private void goToHomePage(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../View/MenuBarBorderPane.fxml"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}
