package com.example.inventorycapstone.model;

import com.example.inventorycapstone.doa.model.CourseDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void addMiniature(){
        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(00.50),
                5, 2, 6, miniatures, counts);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY,16,10,30);
        Course course = new Course("Test Course",
                1, calendar , 1,
                10 , setC);

        course.getCourseSet().addMiniature(addOn,4);

    }

    @Test
    void updateMiniature(){

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(00.50),
                5, 2, 6, miniatures, counts);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY,16,10,30);
        Course course = new Course("Test Course",
                1, calendar , 1,
                10 , setC);

    }

    @Test
    void deleteMiniature(){

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00), new BigDecimal(00.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(00.50),
                5, 2, 6, miniatures, counts);

    }


}