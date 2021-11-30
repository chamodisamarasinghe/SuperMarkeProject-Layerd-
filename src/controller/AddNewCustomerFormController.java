package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import validation.ValidationUtil;
import views.tdm.CustomerTM;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AddNewCustomerFormController {
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public AnchorPane addNewCustomerContext;
    public ComboBox<String> cmbTitle;
    public JFXTextField txtCustomerId;
    public JFXTextField txtTitle;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXButton btnAddNewCustomer;
    public TableView<CustomerTM> tblCustomers;
    public JFXButton btnSave;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public JFXButton btnDelete;
    public TableColumn colTitle;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    //  Pattern customerIDPattern = Pattern.compile("^(C)[0-9]{3,4}$");
    //  Pattern titlePattern = Pattern.compile("^(Miss|Mrs|Mr)(.)?$");
    Pattern namePattern = Pattern.compile("^[A-z ]{2,}$");
    Pattern addressPattern = Pattern.compile("^[A-z ]{3,30}([0-9]{1,2})?$");
    Pattern cityPattern = Pattern.compile("^[A-z ]{3,30}([0-9]{1,2})?$");
    Pattern provincePattern = Pattern.compile("^[A-z ]{2,}$");
    Pattern postalCodePattern = Pattern.compile("^[1-9][0-9]*$");


    public void initialize() {
        storeValidations();

        colCustomerId.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colTitle.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colName.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colAddress.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colCity.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colProvince.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colPostalCode.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");


        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("city"));
        tblCustomers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("province"));
        tblCustomers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        initUI();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);
            if (newValue != null) {
                txtCustomerId.setText(newValue.getCustomerId());
                txtTitle.setText(newValue.getName());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtCity.setText(newValue.getCity());
                txtProvince.setText(newValue.getProvince());
                txtPostalCode.setText(newValue.getPostalCode());
                txtCustomerId.setDisable(false);
                txtTitle.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtCity.setDisable(false);
                txtProvince.setDisable(false);
                txtPostalCode.setDisable(false);

            }
        });
        txtAddress.setOnAction(event -> btnSave.fire());
        loadAllCustomers();
    }

    private void storeValidations() {
        //  map.put(txtCustomerID, customerIDPattern);
        //  map.put(txtTitle, titlePattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtCity, cityPattern);
        map.put(txtProvince, provincePattern);
        map.put(txtPostalCode, postalCodePattern);
    }


    private void initUI() {
        txtCustomerId.clear();
        txtTitle.clear();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtCustomerId.setDisable(true);
        txtTitle.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtCity.setDisable(true);
        txtProvince.setDisable(true);
        txtPostalCode.setDisable(true);
        txtCustomerId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);


        cmbTitle.getItems().addAll("Mr", "Mrs", "Ms");
        cmbTitle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue);
            System.out.println(newValue);
            txtTitle.setText(newValue);
        });


    }

    private void loadAllCustomers() {
        tblCustomers.getItems().clear();

        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();
            for (CustomerDTO customer : allCustomers) {
                tblCustomers.getItems().add(new CustomerTM(customer.getCustomerId(), customer.getTitle(), customer.getName(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void backToManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/MakeCustomerOrder.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addNewCustomerContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void addOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();
        String title = txtTitle.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();
        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {

            try {
                if (existCustomer(customerId)) {
                    new Alert(Alert.AlertType.ERROR, customerId + " already exists").show();
                }
                CustomerDTO customerDTO = new CustomerDTO(customerId, title, name, address, city, province, postalCode);
                customerBO.addCustomer(customerDTO);
                tblCustomers.getItems().add(new CustomerTM(customerId, title, name, address, city, province, postalCode));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {

            try {
                if (!existCustomer(customerId)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + customerId).show();
                }
                CustomerDTO customerDTO = new CustomerDTO(customerId, title, name, address, city, province, postalCode);
                customerBO.updateCustomer(customerDTO);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + customerId + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
            selectedCustomer.setTitle(title);
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setCity(city);
            selectedCustomer.setProvince(province);
            selectedCustomer.setPostalCode(postalCode);
            tblCustomers.refresh();
        }
        btnAddNewCustomer.fire();
    }

    boolean existCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(customerId);
    }


    public void updateOnAction(ActionEvent actionEvent) {

    }

    public void deleteOnAction(ActionEvent actionEvent) {

        String customerId = tblCustomers.getSelectionModel().getSelectedItem().getCustomerId();
        try {
            if (!existCustomer(customerId)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + customerId).show();
            }
            customerBO.deleteCustomer(customerId);
            tblCustomers.getItems().remove(tblCustomers.getSelectionModel().getSelectedItem());
            tblCustomers.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + customerId).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        txtTitle.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtCity.setDisable(false);
        txtProvince.setDisable(false);
        txtPostalCode.setDisable(false);
        txtCustomerId.clear();
        txtCustomerId.setText(generateNewId());
        txtTitle.clear();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomers.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return customerBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblCustomers.getItems().isEmpty()) {
            return "C001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        }
    }

    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomers.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCustomerId();
    }

    public void moveToTitle(ActionEvent actionEvent) {
        txtTitle.requestFocus();
    }

    public void moveToName(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void moveToTheAddress(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void moveToTheCity(ActionEvent actionEvent) {
        txtCity.requestFocus();
    }

    public void moveToTheProvince(ActionEvent actionEvent) {
        txtProvince.requestFocus();
    }

    public void moveToThePostalCode(ActionEvent actionEvent) {
        txtPostalCode.requestFocus();
    }

    public void textFieldsKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }
}
