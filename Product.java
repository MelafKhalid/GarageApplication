package com.example.project;

public class Product {
    private int id;
    private String productName;
    private String productDesc;
    private String productPrice;
    private byte[] productPic;
    private String categoryName;


    public Product(int id, String productName,String productDesc, String productPrice, byte[] productPic, String categoryName) {
        this.id = id;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productPic = productPic;
        this.categoryName = categoryName;
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

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public byte[] getProductPic() {
        return productPic;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}//end of class
