package com.example.inventorycapstone.model;

public class Course {

    int courseId;
    String courseName;
    int locationId;
    int managingEmployeeId;
    int numberOfAttendees;
    CustomSet neededMiniatures;

    public Course(){}

    public Course(String courseName,
                  int locationId, int managingEmployeeId, int numberOfAttendees,
                  CustomSet neededMiniatures) {
        this.courseName = courseName;
        this.locationId = locationId;
        this.managingEmployeeId = managingEmployeeId;
        this.numberOfAttendees = numberOfAttendees;
        this.neededMiniatures = neededMiniatures;
    }

    public Course(int courseId, String courseName,
                  int locationId, int managingEmployeeId, int numberOfAttendees,
                  CustomSet neededMiniatures) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.locationId = locationId;
        this.managingEmployeeId = managingEmployeeId;
        this.numberOfAttendees = numberOfAttendees;
        this.neededMiniatures = neededMiniatures;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

    public CustomSet getNeededMiniatures() {
        return neededMiniatures;
    }

    public void setNeededMiniatures(CustomSet neededMiniatures) {
        this.neededMiniatures = neededMiniatures;
    }
}
