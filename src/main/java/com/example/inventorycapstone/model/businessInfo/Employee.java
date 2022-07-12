package com.example.inventorycapstone.model.businessInfo;

public class Employee {

    int employeeId;
    int locationId;
    String employeeName;

    public Employee(){}

    public Employee(int employeeId, int locationId, String employeeName) {
        this.employeeId = employeeId;
        this.locationId = locationId;
        this.employeeName = employeeName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
