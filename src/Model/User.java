package Model;

import java.util.Date;

public class User {
    private String maNV,tenNV;
    private Date ngaySinh;
    private String gioiTinh;
    private String username,password,sdt,email,diaChi;
    private int luong;

    public User(String maNV, String tenNV, Date ngaySinh, String gioiTinh, String username, String password, String sdt, String email, String diaChi,int luong) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.username = username;
        this.password = password;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.luong=luong;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public String getGioiTinh(){ return gioiTinh; }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLuong() {
        return luong;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
