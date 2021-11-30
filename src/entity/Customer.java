package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Customer {
    @Id
    private String customerId;
    private String title;
    private String name;
    private String address;
    private String city;
    private String province;
    private String postalCode;

    public Customer() {
    }

    public Customer(String customerId, String title, String name, String address, String city, String province, String postalCode) {
        this.setCustomerId(customerId);
        this.setTitle(title);
        this.setName(name);
        this.setAddress(address);
        this.setCity(city);
        this.setProvince(province);
        this.setPostalCode(postalCode);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
