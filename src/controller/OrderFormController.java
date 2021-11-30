package controller;

import entity.NewOrder;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.DataBaseAccessCode;
import views.tdm.NewOrderTM;
import views.tdm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class OrderFormController {
    public AnchorPane orderFormContext;
    public TableView<NewOrderTM> tblOrders;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colOrderId;
    public TableColumn colOrderDate;
    public TableColumn colTotalCost;

    public void initialize() {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("total"));


        try {
            loadAllData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblOrders.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        loadDetailsUI(newValue.getOrderId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadDetailsUI(String orderId) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("../views/ItemDetailsForm.fxml"));
        Parent load = fxmlLoader.load();
        ItemDetailsFormController controller = fxmlLoader.getController();
        controller.loadAllData(orderId);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }


    private void loadAllData() throws SQLException, ClassNotFoundException {
        ObservableList<NewOrderTM> tmList = FXCollections.observableArrayList();
        for (NewOrder tempNewOrder : new DataBaseAccessCode().getAllOrders()
        ) {
            tmList.add(
                    new NewOrderTM(tempNewOrder.getCustomerId(),
                            tempNewOrder.getName(),
                            tempNewOrder.getOrderId(),
                            tempNewOrder.getOrderDate(),
                            tempNewOrder.getTotal())
            );
        }

        tblOrders.setItems(tmList);

    }

    public void backToCashierOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/CashierLogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) orderFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
