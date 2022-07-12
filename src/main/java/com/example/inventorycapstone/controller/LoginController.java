package com.example.inventorycapstone.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    //TODO takes username and hashed password input and compares w/ database
    //TODO if correct user/pass combo take user to main page
    //TODO if incorrect show alert

    public LoginController() {
    }

    public void initialize() {
    }

    @FXML
    public void login(ActionEvent event){
        try{
            toMain(event);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void toMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/com/example/inventorycapstone/main_menu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
