package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CashierLogInFormController {
    public AnchorPane cashierFormContext;

    public void backToHome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) cashierFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openManageOrdersForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/PlaceOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) cashierFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openMakeCustomerOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/MakeCustomerOrder.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) cashierFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openViewOrders(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/OrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) cashierFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
