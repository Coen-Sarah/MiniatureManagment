package com.example.inventorycapstone.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CustomSetTest {

    @Test
    void addMiniature() {

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

        setC.addMiniature(addOn);

        assertEquals(5, setC.neededMiniatures.size());

    }

    @Test
    void getMiniature() {

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

        assertEquals("One", setC.getMiniature(1).getMiniature().getName());

    }

    @Test
    void updateMiniature() {

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

        miniatures.get(2).setCount(1);
        setC.updateMiniature(miniatures.get(2));

        assertEquals(1, setC.getNeededMiniatures().get(2).count);
        assertEquals(4, setC.neededMiniatures.size());

    }

    @Test
    void removeMiniature() {

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

        assertEquals(4, setC.neededMiniatures.size());
        setC.removeMiniature(miniatures.get(2));
        assertEquals(3, setC.neededMiniatures.size());
    }

    @Test
    void getWholeSalePriceOutputsCorrectValue() {

        ObservableList<NeededMiniature> miniatures = FXCollections.observableArrayList();
        miniatures.add( new NeededMiniature(
                new Miniature(1, "One", "Test", "Test",
                        new BigDecimal(10.50F), new BigDecimal(0.50), 5, 3, 8), 1));
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

        assertEquals( new BigDecimal(110.50) , setC.getWholesalePrice());

    }
}