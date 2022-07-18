package com.example.inventorycapstone.controller.set;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class SetController {

    public GridPane detailsGrid;
    public TextField setInventoryId;

    public Button setChange;
    public Button setDiscard;

    private void clearDetails() {
        detailsGrid.getChildren().clear();
    }

    protected void disableEdit(){

        setChange.setText("Edit");
        setDiscard.setText("Delete");

        setChange.setOnAction((event) ->{
            editSet();
        });

        setDiscard.setOnAction((event) ->{
            deleteSet();
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

    protected void enableEdit(){
        setChange.setText("Save");
        setDiscard.setText("Cancel");

        setChange.setOnAction((event) ->{
            saveSet();
        });

        setDiscard.setOnAction((event) ->{
            cancelSet();
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
        setInventoryId.setDisable(true);
    }

    protected void cancelSet() {
        if(!setInventoryId.getText().isEmpty()) {
            fillSetFields();
            disableEdit();
        } else {
            clearDetails();
        }

    }

    protected void saveSet(){}

    protected void editSet() {
        enableEdit();
    }

    protected void deleteSet() {}

    protected void fillSetFields() {}

}
