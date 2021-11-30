package dto;

import javafx.scene.control.Label;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OrderDTO implements Serializable {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String time;
    private double total;
    private List<OrderDetailDTO> orderDetail;

    public OrderDTO(String orderId, String customerId, String orderDate, String time, double total, List<OrderDetailDTO> orderDetail) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.time = time;
        this.total = total;
        this.orderDetail=orderDetail;
    }


//    public OrderDTO(String orderId, String customerId, LocalDate orderDate, LocalTime time, Label total, List<OrderDetailDTO> orderDetail) {
//    }

//    public OrderDTO(String orderId, String customerId, String orderDate, String time, BigDecimal total) {
//        this.setOrderId(orderId);
//        this.setCustomerId(customerId);
//        this.setOrderDate(orderDate);
//        this.setTime(time);
//        this.setTotal(total);
//    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public  double getTotal() {
        return total;
    }

    public void setTotal( double total) {
        this.total = total;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
