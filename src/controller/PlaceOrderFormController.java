package controller;

import bo.BoFactory;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import views.tdm.ItemTM;
import views.tdm.OrderDetailTM;

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

public class PlaceOrderFormController {

    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASE_ORDER);


    public AnchorPane placeOrderContext;
    public Label lblDate;
    public Label lblTime;
    public Label lblOrderId;
    public ComboBox cmbCustomerId;
    public JFXButton btnSave;
    public TableView<OrderDetailTM> tblOrderDetails;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrize;
    public TableColumn colQty;
    public TableColumn colTotal;
    public JFXButton btnPlaceOrder;
    public ComboBox<String> cmbItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrize;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public Label lblTotal;
    private String orderId;

    public void initialize() {
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
        lblOrderId.setText("Order ID: " + orderId);
        lblDate.setText(LocalDate.now().toString());
        lblTime.setText(LocalTime.now().toString());
        btnPlaceOrder.setDisable(true);
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

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {
                try {
                    if (!existItem(newItemCode + "")) {
                        //throw new NotFoundException("There is no such item associated with the id " + code);
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
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getCode());
                btnSave.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnSave.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }
        });




        loadAllCustomerIds();
        loadAllItemCodes();

        
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


    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getCode());
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




    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbItemCode.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));

   }

    private void calculateTotal() {
        double total=0;
        for (OrderDetailTM detail : tblOrderDetails.getItems()) {
            total = detail.getTotal();
        }
        lblTotal.setText("Total: " + total);
    }

    public void backToCashierOnAction (ActionEvent actionEvent) throws IOException {
            URL resource = getClass().getResource("../views/CashierLogInForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) placeOrderContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }

        public void btnAdd_OnAction (ActionEvent actionEvent){
            if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                    Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
                new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
                txtQty.requestFocus();
                txtQty.selectAll();
                return;
            }

            String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
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
                    total =qty * unitPrice;
                    orderDetailTM.setTotal(total);
                }
                tblOrderDetails.refresh();
            } else {
                tblOrderDetails.getItems().add(new OrderDetailTM(itemCode, description, packSize, qty, unitPrice, total));
            }
            cmbItemCode.getSelectionModel().clearSelection();
            cmbItemCode.requestFocus();
            calculateTotal();
            enableOrDisablePlaceOrderButton();
        }

        public void btnPlaceOrder_OnAction (ActionEvent actionEvent){
//            boolean b = saveOrder(orderId, (String) cmbCustomerId.getValue(),lblDate.getText(), lblTime.getText(), Double.parseDouble(lblTotal.getText()),
//                    tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(tm.getCode(),orderId, tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

            boolean b = saveOrder(lblOrderId.getText(),String.valueOf(cmbCustomerId.getValue()),lblDate.getText(),lblTime.getText(),
                    Double.parseDouble(lblTotal.getText()),
                    tblOrderDetails.getItems().stream().map(tm ->
                            new OrderDetailDTO(tm.getCode(), orderId, tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
            }

            orderId = generateNewOrderId();
            lblOrderId.setText(generateNewOrderId());
            cmbCustomerId.getSelectionModel().clearSelection();
            cmbItemCode.getSelectionModel().clearSelection();
            tblOrderDetails.getItems().clear();
            txtQty.clear();
            calculateTotal();
        }

    private boolean saveOrder(String orderId, String customerId, String orderDate, String time, double total, List<OrderDetailDTO> orderDetail) {
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

    }


