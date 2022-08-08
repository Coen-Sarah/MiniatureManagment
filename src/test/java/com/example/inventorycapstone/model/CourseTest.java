package com.example.inventorycapstone.model;

import com.example.inventorycapstone.doa.model.CourseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void addMiniature(){
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

        course.getCourseSet().addMiniature(new NeededMiniature(addOn.miniature, addOn.count));

    }

    @Test
    void updateMiniature(){

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

    }

    @Test
    void deleteMiniature(){

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

    }


}