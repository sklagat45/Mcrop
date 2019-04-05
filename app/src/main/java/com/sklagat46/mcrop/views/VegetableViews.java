package com.sklagat46.mcrop.views;

public class VegetableViews {

    private int productId;
    private String vegetableName;
    private int productImage;
    private int productPrice;
    private String description;

    public VegetableViews(int productId, String vegetableName, int productImage, int productPrice, String description) {
        this.productId = productId;
        this.vegetableName = vegetableName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.description = description;
    }

    public int getproductId() {
        return productId;
    }


    public void setproductId(int productId) {
        this.productId = productId;
    }

    public String getvegetableName() {
        return vegetableName;
    }

    public void setvegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }


    public int getproductImage() {
        return productImage ;
    }

    public void setproductImage(int productImage) {
        this.productImage = productImage;
    }


    public int getproductPrice() {
        return productPrice ;
    }

    public void setproductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getdescription() {
        return description ;
    }

    public void setdescription(String description) {
        this.description = description;
    }
}
