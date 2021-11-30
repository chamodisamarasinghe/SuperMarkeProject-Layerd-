package dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomDTO {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;
    private String itemCode;
    private int qty;
    private double unitPrice;

    public CustomDTO() {
    }

    public CustomDTO(String orderId, LocalDate orderDate, String customerId, String itemCode, int qty, double unitPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CustomDTO{");
        sb.append("orderId='").append(orderId).append('\'');
        sb.append(", orderDate=").append(orderDate);
        sb.append(", customerId='").append(customerId).append('\'');
        sb.append(", itemCode='").append(itemCode).append('\'');
        sb.append(", qty=").append(qty);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append('}');
        return sb.toString();
    }
}
