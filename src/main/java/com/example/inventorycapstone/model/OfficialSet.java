package com.example.inventorycapstone.model;

public class OfficialSet extends MiniatureSet{

    String brand;
    String supplier;

    public OfficialSet() {}

    public OfficialSet(String setName,
                       String brand, String supplier,
                       float wholeSalePrice,  float retailMarkup,
                       int currentStock, int lowStockAmount, int overStockAmount) {
        super(setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.brand = brand;
        this.supplier = supplier;
        this.wholeSalePrice = wholeSalePrice;
    }

    public OfficialSet(int setId, String setName,
                       String brand, String supplier,
                       float wholeSalePrice,  float retailMarkup,
                       int currentStock, int lowStockAmount, int overStockAmount) {
        super(setId, setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.brand = brand;
        this.supplier = supplier;
        super.wholeSalePrice = wholeSalePrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public float getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(float wholeSalePrice) {
        super.wholeSalePrice = wholeSalePrice;
    }
}
