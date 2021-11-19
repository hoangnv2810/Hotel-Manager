package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KhachHang {
    private String maKH;
    private String ten;
    private Date ngaySinh;
    private String gioiTinh;
    private String soCMND;
    private String soDT;
    private String queQuan;
    private String quocTinh;

    public KhachHang(String maKH, String ten, String ngaySinh, String gioiTinh, String soCMND, String soDT, String queQuan, String quocTinh) throws ParseException {
        this.maKH = maKH;
        this.ten = ten;
        this.ngaySinh = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinh);
        this.gioiTinh = gioiTinh;
        this.soCMND = soCMND;
        this.soDT = soDT;
        this.queQuan = queQuan;
        this.quocTinh = quocTinh;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getQuocTinh() {
        return quocTinh;
    }

    public void setQuocTinh(String quocTinh) {
        this.quocTinh = quocTinh;
    }
}
