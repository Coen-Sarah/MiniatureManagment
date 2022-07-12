package com.example.inventorycapstone.doa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String databaseName="Capstone";
    private static final String DB_URL="jdbc:mysql://localhost:3306/"+databaseName;
    private static final String username="root";
    private static final String password="Konoha07!";
    static Connection conn;
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn=(Connection) DriverManager.getConnection(DB_URL,username,password);
    }

    public static Connection getConnection(){
        return conn;
    }

    public static void closeConnection() throws ClassNotFoundException,SQLException, Exception{
        try {
            conn.close();
        }catch(Exception e){
            //Do Nothing
        }
    }

}
