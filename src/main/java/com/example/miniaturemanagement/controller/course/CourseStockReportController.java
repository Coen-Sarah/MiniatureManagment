package com.example.miniaturemanagement.controller.course;

import com.example.miniaturemanagement.controller.MainController;
import com.example.miniaturemanagement.doa.model.EmployeeDAO;
import com.example.miniaturemanagement.doa.model.LocationDAO;
import com.example.miniaturemanagement.model.Course;
import com.example.miniaturemanagement.model.NeededMiniature;
import com.example.miniaturemanagement.model.businessInfo.Location;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.*;
import org.kordamp.ikonli.feather.FeatherIkonHandler;

import java.time.format.DateTimeFormatter;

import static org.kordamp.ikonli.feather.Feather.ALERT_OCTAGON;

public class CourseStockReportController {

    public TitledPane classReportItem;

    public Label courseLocation;
    public Label courseDate;
    public Label courseEmployee;
    public Label courseAttendees;
    public TableView reportStockTable;
    public TableColumn<NeededMiniature, NeededMiniature> reportStockFlag;
    public TableColumn reportStockId;
    public TableColumn reportStockName;
    public TableColumn reportStockCurrent;
    public TableColumn reportStockNeeded;
    public TableColumn reportStockCount;

    private Course activeCourse;

    public void initialize(){
        activeCourse = MainController.getReportCourse();
        FeatherIkonHandler iconHandler = new FeatherIkonHandler();
        FontIcon hasStock = FontIcon.of(ALERT_OCTAGON);
        if(activeCourse.isCourseSetUnderstockedStocked()) {
            classReportItem.setGraphic(hasStock);
        }
        initializeTable();
        Location location = LocationDAO.get(activeCourse.getLocationId());
        classReportItem.setText(activeCourse.getName());
        courseLocation.setText(location.getName() + "\n" + location.getAddress() );
        courseEmployee.setText("Managing Employee: " + EmployeeDAO.get(activeCourse.getManagingEmployeeId()).getName());
        courseDate.setText( activeCourse.getStartTime().format(DateTimeFormatter.ofPattern("MMMM dd")) + " at " +
                            activeCourse.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm:a")));
        courseAttendees.setText("Number of Attendees: " + activeCourse.getNumberOfAttendees());
    }

    private void initializeTable() {

        reportStockTable.setItems(activeCourse.getCourseSet().getNeededMiniatures());

        reportStockFlag.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );

        reportStockFlag.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
            private final FontIcon icon = FontIcon.of(ALERT_OCTAGON);

            @Override
            protected void updateItem(NeededMiniature miniature, boolean empty) {
                super.updateItem(miniature, empty);

                if (miniature == null) {
                    setGraphic(null);
                    return;
                }

                if(activeCourse.isItemUnderStocked(miniature) == -1) {
                    setGraphic(icon);
                }
            }
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
