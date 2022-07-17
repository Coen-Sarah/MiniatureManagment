package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.Course;
import com.example.inventorycapstone.model.CustomSet;
import com.example.inventorycapstone.model.Miniature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        makeConnection();
    }

    @Test
    void addsCourseToDatabase() {

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(-1, "One", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(-2, "Two", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(-3, "Three", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(-4, "Four", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(-5, "Five", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8);

        for(int i = 0; i < miniatures.size(); i++) {
            miniatures.get(i).setId(MiniatureDAO.add(miniatures.get(i)));
        }
        MiniatureDAO.add(addOn);

        CustomSet setC = new CustomSet("Test", new BigDecimal(00.50),
                5, 2, 6, miniatures, counts);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY,16,10,30);
        System.out.println( calendar.get(Calendar.YEAR) + " " +
                            calendar.get(Calendar.MONTH) + " " +
                            calendar.get(Calendar.DAY_OF_MONTH) + " " +
                            calendar.get(Calendar.HOUR) + ":" +
                            calendar.get(Calendar.MINUTE) + " " +
                            calendar.get(Calendar.AM_PM));
        Course course = new Course("Test Course",
                1, calendar , 1,
                10 , setC);

        int courseId = CourseDAO.add(course);
        assertTrue(courseId != -1);
    }

    @Test
    void getsCourseFromDatabase(){
        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(-1, "One", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(-2, "Two", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(-3, "Three", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(-4, "Four", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(-5, "Five", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8);

        for(int i = 0; i < miniatures.size(); i++) {
            miniatures.get(i).setId(MiniatureDAO.add(miniatures.get(i)));
        }
        MiniatureDAO.add(addOn);

        CustomSet setC = new CustomSet("Test", new BigDecimal(00.50),
                5, 2, 6, miniatures, counts);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY,16,10,30);
        System.out.println( calendar.get(Calendar.YEAR) + " " +
                calendar.get(Calendar.MONTH) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + " " +
                calendar.get(Calendar.HOUR) + ":" +
                calendar.get(Calendar.MINUTE) + " " +
                calendar.get(Calendar.AM_PM));
        Course course = new Course("Test Course",
                1, calendar , 1,
                10 , setC);

        int courseId = CourseDAO.add(course);
        course.setId(courseId);
        assertTrue(courseId != -1);

        Course test = CourseDAO.get(courseId);

        assertEquals(course.getName(), test.getName());
        assertEquals(course.getId(), test.getId());
        System.out.println(course.getCourseSet().getName());
        System.out.println(test.getCourseSet().getName());
        assertEquals(course.getCourseSet().getName(), test.getCourseSet().getName());
        assertEquals(0, course.getCourseSet().getWholesalePrice().compareTo(test.getCourseSet().getWholesalePrice()));
    }

    @Test
    void updatesCourseFromDatabase(){
        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(-1, "One", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(-2, "Two", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(-3, "Three", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(-4, "Four", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(-5, "Five", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8);

        for(int i = 0; i < miniatures.size(); i++) {
            miniatures.get(i).setId(MiniatureDAO.add(miniatures.get(i)));
        }
        addOn.setId(MiniatureDAO.add(addOn));

        CustomSet setC = new CustomSet("Test", new BigDecimal(00.50),
                5, 2, 6, miniatures, counts);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY,16,10,30);
        Course insert = new Course("Test Course",
                1, calendar , 1,
                10 , setC);

        int courseId = CourseDAO.add(insert);
        Course course = CourseDAO.get(courseId);

        course.setName("UpdatedCourse");


        course.getCourseSet().updateMiniatureCount(miniatures.get(0),4);
        course.getCourseSet().removeMiniature(miniatures.get(2));
        course.getCourseSet().removeMiniature(miniatures.get(3));
        course.getCourseSet().addMiniature(addOn, 10);

        System.out.println("--------UPDATE STARTING---------");
        CourseDAO.update(course);
        Course test = CourseDAO.get(courseId);

        assertEquals(course.getName(), test.getName());
        assertEquals(course.getId(), test.getId());
        assertEquals(course.getCourseSet().getWholesalePrice(), test.getCourseSet().getWholesalePrice());
        assertEquals(course.getCourseSet().getNeededMiniatures().size(), test.getCourseSet().getNeededMiniatures().size());

    }

    @Test
    void deletesCourseFromDatabase(){
        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(-1, "One", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(-2, "Two", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(-3, "Three", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(-4, "Four", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature("Five", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8);

        for(int i = 0; i < miniatures.size(); i++) {
            miniatures.get(i).setId(MiniatureDAO.add(miniatures.get(i)));
        }
        addOn.setId(MiniatureDAO.add(addOn));

        CustomSet setC = new CustomSet("Test", new BigDecimal(00.50),
                5, 2, 6, miniatures, counts);

        int setId = SetDAO.add(setC);
        setC.setId(setId);
        assertTrue(setId != -1);
        SetDAO.delete(setC);
    }


}