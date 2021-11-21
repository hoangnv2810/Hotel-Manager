package Model;

import java.util.Date;

public class KhachHang {
    private String maKH;
    private String ten;
    private Date ngaySinh;
    private String gioiTinh;
    private String soCMND;
    private String soDT;
    private String queQuan;
    private String quocTich;

    public KhachHang() {
    }

    public KhachHang(String maKH, String ten, Date ngaySinh, String gioiTinh, String soCMND, String soDT, String queQuan, String quocTich) {
        this.maKH = maKH;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soCMND = soCMND;
        this.soDT = soDT;
        this.queQuan = queQuan;
        this.quocTich = this.quocTich;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getTen() {
        return ten;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public String getSoDT() {
        return soDT;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public String getQuocTich() {
        return quocTich;
    }
}
