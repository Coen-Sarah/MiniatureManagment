package com.example.inventorycapstone.controller.set;

import com.example.inventorycapstone.controller.MainController;
import com.example.inventorycapstone.doa.model.SetDAO;
import com.example.inventorycapstone.model.CustomSet;
import com.example.inventorycapstone.model.Inventory;
import com.example.inventorycapstone.model.NeededMiniature;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.math.BigDecimal;

public class CustomSetController extends SetController {

    public VBox miniEditLabel;
    public VBox miniEditTable;

    public Label allMiniLabel;
    public TableView allMiniTable;
    public TableColumn<NeededMiniature, NeededMiniature> allMiniAdd;
    public TableColumn allMiniId;
    public TableColumn allMiniName;
    public TableColumn<NeededMiniature, NeededMiniature> allMiniCount;

    public TableView setMiniTable;
    public TableColumn<NeededMiniature, NeededMiniature> setMiniRemove;
    public TableColumn setMiniId;
    public TableColumn setMiniName;
    public TableColumn setMiniCount;

    public TextField setName;
    public TextField setInventoryId;
    public TextField setOverStock;
    public TextField setLowStock;
    public TextField setStock;
    public TextField setRetail;

    protected CustomSet activeSet;

    public void initialize(){
        this.activeSet = (CustomSet) MainController.getActiveSet();
        if (activeSet != null) {
            fillSetFields();
            disableEdit();
        } else {
            activeSet = new CustomSet();
            enableEdit();
        }
        initializeNeededMiniaturesTable();
        MainController.clearActiveSet();
    }

    protected void fillSetFields(){
        setName.setText(activeSet.getName());
        setInventoryId.setText(String.valueOf(activeSet.getId()));
        setRetail.setText(String.valueOf(activeSet.getRetailMarkup()));
        setStock.setText(String.valueOf(activeSet.getCurrentStock()));
        setLowStock.setText(String.valueOf(activeSet.getLowStockAmount()));
        setOverStock.setText(String.valueOf(activeSet.getOverStockAmount()));
    }


    @Override
    protected void saveSet() {

        if(activeSet == null) {
            activeSet = new CustomSet();
        }

        activeSet.setName(setName.getText());
        activeSet.setRetailMarkup(new BigDecimal(setRetail.getText()));
        activeSet.setCurrentStock(Integer.parseInt(setStock.getText()));
        activeSet.setLowStockAmount(Integer.parseInt(setLowStock.getText()));
        activeSet.setOverStockAmount(Integer.parseInt(setOverStock.getText()));
        activeSet.setNeededMiniatures(activeSet.getNeededMiniatures());

        if(activeSet.getId() > 0) {
            activeSet.setId(Integer.valueOf(setInventoryId.getText()));
            Inventory.updateSet(activeSet);
            SetDAO.update(activeSet);
        } else {
            activeSet.setId(SetDAO.add(activeSet));
            Inventory.addSet(activeSet);
        }
        disableEdit();

    }

    private void initializeNeededMiniaturesTable(){

        setMiniTable.setItems(activeSet.getNeededMiniatures());
        setMiniId.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getId()));
        setMiniName.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, String>, ObservableValue<String>>)
                        cellData -> new SimpleStringProperty(cellData.getValue().getMiniature().getName()));
        setMiniCount.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()));
    }

    private void addAllMiniatureTable(){

        allMiniLabel = new Label("All Miniatures");
        allMiniTable = new TableView();
        allMiniAdd = new TableColumn();
        allMiniId = new TableColumn();
        allMiniName = new TableColumn();
        allMiniCount = new TableColumn();

        allMiniTable.setItems(Inventory.getAllNeededMiniatures());

        allMiniAdd = new TableColumn<>("Add Mini");
        allMiniAdd.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        allMiniAdd.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
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
                    activeSet.addMiniature(miniature);
                    System.out.println(activeSet.getNeededMiniatures().size());
                });
            }
        });
        allMiniId.setText("ID");
        allMiniId.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getId()));
        allMiniName.setText("Name");
        allMiniName.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, String>, ObservableValue<String>>)
                        cellData -> new SimpleStringProperty(cellData.getValue().getMiniature().getName()));
        allMiniCount.setText("Count");

        allMiniCount.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        allMiniCount.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
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

        allMiniTable.getColumns().addAll(
                allMiniAdd,allMiniId, allMiniName, allMiniCount);

        setMiniRemove = new TableColumn<>();
        setMiniRemove.setText("Remove");

        setMiniRemove.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );

        setMiniRemove.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
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
                    activeSet.removeMiniature(miniature);
                    System.out.println(activeSet.getNeededMiniatures().size());
                });
            }
        });

        setMiniTable.getColumns().add(setMiniRemove);

        miniEditLabel.setMaxHeight(1000);
        miniEditTable.setMaxHeight(1000);

        miniEditLabel.getChildren().add(allMiniLabel);
        miniEditTable.getChildren().add(allMiniTable);
    }

    private void removeAllMiniaturesTable(){
        setMiniTable.getColumns().remove(setMiniRemove);
        miniEditLabel.getChildren().clear();
        miniEditTable.getChildren().clear();
        miniEditLabel.setMaxHeight(0);
        miniEditTable.setMaxHeight(0);
    }

    @Override
    protected void deleteSet() {
        Inventory.deleteSet(activeSet);
        SetDAO.delete(activeSet);
        detailsGrid.getChildren().clear();
    }

    @Override
    protected void enableEdit(){
        super.enableEdit();
        addAllMiniatureTable();
    }

    @Override
    protected void disableEdit(){
        super.disableEdit();
        removeAllMiniaturesTable();
    }

}
