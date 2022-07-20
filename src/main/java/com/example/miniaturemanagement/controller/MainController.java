package com.example.miniaturemanagement.controller;

import com.example.miniaturemanagement.doa.model.LocationDAO;
import com.example.miniaturemanagement.model.*;
import com.example.miniaturemanagement.model.businessInfo.Location;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Consumer;

import static com.example.miniaturemanagement.util.CSVParser.*;


public class MainController {

    public Label username;
    public Label liveClock;
    public Timeline clock;

    public Accordion classStockReport;
    public ArrayList<TitledPane> reportItems = new ArrayList<TitledPane>();

    public Label upcomingCoursesAlert;
    public Label lowStockAlert;
    public Label overStockAlert;

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
    private static Course reportCourse;

    private static Miniature emptyMiniature;

    public MainController() {
    }

    public void initialize() {
        initializeHeader();
        emptyMiniature = new Miniature();
        emptyMiniature.setName("No Miniatures Added");
        Inventory.setInventory();
        initializeMiniatureTab();
        initializeSetTab();
        initializeCourseTab();
        setOverviewNotifications();
        addClassReportContent();
    }

    private void initializeHeader() {
        username.setText(LoginController.getActiveUser());
        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                liveClock.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY hh:mm a ZZZZ")))),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

// OVERVIEW TAB METHODS

    private void setOverviewNotifications(){
        setEventNotification();
        setLowStockNotification();
        setOverStockNotification();
    }

    private void setOverStockNotification() {
        int miniatureCount = Inventory.getOverStockMiniatures().size();
        int setCount = Inventory.getOverStockSets().size();
        String alert = "There are \n\n" + miniatureCount + " Overstocked Miniatures\n" + setCount + " " +
                "Overstocked Sets\n ";
        overStockAlert.setText(alert);
    }

    private void setLowStockNotification() {
        int miniatureCount = Inventory.getLowStockMiniatures().size();
        int setCount = Inventory.getLowStockSets().size();
        String alert = "There are \n" + miniatureCount + " Understocked Miniatures\n" + setCount + " Understocked Sets\n ";
        lowStockAlert.setText(alert);
    }

    private void setEventNotification(){
        Pair<LocalDateTime, ObservableList<Course>> output = Inventory.getUpcomingCourses();

        String weekStartDate = output.getKey().toLocalDate().format(DateTimeFormatter.ofPattern("MMMM dd"));
        ObservableList<Course> upcomingCourses = output.getValue();
        final String[] alertText = {"For the week of: " + weekStartDate};
        if (upcomingCourses.size() > 1) {
            alertText[0] += " there are " + output.getValue().size() + " classes scheduled.\n";
        } else if (upcomingCourses.size() == 1) {
            alertText[0] += " there is " + output.getValue().size() + " class scheduled.\n";
        } else {
            alertText[0] += " there are no classes scheduled.";
        }

        output.getValue().forEach(
                new Consumer<Course>(){
                    @Override
                    public void accept(Course course) {
                        Location location = LocationDAO.get(course.getLocationId());
                        alertText[0] += " - " + course.getName() + " will be held at " + location.getName() + ", " +
                                location.getAddress() + " on " +
                                course.getStartTime().format(DateTimeFormatter.ofPattern("MMMM dd")) + " at " +
                                course.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm:a")) + ".";

                    }
                });

        upcomingCoursesAlert.setText(alertText[0]);
    }

    public void generateLowStockReport(ActionEvent event){
        System.out.println("---- Low Mini ------");
        String fileName = "src/Reports/Low Stock Report.csv";

        startCSVWriter(fileName);
        writeLine(new String[]{LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                "Low Stock Report"});
        writeLine(new String[]{"Item Type", "Id", "Item Name", "Current Stock", "Low Stock Amount"});
        Inventory.getLowStockMiniatures().forEach(
                new Consumer<Miniature>(){
                    @Override
                    public void accept(Miniature miniature) {
                        writeLine( new String[]{
                                "Miniature", String.valueOf(miniature.getId()),
                                miniature.getName(),
                                String.valueOf(miniature.getCurrentStock()),
                                String.valueOf(miniature.getLowStockAmount())});
                        }}
                    );

        Inventory.getLowStockSets().forEach(
                new Consumer<MiniatureSet>(){
                    @Override
                    public void accept(MiniatureSet set) {
                            writeLine( new String[]{
                                    "Set", String.valueOf(set.getId()), set.getName(),
                                    String.valueOf(set.getCurrentStock()),
                                    String.valueOf(set.getLowStockAmount())});
                    }
                });

        closeCSV();
    }

    public void generateOverStockReport(ActionEvent event){
        String fileName = "src/Reports/Over Stock Report.csv";

        startCSVWriter(fileName);
        writeLine(new String[]{LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                "Over Stock Report"});
        writeLine(new String[]{"Item Type", "Id", "Item Name", "Current Stock", "Over Stock Amount"});
        Inventory.getOverStockMiniatures().forEach(
                new Consumer<Miniature>(){
                    @Override
                    public void accept(Miniature miniature) {
                        writeLine( new String[]{
                                "Miniature", String.valueOf(miniature.getId()),
                                miniature.getName(),
                                String.valueOf(miniature.getCurrentStock()),
                                String.valueOf(miniature.getLowStockAmount())});
                    }}
        );

        Inventory.getOverStockSets().forEach(
                new Consumer<MiniatureSet>(){
                    @Override
                    public void accept(MiniatureSet set) {
                        writeLine( new String[]{
                                "Set", String.valueOf(set.getId()), set.getName(),
                                String.valueOf(set.getCurrentStock()),
                                String.valueOf(set.getLowStockAmount())});
                    }
                });

        closeCSV();
    }

    private void addClassReportContent() {
        try {
            ObservableList<Course> sorted = Inventory.getCoursesByDate();
            for( int i = 0; i < sorted.size() ; i++){
                reportCourse = sorted.get(i);
                TitledPane pane = FXMLLoader.load(this.getClass().getResource("/com/example/miniaturemanagement/courseStockReportItem.fxml"));
                reportItems.add(pane);
            }
            classStockReport.getPanes().addAll(reportItems);

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: OVERVIEW CLASS STOCK REPORT FAILED TO LOAD");
            e.printStackTrace();
        }
    }

    public static Course getReportCourse(){
        return reportCourse;
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
            GridPane miniDetailPane = FXMLLoader.load(this.getClass().getResource("/com/example/miniaturemanagement/miniDetails.fxml"));
            miniDetailPane.setMaxWidth(miniDetails.getWidth());
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

    public static void clearActiveMiniature(){
        activeMiniature = null;
    }
    
// SET TAB METHODS
    
    private void initializeSetTab() {

        setSetSearch();

        setTable.setRowFactory(tv -> {
            TableRow<MiniatureSet> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()
                        && event.getButton() == MouseButton.PRIMARY) {

                    MiniatureSet selectedSet = row.getItem();
                    activeSet = selectedSet;
                    if(selectedSet instanceof OfficialSet) {
                        showOfficialSetDetailPane(false);
                    } else {
                        showCustomSetDetailPane(false);
                    }
                }
            });
            return row ;
        });

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
        String officialSetPath = "/com/example/miniaturemanagement/officialSetDetails.fxml";
        try {
            GridPane setDetailPane = FXMLLoader.load(this.getClass().getResource(officialSetPath));
            setDetailPane.setMaxWidth(setDetails.getWidth());
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
        String customSetView = "/com/example/miniaturemanagement/customSetDetailsView.fxml";
        try {
            GridPane setDetailPane;
            if(isNewSet){
                activeMiniature = null;
            }
            setDetailPane =  FXMLLoader.load(this.getClass().getResource(customSetView));
            setDetailPane.setMaxWidth(setDetails.getWidth()-10);
            setDetails.setContent(setDetailPane);

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: SET DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    public static MiniatureSet getActiveSet(){
        return activeSet;
    }

    public static void clearActiveSet(){
        activeSet = null;
    }
    
// COURSE TAB METHODS
    
    private void initializeCourseTab() {
        setCourseSearch();

        courseTable.setRowFactory(tv -> {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()
                        && event.getButton() == MouseButton.PRIMARY) {

                    Course selectedCourse = row.getItem();
                    activeCourse = selectedCourse;
                    showCourseDetailPane(false);
                }
            });
            return row ;
        });

        this.courseTable.setItems(Inventory.getAllCourses());
        this.courseId.setCellValueFactory(new PropertyValueFactory("id"));
        this.courseName.setCellValueFactory(new PropertyValueFactory("name"));
        this.courseLocation.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>)
                        cellData -> {
                            return new SimpleStringProperty(LocationDAO.get(cellData.getValue().getLocationId()).getName());
                        });
        this.courseDate.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>)
                        cellData -> {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd\nhh:mm");
                            return new SimpleStringProperty(cellData.getValue().getStartTime().format(formatter));
                });
        
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
        showCourseDetailPane(true);
    }

    private void showCourseDetailPane(boolean isNewCourse){
        try {
            String course = "/com/example/miniaturemanagement/classDetailsView.fxml";
            GridPane courseDetailPane;
            if(isNewCourse){
                activeMiniature = null;
            }
            courseDetailPane =  FXMLLoader.load(this.getClass().getResource(course));
            courseDetailPane.setMaxWidth(courseDetails.getWidth()-10);
            courseDetails.setContent(courseDetailPane);


        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: CLASS DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    public static Course getActiveCourse(){
        return activeCourse;
    }

    public static void clearActiveCourse(){
        activeCourse = null;
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
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/com/example/miniaturemanagement/login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

// GENERAL METHODS
    public static Miniature getEmptyMiniature(){
        emptyMiniature.setName("No Miniatures Added.");
        return emptyMiniature;
    }
}
