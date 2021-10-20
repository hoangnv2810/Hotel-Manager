package Manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer {
    private String id;
    private String name;
    private Date dob;
    private Boolean gender;
    private String idCard;
    private String numberPhone;
    private String nativePlace;
    private String nationality;

    public Customer() {
    }

    public Customer(String id, String name, String dob, Boolean gender, String idCard, String numberPhone, String nativePlace, String nationality) throws ParseException {
        this.id = id;
        this.name = name;
        this.dob = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
        this.gender = gender;
        this.idCard = idCard;
        this.numberPhone = numberPhone;
        this.nativePlace = nativePlace;
        this.nationality = nationality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dob);
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender ? "Nam" : "Nữ";
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
