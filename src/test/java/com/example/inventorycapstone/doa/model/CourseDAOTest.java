package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.Course;
import com.example.inventorycapstone.model.CustomSet;
import com.example.inventorycapstone.model.Miniature;
import com.example.inventorycapstone.model.NeededMiniature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

        ObservableList<NeededMiniature> miniatures = FXCollections.observableArrayList();
        miniatures.add( new NeededMiniature(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 1));
        miniatures.add( new NeededMiniature(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2));
        NeededMiniature addOn = new NeededMiniature(new Miniature(5, "Five", "Test", "Test",
                new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures);

        LocalDateTime dateTime = LocalDateTime.now();
        Course course = new Course("Test Course",
                1, dateTime , 1,
                10 , setC);

        int courseId = CourseDAO.add(course);
        assertTrue(courseId != -1);
    }

    @Test
    void getsCourseFromDatabase(){
        ObservableList<NeededMiniature> miniatures = FXCollections.observableArrayList();
        miniatures.add( new NeededMiniature(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 1));
        miniatures.add( new NeededMiniature(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2));
        NeededMiniature addOn = new NeededMiniature(new Miniature(5, "Five", "Test", "Test",
                new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures);

        LocalDateTime dateTime = LocalDateTime.now();
        Course course = new Course("Test Course",
                1, dateTime , 1,
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
        ObservableList<NeededMiniature> miniatures = FXCollections.observableArrayList();
        miniatures.add( new NeededMiniature(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 1));
        miniatures.add( new NeededMiniature(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2));
        NeededMiniature addOn = new NeededMiniature(new Miniature(5, "Five", "Test", "Test",
                new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures);
;
        LocalDateTime dateTime = LocalDateTime.now();
        Course insert = new Course("Test Course",
                1, dateTime , 1,
                10 , setC);

        int courseId = CourseDAO.add(insert);
        Course course = CourseDAO.get(courseId);

        course.setName("UpdatedCourse");


        course.getCourseSet().updateMiniature(miniatures.get(0));
        course.getCourseSet().removeMiniature(miniatures.get(2));
        course.getCourseSet().removeMiniature(miniatures.get(3));
        course.getCourseSet().addMiniature( new NeededMiniature(addOn.getMiniature(), 10));

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
        ObservableList<NeededMiniature> miniatures = FXCollections.observableArrayList();
        miniatures.add( new NeededMiniature(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 1));
        miniatures.add( new NeededMiniature(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 4));
        miniatures.add( new NeededMiniature(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2));
        NeededMiniature addOn = new NeededMiniature(new Miniature(5, "Five", "Test", "Test",
                new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8), 2);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures);

        LocalDateTime dateTime = LocalDateTime.now();
        Course insert = new Course("Test Course",
                1, dateTime , 1,
                10 , setC);

        int setId = SetDAO.add(setC);
        setC.setId(setId);
        assertTrue(setId != -1);
        SetDAO.delete(setC);
    }


}