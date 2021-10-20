package Manage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, Date> dobColumn;

    @FXML
    private TableColumn<Customer, Boolean> genderColumn;

    @FXML
    private TableColumn<Customer, String> idCardColumn;

    @FXML
    private TableColumn<Customer, String> numberPhoneColumn;

    @FXML
    private TableColumn<Customer, String> nativePlaceColumn;

    @FXML
    private TableColumn<Customer, String> nationalityColumn;

    private ObservableList<Customer> customersList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customersList = FXCollections.observableArrayList(
                new Customer("1", "Nguyễn Văn Hoàng", "1/1/2001", true, "123456789999", "0888888888", "Hà Nam", "Việt Nam"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<Customer, Date>("dob"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Customer, Boolean>("gender"));
        idCardColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("idCard"));
        numberPhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("numberPhone"));
        nativePlaceColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("nativePlace"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("nationality"));
        customerTable.setItems(customersList);
    }
}
