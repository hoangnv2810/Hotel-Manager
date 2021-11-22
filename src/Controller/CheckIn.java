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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CheckIn implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btDatPhong;

    @FXML
    private Button btDKKH;

    @FXML
    private Button btSua;

    @FXML
    private MenuItem menuItemThanhToan;

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
    private TableView<ThuePhong> tvThuePhong;

    @FXML
    private TableColumn<ThuePhong, String> tcMT;

    @FXML
    private TableColumn<ThuePhong, String> tcMKH;

    @FXML
    private TableColumn<ThuePhong, String> tcMP;

    @FXML
    private TableColumn<ThuePhong, String> tcNgayDen;

    @FXML
    private TableColumn<ThuePhong, String> tcNgayDi;

    private String maThue;
    private String maPhong;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> listMKH = getMKH();
        ObservableList<String> listMP = getMP();
        comBoxMP.setItems(listMP);
        comBoxMKH.setItems(listMKH);
        showRooms();
        showThuePhong();
    }
    private Stage stage;

    @FXML
    void gotoInforCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../View/CustomerInfo.fxml"));
        stage.centerOnScreen();
        stage.setHeight(325);
        stage.setScene(new Scene(root));
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
            alert.setHeaderText("Thông báo");
            alert.setContentText("Đặt phòng thành công");
            alert.show();
            updateTrangThaiPhong();
            showThuePhong();
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui lòng thử lại");
            alert.show();
        }
    }

    private void updateTrangThaiPhong(){
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


    public void showThuePhong(){
        ObservableList<ThuePhong> listTP = getListThuePhong();
        tcMT.setCellValueFactory(new PropertyValueFactory<>("maThue"));
        tcMKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        tcMP.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        tcNgayDen.setCellValueFactory(new PropertyValueFactory<>("ngayDen"));
        tcNgayDi.setCellValueFactory(new PropertyValueFactory<>("ngayDi"));
        tvThuePhong.setItems(listTP);
    }

    public ObservableList<ThuePhong> getListThuePhong(){
        ObservableList<ThuePhong> thuePhongList = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "Select * FROM ThuePhong";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            ThuePhong thuePhong;
            ObservableList<KhachHang> listKH = getListKH();
            while (rs.next()){
                thuePhong = new ThuePhong(rs.getString("maThue"), rs.getString("maKH"), rs.getString("maPhong"), rs.getDate("ngayDen"), rs.getDate("ngayDi"));
                thuePhongList.add(thuePhong);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return thuePhongList;
    }

    public ObservableList<KhachHang> getListKH(){
        ObservableList<KhachHang> khachHangList = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT * FROM KhachHang";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            KhachHang khachHang;
            while (rs.next()){
                khachHang = new KhachHang(rs.getString("maKH"), rs.getString("ten"), rs.getDate("ngaySinh"), rs.getString("gioiTinh"), rs.getString("soCMND"), rs.getString("soDT"), rs.getString("queQuan"), rs.getString("quocTich"));
                khachHangList.add(khachHang);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return khachHangList;
    }

    @FXML
    void handleButtonSua(ActionEvent event) {
        updateThuePhong();
    }

    private void updateThuePhong(){
        if(comBoxMP.getSelectionModel().getSelectedItem().compareTo(maPhong) != 0){
            upTTPkhiupThuePhong(maPhong);
            upTTPkhiupThuePhong(comBoxMP.getSelectionModel().getSelectedItem());
        }
        String query = "UPDATE ThuePhong SET maKH = '" + comBoxMKH.getSelectionModel().getSelectedItem() + "', maPhong = '"
                + comBoxMP.getSelectionModel().getSelectedItem() + "', ngayDen = N'" + dpNgayDen.getValue().toString() + "', ngayDi = N'"
                + dpNgayDi.getValue().toString() + "' WHERE maThue = " + maThue;
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cập nhật thành công");
            alert.setHeaderText("Thông báo");
            alert.show();
        } catch (Exception e){
            e.printStackTrace();
        }
        showThuePhong();
    }

    private void upTTPkhiupThuePhong(String mPhong){
        String query = "BEGIN\n" +
                "    DECLARE @trangthai BIT\n" +
                "    SELECT @trangthai = trangThai from Phong WHERE maPhong = '"+ mPhong + "'\n" +
                "    IF @trangthai = 'true'\n" +
                "    BEGIN\n" +
                "        UPDATE Phong SET trangThai = 'false' WHERE maPhong = '" + mPhong + "'\n" +
                "    END\n" +
                "    ELSE\n" +
                "    BEGIN\n" +
                "        UPDATE Phong SET trangThai = 'true' WHERE maPhong = '" + mPhong + "'\n" +
                "    END\n" +
                "END";
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

    @FXML
    void handleMouseAction(MouseEvent event) {
        ThuePhong tp = tvThuePhong.getSelectionModel().getSelectedItem();
        maThue = tp.getMaThue();
        maPhong = tp.getMaPhong();
        comBoxMKH.getSelectionModel().select(tp.getMaKH());
        comBoxMP.getSelectionModel().select(tp.getMaPhong());
        dpNgayDen.setValue(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(tp.getNgayDen())));
        dpNgayDi.setValue(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(tp.getNgayDi())));
    }


    @FXML
    void handleHuyPhong(ActionEvent event) {
        deleteThuePhong();
    }

    private void deleteThuePhong(){
        System.out.println(maPhong);
        String query = "DELETE FROM ThuePhong WHERE maThue = " + maThue;
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
            if(maThue != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Hủy phòng thành công");
                alert.setHeaderText("Thông báo");
                upTTPkhiupThuePhong(maPhong);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Lỗi...");
                alert.setContentText("Vui lòng chọn phòng cần hủy");
                alert.show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        showThuePhong();
    }

}
