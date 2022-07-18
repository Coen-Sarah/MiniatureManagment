package com.example.inventorycapstone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;

public class CustomSet extends MiniatureSet{
    final String ID_SUFFIX = "SC";
    ObservableList<NeededMiniature> neededMiniatures = FXCollections.observableArrayList();

    public CustomSet() {}

    public CustomSet(String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount,
                     ObservableList<NeededMiniature> neededMiniatures) {
        super(setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.neededMiniatures = neededMiniatures;
    }

    public CustomSet(int setId, String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount,
                     ObservableList<NeededMiniature> neededMiniatures) {
        super(setId, setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.neededMiniatures = neededMiniatures;
    }
    public CustomSet(String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount) {
        super(setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
    }

    public CustomSet(int setId, String setName, BigDecimal retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount) {
        super(setId, setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
    }

    public ObservableList<NeededMiniature> getNeededMiniatures() {
        return neededMiniatures;
    }

    public void setNeededMiniatures(ObservableList<NeededMiniature> neededMiniatures) {
        this.neededMiniatures = neededMiniatures;
    }

    public BigDecimal getWholesalePrice() {
        BigDecimal totalWholesalePrice = new BigDecimal(0.00);
        for (int i = 0; i < neededMiniatures.size(); i++) {
            totalWholesalePrice = totalWholesalePrice.add((neededMiniatures.get(i).getMiniature().getWholesalePrice()
                                                            .multiply(new BigDecimal(neededMiniatures.get(i).getCount()))));
        }
        return totalWholesalePrice;
    }

    public NeededMiniature getMiniature(int miniatureId){
        for(int i = 0; i < neededMiniatures.size(); i++){
            if(neededMiniatures.get(i).getMiniature().getId() == miniatureId){
                return neededMiniatures.get(i);
            }
        }
        return null;
    }

    public void addMiniature(NeededMiniature miniature){
        neededMiniatures.add( miniature);
    }

    public void updateMiniature(NeededMiniature miniature){
        for(int i = 0; i < neededMiniatures.size(); i++){
            if(neededMiniatures.get(i).getMiniature().getId() == miniature.getMiniature().getId()){
                neededMiniatures.set(i, miniature);
            }
        }
    }

    public void removeMiniature(NeededMiniature miniature){
        for(int i = 0; i < neededMiniatures.size(); i++){
            if(neededMiniatures.get(i).getMiniature().getId() == miniature.getMiniature().getId()){
                neededMiniatures.remove(i);
            }
        }
    }

    public String getSetType(){
        return "Custom";
    }
}
