package com.example.inventorycapstone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Miniature Managment!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        /*try{
            makeConnection();

        }catch(Exception e){
            e.printStackTrace();
        }*/

        exit();
    }
}