package entity;

public class NewOrder {
    private String customerId;
    private String name;
    private String orderId;
    private String orderDate;
    private double total;

    public NewOrder() {
    }

    public NewOrder(String customerId, String name, String orderId, String orderDate, double total) {
        this.setCustomerId(customerId);
        this.setName(name);
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setTotal(total);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NewOrder{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", total=" + total +
                '}';
    }
}
