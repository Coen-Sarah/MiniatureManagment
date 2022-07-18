package com.example.inventorycapstone.controller;

import com.example.inventorycapstone.doa.model.UserDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.inventorycapstone.util.HashController.*;

public class LoginController {

    public Label timeLabel;
    public Timeline clock;

    public TextField usernameInput;
    public TextField passwordInput;

    public static String activeUser;

    public LoginController() {
    }

    public void initialize() {
        displayClock();
    }

    private void displayClock() {
        clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/YYYY hh:mm a ZZZZ")))),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void login(ActionEvent event){
        try{
            if(isValidLogin()) {
                toMain(event);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private boolean isValidLogin(){
        Alert invalidUsernameAlert = new Alert(Alert.AlertType.ERROR,"The entered username is incorrect.");
        Alert invalidPasswordAlert = new Alert(Alert.AlertType.ERROR,"The entered password is incorrect.");

        String[] userData = UserDAO.get(usernameInput.getText());
        if (userData != null){
            try {
                if(comparePassword(passwordInput.getText(), userData[1], userData[2])){
                    return true;
                } else {
                    invalidPasswordAlert.show();
                    return false;
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }

        } else {
            invalidUsernameAlert.show();
            return false;
        }
        return false;
    }

    private void toMain(ActionEvent event) throws IOException {
        activeUser = usernameInput.getText();
        Parent root = FXMLLoader.load(this.getClass().getResource("/com/example/inventorycapstone/main_menu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static String getActiveUser(){
        return activeUser;
    }

}
