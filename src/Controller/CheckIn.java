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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CheckIn implements Initializable {
    @FXML
    private DatePicker dpNgayDen;

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
    private TableColumn<ThuePhong, String> tcThanhToan;

    @FXML
    private TableColumn<ThuePhong, Float> tcTienCoc;

    @FXML
    private TextField tfTienCoc;

    private String maThue;
    private String maPhong;
    ObservableList<ThuePhong> listThuePhong;
    ObservableList<Phong> listPhong;
    ObservableList<String> listMKH;
    ObservableList<String> listMPTrong;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listThuePhong = FXCollections.observableArrayList();
        listPhong = FXCollections.observableArrayList();
        listMKH = FXCollections.observableArrayList();
        listMPTrong = FXCollections.observableArrayList();
        tvThuePhong.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvPhong.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        dpNgayDen.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
//        tfTienCoc.setText("0");
        tfTienCoc.setFocusTraversable(false);
        setCellValueThuePhong();
        setCellValueRoom();
        showMPTrong();
        showMKH();
        showPhong();
        showThuePhong();
    }


    @FXML
    void gotoInforCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../View/CustomerInfo.fxml"));
        StackPane secondLayout = new StackPane();
        secondLayout.getChildren().add(root);

        Scene secondScene = new Scene(secondLayout);

        Stage newWindow = new Stage();
        newWindow.setTitle("Thông tin tài khoản");
        newWindow.setScene(secondScene);
        newWindow.getIcons().add(new Image("Images/icons8-user-48.png"));
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);

        newWindow.setX(stage.getX() + 50);
        newWindow.setY(stage.getY() + 100);
        newWindow.show();
    }

    @FXML
    void handleInsertDP(ActionEvent event) {
        datPhong(event);
        handleButtonLamMoi(event);
    }

    private void datPhong(ActionEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (listMPTrong.contains(comBoxMP.getValue())) {
            if (!getMKHDaTT().contains(comBoxMKH.getValue())) {
                String query = "INSERT INTO HotelManager.dbo.ThuePhong VALUES ( "
                        + Integer.parseInt(comBoxMKH.getSelectionModel().getSelectedItem().substring(2))
                        + ", '" + comBoxMP.getValue()
                        + "', N'" + dpNgayDen.getValue().toString() + "', N'" + sdf.format(date) + "'," + 0 + "," + tfTienCoc.getText() + ")";
                System.out.println(query);

                DBConnection dbc = new DBConnection();
                Connection cn = dbc.getConnection();
                try {
                    Statement st = cn.createStatement();
                    st.executeUpdate(query);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Thông báo");
                    alert.setContentText("Đặt phòng thành công");
                    alert.show();
                    updateTrangThaiPhong();
                    showThuePhong();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Vui lòng thử lại");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Khách hàng đã thanh toán");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng thử lại");
            alert.show();
        }
    }

    @FXML
    void handleButtonSua(ActionEvent event) {
        updateThuePhong();
        handleButtonLamMoi(event);
    }

    @FXML
    void handleMouseAction(MouseEvent event) {
        ThuePhong tp = tvThuePhong.getSelectionModel().getSelectedItem();
        if (tp != null) {
            ObservableList<String> getMKHDaTT = getMKHDaTT();
            maThue = tp.getMaThue();
            maPhong = tp.getMaPhong();
            comBoxMP.getSelectionModel().select(tp.getMaPhong());
            dpNgayDen.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tp.getNgayDen())));
            comBoxMKH.getSelectionModel().select(tp.getMaKH());
            tfTienCoc.setText(String.valueOf(tp.getTienCoc()));
            if (getMKHDaTT.contains(tp.getMaKH())) {
                comBoxMP.setDisable(true);
                comBoxMKH.setDisable(true);
                dpNgayDen.setDisable(true);
                tfTienCoc.setDisable(true);
            } else {
                comBoxMP.setDisable(false);
                comBoxMKH.setDisable(false);
                dpNgayDen.setDisable(false);
                tfTienCoc.setDisable(false);
            }
        }

    }

    @FXML
    void handleButtonLamMoi(ActionEvent event) {
        refesh(event);
    }

    private void refesh(ActionEvent event) {
        comBoxMP.setValue(null);
        comBoxMKH.setValue(null);
        dpNgayDen.setValue(null);
        tfTienCoc.setText(null);
        comBoxMKH.setDisable(false);
        comBoxMP.setDisable(false);
        dpNgayDen.setDisable(false);
        tfTienCoc.setDisable(false);
        showThuePhong();
        showPhong();
        showMPTrong();
        showMKH();
    }

    @FXML
    void handleHuyPhong(ActionEvent event) {
        deleteThuePhong();
        handleButtonLamMoi(event);
    }


    public ArrayList<Phong> getListRoom() {
        ArrayList<Phong> listPhong = new ArrayList<>();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT* FROM dbo.Phong";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listPhong.add(new Phong(rs.getString("maPhong"), rs.getString("loaiPhong"), rs.getString("trangThai"), rs.getInt("gia")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPhong;
    }

    public ArrayList<String> getMKH() {
        ArrayList<String> listMK = new ArrayList<>();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT kh.maKH FROM KhachHang kh LEFT JOIN ThuePhong TP on kh.maKH = TP.maKH WHERE TP.thanhToan = 'false' OR tp.thanhToan IS NULL";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listMK.add("KH" + String.format("%02d", rs.getInt("maKH")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMK;
    }

    public void showMKH() {
        listMKH.clear();
        ArrayList<String> listMK = getMKH();
        for (String s : listMK) {
            listMKH.add(s);
        }
        comBoxMKH.setItems(listMKH);
    }

    public ObservableList<String> getMKHDaTT() {
        ObservableList<String> listMKHDaTT = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maKH FROM ThuePhong WHERE thanhToan = 1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listMKHDaTT.add("KH" + String.format("%02d", rs.getInt("maKH")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMKHDaTT;
    }

    public ArrayList<String> getMPTrong() {
        ArrayList<String> listMPTrong = new ArrayList<>();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maPhong FROM dbo.Phong WHERE trangThai = 'true'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listMPTrong.add(rs.getString("maPhong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMPTrong;
    }

    public void showMPTrong() {
        listMPTrong.clear();
        ArrayList<String> listMP = getMPTrong();
        for (String s : listMP) {
            listMPTrong.add(s);
        }
        comBoxMP.setItems(listMPTrong);
    }

    public void showPhong() {
        listPhong.clear();
        ArrayList<Phong> listRoom = getListRoom();
        for (Phong p : listRoom) {
            listPhong.add(p);
        }
        tvPhong.setItems(listPhong);
    }

    private void setCellValueRoom() {
        tcMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        tcLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));
        tcTinhTrang.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        tcGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
    }


    private void updateTrangThaiPhong() {
        String query = "UPDATE Phong SET trangThai = '" + false + "' WHERE maPhong = '" + comBoxMP.getValue() + "'";
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showPhong();
    }

    private void setCellValueThuePhong() {
        tcMT.setCellValueFactory(new PropertyValueFactory<>("maThue"));
        tcMKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        tcMP.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
        tcNgayDen.setCellValueFactory(new PropertyValueFactory<>("ngayDen"));
        tcTienCoc.setCellValueFactory(new PropertyValueFactory<>("tienCoc"));
        tcThanhToan.setCellValueFactory(new PropertyValueFactory<>("thanhToan"));
    }

    public ArrayList<ThuePhong> getListThuePhong() {
        DBConnection dbc = new DBConnection();
        ArrayList<ThuePhong> listTP = new ArrayList<>();
        Connection cn = dbc.getConnection();
        String query = "SELECT * FROM ThuePhong ORDER BY maThue DESC";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listTP.add(new ThuePhong(rs.getString("maThue"), rs.getInt("maKH"), rs.getString("maPhong"), rs.getDate("ngayDen"), rs.getDate("ngayDi"), rs.getString("thanhToan"), rs.getInt("tienCoc")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTP;
    }

    public void showThuePhong() {
        listThuePhong.clear();
        ArrayList<ThuePhong> listTP = getListThuePhong();
        for (ThuePhong tp : listTP) {
            listThuePhong.add(tp);
        }
        tvThuePhong.setItems(listThuePhong);
    }


    private void updateThuePhong() {
        try {
            String cbmp = comBoxMP.getValue();
            int cbmk = Integer.parseInt(comBoxMKH.getSelectionModel().getSelectedItem().substring(2));
            String query = "UPDATE ThuePhong SET maKH = " + cbmk + ", maPhong = '"
                    + cbmp + "', ngayDen = N'" + dpNgayDen.getValue().toString() + "' , tienCoc = " + tfTienCoc.getText() +
                    " WHERE maThue = " + maThue;
            if (cbmp.compareTo(maPhong) != 0) {
                doiPhong(maPhong);
                doiPhong(cbmp);
            }
            DBConnection dbc = new DBConnection();
            Connection cn = dbc.getConnection();
            try {
                Statement st = cn.createStatement();
                st.executeUpdate(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Cập nhật thành công");
                alert.setHeaderText("Thông báo");
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            showThuePhong();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn mã");
            alert.show();
        }
    }

    // Cập nhật trạng thái phòng khỉ đổi phòng
    private void doiPhong(String mPhong) {
        String query = "BEGIN\n" +
                "    DECLARE @trangthai BIT\n" +
                "    SELECT @trangthai = trangThai from Phong WHERE maPhong = '" + mPhong + "'\n" +
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
        try {
            Statement st = cn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showPhong();
    }

    private void deleteThuePhong() {
        String query = "DELETE FROM ThuePhong WHERE maThue = " + maThue;
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        try {
            Statement st = cn.createStatement();
            st.executeUpdate(query);
            if (maThue != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Hủy phòng thành công");
                alert.setHeaderText("Thông báo");
                updateTrangThaiPhongKhiHuy();
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Lỗi");
                alert.setContentText("Vui lòng chọn phòng cần hủy");
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Lỗi");
            alert.setContentText("Không thể xóa phòng đã thanh toán");
            alert.show();
        }
        showMPTrong();
    }

    private void updateTrangThaiPhongKhiHuy() {
        String query = "UPDATE Phong SET trangThai = '" + true + "' WHERE maPhong = '" + comBoxMP.getValue() + "'";
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        Statement st;
        try {
            st = cn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
