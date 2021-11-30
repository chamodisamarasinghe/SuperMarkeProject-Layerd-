package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer (customerId,title,name,address,city,province,postalCode) VALUES (?,?,?,?,?,?,?)", dto.getCustomerId(), dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode());
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE customerId=?", customerId);
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET name=?, title=?, address=?, city=?, province=?, postalCode=? WHERE customerId=?", dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode(), dto.getCustomerId());
    }

    @Override
    public Customer search(String customerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE customerId=?", customerId);
        rst.next();
        return new Customer(customerId, rst.getString("title"), rst.getString("name"), rst.getString("address"), rst.getString("city"), rst.getString("province"), rst.getString("postalCode"));
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString("customerId"), rst.getString("title"), rst.getString("name"), rst.getString("address"), rst.getString("city"), rst.getString("province"), rst.getString("postalCode")));

        }
        return allCustomers;
    }


    @Override
    public boolean ifCustomerExist(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT customerId FROM Customer WHERE customerId=?", customerId).next();
    }


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT customerId FROM Customer ORDER BY customerId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("customerId");
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
    }
}
