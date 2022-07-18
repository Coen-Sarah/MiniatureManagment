package com.example.inventorycapstone.model.businessInfo;

public class Employee {

    int id;
    int locationId;
    String name;

    public Employee(){}

    public Employee(int id, int locationId, String name) {
        this.id = id;
        this.locationId = locationId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
