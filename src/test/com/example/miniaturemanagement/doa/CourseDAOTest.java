package com.example.miniaturemanagement.doa;

import com.example.miniaturemanagement.doa.model.CourseDAO;
import com.example.miniaturemanagement.doa.model.SetDAO;
import com.example.miniaturemanagement.model.Course;
import com.example.miniaturemanagement.model.CustomSet;
import com.example.miniaturemanagement.model.Miniature;
import com.example.miniaturemanagement.model.NeededMiniature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;

import static com.example.miniaturemanagement.doa.database.DBConnection.makeConnection;
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
        Course test = CourseDAO.get(2);

        assertEquals("Terrain Tips and Tricks", test.getName());
        assertEquals(2, test.getId());
        assertEquals("Terrain Tips and Tricks Class Set", test.getCourseSet().getName());
    }

    @Test
    void updatesCourseFromDatabase(){
        Course course = CourseDAO.get(1);
        course.setName("Course Update");
        CourseDAO.update(course);

        assertEquals("Course Update", CourseDAO.get(1).getName());

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