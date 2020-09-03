package com.example.project;

public class Cart {


    private int id;
    private String productName;
    private String productPrice;
    private byte[] productPic;


    public Cart(String productName, String productPrice, byte[] productPic) {

        this.productName = productName;
        this.productPrice = productPrice;
        this.productPic = productPic;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductPic(byte[] productPic) {
        this.productPic = productPic;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public byte[] getProductPic() {
        return productPic;
    }}


