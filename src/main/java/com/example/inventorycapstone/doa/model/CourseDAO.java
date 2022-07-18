package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.Course;
import com.example.inventorycapstone.model.CustomSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.example.inventorycapstone.doa.database.Query.getResult;
import static com.example.inventorycapstone.doa.database.Query.makeQuery;

public class CourseDAO {

    public static final String TABLE_NAME = "Course";
    public static final String ID = "CourseId";
    public static final String NAME = "CourseName";
    public static final String LOCATION_ID = "LocationId";
    public static final String DATETIME = "StartDateTime";
    public static final String EMPLOYEE_ID = "EmployeeId";
    public static final String SET_ID = "SetId";
    public static final String ATTENDEES = "NumberOfAttendees";


    public static int add(Course course) {
        int courseId = 0;
        int setId = SetDAO.add(course.getCourseSet(), true);
        String createCourseQuery =
                "INSERT INTO " + TABLE_NAME + "(" +
                        NAME + "," +
                        LOCATION_ID + "," +
                        DATETIME + "," +
                        EMPLOYEE_ID + "," +
                        SET_ID + "," +
                        ATTENDEES +
                        " ) VALUES (" +
                        "\""+ course.getName() +
                        "\", \""+ course.getLocationId() +
                        "\", \""+ toUTCTime(course.getStartTime()) +
                        "\", \""+ course.getManagingEmployeeId() +
                        "\", \""+ setId +
                        "\",\""+ course.getNumberOfAttendees() + "\");";
        makeQuery(createCourseQuery);

        try {
            ResultSet insertResult = getResult();
            if (insertResult.next()) {
                courseId = insertResult.getInt(1);
            }
            insertResult.close();
            return courseId;
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }

    }

    public static Course get(int id) {

        Course course;

        try {
            String getCourseString =
                    "SELECT * FROM " + TABLE_NAME +
                            " WHERE " + ID + " = \""+ id +"\";";
            makeQuery(getCourseString);
            ResultSet courseResults = getResult();

            courseResults.next();
            System.out.println(courseResults.getTimestamp(DATETIME).toLocalDateTime());
            course = new Course(
                    courseResults.getInt(ID),
                    courseResults.getString(NAME),
                    courseResults.getInt(LOCATION_ID),
                    fromUTCTime(courseResults.getTimestamp(DATETIME).toLocalDateTime()),
                    courseResults.getInt(EMPLOYEE_ID),
                    courseResults.getInt(ATTENDEES),
                    (CustomSet) SetDAO.get(courseResults.getInt(SET_ID)));
            return course;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static ObservableList<Course> getAll(){

        ObservableList<Course> allCourses = FXCollections.observableArrayList();
        Course course;
        try {
            String getCourseString =
                    "SELECT * FROM " + TABLE_NAME;
            makeQuery(getCourseString);
            ResultSet courseResults = getResult();

            while (courseResults.next()) {
                System.out.println(courseResults.getTimestamp(DATETIME).toLocalDateTime());

                course = new Course(
                        courseResults.getInt(ID),
                        courseResults.getString(NAME),
                        courseResults.getInt(LOCATION_ID),
                        fromUTCTime(courseResults.getTimestamp(DATETIME).toLocalDateTime()),
                        courseResults.getInt(EMPLOYEE_ID),
                        courseResults.getInt(ATTENDEES));
                    course.setCourseSet((CustomSet) SetDAO.get(courseResults.getInt(SET_ID)));

                allCourses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCourses;
    }

    public static void update(Course course){
        String updateCourseQuery =
                "UPDATE " + TABLE_NAME +  " SET " +
                        NAME + " = \""+ course.getName()+ "\",\n" +
                        LOCATION_ID + " = "+ course.getLocationId()+ ",\n" +
                        DATETIME + " = \""+ toUTCTime(course.getStartTime()) + "\",\n" +
                        EMPLOYEE_ID + " = "+ course.getManagingEmployeeId()+ ",\n" +
                        ATTENDEES + " = " + course.getNumberOfAttendees()+
                        " WHERE " + ID + " = " + course.getId() +";";
        makeQuery(updateCourseQuery);
        SetDAO.update(course.getCourseSet());
    }

    public static void delete(Course course){
        String deleteCourseQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + ID + " = "+ course.getId() + ";";
        makeQuery(deleteCourseQuery);
        SetDAO.delete(course.getCourseSet());
    }

    private static LocalDateTime toUTCTime(LocalDateTime time) {
        ZonedDateTime localZoneTime = time.atZone(ZoneId.systemDefault());
        ZonedDateTime utcTime = localZoneTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localDateTime = utcTime.toLocalDateTime();
        return localDateTime;
    }

    private static LocalDateTime fromUTCTime(LocalDateTime time) {
        ZonedDateTime localZoneTime = time.atZone(ZoneId.of("UTC"));
        ZonedDateTime utcTime = localZoneTime.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localDateTime = utcTime.toLocalDateTime();
        return localDateTime;
    }


}

