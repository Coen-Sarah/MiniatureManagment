package com.example.miniaturemanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.miniaturemanagement.doa.database.DBConnection.makeConnection;
import static javafx.application.Platform.exit;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //TODO RESET TO LOGIN
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Miniature Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try{

            makeConnection();
            launch();

        }catch(Exception e){
            e.printStackTrace();
        }

        exit();
    }
}