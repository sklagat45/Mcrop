package com.sklagat46.mcrop.views;

public class AddStockViews {
    private int productId;
    private String productName;
    private String location;
    private String productType;
    private String description;

    public AddStockViews(int productId, String productName, String location, String productType, String description) {
        this.productId = productId;
        this.productName = productName;
        this.location = location;
        this.productType = productType;
        this.description = description;
    }
    public int getproductId() {
        return productId;
    }


    public void setproductId(int productId) {
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


    public String getproductType() {
        return productType ;
    }

    public void setproductType(String productType) {
        this.productType = productType;
    }

    public String getdescription() {
        return description ;
    }

    public void setdescription(String description) {
        this.description = description;
    }
}

