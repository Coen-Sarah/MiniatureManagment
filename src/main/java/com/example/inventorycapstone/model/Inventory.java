package com.example.inventorycapstone.model;

import com.example.inventorycapstone.doa.model.CourseDAO;
import com.example.inventorycapstone.doa.model.MiniatureDAO;
import com.example.inventorycapstone.doa.model.SetDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Inventory {

    private static ObservableList<Miniature> allMiniatures = FXCollections.observableArrayList();
    private static ObservableList<MiniatureSet> allSets = FXCollections.observableArrayList();
    private static ObservableList<Course> allCourses = FXCollections.observableArrayList();

    public Inventory(){}

    public static void setInventory() {
        allMiniatures = MiniatureDAO.getAll();
        allSets = SetDAO.getAll();
        allCourses = CourseDAO.getAll();
    }
    public static void update(){
        setInventory();
    }

    public static void addMiniature(Miniature newMiniature){
        allMiniatures.add(newMiniature);
    }
    
    public static void addSet(MiniatureSet newSet){
        allSets.add(newSet);
    }

    public static void addCourse(Course newCourse){
        allCourses.add(newCourse);
    }
    
    public static Miniature lookupMiniature(int miniatureId){
        for(int i = 0; i < allMiniatures.size(); i++){
            if (allMiniatures.get(i).getId() == miniatureId){
                return allMiniatures.get(i);
            }
        }
        return null;
    }
    
    public static MiniatureSet lookupSet(int setId){
        for(int i = 0; i < allSets.size(); i++){
            if (allSets.get(i).getId() == setId){
                return allSets.get(i);
            }
        }
        return null;
    }

    public static Course lookupCourse(int courseId){
        for(int i = 0; i < allCourses.size(); i++){
            if (allCourses.get(i).getId() == courseId){
                return allCourses.get(i);
            }
        }
        return null;
    }

    //TODO CITE: https://stackoverflow.com/questions/53075175/observablelist-returns-sublist-thatFXCollections.observableArrayList()-matches
    public static ObservableList<Miniature> lookupMiniature(String miniatureName){
        FilteredList<Miniature> miniatureFilteredList = allMiniatures.filtered(miniature -> miniature.getName().toLowerCase().contains(miniatureName.toLowerCase()));
        return miniatureFilteredList;
    }
    
    public static ObservableList<MiniatureSet> lookupSet(String setName){
        FilteredList<MiniatureSet> setFilteredList = allSets.filtered(set-> set.getName().toLowerCase().contains(setName.toLowerCase()));
        return setFilteredList;
    }

    public static ObservableList<Course> lookupCourse(String courseName){
        FilteredList<Course> courseFilteredList = allCourses.filtered(course-> course.getName().toLowerCase().contains(courseName.toLowerCase()));
        return courseFilteredList;
    }
    
    public static void updateMiniature(Miniature selectedMiniature){
        int index = 0;
        for(int i = 0; i < allMiniatures.size(); i++){
            if (allMiniatures.get(i).getId() == selectedMiniature.getId()){
                index = i;
            }
        }
        allMiniatures.set(index, selectedMiniature);
    }
    
    public static void updateSet(MiniatureSet selectedSet){

        int index = 0;
        for(int i = 0; i < allSets.size(); i++){
            if (allSets.get(i).getId() == selectedSet.getId()){
                index = i;
            }
        }
        allSets.set(index, selectedSet);
    }

    public static void updateCourse(Course selectedCourse){
        int index = 0;
        for(int i = 0; i < allSets.size(); i++){
            if (allSets.get(i).getId() == selectedCourse.getId()){
                index = i;
            }
        }
        allCourses.set(index, selectedCourse);
    }
    
    public static boolean deleteMiniature(Miniature selectedMiniature){
        return allMiniatures.remove(selectedMiniature);
    }
    
    public static boolean deleteSet(MiniatureSet selectedSet){
        return allSets.remove(selectedSet);
    }

    public static boolean deleteCourse(Course selectedCourse){
        return allCourses.remove(selectedCourse);
    }

    public static void setAllMiniatures(ObservableList<Miniature> allMiniatures) {
        Inventory.allMiniatures = allMiniatures;
    }

    public static void setAllSets(ObservableList<MiniatureSet> allSets) {
        Inventory.allSets = allSets;
    }

    public static void setAllCourses(ObservableList<Course> allCourses) {
        Inventory.allCourses = allCourses;
    }

    public static ObservableList<Miniature> getAllMiniatures() {
        return allMiniatures;
    }

    public static ObservableList<MiniatureSet> getAllSets(){
        return allSets;
    }

    public static ObservableList<Course> getAllCourses(){
        return allCourses;
    }

    public static ObservableList<NeededMiniature> getAllNeededMiniatures(){
        ObservableList<NeededMiniature> allNeededMiniatures = FXCollections.observableArrayList();
        for(int i = 0; i < allMiniatures.size(); i++){
            allNeededMiniatures.add(new NeededMiniature(allMiniatures.get(i), 0));
        }
        return allNeededMiniatures;
    }

}