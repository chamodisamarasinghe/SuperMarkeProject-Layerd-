package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` (orderId, customerId, orderDate, time , total) VALUES (?,?,?,?,?)", dto.getOrderId(), dto.getCustomerId(), dto.getOrderDate(), dto.getTime(), dto.getTotal());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(Order orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Order search(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE orderId=?", orderId);
        rst.next();
        return new Order(rst.getString("orderId"), rst.getString("customerId"), rst.getString("orderDate"), rst.getString("time"), rst.getDouble("total"));
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean ifOrderExist(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` WHERE orderId=?", orderId);
        return rst.next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1;");
        return rst.next() ? String.format("OD%03d", (Integer.parseInt(rst.getString("orderId").replace("OD", "")) + 1)) : "OD001";
    }
}
