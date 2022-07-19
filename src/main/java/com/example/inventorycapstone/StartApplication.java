package com.example.inventorycapstone;

import com.example.inventorycapstone.doa.model.CourseDAO;
import com.example.inventorycapstone.doa.model.MiniatureDAO;
import com.example.inventorycapstone.doa.model.SetDAO;
import com.example.inventorycapstone.model.Course;
import com.example.inventorycapstone.model.CustomSet;
import com.example.inventorycapstone.model.Miniature;
import com.example.inventorycapstone.model.OfficialSet;
import com.example.inventorycapstone.util.HashController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static com.example.inventorycapstone.doa.database.DBTable.insertCurrentInventory;
import static javafx.application.Platform.exit;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("main_menu.fxml"));
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