package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import validation.ValidationUtil;
import views.tdm.ItemTM;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddNewOrderFormController {
    public AnchorPane newOrderContext;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<ItemTM> tblItems;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colQtyHand;
    public TableColumn colUnitPrize;
    public JFXButton btnAddNewItem;

    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    // Pattern itemCodePattern = Pattern.compile("^(I)[0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{2,}$");
    Pattern packSizePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern unitPricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern qtyOnHandPattern = Pattern.compile("^[1-9][0-9]*$");


    public void initialize() {
        storeValidations();

        colCode.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colDescription.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colPackSize.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colQtyHand.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colUnitPrize.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");


        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItems.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        initUI();
        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtPackSize.setText(newValue.getPackSize());
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                // txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                txtCode.setDisable(false);
                txtDescription.setDisable(false);
                txtPackSize.setDisable(false);
                txtQtyOnHand.setDisable(false);
                txtUnitPrice.setDisable(false);
                // txtQtyOnHand.setDisable(false);
            }
        });

        txtQtyOnHand.setOnAction(event -> btnSave.fire());
        loadAllItems();
    }

    private void storeValidations() {
        //  map.put(txtItemCode, itemCodePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtPackSize, packSizePattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQtyOnHand, qtyOnHandPattern);
    }


    private void initUI() {
        txtCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtCode.setDisable(true);
        txtDescription.setDisable(true);
        txtPackSize.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

    }

    private void loadAllItems() {
        tblItems.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItems.getItems().add(new ItemTM(item.getCode(), item.getDescription(), item.getPackSize(), item.getQtyOnHand(), item.getUnitPrice()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void backToManagerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/ManagerLogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) newOrderContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }


    public void btnSave_OnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item
                ItemDTO dto = new ItemDTO(code, description, packSize, qtyOnHand, unitPrice);
                itemBO.addItem(dto);

                tblItems.getItems().add(new ItemTM(code, description, packSize, qtyOnHand, unitPrice));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }
                /*Update Item*/
                ItemDTO dto = new ItemDTO(code, description, packSize, qtyOnHand, unitPrice);
                itemBO.updateItem(dto);

                ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setQtyOnHand(qtyOnHand);
                selectedItem.setUnitPrice(unitPrice);
                tblItems.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        btnAddNewItem.fire();
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(code);
    }


    public void btnDelete_OnAction(ActionEvent actionEvent) {
        /*Delete Item*/
        String code = tblItems.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }

            itemBO.deleteItem(code);
            tblItems.getItems().remove(tblItems.getSelectionModel().getSelectedItem());
            tblItems.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCode.setDisable(false);
        txtDescription.setDisable(false);
        txtPackSize.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtCode.clear();
        txtCode.setText(generateNewId());
        txtDescription.clear();
        txtPackSize.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItems.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return itemBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I001";
    }

    public void moveToDescription(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void moveToPackSize(ActionEvent actionEvent) {
        txtPackSize.requestFocus();
    }

    public void moveToQtyOnHand(ActionEvent actionEvent) {
        txtQtyOnHand.requestFocus();
    }

    public void moveToUnitPrice(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    public void textFieldsKeyReleased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}
