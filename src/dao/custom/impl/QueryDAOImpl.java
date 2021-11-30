package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import dto.CustomDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO> getOrderDetailsFromOrderID(String oid) throws SQLException, ClassNotFoundException {
        ArrayList<CustomDTO> allData= new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("select o.orderId,o.orderDate,o.customerId,od.oid,od.itemCode,od.qty,od.unitPrice from Orders o inner join OrderDetails od on o.oid=od.oid where o.oid=?;", oid);
        while (rst.next()) {
            allData.add(new CustomDTO(rst.getString("orderId"), LocalDate.parse(rst.getString("date")), rst.getString("customerId"), rst.getString("code"), rst.getInt("qty"), rst.getDouble("unitPrice")));
        }
        return allData;
    }
}
