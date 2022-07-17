package com.example.inventorycapstone.controller;

import com.example.inventorycapstone.doa.model.MiniatureDAO;
import com.example.inventorycapstone.model.Inventory;
import com.example.inventorycapstone.model.Miniature;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class MiniatureController {

    public GridPane detailsGrid;

    public Button miniChange;
    public Button miniDiscard;

    public TextField miniatureName;
    public TextField miniatureInventoryId;
    public TextField miniatureBrand;
    public TextField miniatureSupplier;
    public TextField miniatureWholesale;
    public TextField miniatureRetail;
    public TextField miniatureStock;
    public TextField miniatureLowStock;
    public TextField miniatureOverStock;

    private Miniature activeMiniature = new Miniature();

    public void initialize(){
        this.activeMiniature = MainController.getActiveMiniature();
        if (activeMiniature != null) {
            fillMiniatureFields();
            disableEdit();
            this.activeMiniature = MainController.getActiveMiniature();
        } else {
            enableEdit();
        }
    }

    private void fillMiniatureFields() {

        miniatureName.setText(activeMiniature.getName());
        miniatureInventoryId.setText(String.valueOf(activeMiniature.getId()));
        miniatureBrand.setText(activeMiniature.getBrand());
        miniatureSupplier.setText(activeMiniature.getSupplier());
        miniatureWholesale.setText(String.valueOf(activeMiniature.getWholesalePrice()));
        miniatureRetail.setText(String.valueOf(activeMiniature.getRetailMarkup()));
        miniatureStock.setText(String.valueOf(activeMiniature.getCurrentStock()));
        miniatureLowStock.setText(String.valueOf(activeMiniature.getLowStockAmount()));
        miniatureOverStock.setText(String.valueOf(activeMiniature.getOverStockAmount()));

    }

    private void saveMiniature() {

        if(activeMiniature == null) {
            activeMiniature = new Miniature();
        }

        activeMiniature.setName(miniatureName.getText());
        activeMiniature.setBrand(miniatureBrand.getText());
        activeMiniature.setSupplier(miniatureSupplier.getText());
        activeMiniature.setWholesalePrice( new BigDecimal(miniatureWholesale.getText()));
        activeMiniature.setRetailMarkup(new BigDecimal(miniatureRetail.getText()));
        activeMiniature.setCurrentStock(Integer.parseInt(miniatureStock.getText()));
        activeMiniature.setLowStockAmount(Integer.parseInt(miniatureLowStock.getText()));
        activeMiniature.setOverStockAmount(Integer.parseInt(miniatureOverStock.getText()));

        if(activeMiniature.getId() > 0) {
            activeMiniature.setId(Integer.valueOf(miniatureInventoryId.getText()));
            MiniatureDAO.update(activeMiniature);
        } else {
            activeMiniature.setId(MiniatureDAO.add(activeMiniature));
            Inventory.addMiniature(activeMiniature);

        }
        disableEdit();
    }

    private void cancelMiniature() {
        if(!miniatureInventoryId.getText().isEmpty()) {
            fillMiniatureFields();
            disableEdit();
        } else {
            clearDetails();
        }

    }

    private void clearDetails() {
        detailsGrid.getChildren().clear();
    }

    private void editMiniature() {
        enableEdit();
    }

    private void deleteMiniature() {
        Inventory.deleteMiniature(activeMiniature);
        MiniatureDAO.delete(activeMiniature);
        detailsGrid.getChildren().clear();
    }

    private void disableEdit(){

        miniChange.setText("Edit");
        miniDiscard.setText("Delete");

        miniChange.setOnAction((event) ->{
            editMiniature();
        });

        miniDiscard.setOnAction((event) ->{
            deleteMiniature();
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
        miniChange.setText("Save");
        miniDiscard.setText("Cancel");

        miniChange.setOnAction((event) ->{
            saveMiniature();
        });

        miniDiscard.setOnAction((event) ->{
            cancelMiniature();
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
