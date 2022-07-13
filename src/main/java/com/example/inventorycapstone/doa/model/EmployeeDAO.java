package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.businessInfo.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.inventorycapstone.doa.database.Query.getResult;
import static com.example.inventorycapstone.doa.database.Query.makeQuery;

public class EmployeeDAO {

    public static final String TABLE_NAME = "Employee";
    public static final String ID = "EmployeeId";
    public static final String LOCATION_ID = "LocationId";
    public static final String NAME = "EmployeeName";

    public static void add(Employee employee) {
        String createEmployeeQuery =
                "INSERT INTO " + TABLE_NAME + "(" +
                        ID + "," +
                        LOCATION_ID + "," +
                        NAME +
                        " ) VALUES (" +
                        "\""+ employee.getId() +
                        "\", \""+ employee.getLocationId() +
                        "\",\""+ employee.getName() + "\");";

        makeQuery(createEmployeeQuery);
    }

    public static Employee get(int id) {

        Employee employee;

        try {
            String getEmployeeString =
                    "SELECT * FROM " + TABLE_NAME +
                            " WHERE " + ID + " = \""+ id +"\";";
            makeQuery(getEmployeeString);
            ResultSet employeeResults = getResult();

            employeeResults.next();
            employee = new Employee(
                    employeeResults.getInt(ID),
                    employeeResults.getInt(LOCATION_ID),
                    employeeResults.getString(NAME));
            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Employee> getAll(){

        ObservableList<Employee> allEmployees = FXCollections.observableArrayList();
        Employee employee;
        try {
            String getEmployeeString =
                    "SELECT * FROM " + TABLE_NAME;
            makeQuery(getEmployeeString);
            ResultSet employeeResults = getResult();

            while (employeeResults.next()) {
                employee = new Employee(employeeResults.getInt(ID),
                        employeeResults.getInt(LOCATION_ID),
                        employeeResults.getString(NAME));
                allEmployees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allEmployees;
    }

    public static void update(Employee employee){
        String updateEmployeeQuery =
                "UPDATE Employees SET " +
                        ID + " = \""+ employee.getId()+ "\",\n" +
                        LOCATION_ID + " = \""+ employee.getLocationId()+ "\",\n" +
                        NAME + " = \"" + employee.getName()+ "\",\n" + ";";
        makeQuery(updateEmployeeQuery);
    }

    public static void delete(Employee employee){
        String deleteEmployeeQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE" + ID + "="+ employee.getId();
        makeQuery(deleteEmployeeQuery);
    }
    
}
