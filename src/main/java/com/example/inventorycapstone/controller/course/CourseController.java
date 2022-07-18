package com.example.inventorycapstone.controller.course;

import com.example.inventorycapstone.controller.MainController;
import com.example.inventorycapstone.doa.model.CourseDAO;
import com.example.inventorycapstone.doa.model.EmployeeDAO;
import com.example.inventorycapstone.doa.model.LocationDAO;
import com.example.inventorycapstone.model.CustomSet;
import com.example.inventorycapstone.model.Inventory;
import com.example.inventorycapstone.model.Course;
import com.example.inventorycapstone.model.NeededMiniature;
import com.example.inventorycapstone.model.businessInfo.Employee;
import com.example.inventorycapstone.model.businessInfo.Location;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.function.Consumer;

public class CourseController {

    public GridPane detailsGrid;

    public Button courseChange;
    public Button courseDiscard;

    public TextField courseName;
    public TextField courseId;
    public ComboBox<Location> courseLocation;
    public ComboBox<Employee> courseEmployee;
    public DatePicker courseDate;
    public Spinner<Integer> courseTimeHours;
    public Spinner<Integer> courseTimeMinutes;
    public Spinner<String> courseTimeAP;
    public Spinner<Integer> courseAttendees;
    public TextField courseWholesale;
    public TextField courseRetail;

    public Label courseAllMiniLabel;
    public TableView courseMiniTable;
    public TableColumn<NeededMiniature, NeededMiniature> courseMiniRemove;
    public TableColumn courseMiniId;
    public TableColumn courseMiniName;
    public TableColumn courseMiniPer;
    public TableColumn courseMiniTotal;

    public TableView courseAllMiniTable;
    public TableColumn<NeededMiniature, NeededMiniature> courseAllMiniAdd;
    public TableColumn courseAllMiniId;
    public TableColumn courseAllMiniName;
    public TableColumn<NeededMiniature, NeededMiniature> courseAllMiniCount;

    private Course activeCourse = new Course();

    private ObservableList<String> AP = FXCollections.observableArrayList("AM", "PM");
    private ObservableList<Employee> allEmployees = FXCollections.observableArrayList();

    public void initialize(){
        courseLocation.setItems(LocationDAO.getAll());
        courseLocation.setOnAction(event->{
            setEmployeeItems();
        });

        courseTimeHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12,1));
        courseTimeMinutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60,0));
        courseTimeAP.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory(AP));

        courseAttendees.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));

        this.activeCourse = MainController.getActiveCourse();
        if (activeCourse != null) {
            fillCourseFields();
            disableEdit();
            setEmployeeItems();
            MainController.clearActiveCourse();
        } else {
            enableEdit();
            activeCourse = new Course();
            activeCourse.setCourseSet(new CustomSet());
        }
        initializeNeededMiniaturesTable();
    }

    private void setEmployeeItems() {
        Location location = courseLocation.getSelectionModel().getSelectedItem();
        allEmployees = EmployeeDAO.getAll();
        FilteredList<Employee> selectedEmployees = allEmployees.filtered(employee -> ((employee.getLocationId()) == (location.getId())));
        courseEmployee.setItems(selectedEmployees);
    }

    private void fillCourseFields() {

        courseName.setText(activeCourse.getName());
        courseId.setText(String.valueOf(activeCourse.getId()));
        courseLocation.setValue(LocationDAO.get(activeCourse.getLocationId()));
        courseEmployee.setValue(EmployeeDAO.get(activeCourse.getManagingEmployeeId()));

        courseDate.setValue(activeCourse.getStartTime().toLocalDate());

        int hour = activeCourse.getStartTime().getHour();
        if (hour > 12){
            hour -= 12;
            courseTimeAP.getValueFactory().setValue(AP.get(1));
        }
        int minute = activeCourse.getStartTime().getMinute();
        courseTimeHours.getValueFactory().setValue(hour);
        courseTimeMinutes.getValueFactory().setValue(minute);
        courseAttendees.getValueFactory().setValue(activeCourse.getNumberOfAttendees());
        courseRetail.setText(String.valueOf(activeCourse.getCourseSet().getRetailMarkup()));

    }

    private void saveCourse() {

        if(activeCourse == null) {
            activeCourse = new Course();
        }
        int hour = courseTimeHours.getValue();
        int minute = courseTimeMinutes.getValue();
        if (courseTimeAP.getValue().equals("PM")){
            hour += 12;
        }

        activeCourse.setName(courseName.getText());
        activeCourse.setStartTime(courseDate.getValue().atTime(hour,minute));
        activeCourse.setLocationId(courseLocation.getSelectionModel().getSelectedItem().getId());
        activeCourse.setManagingEmployeeId(courseEmployee.getSelectionModel().getSelectedItem().getId());
        activeCourse.setNumberOfAttendees(courseAttendees.getValue());

        activeCourse.getCourseSet().setName(courseName.getText() + " Class Set");
        activeCourse.getCourseSet().setRetailMarkup(new BigDecimal(courseRetail.getText()));

        if(activeCourse.getId() > 0) {
            activeCourse.setId(Integer.valueOf(courseId.getText()));
            Inventory.updateCourse(activeCourse);
            CourseDAO.update(activeCourse);
        } else {
            activeCourse.setId(CourseDAO.add(activeCourse));
            Inventory.addCourse(activeCourse);

        }
        disableEdit();
    }

    private void initializeNeededMiniaturesTable(){

        courseMiniTable.setItems(activeCourse.getCourseSet().getNeededMiniatures());
        courseMiniId.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getId()));
        courseMiniName.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, String>, ObservableValue<String>>)
                        cellData -> new SimpleStringProperty(cellData.getValue().getMiniature().getName()));
        courseMiniPer.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()));
        courseMiniTotal.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()*activeCourse.getNumberOfAttendees()));
    }

    private void addAllMiniatureTable(){

        courseAllMiniLabel = new Label("CourseAll Miniatures");
        courseAllMiniTable = new TableView();
        courseAllMiniAdd = new TableColumn();
        courseAllMiniId = new TableColumn();
        courseAllMiniName = new TableColumn();
        courseAllMiniCount = new TableColumn();

        courseAllMiniTable.setItems(Inventory.getAllNeededMiniatures());

        courseAllMiniAdd = new TableColumn<>("Add Mini");
        courseAllMiniAdd.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        courseAllMiniAdd.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
            private final Button addButton = new Button("Add");

            @Override
            protected void updateItem(NeededMiniature miniature, boolean empty) {
                super.updateItem(miniature, empty);

                if (miniature == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(addButton);
                addButton.setOnAction(event -> {
                    System.out.println(miniature.getCount());
                    activeCourse.getCourseSet().addMiniature(miniature);
                    System.out.println(activeCourse.getCourseSet().getNeededMiniatures().size());
                });
            }
        });
        courseAllMiniId.setText("ID");
        courseAllMiniId.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getId()));
        courseAllMiniName.setText("Name");
        courseAllMiniName.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, String>, ObservableValue<String>>)
                        cellData -> new SimpleStringProperty(cellData.getValue().getMiniature().getName()));
        courseAllMiniCount.setText("Count");

        courseAllMiniCount.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        courseAllMiniCount.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
            private final Spinner count = new Spinner();

            @Override
            protected void updateItem(NeededMiniature miniature, boolean empty) {
                super.updateItem(miniature, empty);
                count.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
                count.valueProperty().addListener((obs, oldValue, newValue) -> {
                    miniature.setCount((Integer) newValue);
                });
                if (miniature == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(count);

            }
        });

        courseAllMiniTable.getColumns().addAll(
                courseAllMiniAdd,courseAllMiniId, courseAllMiniName, courseAllMiniCount);

        courseMiniRemove = new TableColumn<>();
        courseMiniRemove.setText("Remove");

        courseMiniRemove.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );

        courseMiniRemove.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
            private final Button removeButton = new Button("Remove");

            @Override
            protected void updateItem(NeededMiniature miniature, boolean empty) {
                super.updateItem(miniature, empty);

                if (miniature == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(removeButton);
                removeButton.setOnAction(event -> {
                    System.out.println(miniature.getCount());
                    activeCourse.getCourseSet().removeMiniature(miniature);
                    System.out.println(activeCourse.getCourseSet().getNeededMiniatures().size());
                });
            }
        });

        courseMiniTable.getColumns().add(courseMiniRemove);

        courseAllMiniLabel.setMaxHeight(1000);
        courseAllMiniTable.setMaxHeight(1000);

        detailsGrid.add( courseAllMiniLabel, 0,11,2,1);
        detailsGrid.add( courseAllMiniTable, 0,12,2,1);
    }

    private void cancelCourse() {
        if(!courseId.getText().isEmpty()) {
            fillCourseFields();
            disableEdit();
        } else {
            clearDetails();
        }

    }

    private void clearDetails() {
        detailsGrid.getChildren().clear();
    }

    private void editCourse() {
        enableEdit();
    }

    private void deleteCourse() {
        Inventory.deleteCourse(activeCourse);
        CourseDAO.delete(activeCourse);
        detailsGrid.getChildren().clear();
    }

    private void disableEdit(){

        courseChange.setText("Edit");
        courseDiscard.setText("Delete");

        courseChange.setOnAction((event) ->{
            editCourse();
        });

        courseDiscard.setOnAction((event) ->{
            deleteCourse();
        });

        detailsGrid.getChildren().forEach(
                new Consumer<Node>(){
                    @Override
                    public void accept(Node node) {
                        if (node instanceof TextField){
                            TextField field = (TextField) node;
                            field.setDisable(true);
                        }
                    }
                });
    }

    private void enableEdit(){
        courseChange.setText("Save");
        courseDiscard.setText("Cancel");
        addAllMiniatureTable();

        courseChange.setOnAction((event) ->{
            saveCourse();
        });

        courseDiscard.setOnAction((event) ->{
            cancelCourse();
        });

        detailsGrid.getChildren().forEach(
                new Consumer<Node>(){
                    @Override
                    public void accept(Node node) {
                        if (node instanceof TextField){
                            TextField field = (TextField) node;
                            field.setDisable(false);
                        }
                    }
                });
    }

}
