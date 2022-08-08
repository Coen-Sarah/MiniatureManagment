package com.example.inventorycapstone.model;

import java.math.BigDecimal;

public class OfficialSet extends MiniatureSet{

    final String ID_SUFFIX = "SO";
    String brand;
    String supplier;

    public OfficialSet() {}

    public OfficialSet(String name, String brand, String supplier,
                       BigDecimal wholeSalePrice, BigDecimal retailMarkup,
                       int currentStock, int lowStockAmount, int overStockAmount) {
        super(name, wholeSalePrice, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.brand = brand;
        this.supplier = supplier;
    }

    public OfficialSet(int id, String name, String brand, String supplier,
                       BigDecimal wholeSalePrice, BigDecimal retailMarkup,
                       int currentStock, int lowStockAmount, int overStockAmount) {
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

    public BigDecimal getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(BigDecimal wholeSalePrice) {
        super.wholeSalePrice = wholeSalePrice;
    }

    public String getSetType(){
        return "Official";
    }
}
