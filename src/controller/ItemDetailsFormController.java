package controller;

import entity.NewOrderDetail;
import entity.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.DataBaseAccessCode;
import views.tdm.OrderDetailTM;

import java.sql.SQLException;

public class ItemDetailsFormController {
    public AnchorPane ItemDetailsContext;
    public Label lblOrderId;
    public TableView tblItemDetails;
    public TableColumn colUnitPrice;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public Label lblTotal;


    public void initialize(){
        colItemCode.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colQty.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");
        colUnitPrice.setStyle("-fx-border-color: black;-fx-table-cell-border-color:black;");



        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    public void loadAllData(String orderId){
        lblOrderId.setText(orderId);
        double total=0;
        try {
            ObservableList<NewOrderDetail> tmList = FXCollections.observableArrayList();
            for (NewOrderDetail tempD : new DataBaseAccessCode().getAllOrderDetails(orderId)
            ) {
                total+=tempD.getUnitPrice();
                tmList.add(new NewOrderDetail(
                        tempD.getCode(), tempD.getQty(), tempD.getUnitPrice()));
            }
            tblItemDetails.setItems(tmList);
            lblTotal.setText(total+"/=");

            new DataBaseAccessCode().getAllOrderDetails(orderId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
