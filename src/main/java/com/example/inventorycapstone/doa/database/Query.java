package com.example.inventorycapstone.doa.database;

import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.inventorycapstone.doa.database.DBConnection.getConnection;

public class Query {

    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    public static void makeQuery(String q){
        query =q;
        try{
            stmt = getConnection().createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("insert")) {
                stmt.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);
                result = stmt.getGeneratedKeys();
            }
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static ResultSet getResult(){
        return result;
    }

}