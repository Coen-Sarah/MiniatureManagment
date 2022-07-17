package com.example.inventorycapstone.controller;

import com.example.inventorycapstone.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    public Accordion classStockReport;
    public ArrayList<TitledPane> reportItems = new ArrayList<TitledPane>();

    public ScrollPane miniDetails;
    public ScrollPane setDetails;
    public ScrollPane courseDetails;

    public Button miniAddNew;
    public TextField miniatureSearch;
    public TableView miniatureTable;
    public TableColumn miniId;
    public TableColumn miniName;
    public TableColumn miniBrand;
    public TableColumn miniStock;

    public MenuItem officialSetAddNew;
    public MenuItem customSetAddNew;
    public TextField setSearch;
    public TableView setTable;
    public TableColumn setId;
    public TableColumn setName;
    public TableColumn setType;
    public TableColumn setStock;

    public Button courseAddNew;
    public TextField courseSearch;
    public TableView courseTable;
    public TableColumn courseId;
    public TableColumn courseName;
    public TableColumn courseLocation;
    public TableColumn courseDate;

    private static Miniature activeMiniature;
    private static MiniatureSet activeSet;
    private static Course activeCourse;

    public MainController() {
    }

    public void initialize() {

        Inventory.setInventory();
        initializeMiniatureTab();
        initializeSetTab();
        initializeCourseTab();

        addClassReportContent();
    }

// OVERVIEW TAB METHODS

    private void addClassReportContent() {
        try {
            for( int i = 0; i < 3 ; i++){
                TitledPane pane = FXMLLoader.load(this.getClass().getResource("/com/example/inventorycapstone/courseStockReportItem.fxml"));
                pane.setText("" + i);
                reportItems.add(pane);
            }
            classStockReport.getPanes().addAll(reportItems);

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: OVERVIEW CLASS STOCK REPORT FAILED TO LOAD");
            e.printStackTrace();
        }
    }
    
    
// MINIATURE TAB METHODS    

    private void initializeMiniatureTab() {
        setMiniatureSearch();
        this.miniatureTable.setRowFactory(tv -> {
            TableRow<Miniature> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()
                        && event.getButton() == MouseButton.PRIMARY) {

                    Miniature selectedMiniature = row.getItem();
                    activeMiniature = selectedMiniature;
                    showMiniDetailPane(false);
                }
            });
            return row ;
        });

        this.miniatureTable.setItems(Inventory.getAllMiniatures());
        this.miniId.setCellValueFactory(new PropertyValueFactory("id"));
        this.miniName.setCellValueFactory(new PropertyValueFactory("name"));
        this.miniBrand.setCellValueFactory(new PropertyValueFactory("brand"));
        this.miniStock.setCellValueFactory(new PropertyValueFactory("currentStock"));

    }
    
    private void setMiniatureSearch() {

        this.miniatureSearch.textProperty().addListener((observablePart, oldValuePart, newValuePart) -> {
            ObservableList<Miniature> miniList = FXCollections.observableArrayList();
            Miniature noMiniatureFound = new Miniature();
                noMiniatureFound.setName("No Miniatures Found");
            if (this.miniatureSearch.getText().trim().isEmpty()) {
                System.out.println("in");
                miniList = Inventory.getAllMiniatures();
            } else {
                try {
                    System.out.println(Integer.parseInt(this.miniatureSearch.textProperty().getValue()));
                    miniList.add(Inventory.lookupMiniature(Integer.parseInt(this.miniatureSearch.textProperty().getValue())));
                } catch (NumberFormatException var9) {
                    System.out.println("atsetitems");

                    for(int i = 0; i < Inventory.lookupMiniature(this.miniatureSearch.getText()).size(); ++i) {
                        miniList.add(Inventory.lookupMiniature(this.miniatureSearch.getText()).get(i));
                    }
                }
            }

            try {
                System.out.println(miniList);
                (miniList.get(0)).getId();
            } catch (Exception var8) {
                if (miniList.size() > 0) {
                    miniList.remove(0);
                }

                miniList.add(noMiniatureFound);
                System.out.println(miniList);
            }

            this.miniatureTable.setItems(miniList);

        });
    }

    public void showNewMini(ActionEvent event){
        showMiniDetailPane(true);
    }

    private void showMiniDetailPane(boolean isNewMini){

        try {
            GridPane miniDetailPane = FXMLLoader.load(this.getClass().getResource("/com/example/inventorycapstone/miniDetails.fxml"));
            miniDetails.setContent(miniDetailPane);
            if(!isNewMini){
                activeMiniature = null;
            }

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: MINIATURE DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    public static Miniature getActiveMiniature(){
        return activeMiniature;
    }
    
// SET TAB METHODS
    
    private void initializeSetTab() {

        setSetSearch();
        this.setTable.setItems(Inventory.getAllSets());
        this.setId.setCellValueFactory(new PropertyValueFactory("id"));
        this.setName.setCellValueFactory(new PropertyValueFactory("name"));
        this.setType.setCellValueFactory(new PropertyValueFactory("setType"));
        this.setStock.setCellValueFactory(new PropertyValueFactory("currentStock"));

    }

    private void setSetSearch() {

        this.setSearch.textProperty().addListener((observablePart, oldValuePart, newValuePart) -> {
            ObservableList<MiniatureSet> setList = FXCollections.observableArrayList();
            OfficialSet noSetFound = new OfficialSet();
            noSetFound.setName("No Sets Found");
            if (this.setSearch.getText().trim().isEmpty()) {
                setList = Inventory.getAllSets();
            } else {
                try {
                    System.out.println(Integer.parseInt(this.setSearch.textProperty().getValue()));
                    setList.add(Inventory.lookupSet(Integer.parseInt(this.setSearch.textProperty().getValue())));
                } catch (NumberFormatException var9) {
                    for (int i = 0; i < Inventory.lookupSet(this.setSearch.getText()).size(); ++i) {
                        setList.add(Inventory.lookupSet(this.setSearch.getText()).get(i));
                    }
                }
            }

            try {
                System.out.println(setList);
                (setList.get(0)).getId();
            } catch (Exception var8) {
                if (setList.size() > 0) {
                    setList.remove(0);
                }
                setList.add(noSetFound);
                System.out.println(setList);
            }

            this.setTable.setItems(setList);
        });
    }

    public void showNewCustomSet(ActionEvent event){
        showCustomSetDetailPane(true);
    }

    public void showNewOfficialSet(ActionEvent event){
        showOfficialSetDetailPane(true);
    }

    private void showOfficialSetDetailPane(boolean isNewSet){
        String officialSetPath = "/com/example/inventorycapstone/officialSetDetails.fxml";
        try {
            GridPane setDetailPane = FXMLLoader.load(this.getClass().getResource(officialSetPath));
            setDetails.setContent(setDetailPane);
            if(!isNewSet){
                activeSet = null;
            }

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: SET DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    private void showCustomSetDetailPane(boolean isNewSet){
        String customSetEdit = "/com/example/inventorycapstone/customSetDetailsEdit.fxml";
        String customSetView = "/com/example/inventorycapstone/customSetDetailsView.fxml";
        try {
            GridPane setDetailPane;
            if(!isNewSet){
                setDetailPane =  FXMLLoader.load(this.getClass().getResource(customSetView));
            } else {
                activeMiniature = null;
                setDetailPane =  FXMLLoader.load(this.getClass().getResource(customSetEdit));
            }
            setDetails.setContent(setDetailPane);

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: SET DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    public static MiniatureSet getActiveSet(){
        return activeSet;
    }
    
// COURSE TAB METHODS
    
    private void initializeCourseTab() {
        setCourseSearch();
        //TODO LOCATION NAME FROM ID
        //TODO DATE STRING FROM STARTTIME OBJECT
        this.courseTable.setItems(Inventory.getAllCourses());
        this.courseId.setCellValueFactory(new PropertyValueFactory("id"));
        this.courseName.setCellValueFactory(new PropertyValueFactory("name"));
        this.courseLocation.setCellValueFactory(new PropertyValueFactory("locationId"));
        this.courseDate.setCellValueFactory(new PropertyValueFactory("startTime"));
        
    }

    private void setCourseSearch() {

        this.courseSearch.textProperty().addListener((observablePart, oldValuePart, newValuePart) -> {
            ObservableList<Course> courseList = FXCollections.observableArrayList();
            Course noCourseFound = new Course();
            noCourseFound.setName("No Courses Found");
            if (this.courseSearch.getText().trim().isEmpty()) {
                courseList = Inventory.getAllCourses();
            } else {
                try {
                    System.out.println(Integer.parseInt(this.courseSearch.textProperty().getValue()));
                    courseList.add(Inventory.lookupCourse(Integer.parseInt(this.courseSearch.textProperty().getValue())));
                } catch (NumberFormatException var9) {
                    for(int i = 0; i < Inventory.lookupCourse(this.courseSearch.getText()).size(); ++i) {
                        courseList.add(Inventory.lookupCourse(this.courseSearch.getText()).get(i));
                    }
                }
            }

            try {
                System.out.println(courseList);
                (courseList.get(0)).getId();
            } catch (Exception var8) {
                if (courseList.size() > 0) {
                    courseList.remove(0);
                }
                courseList.add(noCourseFound);
                System.out.println(courseList);
            }

            this.courseTable.setItems(courseList);

        });
    }

    public void showNewCourse(ActionEvent event){
        showCourseDetailsPane(true);
    }

    private void showCourseDetailsPane(boolean isNewCourse){
        try {
            GridPane courseDetailsPane;
            String courseEdit = "/com/example/inventorycapstone/classDetailsEdit.fxml";
            String courseView = "/com/example/inventorycapstone/classDetailsView.fxml";
            if(!isNewCourse){
                courseDetailsPane =  FXMLLoader.load(this.getClass().getResource(courseView));
            } else {
                activeMiniature = null;
                courseDetailsPane =  FXMLLoader.load(this.getClass().getResource(courseEdit));
            }
            courseDetails.setContent(courseDetailsPane);


        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: CLASS DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    public static Course getActiveCourse(){
        return activeCourse;
    }

// NAVIGATION METHODS

    public void logout(ActionEvent event){
        try{
            toLogin(event);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void toLogin(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/com/example/inventorycapstone/login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

// GENERAL METHODS

}
