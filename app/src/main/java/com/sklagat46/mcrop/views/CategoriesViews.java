package com.sklagat46.mcrop.views;

public class CategoriesViews {
    private int id;
    private String productName;
    private int imageName;

    public CategoriesViews(int id, String productName, int imageName) {
        this.id = id;
        this.productName = productName;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getImageName() {
        return imageName;
    }

    public void setImageName(int imageName) {
        this.imageName = imageName;
    }
}



