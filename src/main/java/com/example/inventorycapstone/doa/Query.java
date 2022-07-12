package com.example.inventorycapstone.doa;

import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.inventorycapstone.doa.DBConnection.getConnection;

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
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
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
