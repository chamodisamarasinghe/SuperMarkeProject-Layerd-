package entity;

public class NewOrderDetail {
    private String code;
    private int qty;
    private double unitPrice;

    public NewOrderDetail() {
    }

    public NewOrderDetail(String code, int qty, double unitPrice) {
        this.setCode(code);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return "NewOrderDetail{" +
                "code='" + code + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
