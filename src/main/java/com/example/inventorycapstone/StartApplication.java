package com.example.inventorycapstone;

import com.example.inventorycapstone.util.HashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static javafx.application.Platform.exit;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Miniature Management!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try{
            launch();
            makeConnection();

            //TODO IMPLEMENT PASSWORD COMPARISON TO
            /*byte[][] one = HashController.encodePassword("test");
            byte[] two = HashController.encodePassword("Password", one[0]);

            System.out.println(toHex(one[0]));
            System.out.println(toHex(one[1]));*/

        }catch(Exception e){
            e.printStackTrace();
        }

        exit();
    }
}