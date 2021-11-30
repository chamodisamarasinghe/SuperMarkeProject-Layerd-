package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO{

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCustomerId(), customer.getTitle(), customer.getName(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getCustomerId(), dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustomerId(), dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode()));
    }


    @Override
    public boolean ifCustomerExist(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(customerId);
    }


    @Override
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }
}
