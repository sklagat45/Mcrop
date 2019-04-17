package com.sklagat46.mcrop.views;

public class AddStockViews {
    private String productId;
    private String productName;
    private String location;
    private String description;

    public AddStockViews(String productId, String productName, String location, String description) {
        this.productId = productId;
        this.productName = productName;
        this.location = location;
        this.description = description;
    }
    public String getproductId() {
        return productId;
    }


    public void setproductId(String productId) {
        this.productId = productId;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String vegetableName) {
        this.productName = productName;
    }


    public String getlocation() {
        return location ;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public String getdescription() {
        return description ;
    }

    public void setdescription(String description) {
        this.description = description;
    }
}

