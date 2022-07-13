package com.example.inventorycapstone;

import com.example.inventorycapstone.util.HashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static com.example.inventorycapstone.util.HashController.fromHex;
import static com.example.inventorycapstone.util.HashController.toHex;
import static javafx.application.Platform.exit;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("main_menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Miniature Management!");
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