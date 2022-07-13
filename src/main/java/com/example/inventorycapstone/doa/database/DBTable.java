package com.example.inventorycapstone.doa.database;

import com.example.inventorycapstone.doa.model.*;
import com.example.inventorycapstone.model.businessInfo.Employee;
import com.example.inventorycapstone.model.businessInfo.Location;
import com.example.inventorycapstone.util.CSVParser;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.inventorycapstone.doa.database.DBConnection.getConnection;

public class DBTable {

    static String createUserTable =
            "CREATE TABLE IF NOT EXISTS " + UserDAO.TABLE_NAME + " (\n" +
                    "  " + UserDAO.ID + " INT NOT NULL,\n" +
                    "  " + UserDAO.NAME + " VARCHAR(45) UNIQUE NULL,\n" +
                    "  " + UserDAO.PASSWORD_HASH + " VARCHAR(32) NULL,\n" +
                    "  " + UserDAO.PASSWORD_SALT + " VARCHAR(32) NULL,\n" +
                    "  PRIMARY KEY (" + UserDAO.ID + "));";

    static String createLocationTable =
            "CREATE TABLE IF NOT EXISTS " + LocationDAO.TABLE_NAME + " (\n" +
                    "  " + LocationDAO.ID + " INT NOT NULL,\n" +
                    "  " + LocationDAO.NAME + " VARCHAR(45) NULL,\n" +
                    "  " + LocationDAO.ADDRESS + " VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (" + LocationDAO.ID + "));" ;

    static String createEmployeeTable =
            " CREATE TABLE IF NOT EXISTS " + EmployeeDAO.TABLE_NAME + " (\n" +
                    "  " + EmployeeDAO.ID + " INT NOT NULL,\n" +
                    "  " + EmployeeDAO.LOCATION_ID + " INT NOT NULL,\n" +
                    "  " + EmployeeDAO.NAME + " VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (" + EmployeeDAO.ID + "),\n" +
                    "  CONSTRAINT " + EmployeeDAO.TABLE_NAME + EmployeeDAO.LOCATION_ID + "\n" +
                    "    FOREIGN KEY (" + EmployeeDAO.LOCATION_ID + ")\n" +
                    "    REFERENCES `Location` (" + LocationDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);";

    static String createCourseTable =
            " CREATE TABLE IF NOT EXISTS " + CourseDAO.TABLE_NAME + " (\n" +
                    "  " + CourseDAO.ID + " INT NOT NULL,\n" +
                    "  " + CourseDAO.NAME + " VARCHAR(45) NULL,\n" +
                    "  " + CourseDAO.LOCATION_ID + " INT NULL,\n" +
                    "  " + CourseDAO.EMPLOYEE_ID + " INT NULL,\n" +
                    "  " + CourseDAO.ATTENDEES + " INT NULL,\n" +
                    "  PRIMARY KEY (" + CourseDAO.ID + "),\n" +
                    "  CONSTRAINT " + CourseDAO.TABLE_NAME + CourseDAO.LOCATION_ID + "\n" +
                    "    FOREIGN KEY (" + CourseDAO.LOCATION_ID + ")\n" +
                    "    REFERENCES " + LocationDAO.TABLE_NAME + " (" + LocationDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT " + CourseDAO.TABLE_NAME + CourseDAO.EMPLOYEE_ID + "\n" +
                    "    FOREIGN KEY (" + CourseDAO.EMPLOYEE_ID + ")\n" +
                    "    REFERENCES " + EmployeeDAO.TABLE_NAME + " (" + EmployeeDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION); ";

    static String createMiniatureTable =
            " CREATE TABLE IF NOT EXISTS " + MiniatureDAO.TABLE_NAME + " (\n" +
                    "  " + MiniatureDAO.ID + " INT NOT NULL,\n" +
                    "  " + MiniatureDAO.NAME + " VARCHAR(45) NULL,\n" +
                    "  " + MiniatureDAO.BRAND + " VARCHAR(45) NULL,\n" +
                    "  " + MiniatureDAO.SUPPLIER + " VARCHAR(45) NULL,\n" +
                    "  " + MiniatureDAO.WHOLESALE + " FLOAT NULL,\n" +
                    "  " + MiniatureDAO.RETAIL_MARKUP + " FLOAT NULL,\n" +
                    "  " + MiniatureDAO.LOW_STOCK + " INT NULL,\n" +
                    "  " + MiniatureDAO.OVER_STOCK + " INT NULL,\n" +
                    "  " + MiniatureDAO.CURRENT_STOCK + " INT NULL,\n" +
                    "  PRIMARY KEY (" + MiniatureDAO.ID + ")); ";

    static String createSetTable =
            " CREATE TABLE IF NOT EXISTS " + SetDAO.TABLE_NAME + " (\n" +
                    "  " + SetDAO.ID + " INT NOT NULL,\n" +
                    "  " + SetDAO.NAME + " VARCHAR(45) NULL,\n" +
                    "  " + SetDAO.RETAIL_MARKUP + " FLOAT NULL,\n" +
                    "  " + SetDAO.LOW_STOCK + " INT NULL,\n" +
                    "  " + SetDAO.OVER_STOCK + " INT NULL,\n" +
                    "  " + SetDAO.CURRENT_STOCK + " INT NULL,\n" +
                    "  " + SetDAO.IS_OFFICIAL_SET + " TINYINT NULL,\n" +
                    "  PRIMARY KEY (" + SetDAO.ID + ")); ";

    static String createCustomSetLinkTable =
            " CREATE TABLE IF NOT EXISTS " + SetDAO.CUSTOM_SET_TABLE + " (\n" +
                    "  " + SetDAO.ID + " INT NOT NULL,\n" +
                    "  " + SetDAO.MINIATURE_ID + " INT NOT NULL,\n" +
                    "  " + SetDAO.COUNT + " INT NULL,\n" +
                    "  PRIMARY KEY (" + SetDAO.ID + ", " + SetDAO.MINIATURE_ID + "),\n" +
                    "  CONSTRAINT " + SetDAO.CUSTOM_SET_TABLE + SetDAO.ID + "\n" +
                    "    FOREIGN KEY (" + SetDAO.ID + ")\n" +
                    "    REFERENCES " + SetDAO.TABLE_NAME + " (" + SetDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT " + SetDAO.CUSTOM_SET_TABLE + SetDAO.MINIATURE_ID + "\n" +
                    "    FOREIGN KEY (" + SetDAO.MINIATURE_ID + ")\n" +
                    "    REFERENCES " + MiniatureDAO.TABLE_NAME + " (" + MiniatureDAO.ID + ")\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION); ";

    static String createOfficialSetTable =
            " CREATE TABLE IF NOT EXISTS " + SetDAO.OFFICIAL_SET_TABLE + " (\n" +
                    "  " + SetDAO.ID + " INT NOT NULL,\n" +
                    "  " + SetDAO.BRAND + " VARCHAR(45) NULL,\n" +
                    "  " + SetDAO.SUPPLIER + " VARCHAR(45) NULL,\n" +
                    "  " + SetDAO.WHOLESALE + " FLOAT NULL,\n" +
                    "  PRIMARY KEY (" + SetDAO.ID + "),\n" +
                    "  CONSTRAINT " + SetDAO.OFFICIAL_SET_TABLE + SetDAO.ID + "\n" +
                    "    FOREIGN KEY (" + SetDAO.ID + ")\n" +
                    "    REFERENCES " + SetDAO.TABLE_NAME + " (" + SetDAO.ID + ")\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION); ";

    static String createClassSetLinkTable =
            " CREATE TABLE IF NOT EXISTS " + SetDAO.CLASS_SET_TABLE + " (\n" +
                    "  " + SetDAO.CLASS_ID + " INT NOT NULL,\n" +
                    "  " + SetDAO.MINIATURE_ID + " INT NOT NULL,\n" +
                    "  " + SetDAO.COUNT + " INT NULL,\n" +
                    "  PRIMARY KEY (" + SetDAO.CLASS_ID + ", " + SetDAO.MINIATURE_ID + "),\n" +
                    "  CONSTRAINT " + SetDAO.CLASS_SET_TABLE + SetDAO.CLASS_ID +"\n" +
                    "    FOREIGN KEY (" + SetDAO.CLASS_ID + ")\n" +
                    "    REFERENCES " + CourseDAO.TABLE_NAME + " (" + CourseDAO.ID + ")\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT " + SetDAO.MINIATURE_ID + "\n" +
                    "    FOREIGN KEY (" + SetDAO.MINIATURE_ID + ")\n" +
                    "    REFERENCES " + MiniatureDAO.TABLE_NAME + " (" + MiniatureDAO.ID + ")\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION); ";

    public static void createDatabaseTables() throws SQLException {
        Statement statement = getConnection().createStatement();
        try {
            statement.executeUpdate(createUserTable);
            statement.executeUpdate(createLocationTable);
            statement.executeUpdate(createEmployeeTable);
            statement.executeUpdate(createCourseTable);
            statement.executeUpdate(createMiniatureTable);
            statement.executeUpdate(createSetTable);
            statement.executeUpdate(createCustomSetLinkTable);
            statement.executeUpdate(createOfficialSetTable);
            statement.executeUpdate(createClassSetLinkTable);

            insertBusinessData();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertBusinessData() {

        String locationFileName = "src/main/resources/businessData/location_data.csv";
        String employeeFileName = "src/main/resources/businessData/employee_data.csv";
        String userFileName = "src/main/resources/businessData/user_data.csv";

        ArrayList<String[]> locationArray = CSVParser.parseCSV(locationFileName);
        ArrayList<String[]> employeeArray = CSVParser.parseCSV(employeeFileName);
        ArrayList<String[]> userArray = CSVParser.parseCSV(userFileName);

        for (String[] location: locationArray) {
            LocationDAO.add(new Location(Integer.valueOf(location[0]),location[1],location[2]));
        }

        for (String[] employee: employeeArray) {
            EmployeeDAO.add(new Employee(Integer.valueOf(employee[0]),Integer.valueOf(employee[1]),employee[2]));
        }

        for (String[] user: userArray) {
            UserDAO.add(Integer.valueOf(user[0]),user[1],user[2],user[3]);
        }


    }
}
