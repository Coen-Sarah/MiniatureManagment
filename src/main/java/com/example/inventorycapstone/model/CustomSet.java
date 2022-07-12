package com.example.inventorycapstone.model;

import javafx.collections.ObservableMap;
import java.util.concurrent.atomic.AtomicReference;

public class CustomSet extends MiniatureSet{
    ObservableMap<Miniature, Integer> neededMiniatures;

    public CustomSet() {}

    public CustomSet(String setName, float retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount,
                     ObservableMap<Miniature, Integer> neededMiniatures) {
        super(setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.neededMiniatures = neededMiniatures;
    }

    public CustomSet(int setId, String setName, float retailMarkup,
                     int currentStock, int lowStockAmount, int overStockAmount,
                     ObservableMap<Miniature, Integer> neededMiniatures) {
        super(setId, setName, retailMarkup, currentStock, lowStockAmount, overStockAmount);
        this.neededMiniatures = neededMiniatures;
    }

    public ObservableMap<Miniature, Integer> getNeededMiniatures() {
        return neededMiniatures;
    }

    public void setNeededMiniatures(ObservableMap<Miniature, Integer> neededMiniatures) {
        this.neededMiniatures = neededMiniatures;
    }

    public float getWholeSalePrice() {
        AtomicReference<Float> totalWholeSalePrice = new AtomicReference<>((float) 0);
        neededMiniatures.forEach( (miniature, integer) -> {
            totalWholeSalePrice.updateAndGet(v -> v + miniature.getWholeSalePrice() * integer);
        } );

        return totalWholeSalePrice.get();
        
    }

    public void addMiniature(Miniature newMiniature, int count){
        neededMiniatures.put(newMiniature, count);
    }

    public void updateMiniature(Miniature newMiniature, int newCount){
        neededMiniatures.put(newMiniature, newCount);
    }

    public void removeMiniature(Miniature newMiniature){
        neededMiniatures.remove(newMiniature);
    }

}
