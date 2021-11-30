package views.tdm;

public class NewOrderTM {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String time;
    private double total;

    public NewOrderTM() {
    }

    public NewOrderTM(String orderId, String customerId, String orderDate, String time, double total) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setOrderDate(orderDate);
        this.setTime(time);
        this.setTotal(total);
    }

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NewOrderTM{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", time='" + time + '\'' +
                ", total=" + total +
                '}';
    }
}
