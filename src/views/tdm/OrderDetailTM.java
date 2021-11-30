package views.tdm;

import java.math.BigDecimal;

public class OrderDetailTM {
    private String code;
    private String description;
    private String packSize;
    private int qty;
    private double unitPrice;
    private double total;


    public OrderDetailTM() {
    }

    public OrderDetailTM(String code, String description, String packSize, int qty, double unitPrice, double total) {
        this.setCode(code);
        this.setDescription(description);
        this.setPackSize(packSize);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
       // this.setDiscount(discount);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    /*public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }*/

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", qtyOnHand=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
               // ", discount=" + discount +
                '}';
    }
}
