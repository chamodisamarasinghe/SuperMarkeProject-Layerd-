package util;

import db.DbConnection;
import entity.NewOrder;
import entity.NewOrderDetail;
import entity.Order;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseAccessCode {
    public ArrayList<NewOrder> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<NewOrder> list = new ArrayList();
        ResultSet resultSet = DbConnection.getInstance().getConnection().
                prepareStatement
                        ("SELECT c.customerId, c.name, o.orderId, o.orderDate,o.total FROM Customer c JOIN `Order` o ON o.customerId=c.customerId")
                .executeQuery();
        while (resultSet.next()) {
            list.add(
                    new NewOrder(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5)
                    )
            );
        }
        return list;

    }




        public ArrayList<NewOrderDetail> getAllOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
            ArrayList<NewOrderDetail> details = new ArrayList<>();
            ResultSet resultSet = DbConnection.getInstance().getConnection()
                    .prepareStatement("SELECT code, qty, unitPrice FROM `Order Detail` WHERE orderId='"
                            + orderId + "'").executeQuery();
            while (resultSet.next()) {
                details.add(
                        new NewOrderDetail(
                                resultSet.getString(1),
                                resultSet.getInt(2),
                                resultSet.getDouble(3)
                        )
                );
            }
            return details;

    }



}
