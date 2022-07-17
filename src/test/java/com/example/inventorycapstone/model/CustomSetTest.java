package com.example.inventorycapstone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CustomSetTest {

    @Test
    void addMiniature() {

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures, counts);

        setC.addMiniature(addOn, 1);

        assertEquals(5, setC.neededMiniatures.size());

    }

    @Test
    void getMiniature() {

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures, counts);

        assertEquals("One", setC.getMiniature(1).getName());

    }

    @Test
    void updateMiniature() {

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures, counts);

        setC.updateMiniatureCount(miniatures.get(2), 1);

        assertEquals(1, setC.getMiniatureCount().get(2));
        assertEquals(4, setC.neededMiniatures.size());

    }

    @Test
    void removeMiniature() {

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures, counts);

        assertEquals(4, setC.neededMiniatures.size());
        setC.removeMiniature(miniatures.get(2));
        assertEquals(3, setC.neededMiniatures.size());
    }

    @Test
    void getWholeSalePriceOutputsCorrectValue() {

        ObservableList<Miniature> miniatures = FXCollections.observableArrayList();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        miniatures.add(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.50F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(1);
        miniatures.add(
                new Miniature(2, "Two", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(4);
        miniatures.add(
                new Miniature(3, "Three", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        miniatures.add(
                new Miniature(4, "Four", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8));
        counts.add(2);
        Miniature addOn =
                new Miniature(5, "Five", "Test", "Test",
                        new BigDecimal(10.00F), new BigDecimal(0.50), 5, 3, 8);

        CustomSet setC = new CustomSet("Test", new BigDecimal(0.50),
                5, 2, 6, miniatures, counts);

        assertEquals( new BigDecimal(90.50) , setC.getWholesalePrice());

    }
}