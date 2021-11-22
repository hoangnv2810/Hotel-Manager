package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarBorderPane implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuItem menuItemThanhToan;

    @FXML
    void handleMenuItem(ActionEvent event) throws IOException {
        MenuItem mItem = (MenuItem) event.getSource();
        String side = mItem.getText();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        System.out.println(mItem.getText());
        if ("Đặt phòng".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CheckIn.fxml"));
            try {
                stage.setHeight(575);
                stage.setWidth(720);
                stage.centerOnScreen();
                borderPane.setCenter(menuLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("Thanh toán".equalsIgnoreCase(side)) {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CheckOut.fxml"));
            try {
                borderPane.setCenter(menuLoader.load());
                stage.centerOnScreen();
                stage.setWidth(720);
                stage.setHeight(420);
                System.out.println(borderPane.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("Quản lý phòng".equalsIgnoreCase(side)){
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/RoomManager.fxml"));
            try {
                stage.centerOnScreen();
                stage.setHeight(400);
                stage.setWidth(735);
                borderPane.setCenter(menuLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if("Quản lý khách hàng".equalsIgnoreCase(side)){
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CustomerManager.fxml"));
            try {
                stage.centerOnScreen();
                stage.setHeight(500);
                stage.setWidth(1000);
                borderPane.setCenter(menuLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if("Quản lý nhân viên".equalsIgnoreCase(side)){
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/EmployeeManager.fxml"));
            try {
                stage.centerOnScreen();
                stage.setHeight(600);
                stage.setWidth(1000);
                borderPane.setCenter(menuLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../View/CheckIn.fxml"));
        try {
            borderPane.setCenter(menuLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
