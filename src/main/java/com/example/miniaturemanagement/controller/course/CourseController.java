package com.example.miniaturemanagement.controller.course;

import com.example.miniaturemanagement.controller.MainController;
import com.example.miniaturemanagement.doa.model.CourseDAO;
import com.example.miniaturemanagement.doa.model.EmployeeDAO;
import com.example.miniaturemanagement.doa.model.LocationDAO;
import com.example.miniaturemanagement.model.CustomSet;
import com.example.miniaturemanagement.model.Inventory;
import com.example.miniaturemanagement.model.Course;
import com.example.miniaturemanagement.model.NeededMiniature;
import com.example.miniaturemanagement.model.businessInfo.Employee;
import com.example.miniaturemanagement.model.businessInfo.Location;
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
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.util.function.Consumer;

import static com.example.miniaturemanagement.util.TextFieldChecker.*;

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
    public TextField courseWholeSale;
    public TextField courseRetail;

    public Label courseAllMiniLabel;
    public TableView courseMiniTable;
    public TableColumn<NeededMiniature, NeededMiniature> courseMiniRemove;
    public TableColumn courseMiniId;
    public TableColumn courseMiniName;
    public TableColumn<NeededMiniature, NeededMiniature> courseMiniPer;
    public TableColumn courseMiniTotal;

    public TableView courseAllMiniTable;
    public TableColumn<NeededMiniature, NeededMiniature> courseAllMiniAdd;
    public TableColumn courseAllMiniId;
    public TableColumn courseAllMiniName;
    public TableColumn<NeededMiniature, NeededMiniature> courseAllMiniCount;

    private Course activeCourse = new Course();
    private CustomSet emptySet = new CustomSet();

    private ObservableList<String> AP = FXCollections.observableArrayList("AM", "PM");
    private ObservableList<Employee> allEmployees = FXCollections.observableArrayList();

    public void initialize(){
        emptySet.getNeededMiniatures().add(new NeededMiniature(MainController.getEmptyMiniature(), 0));
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
        courseWholeSale.setText(String.valueOf(activeCourse.getCourseSet().getWholesalePrice()));
        courseRetail.setText(String.valueOf(activeCourse.getCourseSet().getRetailMarkup()));

    }

    private void saveCourse() {

        if(checkAllFields()){
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
        } else {
            Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR, "Please verify the inserted data.");
            invalidInputAlert.show();
        }
    }

    private boolean checkAllFields() {
        return  checkValidText(courseName) &&
                checkValidDecimal(courseRetail);
    }

    private void initializeNeededMiniaturesTable(){

        if(activeCourse.getCourseSet().getNeededMiniatures().size() == 0) {
            courseMiniTable.setItems(Inventory.getEmptySet().getNeededMiniatures());
        } else {
            courseMiniTable.setItems(activeCourse.getCourseSet().getNeededMiniatures());
        }

        courseMiniId.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getId()));
        courseMiniName.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, String>, ObservableValue<String>>)
                        cellData -> new SimpleStringProperty(cellData.getValue().getMiniature().getName()));
        courseMiniPer.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        courseMiniPer.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
            private final Label count = new Label();

            @Override
            protected void updateItem(NeededMiniature miniature, boolean empty) {
                super.updateItem(miniature, empty);
                if (miniature == null) {
                    setGraphic(null);
                    return;
                }
                count.setText(String.valueOf(miniature.getCount()));
                setGraphic(count);
            }
        });
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
                    activeCourse.getCourseSet().addMiniature(miniature);
                    updateTable();
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
          courseAllMiniAdd, courseAllMiniCount, courseAllMiniId, courseAllMiniName
        );

        courseMiniRemove = new TableColumn<>();
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
                    activeCourse.getCourseSet().removeMiniature(miniature);
                    updateTable();
                });
            }
        });

        courseMiniPer.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        courseMiniPer.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
            private final Spinner count = new Spinner();

            @Override
            protected void updateItem(NeededMiniature miniature, boolean empty) {
                super.updateItem(miniature, empty);
                if (miniature == null) {
                    setGraphic(null);
                    return;
                }
                count.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(miniature.getCount(),100,0));
                count.valueProperty().addListener((obs, oldValue, newValue) -> {
                    miniature.setCount((Integer) newValue);
                });
                setGraphic(count);

            }
        });

        courseMiniTable.getColumns().add(courseMiniRemove);

        courseAllMiniLabel.setMaxHeight(1000);
        courseAllMiniTable.setMaxHeight(1000);

        detailsGrid.add( courseAllMiniLabel, 0,11,2,1);
        detailsGrid.add( courseAllMiniTable, 0,12,2,1);
    }

    private void removeAllMiniaturesTable(){

        courseMiniPer.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        courseMiniPer.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
            private final Label count = new Label();

            @Override
            protected void updateItem(NeededMiniature miniature, boolean empty) {
                super.updateItem(miniature, empty);
                if (miniature == null) {
                    setGraphic(null);
                    return;
                }
                count.setText(String.valueOf(miniature.getCount()));
                setGraphic(count);
            }
        });

        courseMiniTable.getColumns().remove(courseMiniRemove);
        courseMiniTable.setVisible(false);
        courseAllMiniTable.setVisible(false);
        courseAllMiniLabel.setMaxHeight(0);
        courseAllMiniTable.setMaxHeight(0);
    }

    private void updateTable() {
        if(activeCourse.getCourseSet().getNeededMiniatures().size() == 0) {
            courseMiniTable.setItems(Inventory.getEmptySet().getNeededMiniatures());
        } else {
            courseMiniTable.setItems(activeCourse.getCourseSet().getNeededMiniatures());
        }
        courseWholeSale.setText(String.valueOf(activeCourse.getCourseSet().getWholesalePrice()));
        courseMiniTable.refresh();
    }


    private void cancelCourse() {
        if(!courseId.getText().isEmpty()) {
            courseMiniTable.setItems(CourseDAO.get(activeCourse.getId()).getCourseSet().getNeededMiniatures());
            fillCourseFields();
            removeAllMiniaturesTable();
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
                        if (node instanceof TextField ||
                            node instanceof Spinner ||
                            node instanceof ComboBox ||
                            node instanceof DatePicker){
                            node.setDisable(true);
                        }
                    }
                });
        courseTimeHours.setDisable(true);
        courseTimeMinutes.setDisable(true);
        courseTimeAP.setDisable(true);

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
                        if (node instanceof TextField ||
                            node instanceof Spinner ||
                            node instanceof ComboBox ||
                            node instanceof DatePicker) {
                            if (node instanceof HBox){
                                ((HBox) node).getChildren().forEach(
                                    new Consumer<Node>() {
                                        @Override
                                        public void accept(Node innerNode) {
                                            innerNode.setDisable(false);
                                        }
                                    }
                                );
                            }
                            node.setDisable(false);
                        }
                    }
                });
        courseTimeHours.setDisable(false);
        courseTimeMinutes.setDisable(false);
        courseTimeAP.setDisable(false);

        courseId.setDisable(true);
    }

}
