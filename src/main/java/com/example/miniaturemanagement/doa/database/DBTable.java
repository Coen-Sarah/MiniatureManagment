package com.example.miniaturemanagement.doa.database;

import com.example.miniaturemanagement.doa.model.*;
import com.example.miniaturemanagement.model.*;
import com.example.miniaturemanagement.model.businessInfo.Employee;
import com.example.miniaturemanagement.model.businessInfo.Location;
import com.example.miniaturemanagement.util.CSVParser;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.example.miniaturemanagement.doa.database.DBConnection.getConnection;

public class DBTable {

    static String createUserTable =
            "CREATE TABLE IF NOT EXISTS " + UserDAO.TABLE_NAME + " (\n" +
                    "  " + UserDAO.ID + " INT NOT NULL AUTO_INCREMENT,\n" +
                    "  " + UserDAO.NAME + " VARCHAR(100) UNIQUE NULL,\n" +
                    "  " + UserDAO.PASSWORD_HASH + " VARCHAR(32) NULL,\n" +
                    "  " + UserDAO.PASSWORD_SALT + " VARCHAR(32) NULL,\n" +
                    "  PRIMARY KEY (" + UserDAO.ID + "));";

    static String createLocationTable =
            "CREATE TABLE IF NOT EXISTS " + LocationDAO.TABLE_NAME + " (\n" +
                    "  " + LocationDAO.ID + " INT NOT NULL AUTO_INCREMENT,\n" +
                    "  " + LocationDAO.NAME + " VARCHAR(100) NULL,\n" +
                    "  " + LocationDAO.ADDRESS + " VARCHAR(100) NULL,\n" +
                    "  PRIMARY KEY (" + LocationDAO.ID + "));" ;

    static String createEmployeeTable =
            " CREATE TABLE IF NOT EXISTS " + EmployeeDAO.TABLE_NAME + " (\n" +
                    "  " + EmployeeDAO.ID + " INT NOT NULL AUTO_INCREMENT,\n" +
                    "  " + EmployeeDAO.LOCATION_ID + " INT NOT NULL,\n" +
                    "  " + EmployeeDAO.NAME + " VARCHAR(100) NULL,\n" +
                    "  PRIMARY KEY (" + EmployeeDAO.ID + "),\n" +
                    "  CONSTRAINT " + EmployeeDAO.TABLE_NAME + EmployeeDAO.LOCATION_ID + "\n" +
                    "    FOREIGN KEY (" + EmployeeDAO.LOCATION_ID + ")\n" +
                    "    REFERENCES `Location` (" + LocationDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);";

    static String createMiniatureTable =
            " CREATE TABLE IF NOT EXISTS " + MiniatureDAO.TABLE_NAME + " (\n" +
                    "  " + MiniatureDAO.ID + " INT NOT NULL AUTO_INCREMENT,\n" +
                    "  " + MiniatureDAO.NAME + " VARCHAR(100) NULL,\n" +
                    "  " + MiniatureDAO.BRAND + " VARCHAR(100) NULL,\n" +
                    "  " + MiniatureDAO.SUPPLIER + " VARCHAR(100) NULL,\n" +
                    "  " + MiniatureDAO.WHOLESALE + " DECIMAL(6,2) NULL,\n" +
                    "  " + MiniatureDAO.RETAIL_MARKUP + " DECIMAL(6,2) NULL,\n" +
                    "  " + MiniatureDAO.LOW_STOCK + " INT NULL,\n" +
                    "  " + MiniatureDAO.OVER_STOCK + " INT NULL,\n" +
                    "  " + MiniatureDAO.CURRENT_STOCK + " INT NULL,\n" +
                    "  PRIMARY KEY (" + MiniatureDAO.ID + ")); ";

    static String createSetTable =
            " CREATE TABLE IF NOT EXISTS " + SetDAO.TABLE_NAME + " (\n" +
                    "  " + SetDAO.ID + " INT NOT NULL AUTO_INCREMENT,\n" +
                    "  " + SetDAO.NAME + " VARCHAR(100) NULL,\n" +
                    "  " + SetDAO.RETAIL_MARKUP + " DECIMAL(6,2) NULL,\n" +
                    "  " + SetDAO.LOW_STOCK + " INT NULL,\n" +
                    "  " + SetDAO.OVER_STOCK + " INT NULL,\n" +
                    "  " + SetDAO.CURRENT_STOCK + " INT NULL,\n" +
                    "  " + SetDAO.IS_OFFICIAL_SET + " TINYINT NULL,\n" +
                    "  " + SetDAO.IS_CLASS_SET + " TINYINT NULL,\n" +
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
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT " + SetDAO.CUSTOM_SET_TABLE + SetDAO.MINIATURE_ID + "\n" +
                    "    FOREIGN KEY (" + SetDAO.MINIATURE_ID + ")\n" +
                    "    REFERENCES " + MiniatureDAO.TABLE_NAME + " (" + MiniatureDAO.ID + ")\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE); ";

    static String createOfficialSetTable =
            " CREATE TABLE IF NOT EXISTS " + SetDAO.OFFICIAL_SET_TABLE + " (\n" +
                    "  " + SetDAO.ID + " INT NOT NULL,\n" +
                    "  " + SetDAO.BRAND + " VARCHAR(100) NULL,\n" +
                    "  " + SetDAO.SUPPLIER + " VARCHAR(100) NULL,\n" +
                    "  " + SetDAO.WHOLESALE + " DECIMAL(6,2) NULL,\n" +
                    "  PRIMARY KEY (" + SetDAO.ID + "),\n" +
                    "  CONSTRAINT " + SetDAO.OFFICIAL_SET_TABLE + SetDAO.ID + "\n" +
                    "    FOREIGN KEY (" + SetDAO.ID + ")\n" +
                    "    REFERENCES " + SetDAO.TABLE_NAME + " (" + SetDAO.ID + ")\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE); ";

    static String createCourseTable =
            " CREATE TABLE IF NOT EXISTS " + CourseDAO.TABLE_NAME + " (\n" +
                    "  " + CourseDAO.ID + " INT NOT NULL AUTO_INCREMENT,\n" +
                    "  " + CourseDAO.NAME + " VARCHAR(100) NULL,\n" +
                    "  " + CourseDAO.LOCATION_ID + " INT NULL,\n" +
                    "  " + CourseDAO.DATETIME + " TIMESTAMP NULL,\n" +
                    "  " + CourseDAO.EMPLOYEE_ID + " INT NULL,\n" +
                    "  " + CourseDAO.SET_ID + " INT NULL,\n" +
                    "  " + CourseDAO.ATTENDEES + " INT NULL,\n" +
                    "  PRIMARY KEY (" + CourseDAO.ID + "),\n" +
                    "  CONSTRAINT " + CourseDAO.TABLE_NAME + CourseDAO.LOCATION_ID + "\n" +
                    "    FOREIGN KEY (" + CourseDAO.LOCATION_ID + ")\n" +
                    "    REFERENCES " + LocationDAO.TABLE_NAME + " (" + LocationDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "CONSTRAINT " + CourseDAO.TABLE_NAME + CourseDAO.EMPLOYEE_ID + "\n" +
                    "    FOREIGN KEY (" + CourseDAO.EMPLOYEE_ID + ")\n" +
                    "    REFERENCES " + EmployeeDAO.TABLE_NAME + " (" + EmployeeDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT " + CourseDAO.TABLE_NAME + CourseDAO.SET_ID + "\n" +
                    "    FOREIGN KEY (" + CourseDAO.SET_ID + ")\n" +
                    "    REFERENCES " + SetDAO.TABLE_NAME + " (" + SetDAO.ID + ")\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION); ";

    public static void createDatabaseTables() throws SQLException {
        Statement statement = getConnection().createStatement();
        try {
            statement.executeUpdate(createUserTable);
            statement.executeUpdate(createLocationTable);
            statement.executeUpdate(createEmployeeTable);
            statement.executeUpdate(createMiniatureTable);
            statement.executeUpdate(createSetTable);
            statement.executeUpdate(createCustomSetLinkTable);
            statement.executeUpdate(createOfficialSetTable);
            statement.executeUpdate(createCourseTable);

            insertBusinessData();
            insertCurrentInventory();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertBusinessData() {

        String locationFileName = "src/main/resources/com/example/miniaturemanagement/businessData/location_data.csv";
        String employeeFileName = "src/main/resources/com/example/miniaturemanagement/businessData/employee_data.csv";
        String userFileName = "src/main/resources/com/example/miniaturemanagement/businessData/user_data.csv";

        ArrayList<String[]> locationArray = CSVParser.readCSV(locationFileName);
        ArrayList<String[]> employeeArray = CSVParser.readCSV(employeeFileName);
        ArrayList<String[]> userArray = CSVParser.readCSV(userFileName);

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

    public static void insertCurrentInventory() {
        String miniatureFileName = "src/main/resources/com/example/miniaturemanagement/businessData/currentInventory/miniature_data.csv";
        String officialSetFileName = "src/main/resources/com/example/miniaturemanagement/businessData/currentInventory/officialSet_data.csv";
        String customSetFileName = "src/main/resources/com/example/miniaturemanagement/businessData/currentInventory/customSet_data.csv";
        String courseFileName = "src/main/resources/com/example/miniaturemanagement/businessData/currentCourses/course_data.csv";
        String courseSetFileName = "src/main/resources/com/example/miniaturemanagement/businessData/currentCourses/courseSet_data.csv";

        ArrayList<String[]> miniatureArray = CSVParser.readCSV(miniatureFileName);
        ArrayList<String[]> officialSetArray = CSVParser.readCSV(officialSetFileName);
        ArrayList<String[]> customSetArray = CSVParser.readCSV(customSetFileName);

        ArrayList<String[]> courseArray = CSVParser.readCSV(courseFileName);
        ArrayList<String[]> courseSetArray = CSVParser.readCSV(courseSetFileName);

        for (String[] miniature: miniatureArray) {
            MiniatureDAO.add(new Miniature(Integer.parseInt(miniature[0]), miniature[1], miniature[2],miniature[3],
                            new BigDecimal(miniature[4]), new BigDecimal(miniature[5]),
                            Integer.parseInt(miniature[6]), Integer.parseInt(miniature[7]), Integer.parseInt(miniature[8])));
        }

        for (String[] set: officialSetArray) {
            SetDAO.add(new OfficialSet(Integer.parseInt(set[0]), set[1], set[2],set[3],
                    new BigDecimal(set[4]), new BigDecimal(set[5]),
                    Integer.parseInt(set[6]), Integer.parseInt(set[7]), Integer.parseInt(set[8])));
        }

        for (String[] set: customSetArray) {
            CustomSet customSet =
                    new CustomSet(Integer.parseInt(set[0]), set[1], new BigDecimal(set[2]),
                    Integer.parseInt(set[3]), Integer.parseInt(set[4]), Integer.parseInt(set[5]));
            for(int i = 6; i < set.length; i++){
                int miniature = Integer.parseInt(set[i].split("\\.")[0]);
                int count = Integer.parseInt(set[i].split("\\.")[1]);
                customSet.addMiniature( new NeededMiniature(MiniatureDAO.get(miniature), count));
            }
            SetDAO.add(customSet);
        }

        for (int i = 0; i < courseArray.size(); i++){
            CustomSet courseSet = new CustomSet(
                    Integer.parseInt(courseSetArray.get(i)[0]), courseSetArray.get(i)[1], new BigDecimal(courseSetArray.get(i)[2]),
                    Integer.parseInt(courseSetArray.get(i)[3]), Integer.parseInt(courseSetArray.get(i)[4]), Integer.parseInt(courseSetArray.get(i)[5]));

            for(int k = 6; k < courseSetArray.get(i).length; k++){
                int miniature = Integer.parseInt(courseSetArray.get(i)[k].split("\\.")[0]);
                int count = Integer.parseInt(courseSetArray.get(i)[k].split("\\.")[1]);
                courseSet.addMiniature( new NeededMiniature(MiniatureDAO.get(miniature), count));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime date = LocalDateTime.parse(courseArray.get(i)[3], formatter);

            Course course = new Course( Integer.parseInt(courseArray.get(i)[0]), courseArray.get(i)[1],
                                        Integer.parseInt(courseArray.get(i)[2]), date,
                                        Integer.parseInt(courseArray.get(i)[4]),Integer.parseInt(courseArray.get(i)[5]),
                                        courseSet);
            CourseDAO.add(course);
        }

    }
}
