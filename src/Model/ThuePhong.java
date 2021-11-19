package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThuePhong {
    private String maThue;
    private String maKH;
    private String maPhong;
    private Date ngayDen;
    private Date ngayDi;

    public ThuePhong(String maThue, String maKH, String maPhong, String ngayDen, String ngayDi) throws ParseException {
        this.maThue = maThue;
        this.maKH = maKH;
        this.maPhong = maPhong;
        this.ngayDen = new SimpleDateFormat("dd/MM/yyyy").parse(ngayDen);
        this.ngayDi = new SimpleDateFormat("dd/MM/yyyy").parse(ngayDi);
    }
}
