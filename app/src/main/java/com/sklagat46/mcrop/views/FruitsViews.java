package com.sklagat46.mcrop.views;

public class FruitsViews {

    private int fruitId;
    private String fruitName;
    private int productImage;
    private int productPrice;
    private String description;

    public FruitsViews(int fruitId, String fruitName, int productImage, int productPrice, String description) {
        this.fruitId = fruitId;
        this.fruitName = fruitName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.description = description;
    }

    public int getfruitId() {
        return fruitId;
    }


    public void setproductId(int productId) {
        this.fruitId = fruitId;
    }

    public String getfruitName() {
        return fruitName;
    }

    public void setvegetableName(String fruitName) {
        this.fruitName = fruitName;
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
