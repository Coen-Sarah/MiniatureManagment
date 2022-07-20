package com.example.miniaturemanagement.controller.set;

import com.example.miniaturemanagement.controller.MainController;
import com.example.miniaturemanagement.doa.model.SetDAO;
import com.example.miniaturemanagement.model.CustomSet;
import com.example.miniaturemanagement.model.Inventory;
import com.example.miniaturemanagement.model.NeededMiniature;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.math.BigDecimal;

import static com.example.miniaturemanagement.util.TextFieldChecker.*;

public class CustomSetController extends SetController {

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
    public TableColumn<NeededMiniature, NeededMiniature> setMiniCount;

    public TextField setName;
    public TextField setInventoryId;
    public TextField setOverStock;
    public TextField setLowStock;
    public TextField setStock;
    public TextField setWholeSalePrice;
    public TextField setRetail;

    protected CustomSet activeSet;

    public void initialize(){
        this.activeSet = (CustomSet) MainController.getActiveSet();
        if (activeSet != null) {
            fillSetFields();
            disableEdit();
            MainController.clearActiveSet();
            initializeNeededMiniaturesTable();
            initializeAllMiniatureTable();
        } else {
            activeSet = new CustomSet();
            enableEdit();
        }

    }

    protected void fillSetFields(){
        setName.setText(activeSet.getName());
        setInventoryId.setText(String.valueOf(activeSet.getId()));
        setWholeSalePrice.setText(String.valueOf(activeSet.getWholesalePrice()));
        setRetail.setText(String.valueOf(activeSet.getRetailMarkup()));
        setStock.setText(String.valueOf(activeSet.getCurrentStock()));
        setLowStock.setText(String.valueOf(activeSet.getLowStockAmount()));
        setOverStock.setText(String.valueOf(activeSet.getOverStockAmount()));
    }

    @Override
    protected void saveSet() {

        if (checkAllFields()) {
            if (activeSet == null) {
                activeSet = new CustomSet();
            }

            activeSet.setName(setName.getText());
            activeSet.setRetailMarkup(new BigDecimal(setRetail.getText()));
            activeSet.setCurrentStock(Integer.parseInt(setStock.getText()));
            activeSet.setLowStockAmount(Integer.parseInt(setLowStock.getText()));
            activeSet.setOverStockAmount(Integer.parseInt(setOverStock.getText()));
            activeSet.setNeededMiniatures(activeSet.getNeededMiniatures());

            if (activeSet.getId() > 0) {
                activeSet.setId(Integer.valueOf(setInventoryId.getText()));
                Inventory.updateSet(activeSet);
                SetDAO.update(activeSet);
            } else {
                activeSet.setId(SetDAO.add(activeSet));
                Inventory.addSet(activeSet);
            }
            disableEdit();

        } else {
            Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR, "Please verify the inserted data.");
            invalidInputAlert.show();
        }
    }

    private boolean checkAllFields() {
        return  checkValidText(setName) &&
                checkValidDecimal(setRetail) &&
                checkValidNumber(setStock) &&
                checkValidNumber(setLowStock) &&
                checkValidNumber(setOverStock);
    }

    private void initializeNeededMiniaturesTable(){

        setMiniTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setMiniName.minWidthProperty().bind(
                setMiniTable.widthProperty().multiply(0.4));

        setMiniRemove = new TableColumn<>();

        if(activeSet.getNeededMiniatures().size() == 0) {
            setMiniTable.setItems(Inventory.getEmptySet().getNeededMiniatures());
        } else {
            setMiniTable.setItems(activeSet.getNeededMiniatures());
        }

        setMiniId.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, Number>, ObservableValue<Number>>)
                        cellData -> new SimpleIntegerProperty(cellData.getValue().getMiniature().getId()));
        setMiniName.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<NeededMiniature, String>, ObservableValue<String>>)
                        cellData -> new SimpleStringProperty(cellData.getValue().getMiniature().getName()));
        setMiniCount.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        setMiniCount.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
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
                    updateTable();
                });
            }
        });
    }

    private void initializeAllMiniatureTable(){

        allMiniLabel = new Label("All Miniatures");
        allMiniTable = new TableView();
        allMiniAdd = new TableColumn();
        allMiniId = new TableColumn();
        allMiniName = new TableColumn();
        allMiniCount = new TableColumn();

        allMiniTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allMiniName.minWidthProperty().bind(
                allMiniTable.widthProperty().multiply(0.4));

        allMiniTable.setItems(Inventory.getRemainingMiniatures(activeSet));

        allMiniAdd = new TableColumn<>();
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
                    if(miniature.getCount() > 0) {
                        System.out.println(miniature.getCount());
                        activeSet.addMiniature(miniature);
                        setMiniTable.refresh();
                        System.out.println(activeSet.getNeededMiniatures().size());
                        updateTable();
                    }
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
                if (miniature == null) {
                    setGraphic(null);
                    return;
                }
                count.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
                count.valueProperty().addListener((obs, oldValue, newValue) -> {
                    miniature.setCount((Integer) newValue);
                });
                setGraphic(count);

            }
        });

        allMiniTable.getColumns().addAll(
                allMiniAdd, allMiniCount, allMiniId, allMiniName);

    }

    private void addAllMiniatureTable(){

        if(allMiniLabel == null){
            initializeNeededMiniaturesTable();
            initializeAllMiniatureTable();
        }

        setMiniCount.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        setMiniCount.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
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

        setMiniTable.getColumns().add(0, setMiniRemove);

        detailsGrid.getRowConstraints().get(9).setMaxHeight(30);
        detailsGrid.getRowConstraints().get(10).setMaxHeight(150);

        detailsGrid.add( allMiniLabel, 0,9,2,1);
        detailsGrid.add( allMiniTable, 0,10,2,1);

    }

    private void removeAllMiniaturesTable(){

        setMiniCount.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        setMiniCount.setCellFactory(param -> new TableCell<NeededMiniature, NeededMiniature>() {
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

        setMiniTable.getColumns().remove(setMiniRemove);
        setMiniName.minWidthProperty().bind(
                setMiniTable.widthProperty().multiply(0.6));
        allMiniLabel.setVisible(false);
        allMiniTable.setVisible(false);
        detailsGrid.getRowConstraints().get(9).setMaxHeight(5);
        detailsGrid.getRowConstraints().get(10).setMaxHeight(5);

    }

    private void updateTable() {
        if(activeSet.getNeededMiniatures().size() == 0) {
            setMiniTable.setItems(Inventory.getEmptySet().getNeededMiniatures());
        } else {
            setMiniTable.setItems(activeSet.getNeededMiniatures());
        }
        allMiniTable.setItems(Inventory.getRemainingMiniatures(activeSet));
        setWholeSalePrice.setText(String.valueOf(activeSet.getWholesalePrice()));
        setMiniTable.refresh();
    }

    @Override
    protected void cancelSet(){
        if(activeSet.getId() != 0) {
            setMiniTable.setItems(((CustomSet) SetDAO.get(activeSet.getId())).getNeededMiniatures());
        }
        super.cancelSet();
        removeAllMiniaturesTable();
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
        setWholeSalePrice.setDisable(true);
    }

    @Override
    protected void disableEdit(){
        super.disableEdit();
    }
}
