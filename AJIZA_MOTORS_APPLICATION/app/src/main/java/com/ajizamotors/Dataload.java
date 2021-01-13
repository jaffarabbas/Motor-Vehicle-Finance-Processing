package com.ajizamotors;

public class Dataload {
    private String Name,Model,Address,Payment,Price,SellerName,SellerContact,Image;
    public Dataload(String name, String model, String address, String payment, String price, String sellerName, String sellerContact, String image) {
        Name = name;
        Model = model;
        Address = address;
        Payment = payment;
        Price = price;
        SellerName = sellerName;
        SellerContact = sellerContact;
        Image = image;
    }

    public Dataload(){
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public String getSellerContact() {
        return SellerContact;
    }

    public void setSellerContact(String sellerContact) {
        SellerContact = sellerContact;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
