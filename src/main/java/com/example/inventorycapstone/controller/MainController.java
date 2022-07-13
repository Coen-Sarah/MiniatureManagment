package com.example.inventorycapstone.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    public Accordion classStockReport;
    public ArrayList<TitledPane> reportItems = new ArrayList<TitledPane>();

    public AnchorPane miniDetails;
    public AnchorPane setDetails;
    public AnchorPane classDetails;

    public Button miniAddNew;
    public MenuItem officialSetAddNew;
    public MenuItem customSetAddNew;
    public Button classAddNew;
    public Button miniChange;
    public Button miniDiscard;
    public Button setChange;
    public Button setDiscard;
    public Button classChange;
    public Button classDiscard;
    private String[] changeText = {"Edit", "Save"};
    private String[] discardText = {"Delete", "Cancel"};



    public MainController() {
    }

    public void initialize(){
        addClassReportContent();
        addMiniDetailsContent();
        addClassDetailsContent();
        addSetDetailsContent();
        //initializeButtons();
    }

    private void initializeButtons() {
        //TODO REWORK W/ NEW FXML SETUP
        miniAddNew.setOnAction(event ->{
            if(miniChange.getText().equals(changeText[0])){
                //TODO set blank details form editable
                miniChange.setText(changeText[1]);
                miniDiscard.setText(discardText[1]);
            }else{
                //TODO cancel form and
            }
        });
        miniChange.setOnAction(event -> {
            if(miniChange.getText().equals(changeText[0])){
                //TODO set details form editable
                miniChange.setText(changeText[1]);
                miniDiscard.setText(discardText[1]);
            }else{
                //TODO save form
                miniChange.setText(changeText[0]);
                miniDiscard.setText(discardText[0]);
            }
        });
        miniDiscard.setOnAction(event -> {
            if(miniDiscard.getText().equals(discardText[0])){
                //TODO delete item
                miniChange.setText(changeText[1]);
                miniDiscard.setText(discardText[1]);
            }else{
                //TODO cancel saving details form
                miniChange.setText(changeText[0]);
                miniDiscard.setText(discardText[0]);
            }
        });

        officialSetAddNew.setOnAction(event ->{
            if(setChange.getText().equals(changeText[0])){
                //TODO set blank details form editable
                setChange.setText(changeText[1]);
                setDiscard.setText(discardText[1]);
            }else{
                //TODO cancel form and
            }
        });
        customSetAddNew.setOnAction(event ->{
            if(setChange.getText().equals(changeText[0])){
                //TODO set blank details form editable
                setChange.setText(changeText[1]);
                setDiscard.setText(discardText[1]);
            }else{
                //TODO cancel form and
            }
        });
        setChange.setOnAction(event -> {
            if(setChange.getText().equals(changeText[0])){
                //TODO set details form editable
                setChange.setText(changeText[1]);
                setDiscard.setText(discardText[1]);
            }else{
                //TODO save form
                setChange.setText(changeText[0]);
                setDiscard.setText(discardText[0]);
            }
        });
        setDiscard.setOnAction(event -> {
            if(setDiscard.getText().equals(discardText[0])){
                //TODO delete item
                setChange.setText(changeText[1]);
                setDiscard.setText(discardText[1]);
            }else{
                //TODO cancel saving details form
                setChange.setText(changeText[0]);
                setDiscard.setText(discardText[0]);
            }
        });

        classAddNew.setOnAction(event ->{
            if(classChange.getText().equals(changeText[0])){
                //TODO set blank details form editable
                classChange.setText(changeText[1]);
                classDiscard.setText(discardText[1]);
            }else{
                //TODO cancel form and
            }
        });
        classChange.setOnAction(event -> {
        if(classChange.getText().equals(changeText[0])){
            //TODO set details form editable
            classChange.setText(changeText[1]);
            classDiscard.setText(discardText[1]);
        }else{
            //TODO save form
            classChange.setText(changeText[0]);
            classDiscard.setText(discardText[0]);
        }});
        classDiscard.setOnAction(event -> {
            if(classDiscard.getText().equals(discardText[0])){
                //TODO delete item
                classChange.setText(changeText[1]);
                classDiscard.setText(discardText[1]);
            }else{
                //TODO cancel saving details form
                classChange.setText(changeText[0]);
                classDiscard.setText(discardText[0]);
            }
        });

    }

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

    private void addMiniDetailsContent(){

        try {
            GridPane miniDetailPane = FXMLLoader.load(this.getClass().getResource("/com/example/inventorycapstone/miniDetails.fxml"));
            miniDetails.getChildren().add(miniDetailPane);

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: MINIATURE DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    private void addSetDetailsContent(){
        //TODO IMPLEMENT THE OFFICIAL VS CUSTOM LOGIC
        String officialSetPath = "/com/example/inventorycapstone/officialSetDetails.fxml";
        String customSetPath = "/com/example/inventorycapstone/customSetDetails.fxml";
        try {
            GridPane miniDetailPane = FXMLLoader.load(this.getClass().getResource(customSetPath));
            setDetails.getChildren().add(miniDetailPane);

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: SET DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

    private void addClassDetailsContent(){
        //TODO IMPLEMENT VIEW VS EDIT LOGIC

        try {
            GridPane miniDetailPane = FXMLLoader.load(this.getClass().getResource("/com/example/inventorycapstone/classDetailsView.fxml"));
            classDetails.getChildren().add(miniDetailPane);

        } catch (IOException e){
            System.out.println("FXML LOAD ERROR: CLASS DETAILS FAILED TO LOAD");
            e.printStackTrace();
        }

    }

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

}
