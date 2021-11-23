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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

    @FXML
    private TableColumn<ThuePhong, String> tcThanhToan;

    private String maThue;
    private String maPhong;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comBoxMP.setItems(getMP());
        comBoxMKH.setItems(getMKH());
        dpNgayDen.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        showRooms();
        showThuePhong();
    }

    @FXML
    void gotoInforCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../View/CustomerInfo.fxml"));
        stage.centerOnScreen();
        stage.setHeight(325);
        stage.setScene(new Scene(root));
    }

    @FXML
    void handleInsertDP(ActionEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        ObservableList<String> listMP = getMP();
        if (listMP.contains(comBoxMP.getValue())) {
            String query = "INSERT INTO HotelManager.dbo.ThuePhong VALUES ( "
                    + Integer.parseInt(comBoxMKH.getSelectionModel().getSelectedItem().substring(2))
                    + ", " + Integer.parseInt(comBoxMP.getSelectionModel().getSelectedItem().substring(1))
                    + ", N'" + dpNgayDen.getValue().toString() + "', N'" + sdf.format(date) + "'," + 0 + ")";
            System.out.println(query);

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
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vui lòng thử lại");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Lỗi");
            alert.setContentText("Phòng đã được thuê");
            alert.show();
        }
    }

    @FXML
    void handleButtonSua(ActionEvent event) {
        if (getMP().contains(comBoxMP.getValue())) {
            updateThuePhong();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Lỗi");
            alert.setContentText("Phòng đã được thuê");
            alert.show();
        }
    }

    @FXML
    void handleMouseAction(MouseEvent event) {
        ThuePhong tp = tvThuePhong.getSelectionModel().getSelectedItem();
        if (tp != null) {
            ObservableList<String> getMKHDaTT = getMKHDaTT();
            maThue = tp.getMaThue();
            maPhong = String.valueOf(Integer.parseInt(tp.getMaPhong().substring(1)));
            comBoxMP.getSelectionModel().select(tp.getMaPhong());
            dpNgayDen.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tp.getNgayDen())));
            comBoxMKH.getSelectionModel().select(tp.getMaKH());
            if (getMKHDaTT.contains(tp.getMaKH())) {
                comBoxMP.setDisable(true);
                comBoxMKH.setDisable(true);
                dpNgayDen.setDisable(true);
            } else {
                comBoxMP.setDisable(false);
                comBoxMKH.setDisable(false);
                dpNgayDen.setDisable(false);
            }
        }

    }

    @FXML
    void handleButtonLamMoi(ActionEvent event) {
        comBoxMP.setValue(null);
        comBoxMKH.setValue(null);
        dpNgayDen.setValue(null);
        comBoxMKH.setDisable(false);
        comBoxMKH.setDisable(false);
        dpNgayDen.setDisable(false);
        tvThuePhong.setSelectionModel(null);
    }

    @FXML
    void handleHuyPhong(ActionEvent event) {
        deleteThuePhong();
    }


    public ObservableList<Phong> getRoomList() {
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
            while (rs.next()) {
                rooms = new Phong(rs.getInt("maPhong"), rs.getString("loaiPhong"),
                        rs.getString("trangThai"), rs.getInt("gia"));
                phongList.add(rooms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phongList;
    }

    public ObservableList<String> getMKH() {
        ObservableList<String> listMKH = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maKH FROM dbo.KhachHang";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                listMKH.add("KH" + String.format("%02d", rs.getInt("maKH")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMKH;
    }

    public ObservableList<String> getMKHDaTT() {
        ObservableList<String> listMKHChuaTT = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maKH FROM ThuePhong WHERE thanhToan = 1";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                listMKHChuaTT.add("KH" + String.format("%02d", rs.getInt("maKH")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMKHChuaTT;
    }

    public ObservableList<String> getMP() {
        ObservableList<String> listMKH = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maPhong FROM dbo.Phong WHERE trangThai = 'true'";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                listMKH.add("P" + String.format("%02d", rs.getInt("maPhong")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMKH;
    }

    public void showRooms() {
        ObservableList<Phong> listPhong = getRoomList();
        tcMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        tcLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));
        tcTinhTrang.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        tcGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tvPhong.setItems(listPhong);
    }


    private void updateTrangThaiPhong() {
        String query = "UPDATE Phong SET trangThai = '" + false + "' WHERE maPhong = " + Integer.parseInt(comBoxMP.getSelectionModel().getSelectedItem().substring(1));
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showRooms();
    }


    public void showThuePhong() {
        ObservableList<ThuePhong> listTP = getListThuePhong();
        tcMT.setCellValueFactory(new PropertyValueFactory<>("maThue"));
        tcMKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        tcMP.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        tcNgayDen.setCellValueFactory(new PropertyValueFactory<>("ngayDen"));
        tcThanhToan.setCellValueFactory(new PropertyValueFactory<>("thanhToan"));
        tvThuePhong.setItems(listTP);
    }

    public ObservableList<ThuePhong> getListThuePhong() {
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
            while (rs.next()) {
                thuePhong = new ThuePhong(rs.getString("maThue"), rs.getInt("maKH"), rs.getInt("maPhong"), rs.getDate("ngayDen"), rs.getDate("ngayDi"), rs.getString("thanhToan"));
                thuePhongList.add(thuePhong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thuePhongList;
    }

    public ObservableList<KhachHang> getListKH() {
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
            while (rs.next()) {
                khachHang = new KhachHang(rs.getInt("maKH"), rs.getString("ten"), rs.getDate("ngaySinh"), rs.getString("gioiTinh"), rs.getString("soCMND"), rs.getString("soDT"), rs.getString("queQuan"), rs.getString("quocTich"));
                khachHangList.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachHangList;
    }


    private void updateThuePhong() {
        int cbmp = Integer.parseInt(comBoxMP.getSelectionModel().getSelectedItem().substring(1));
        int cbmk = Integer.parseInt(comBoxMKH.getSelectionModel().getSelectedItem().substring(2));
        if (cbmp != Integer.parseInt(maPhong)) {
            updateTTPKhiDoiPhong(maPhong);
            updateTTPKhiDoiPhong(String.valueOf(cbmp));
        }
        String query = "UPDATE ThuePhong SET maKH = " + cbmk + ", maPhong = "
                + cbmp + ", ngayDen = N'" + dpNgayDen.getValue().toString() + "' " +
                " WHERE maThue = " + maThue;
        System.out.println(query);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        showThuePhong();
    }

    private void updateTTPKhiDoiPhong(String mPhong) {
        String query = "BEGIN\n" +
                "    DECLARE @trangthai BIT\n" +
                "    SELECT @trangthai = trangThai from Phong WHERE maPhong = " + mPhong + "\n" +
                "    IF @trangthai = 'true'\n" +
                "    BEGIN\n" +
                "        UPDATE Phong SET trangThai = 'false' WHERE maPhong = " + mPhong + "\n" +
                "    END\n" +
                "    ELSE\n" +
                "    BEGIN\n" +
                "        UPDATE Phong SET trangThai = 'true' WHERE maPhong = " + mPhong + "\n" +
                "    END\n" +
                "END";
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showRooms();
    }

    private void deleteThuePhong() {
        String query = "DELETE FROM ThuePhong WHERE maThue = " + maThue;
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
            if (maThue != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Hủy phòng thành công");
                alert.setHeaderText("Thông báo");
                updateTrangThaiPhongKhiHuy();
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Lỗi...");
                alert.setContentText("Vui lòng chọn phòng cần hủy");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showThuePhong();
    }

    private void updateTrangThaiPhongKhiHuy() {
        String query = "UPDATE Phong SET trangThai = '" + true + "' WHERE maPhong = " + Integer.parseInt(comBoxMP.getSelectionModel().getSelectedItem().substring(1));
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showRooms();
    }
}
