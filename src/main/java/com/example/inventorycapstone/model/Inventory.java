package com.example.inventorycapstone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Inventory {

    private static ObservableList<Miniature> allMiniatures = FXCollections.observableArrayList();
    private static ObservableList<MiniatureSet> allSets = FXCollections.observableArrayList();

    public static void addMiniature(Miniature newMiniature){
        allMiniatures.add(newMiniature);
    }
    
    public static void addSet(MiniatureSet newSet){
        allSets.add(newSet);
    }
    
    public static Miniature lookupMiniature(int miniatureId){
        for(int i = 0; i < allMiniatures.size(); i++){
            if (allMiniatures.get(i).getId() == miniatureId){
                return allMiniatures.get(i);
            }
        }
        return null;
    }
    
    //TODO CITE: https://stackoverflow.com/questions/53075175/observablelist-returns-sublist-thatFXCollections.observableArrayList()-matches
    public static ObservableList<Miniature> lookupMiniature(String miniatureName){
        FilteredList<Miniature> miniatureFilteredList = allMiniatures.filtered(miniature -> miniature.getName().toLowerCase().contains(miniatureName.toLowerCase()));
        return miniatureFilteredList;
    }
    
    public static MiniatureSet lookupSet(int setId){
        for(int i = 0; i < allSets.size(); i++){
            if (allSets.get(i).getId() == setId){
                return allSets.get(i);
            }
        }
        return null;
    }
    
    public static ObservableList<MiniatureSet> lookupSet(String setName){
        FilteredList<MiniatureSet> setFilteredList = allSets.filtered(set-> set.getName().toLowerCase().contains(setName.toLowerCase()));
        return setFilteredList;
    }
    
    public static void updateMiniature(int index, Miniature selectedMiniature){
        allMiniatures.set(index, selectedMiniature);
    }
    
    public static void updateSet(int index, MiniatureSet selectedSet){
        allSets.set(index,selectedSet);
    }
    
    public static boolean deleteMiniature(Miniature selectedMiniature){
        return allMiniatures.remove(selectedMiniature);
    }
    
    public static boolean deleteSet(MiniatureSet selectedSet){
        return allSets.remove(selectedSet);
    }
    
    public static ObservableList<Miniature> getAllMiniatures() {
        return allMiniatures;
    }

    public static ObservableList<MiniatureSet> getAllSets(){
        return allSets;
    }
    
}
