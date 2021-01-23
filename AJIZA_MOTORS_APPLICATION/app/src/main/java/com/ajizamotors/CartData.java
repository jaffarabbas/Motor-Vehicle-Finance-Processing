package com.ajizamotors;

public class CartData {
    String name,CarModel,address,payment,price,sellerName,sellerContact,imageUrl,AjizaComition,OrderCount;

    public CartData(){

    }

    public CartData(String name, String carModel, String address, String payment, String price, String sellerName, String sellerContact, String imageUrl, String ajizaComition, String orderCount) {
        this.name = name;
        CarModel = carModel;
        this.address = address;
        this.payment = payment;
        this.price = price;
        this.sellerName = sellerName;
        this.sellerContact = sellerContact;
        this.imageUrl = imageUrl;
        AjizaComition = ajizaComition;
        OrderCount = orderCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String carModel) {
        CarModel = carModel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAjizaComition() {
        return AjizaComition;
    }

    public void setAjizaComition(String ajizaComition) {
        AjizaComition = ajizaComition;
    }

    public String getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(String orderCount) {
        OrderCount = orderCount;
    }
}
