package com.example.inventorycapstone.model;

import java.math.BigDecimal;

public abstract class MiniatureSet {

    int id;
    String name;
    BigDecimal wholeSalePrice;
    BigDecimal retailMarkup;
    int currentStock;
    int lowStockAmount;
    int overStockAmount;

    public MiniatureSet(){}

    public MiniatureSet(String name, BigDecimal retailMarkup, int currentStock, int lowStockAmount, int overStockAmount) {
        this.name = name;
        this.retailMarkup = retailMarkup;
        this.currentStock = currentStock;
        this.lowStockAmount = lowStockAmount;
        this.overStockAmount = overStockAmount;
    }

    public MiniatureSet(int id, String name, BigDecimal retailMarkup, int currentStock, int lowStockAmount, int overStockAmount) {
        this.id = id;
        this.name = name;
        this.retailMarkup = retailMarkup;
        this.currentStock = currentStock;
        this.lowStockAmount = lowStockAmount;
        this.overStockAmount = overStockAmount;
    }

    public MiniatureSet(String name, BigDecimal wholeSalePrice, BigDecimal retailMarkup, int currentStock, int lowStockAmount, int overStockAmount) {
        this.name = name;
        this.wholeSalePrice = wholeSalePrice;
        this.retailMarkup = retailMarkup;
        this.currentStock = currentStock;
        this.lowStockAmount = lowStockAmount;
        this.overStockAmount = overStockAmount;
    }

    public MiniatureSet(int id, String name, BigDecimal wholeSalePrice, BigDecimal retailMarkup, int currentStock, int lowStockAmount, int overStockAmount) {
        this.id = id;
        this.name = name;
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

    public BigDecimal getRetailMarkup() {
        return retailMarkup;
    }

    public void setRetailMarkup(BigDecimal retailMarkup) {
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
