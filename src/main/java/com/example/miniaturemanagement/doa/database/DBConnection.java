package com.example.miniaturemanagement.doa.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.miniaturemanagement.doa.database.DBTable.createDatabaseTables;

public class DBConnection {

    //TODO VERIFY DBConnection WORKS ON OTHER PEOPLE'S COMPUTERS

    private static final String databaseName="MiniatureInventory";
    private static final String DB_URL="jdbc:mysql://localhost:3306/";
    private static final String username="root";
    private static final String password="Konoha07!";
    static Connection conn;
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        try {
            conn = DriverManager.getConnection(DB_URL + databaseName, username, password);
        } catch (SQLException e) {
            conn = DriverManager.getConnection(DB_URL, username, password);
            try {
                createDatabase();
                createDatabaseTables();
            } catch (SQLException sql){
                sql.printStackTrace();
            }
        }
    }

    private static void createDatabase() throws SQLException {

        String databaseCreationSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String useDatabaseSQL = "USE MiniatureInventory";
        Statement statement = conn.createStatement();
        statement.executeUpdate(databaseCreationSQL);
        statement.execute(useDatabaseSQL);

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
