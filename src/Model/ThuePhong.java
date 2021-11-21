package Model;

import java.text.ParseException;
import java.util.Date;

public class ThuePhong {
    private String maThue;
    private String maKH;
    private String maPhong;
    private Date ngayDen;
    private Date ngayDi;

    public ThuePhong(String maThue, String maKH, String maPhong, Date ngayDen, Date ngayDi) throws ParseException {
        this.maThue = maThue;
        this.maKH = maKH;
        this.maPhong = maPhong;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
    }

    public String getMaThue() {
        return maThue;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public Date getNgayDen() {
        return ngayDen;
    }

    public Date getNgayDi() {
        return ngayDi;
    }
}
