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
import javafx.scene.control.*;
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
    private Button btDKKH;

    @FXML
    private Button btDatPhong;

    @FXML
    private DatePicker dpNgayDen;

    @FXML
    private DatePicker dpNgayDi;

    @FXML
    private ComboBox<String> comBoxMKH;

    @FXML
    private ComboBox<String> comBoxMP;

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
        ObservableList<String> listMKH = getMKH();
        ObservableList<String> listMP = getMP();
        comBoxMP.setItems(listMP);
        comBoxMKH.setItems(listMKH);
        showRooms();
    }

    public ObservableList<Phong> getRoomList(){
        ObservableList<Phong> phongList = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT* FROM dbo.Phong";
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

    public ObservableList<String> getMKH(){
        ObservableList<String> listMKH = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maKH FROM dbo.KhachHang";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                listMKH.add(rs.getString("maKH"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listMKH;
    }

    public ObservableList<String> getMP(){
        ObservableList<String> listMKH = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maPhong FROM dbo.Phong WHERE trangThai = 'true'";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                listMKH.add(rs.getString("maPhong"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listMKH;
    }

    public void showRooms(){
        ObservableList<Phong> listPhong = getRoomList();
        tcMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        tcLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));
        tcTinhTrang.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        tcGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tvPhong.setItems(listPhong);
    }

    @FXML
    void handleInsertDP(ActionEvent event) {
        String query  = "INSERT INTO HotelManager.dbo.ThuePhong VALUES (N'"
                + comBoxMKH.getSelectionModel().getSelectedItem()
                + "', N'" + comBoxMP.getSelectionModel().getSelectedItem()
                + "', N'" + dpNgayDen.getValue().toString() + "', N'" + dpNgayDi.getValue().toString() + "')";
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Đã thêm");
            alert.show();
            updateTTPhong();
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui lòng thử lại");
            alert.show();
        }
    }

    private void updateTTPhong(){
        String query = "UPDATE Phong SET trangThai = '" + false + "' WHERE maPhong = '" + comBoxMP.getSelectionModel().getSelectedItem() + "'";
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
        showRooms();
    }
}
