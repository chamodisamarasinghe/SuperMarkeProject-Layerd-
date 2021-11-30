package entity;

import java.math.BigDecimal;

public class OrderDetail {
    private String code;
    private String orderId;
    private int qty;
    private double unitPrice;


    public OrderDetail() {
    }

    public OrderDetail(String code, String orderId, int qty, double unitPrice) {
        this.setCode(code);
        this.setOrderId(orderId);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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


}
