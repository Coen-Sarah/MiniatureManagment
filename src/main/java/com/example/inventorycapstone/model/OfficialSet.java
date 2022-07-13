package com.example.inventorycapstone.model;

public class OfficialSet extends MiniatureSet{

    final String ID_SUFFIX = "SO";
    String brand;
    String supplier;

    public OfficialSet() {}

    public OfficialSet(String name, float wholeSalePrice, float retailMarkup, int currentStock, int lowStockAmount, int overStockAmount, String brand, String supplier) {
        super(name, wholeSalePrice, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.brand = brand;
        this.supplier = supplier;
    }

    public OfficialSet(int id, String name, float wholeSalePrice, float retailMarkup, int currentStock, int lowStockAmount, int overStockAmount, String brand, String supplier) {
        super(id, name, wholeSalePrice, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.brand = brand;
        this.supplier = supplier;
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
