package Controller;

import DBConnection.DBConnection;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageEmployee implements Initializable {

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSua;

    @FXML
    private Button btnThem;

    @FXML
    private Button btnXoa;

    @FXML
    private TextField tfLuong;

    @FXML
    private TableColumn<User, Date> dobColumn;

    @FXML
    private DatePicker dpNgaySinh;

    @FXML
    private TableColumn<User, String> gioiTinhColumn;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> luongColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TextField tfPassword;

    @FXML
    private TableColumn<User, String> phoneNumColumn;

    @FXML
    private RadioButton rbNam;

    @FXML
    private RadioButton rbNu;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPNumber;

    @FXML
    private TextField tfSearch;

    @FXML
    private TextField tfTenNV;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfmaNV;

    @FXML
    private TableView<User> tvEmployeeTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private ToggleGroup gender;

    @FXML
    void handleButtonAction(ActionEvent event) throws ParseException {
        if (event.getSource() == btnThem) {
            insertUser();
        } else if (event.getSource() == btnSua) {
            updateUser();
        } else if (event.getSource() == btnXoa) {
            deleteUser();
        } else if (event.getSource() == btnReset) {
            lamMoi();
        }
    }

    private ObservableList<User> userList;

    public ObservableList<User> getUsersList() {
        userList = FXCollections.observableArrayList();
        DBConnection dbc = new DBConnection();
        Connection conn = dbc.getConnection();
        String query = "SELECT * FROM [User]";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            User user;
            while (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("tenNV"), rs.getDate("ngaySinh"), rs.getString("gioiTinh"), rs.getString("username"), rs.getString("password"), rs.getString("soDT"), rs.getString("email"), rs.getString("diaChi"), rs.getInt("luong"));
                userList.add(user);


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    public void showUser() {
        ObservableList<User> list = getUsersList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        gioiTinhColumn.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        phoneNumColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        luongColumn.setCellValueFactory(new PropertyValueFactory<>("luong"));
        tvEmployeeTable.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showUser();
    }

    private void insertUser() throws ParseException {
        LocalDate date = dpNgaySinh.getValue();
        String s = date.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse(s);
        String d2 = new SimpleDateFormat("yyyy-MM-dd").format(d1);

        String gioitinh = "";
        if (rbNam.isSelected()) gioitinh = "Nam";
        else gioitinh = "Nữ";
        String query = "INSERT INTO [User] VALUES (N'" + tfTenNV.getText() + "', N'" + d2 + "', N'" + gioitinh + "', N'" + tfUsername.getText() + "', N'"
                + tfPassword.getText() + "', N'Nhân viên' , N'" + tfPNumber.getText() + "', N'" + tfEmail.getText() + "', N'" + tfAddress.getText() + "', " + tfLuong.getText() + ")";
        System.out.println(query);
        executeQuery(query);
        showUser();
        dpNgaySinh.setValue(null);
    }

    void lamMoi() {
        tfmaNV.setText(null);
        tfTenNV.setText(null);
        dpNgaySinh.setValue(null);
        tfUsername.setText(null);
        tfPassword.setText(null);

        tfLuong.setText(null);
        tfEmail.setText(null);
        tfPNumber.setText(null);
        tfAddress.setText(null);
    }

    private void updateUser() throws ParseException {
        LocalDate date1 = dpNgaySinh.getValue();
        String s = date1.toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d1 = sdf.parse(s);
        String d2 = new SimpleDateFormat("yyyyMMdd").format(d1);

        String gioitinh = "";
        if (rbNam.isSelected()) gioitinh = "Nam";
        else gioitinh = "Nữ";

        String id = tfmaNV.getText().substring(2);
        int ma = Integer.parseInt(id);
        String query = "UPDATE [User] SET tenNV = N'" + tfTenNV.getText() + "', ngaySinh = N'" + d2 + "', gioiTinh = N'" + gioitinh + "', username = N'" + tfUsername.getText() + "', password = N'"
                + tfPassword.getText() + "', soDT = N'" + tfPNumber.getText() + "', email = N'" + tfEmail.getText() + "', diaChi = N'" + tfAddress.getText() + "', luong=" + tfLuong.getText()
                + " where id =" + ma + "";
        executeQuery(query);
        showUser();
    }

    private void deleteUser() {
        String id = tfmaNV.getText();

        String query = "DELETE FROM [User] WHERE id ='" + Integer.parseInt(id) + "'";
        executeQuery(query);
        showUser();
    }

    private void executeQuery(String query) {
        DBConnection dbc = new DBConnection();
        Connection conn = dbc.getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleMouseAction(MouseEvent event) {
        User user = tvEmployeeTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            tfmaNV.setText("" + user.getMaNV());
            tfTenNV.setText("" + user.getTenNV());
            String date = new SimpleDateFormat("yyyy-MM-dd").format(user.getNgaySinh());
            LocalDate d = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            dpNgaySinh.setValue(d);
            if (user.getGioiTinh().equals("Nam")) {
                rbNam.fire();
            } else {
                rbNu.fire();
            }
            tfUsername.setText(user.getUsername());
            tfPassword.setText(user.getPassword());
            tfPNumber.setText(user.getSdt());
            tfEmail.setText(user.getEmail());
            tfAddress.setText(user.getDiaChi());
            tfLuong.setText("" + user.getLuong());
        }
    }


    // TimKIem
    public void seachKeyReleased(KeyEvent keyEvent) {
        tfSearch.setOnKeyReleased(e -> {
            showInSearchBar();
            showUser();
        });
    }

    public void showInSearchBar() {
        userList.clear();
        if (tfSearch.getText().compareTo("") == 0) {
            addAll();
        } else {
            userList.clear();
            try {
                userList = FXCollections.observableArrayList();
                try {
                    ArrayList<User> p1 = fillCustomer(tfSearch.getText());
                    for (User i : p1) {
                        userList.add(i);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                tvEmployeeTable.setItems(userList);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<User> fillCustomer(String s) throws Exception {
        String sql = "select * from dbo.User as nv where nv.tenNV like '" + s + "%'" +
                "union select * from dbo.User as nv where nv.soDT like '" + s + "%'" +
                "union select * from dbo.User as nv where nv.username like '" + s + "%'";
        DBConnection dbc = new DBConnection();
        ArrayList<User> kh = new ArrayList<>();
        try (Connection cnn = dbc.getConnection();
             PreparedStatement pstm = cnn.prepareStatement(sql);) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("tenNV"), rs.getDate("ngaySinh"), rs.getString("gioiTinh"), rs.getString("username"), rs.getString("password"), rs.getString("soDT"), rs.getString("email"), rs.getString("diaChi"), rs.getInt("luong"));

                kh.add(user);
            }
        }
        return kh;
    }

    private void addAll() {
        userList.clear();
        try {
            ArrayList<User> kh1 = fillAll();
            for (User i : kh1) {
                userList.add(i);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        tvEmployeeTable.setItems(userList);
    }

    public ArrayList<User> fillAll() throws Exception {
        String sql = "select * from dbo.User";
        DBConnection dbc = new DBConnection();
        ArrayList<User> kh = new ArrayList<>();
        try (Connection cnn = dbc.getConnection(); PreparedStatement pstm = cnn.prepareStatement(sql);) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("tenNV"), rs.getDate("ngaySinh"), rs.getString("gioiTinh"), rs.getString("username"), rs.getString("password"), rs.getString("soDT"), rs.getString("email"), rs.getString("diaChi"), rs.getInt("luong"));
            }
        }
        return kh;
    }
}
