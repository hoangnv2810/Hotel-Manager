package Controller;

import DBConnection.DBConnection;
import Model.KhachHang;
import Model.Phong;
import Model.ThuePhong;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class CheckOut implements Initializable {
    @FXML
    private ComboBox<String> cbMaThue;

    @FXML
    private TextField tfMaHoaDon;

    @FXML
    private TextField tfMaKhachHang;

    @FXML
    private TextField tfMaPhong;

    @FXML
    private Label lbNgayDen;

    @FXML
    private Label lbNgayDi;

    @FXML
    private Label lbSoNgay;

    @FXML
    private Label lbNgaySinh;

    @FXML
    private Label lbQueQuan;

    @FXML
    private Label lbQuocTich;

    @FXML
    private Label lbSoCMND;

    @FXML
    private Label lbSoDT;

    @FXML
    private Label lbTenKH;

    @FXML
    private Label lbGioiTinh;

    @FXML
    private Label lbLoaiPhong;

    @FXML
    private Label lbTienPhong;

    @FXML
    private Label lbTienCoc;

    @FXML
    private Label lbTongTien;

    ObservableList<String> listMT;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listMT = FXCollections.observableArrayList();
        tfMaHoaDon.setText(getMHD());
        cbMaThue.setVisibleRowCount(2);
        showMT();
    }

    @FXML
    void handleActionCb(ActionEvent event) throws ParseException {
        String maThue = cbMaThue.getValue();
        tfMaHoaDon.setText(getMHD());

        ThuePhong tp = getTPbyID(maThue);
        String maKH = tp.getMaKH();
        String maPhong = tp.getMaPhong();
        String ngayDen = String.valueOf(tp.getNgayDen());
        String ngayDi = getDateNow();
        lbNgayDen.setText(ngayDen);
        lbNgayDi.setText(ngayDi);
        lbSoNgay.setText(String.valueOf(soNgay(ngayDen, ngayDi)));
        lbTienCoc.setText(formatVND(String.valueOf(tp.getTienCoc())));


        KhachHang kh = getKH(maKH);
        tfMaKhachHang.setText(maKH);
        lbTenKH.setText(kh.getTen());
        lbGioiTinh.setText(kh.getGioiTinh());
        lbSoCMND.setText(kh.getSoCMND());
        lbNgaySinh.setText(String.valueOf(kh.getNgaySinh()));
        lbQueQuan.setText(kh.getQueQuan());
        lbQuocTich.setText(kh.getQuocTich());
        lbSoDT.setText(kh.getSoDT());

        Phong p = getPhong(maPhong);
        tfMaPhong.setText(p.getMaPhong());
        lbLoaiPhong.setText(p.getLoaiPhong());
        lbTienPhong.setText(formatVND(String.valueOf(p.getGia())));

        lbTongTien.setText(formatVND(tongTien(p.getGia(), soNgay(ngayDen, ngayDi), tp.getTienCoc())));
    }

    // Lấy thuê phong theo ma
    private ThuePhong getTPbyID(String maThue){
        for (ThuePhong tp:getThuePhong()){
            if(tp.getMaThue().compareTo(maThue) == 0){
                return tp;
            }
        }
        return new ThuePhong();
    }


    // lấy mã thuê chư thanh toan
    private ArrayList<String> getMT(){
        ArrayList<ThuePhong> listTP = getThuePhong();
        ArrayList<String> listMT = new ArrayList<>();
        for(ThuePhong tp:listTP){
            listMT.add(tp.getMaThue());
        }
        return listMT;
    }

    private void showMT() {
        listMT.clear();
        for (String s : getMT()) {
            listMT.add(s);
        }
        cbMaThue.setItems(listMT);
    }

    private String getDateNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }


    private String getMHD() {
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT MAX(maHoaDon) FROM HoaDon";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                return "HD" + String.format("%02d", rs.getInt(1) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private KhachHang getKH(String maKH){
        String query = "SELECT * FROM KhachHang WHERE maKH = " + Integer.parseInt(maKH.substring(2));
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                return new KhachHang(rs.getInt("maKH"), rs.getString("ten"),
                        rs.getDate("ngaySinh"), rs.getString("gioiTinh"),
                        rs.getString("soCMND"), rs.getString("soDT"), rs.getString("queQuan"),
                        rs.getString("quocTich"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new KhachHang();
    }

    private Phong getPhong(String maPhong){
        String query = "SELECT * FROM Phong WHERE maPhong = '" + maPhong + "'";
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                return new Phong(rs.getString("maPhong"), rs.getString("loaiPhong"), rs.getString("trangThai"), rs.getInt("gia"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Phong();
    }


    private ArrayList<ThuePhong> getThuePhong() {
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        ArrayList<ThuePhong> listTP = new ArrayList<>();
        String query = "SELECT * FROM ThuePhong WHERE thanhToan = 0";
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

    private int soNgay(String ngayDen, String ngayDi) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ngayDen);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(ngayDi);
        long diff = date2.getTime() - date1.getTime();
        if ((diff / (1000 * 60 * 60 * 24)) == 0) {
            return 1;
        }
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    private String tongTien(int donGia, int soNgay, int tienCoc) {
        return String.valueOf(donGia * soNgay - tienCoc);
    }

    private String formatVND(String tien) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(Integer.parseInt(tien));
    }

    @FXML
    void handelBtThanhToan(ActionEvent event) throws ParseException {
        String maThue = cbMaThue.getSelectionModel().getSelectedItem();
        if (maThue != null) {
            String query = "INSERT INTO HotelManager.dbo.HoaDon VALUES (" + maThue + "," + tongTien(getPhong(tfMaPhong.getText()).getGia(), soNgay(String.valueOf(getTPbyID(maThue).getNgayDen()), getDateNow()),getTPbyID(maThue).getTienCoc()) + ", GETDATE())";
            ObservableList<String> listMTHD = getMTHoaDon();
            DBConnection dbc = new DBConnection();
            Connection cn = dbc.getConnection();
            Statement st;
            try {
                if (!listMTHD.contains(maThue)) {
                    st = cn.createStatement();
                    st.executeUpdate(query);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    updateNgayDi();
                    updateTTP();
                    alert.setHeaderText(null);
                    alert.setTitle("Thông báo");
                    alert.setContentText("Thanh toán thành công");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    updateNgayDi();
                    alert.setHeaderText(null);
                    alert.setTitle("Cảnh báo");
                    alert.setContentText("Mã thuê đã thanh toán");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Lỗi");
                alert.setContentText("Vui lòng thử lại");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Cảnh báo");
            alert.setContentText("Vui lòng chọn mã thuê");
            alert.show();
        }

    }

    private ObservableList<String> getMTHoaDon() {
        ObservableList<String> listMTHoaDon = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection cn = dbc.getConnection();
        String query = "SELECT maThue FROM HoaDon";
        Statement st;
        ResultSet rs;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                listMTHoaDon.add(rs.getString("maThue"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMTHoaDon;
    }

    private void updateNgayDi() {
        String query = "UPDATE ThuePhong SET ngayDi = GETDATE(), thanhToan = 1 WHERE maThue = " + cbMaThue.getSelectionModel().getSelectedItem();
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

    private void updateTTP() {
        String query = "UPDATE Phong SET trangThai = 1 WHERE maPhong = '" + tfMaPhong.getText() + "'";
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
