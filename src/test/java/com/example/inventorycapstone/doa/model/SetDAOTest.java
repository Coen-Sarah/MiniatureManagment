package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.CustomSet;
import com.example.inventorycapstone.model.Miniature;
import com.example.inventorycapstone.model.OfficialSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class SetDAOTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        makeConnection();
    }

    @Test
    void addsCustomSetsToDatabase() {

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

        int setId = SetDAO.add(setC);
        assertTrue(setId != -1);
    }

    @Test
    void addsOfficialSetToDatabase(){
        OfficialSet setO = new OfficialSet("OfficialSet", "Test", "Test",
                new BigDecimal(00.50), new BigDecimal(00.50),
                7, 5, 10);
        int setId = SetDAO.add(setO);
        assertTrue(setId != -1);
    }

    @Test
    void getsCustomSetFromDatabase(){
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

        int setId = SetDAO.add(setC);
        setC.setId(setId);
        assertTrue(setId != -1);

        assertTrue(SetDAO.get(setId) instanceof CustomSet);
        CustomSet customSet = (CustomSet) SetDAO.get(setId);
        assertEquals(setC.getName(), customSet.getName());
        assertEquals(setC.getId(), customSet.getId());
    }

    @Test
    void getsOfficialSetFromDatabase(){
        OfficialSet setO = new OfficialSet("OfficialSet", "Test", "Test",
                new BigDecimal(00.50), new BigDecimal(00.50),
                7, 5, 10);
        int setId = SetDAO.add(setO);
        setO.setId(setId);
        assertTrue(setId != -1);

        assertTrue(SetDAO.get(setId) instanceof OfficialSet);
        OfficialSet officialSet = (OfficialSet) SetDAO.get(setId);
        assertEquals(setO.getName(), officialSet.getName());
        assertEquals(setO.getId(), officialSet.getId());
        assertEquals(setO.getBrand(), officialSet.getBrand());
        assertEquals( 0 ,setO.getWholeSalePrice().compareTo(officialSet.getWholeSalePrice()));

    }

    @Test
    void updatesCustomSetFromDatabase(){
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

        int setId = SetDAO.add(setC);
        setC.setId(setId);
        assertTrue(setId != -1);

        setC.setName("Custom Update");
        //setC.getNeededMiniatures().put(addOn, (Integer)1);

        SetDAO.update(setC);

        CustomSet update = (CustomSet) SetDAO.get(setId);

        assertEquals(setC.getName(), update.getName());
        assertEquals(setC.getNeededMiniatures().size(), update.getNeededMiniatures().size());

    }

    @Test
    void updatesOfficialSetFromDatabase(){
        OfficialSet setO = new OfficialSet("TestSet", "Test", "Test",
                new BigDecimal(00.50), new BigDecimal(00.50),
                7, 5, 10);
        int setId = SetDAO.add(setO);
        assertTrue(setId != -1);

        setO.setId(setId);
        setO.setName("Custom Update");
        SetDAO.update(setO);
        OfficialSet update = (OfficialSet) SetDAO.get(setId);
        assertEquals(setO.getName(), update.getName());
    }

    @Test
    void deletesCustomSetFromDatabase(){
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

        int setId = SetDAO.add(setC);
        setC.setId(setId);
        assertTrue(setId != -1);
        SetDAO.delete(setC);
    }

    @Test
    void deletesOfficialSetFromDatabase(){
        OfficialSet setO = new OfficialSet("TestSet", "Test", "Test",
                new BigDecimal(00.50), new BigDecimal(00.50),
                7, 5, 10);
        int setId = SetDAO.add(setO);
        setO.setId(setId);
        assertTrue(setId != -1);
        SetDAO.delete(setO);
    }

}