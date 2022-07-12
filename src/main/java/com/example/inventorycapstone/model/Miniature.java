package com.example.inventorycapstone.model;

public class Miniature {

    int id;
    String name;
    String brand;
    String supplier;
    float wholeSalePrice;
    float retailMarkup;
    int currentStock;
    int lowStockAmount;
    int overStockAmount;

    public Miniature(){}

    public Miniature(String name,
                     String brand, String supplier,
                     float wholeSalePrice, float retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount) {
        this.name = name;
        this.brand = brand;
        this.supplier = supplier;
        this.wholeSalePrice = wholeSalePrice;
        this.retailMarkup = retailMarkup;
        this.currentStock = currentStock;
        this.lowStockAmount = lowStockAmount;
        this.overStockAmount = overStockAmount;
    }

    public Miniature(int id, String name,
                     String brand, String supplier,
                     float wholeSalePrice, float retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.supplier = supplier;
        this.wholeSalePrice = wholeSalePrice;
        this.retailMarkup = retailMarkup;
        this.currentStock = currentStock;
        this.lowStockAmount = lowStockAmount;
        this.overStockAmount = overStockAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.wholeSalePrice = wholeSalePrice;
    }

    public float getRetailMarkup() {
        return retailMarkup;
    }

    public void setRetailMarkup(float retailMarkup) {
        this.retailMarkup = retailMarkup;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getLowStockAmount() {
        return lowStockAmount;
    }

    public void setLowStockAmount(int lowStockAmount) {
        this.lowStockAmount = lowStockAmount;
    }

    public int getOverStockAmount() {
        return overStockAmount;
    }

    public void setOverStockAmount(int overStockAmount) {
        this.overStockAmount = overStockAmount;
    }
}
