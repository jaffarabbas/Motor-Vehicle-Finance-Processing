package com.ajizamotors;

public
class Load{
    String name,model,address,payment,price,sellerName,sellerContact,imageUrl;
    public Load(){}

    public Load(String name, String model, String address, String payment, String price, String sellerName, String sellerContact, String imageUrl) {
        this.name = name;
        this.model = model;
        this.address = address;
        this.payment = payment;
        this.price = price;
        this.sellerName = sellerName;
        this.sellerContact = sellerContact;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

}