package com.example.miniaturemanagement.model;

import java.time.LocalDateTime;

public class Course {

    int id;
    String name;
    int locationId;
    LocalDateTime startTime;
    int managingEmployeeId;
    int numberOfAttendees;
    CustomSet courseSet;

    public Course(){}

    public Course(String name,
                  int locationId, LocalDateTime startTime, int managingEmployeeId, int numberOfAttendees,
                  CustomSet courseSet) {
        this.name = name;
        this.locationId = locationId;
        this.startTime = startTime;
        this.managingEmployeeId = managingEmployeeId;
        this.numberOfAttendees = numberOfAttendees;
        this.courseSet = courseSet;
    }

    public Course(String name,
                  int locationId, LocalDateTime startTime, int managingEmployeeId, int numberOfAttendees) {
        this.name = name;
        this.locationId = locationId;
        this.startTime = startTime;
        this.managingEmployeeId = managingEmployeeId;
        this.numberOfAttendees = numberOfAttendees;
    }

    public Course(int id, String name,
                  int locationId, LocalDateTime startTime, int managingEmployeeId, int numberOfAttendees) {
        this.id = id;
        this.name = name;
        this.locationId = locationId;
        this.startTime = startTime;
        this.managingEmployeeId = managingEmployeeId;
        this.numberOfAttendees = numberOfAttendees;
    }

    public Course(int id, String name,
                  int locationId, LocalDateTime startTime, int managingEmployeeId, int numberOfAttendees,
                  CustomSet courseSet) {
        this.id = id;
        this.name = name;
        this.locationId = locationId;
        this.startTime = startTime;
        this.managingEmployeeId = managingEmployeeId;
        this.numberOfAttendees = numberOfAttendees;
        this.courseSet = courseSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getManagingEmployeeId() {
        return managingEmployeeId;
    }

    public void setManagingEmployeeId(int managingEmployeeId) {
        this.managingEmployeeId = managingEmployeeId;
    }

    public int getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setNumberOfAttendees(int numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public CustomSet getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(CustomSet courseSet) {
        this.courseSet = courseSet;
    }
}
