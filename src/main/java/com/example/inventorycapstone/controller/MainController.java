package com.example.inventorycapstone.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    public Accordion classStockReport;
    public ArrayList<TitledPane> reportItems = new ArrayList<TitledPane>();

    public MainController() {
    }

    public void initialize(){
        addClassReportContent();
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
            e.printStackTrace();
        }
    }

    @FXML
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
