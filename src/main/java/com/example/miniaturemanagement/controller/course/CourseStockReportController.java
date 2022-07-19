package com.example.miniaturemanagement.controller.course;

import com.example.miniaturemanagement.controller.MainController;
import com.example.miniaturemanagement.doa.model.EmployeeDAO;
import com.example.miniaturemanagement.doa.model.LocationDAO;
import com.example.miniaturemanagement.model.Course;
import com.example.miniaturemanagement.model.NeededMiniature;
import com.example.miniaturemanagement.model.businessInfo.Location;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class CourseStockReportController {

    public TitledPane classReportItem;

    public Label courseLocation;
    public Label courseDate;
    public Label courseEmployee;
    public Label courseAttendees;
    public TableView reportStockTable;
    public TableColumn reportStockFlag;
    public TableColumn reportStockId;
    public TableColumn reportStockName;
    public TableColumn reportStockCurrent;
    public TableColumn reportStockNeeded;
    public TableColumn reportStockCount;

    private boolean hasInsufficientStock;
    private Course activeCourse;

    public void initialize(){
        activeCourse = MainController.getReportCourse();
        initializeTable();
        Location location = LocationDAO.get(activeCourse.getLocationId());
        classReportItem.setText(activeCourse.getName() + " " + hasInsufficientStock);
        courseLocation.setText(location.getName() + "\n" + location.getAddress() );
        courseEmployee.setText("Managing Employee: " + EmployeeDAO.get(activeCourse.getManagingEmployeeId()).getName());
        courseDate.setText( activeCourse.getStartTime().format(DateTimeFormatter.ofPattern("MMMM dd")) + " at " +
                            activeCourse.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm:a")));
        courseAttendees.setText("Number of Attendees: " + activeCourse.getNumberOfAttendees());
    }

    private void initializeTable() {

        reportStockTable.setItems(activeCourse.getCourseSet().getNeededMiniatures());
        reportStockFlag.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> {
                    NeededMiniature neededMiniature = cellData.getValue();
                    int flag = 0;
                    int totalNeeded = neededMiniature.getCount() * activeCourse.getNumberOfAttendees();
                    if(totalNeeded >= neededMiniature.getMiniature().getCurrentStock()) {
                        hasInsufficientStock = false;
                        flag = -1;
                    } else if ( totalNeeded >= (neededMiniature.getMiniature().getCurrentStock() - 5)) {
                        flag = 0;
                    } else {
                        flag = 1;
                    }
                    new SimpleIntegerProperty(cellData.getValue().getMiniature().getId());
                    return new SimpleIntegerProperty(flag);
                });

        reportStockId.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getId()));
        reportStockName.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, String>, ObservableValue<String>>)
                        cellData -> new SimpleStringProperty(cellData.getValue().getMiniature().getName()));
        reportStockCurrent.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getCurrentStock()));
        reportStockNeeded.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()*activeCourse.getNumberOfAttendees()));
        reportStockCount.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()));


    }
}
