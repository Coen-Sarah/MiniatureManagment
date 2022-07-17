package com.example.inventorycapstone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;

public class CustomSet extends MiniatureSet{
    final String ID_SUFFIX = "SC";
    ObservableList<Miniature> neededMiniatures = FXCollections.observableArrayList();
    ObservableList<Integer> miniatureCount = FXCollections.observableArrayList();

    public CustomSet() {}

    public CustomSet(String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount,
                     ObservableList<Miniature> neededMiniatures, ObservableList<Integer> miniatureCount) {
        super(setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.neededMiniatures = neededMiniatures;
        this.miniatureCount = miniatureCount;
    }

    public CustomSet(int setId, String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount,
                     ObservableList<Miniature> neededMiniatures, ObservableList<Integer> miniatureCount) {
        super(setId, setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.neededMiniatures = neededMiniatures;
        this.miniatureCount = miniatureCount;
    }
    public CustomSet(String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount) {
        super(setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
    }

    public CustomSet(int setId, String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount) {
        super(setId, setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
    }

    public ObservableList<Miniature> getNeededMiniatures() {
        return neededMiniatures;
    }

    public void setNeededMiniatures(ObservableList<Miniature> neededMiniatures) {
        this.neededMiniatures = neededMiniatures;
    }

    public ObservableList<Integer> getMiniatureCount() {
        return miniatureCount;
    }

    public void setMiniatureCount(ObservableList<Integer> miniatureCount) {
        this.miniatureCount = miniatureCount;
    }

    public BigDecimal getWholesalePrice() {
        BigDecimal totalWholesalePrice = new BigDecimal(0.00);
        for (int i = 0; i < neededMiniatures.size(); i++) {
            totalWholesalePrice = totalWholesalePrice.add((neededMiniatures.get(i).getWholesalePrice()
                                                            .multiply(new BigDecimal(miniatureCount.get(i)))));
        }
        return totalWholesalePrice;
    }

    public Miniature getMiniature(int miniatureId){
        for(int i = 0; i < neededMiniatures.size(); i++){
            if(neededMiniatures.get(i).getId() == miniatureId){
                return neededMiniatures.get(i);
            }
        }
        return null;
    }

    public int getMiniatureCount(int miniatureId){
        for(int i = 0; i < neededMiniatures.size(); i++){
            if(neededMiniatures.get(i).getId() == miniatureId){
                return miniatureCount.get(i);
            }
        }
        return -1;
    }

    public void addMiniature(Miniature miniature, int count){
        neededMiniatures.add(miniature);
        miniatureCount.add(count);
    }

    public void updateMiniatureCount(Miniature miniature, int count){
        for(int i = 0; i < neededMiniatures.size(); i++){
            if(neededMiniatures.get(i).getId() == miniature.getId()){
                miniatureCount.set(i, count);
            }
        }
    }

    public void removeMiniature(Miniature miniature){
        for(int i = 0; i < neededMiniatures.size(); i++){
            if(neededMiniatures.get(i).getId() == miniature.getId()){
                neededMiniatures.remove(i);
                miniatureCount.remove(i);
            }
        }
    }

    public String getSetType(){
        return "Custom";
    }
}
