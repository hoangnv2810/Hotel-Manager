package Controller;

import DBConnection.DBConnection;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CheckIn implements Initializable {
    @FXML
    private ComboBox<?> comBoxMDV;

    @FXML
    private ComboBox<?> comBoxMP;

    @FXML
    private TableView<Phong> tvPhong;

    @FXML
    private TableColumn<Phong, String> tcMaPhong;

    @FXML
    private TableColumn<Phong, String> tcLoaiPhong;

    @FXML
    private TableColumn<Phong, Boolean> tcTinhTrang;

    @FXML
    private TableColumn<Phong, Integer> tcGia;


    @FXML
    void gotoInforCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../View/CustomerInfo.fxml"));
        stage.setScene(new Scene(root));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showRooms();
    }

    public ObservableList<Phong> getRoomList(){
        ObservableList<Phong> phongList = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT* FROM dbo.Phong Where trangThai = 'true'";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            Phong rooms;
            while (rs.next()){
                rooms = new Phong(rs.getString("maPhong"), rs.getString("loaiPhong"),
                        rs.getString("trangThai"), rs.getInt("gia"));
                phongList.add(rooms);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return phongList;
    }

    public void showRooms(){
        ObservableList<Phong> listPhong = getRoomList();
        tcMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        tcLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));
        tcTinhTrang.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        tcGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tvPhong.setItems(listPhong);
    }
}
