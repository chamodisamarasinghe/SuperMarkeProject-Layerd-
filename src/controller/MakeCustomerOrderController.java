package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import dao.custom.ItemDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.OrderDetail;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import views.tdm.ItemTM;
import views.tdm.OrderDetailTM;
import views.tdm.OrderTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MakeCustomerOrderController {

    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASE_ORDER);

    public Label lblDate;
    public Label lblTime;
    public AnchorPane makeCustomerOrderContext;
    public ComboBox<String> cmbCustomerId;
    public TextField txtTitle;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public ComboBox<String> cmbCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrize;
    public TextField txtQty;
    public TableView<OrderDetailTM> tblOrderDetails;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrize;
    public TableColumn colTotal;
    public JFXButton btnSave;
    public JFXButton btnPlaceOrder;
    public Label lblTotal;
    public Label lblOrderId;
    public Label lblDiscount;
    public TableColumn colQty;
    public Label lblTotal1;
    private String orderId;
    double total;

    public void initialize() {

        colItemCode.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colDescription.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colPackSize.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colQty.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colUnitPrize.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colTotal.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");


        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        // tblOrderDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("discount"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrderDetails.getColumns().get(6);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblOrderId.setText(generateNewOrderId());
        lblDate.setText(LocalDate.now().toString());
        lblTime.setText(LocalTime.now().toString());
        btnPlaceOrder.setDisable(true);
        txtTitle.setFocusTraversable(false);
        txtTitle.setEditable(false);
        txtName.setFocusTraversable(false);
        txtName.setEditable(false);
        txtAddress.setFocusTraversable(false);
        txtAddress.setEditable(false);
        txtCity.setFocusTraversable(false);
        txtCity.setEditable(false);
        txtProvince.setFocusTraversable(false);
        txtProvince.setEditable(false);
        txtPostalCode.setFocusTraversable(false);
        txtPostalCode.setEditable(false);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtPackSize.setFocusTraversable(false);
        txtPackSize.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtUnitPrize.setFocusTraversable(false);
        txtUnitPrize.setEditable(false);
        txtQty.setOnAction(event -> btnSave.fire());
        txtQty.setEditable(false);
        btnSave.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {

                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }

                        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(newValue + "");
                        txtTitle.setText(customerDTO.toString());
                        txtName.setText(customerDTO.getName());
                        txtAddress.setText(customerDTO.getAddress());
                        txtCity.setText(customerDTO.getCity());
                        txtProvince.setText(customerDTO.getProvince());
                        txtPostalCode.setText(customerDTO.getPostalCode());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtName.clear();
            }
        });


        cmbCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {
                try {
                    if (!existItem(newItemCode + "")) {

                    }

                    ItemDTO item = purchaseOrderBO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtPackSize.setText(item.getPackSize());
                   // txtQtyOnHand.setText(item.getQtyOnHand());
                     txtUnitPrize.setText(String.valueOf(item.getUnitPrice()));
                    Optional<OrderDetailTM> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                     txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtPackSize.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrize.clear();
            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                 cmbCode.setDisable(true);
                 cmbCode.setValue(selectedOrderDetail.getCode());
                btnSave.setText("Update");
                 txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                 txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnSave.setText("Add");
                cmbCode.setDisable(false);
                cmbCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }
        });
        loadAllCustomerIds();
        loadAllItemCodes();




    }




    private boolean existCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifCustomerExist(customerId);
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifItemExist(code);
    }

    private String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));

    }

    private void calculateTotal() {
        double total = 0;
        for (OrderDetailTM detail : tblOrderDetails.getItems()) {
            total += detail.getTotal();
        }
        lblTotal.setText(total + "");
    }
    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbCode.getItems().add(dto.getCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getCustomerId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void openAddNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/AddNewCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) makeCustomerOrderContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void backToCashier(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/CashierLogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) makeCustomerOrderContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrize.getText());
        // int qty = Integer.parseInt(txtQty.getText());
        double total = qty * unitPrice;

        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnSave.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrderDetails.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = qty * unitPrice;
                orderDetailTM.setTotal(total);
            }
            tblOrderDetails.refresh();
        } else {
            tblOrderDetails.getItems().add(new OrderDetailTM(itemCode, description, packSize, qty, unitPrice, total));
        }
        cmbCode.getSelectionModel().clearSelection();
        cmbCode.requestFocus();
        calculateTotal();
            enableOrDisablePlaceOrderButton();
        }



        public void btnPlaceOrder_OnAction (ActionEvent actionEvent){
            boolean b = saveOrder( orderId, cmbCustomerId.getValue(),lblDate.getText(), lblTime.getText(), Double.parseDouble(lblTotal.getText()),
                    tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(tm.getCode(),orderId, tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
            }


            orderId = generateNewOrderId();
            lblOrderId.setText(generateNewOrderId());
            calculateTotal();
            cmbCustomerId.getSelectionModel().clearSelection();
            cmbCode.getSelectionModel().clearSelection();
            tblOrderDetails.getItems().clear();
            txtQty.clear();
            calculateDiscount();


        }

    private void calculateDiscount() {

    }

    private boolean saveOrder(String orderId, String customerId,String orderDate,String time, double total, List<OrderDetailDTO> orderDetail) {
        try {
            OrderDTO orderDTO = new OrderDTO(orderId, customerId, orderDate, time, total, orderDetail);
            return purchaseOrderBO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void printBillJasperEvent(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/views/reports/CustomerBill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            ObservableList<OrderDetailTM> products = tblOrderDetails.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(products.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void viewOrdersOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/views/reports/ViewOrders.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            ObservableList<OrderDetailTM> products = tblOrderDetails.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(products.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}

