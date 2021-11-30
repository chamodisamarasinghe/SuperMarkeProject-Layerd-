package views.tdm;

import java.math.BigDecimal;

public class ItemTM {
    private String code;
    private String description;
    private String packSize;
    private int qtyOnHand;
    private double unitPrice;

    public ItemTM() {
    }

    public ItemTM(String code, String description, String packSize, int qtyOnHand, double unitPrice) {
        this.setCode(code);
        this.setDescription(description);
        this.setPackSize(packSize);
        this.setQtyOnHand(qtyOnHand);
        this.setUnitPrice(unitPrice);
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
