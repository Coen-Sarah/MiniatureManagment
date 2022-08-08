package com.example.inventorycapstone.model;

import com.example.inventorycapstone.doa.model.CourseDAO;
import com.example.inventorycapstone.doa.model.MiniatureDAO;
import com.example.inventorycapstone.doa.model.SetDAO;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @BeforeEach
    void setUp() throws Exception {
        makeConnection();
        Inventory.setAllMiniatures(MiniatureDAO.getAll());
        Inventory.setAllSets(SetDAO.getAll());
        Inventory.setAllCourses(CourseDAO.getAll());
    }

    @Test
    void addMiniature() {
        Miniature miniature = new Miniature(0,"Test",
                                         "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                                     5, 1,10 );
        Inventory.addMiniature(miniature);

        assertEquals(MiniatureDAO.getAll().size()+1, Inventory.getAllMiniatures().size());
    }

    @Test
    void addSet() {

        OfficialSet officialSet = new OfficialSet( 0,"Test",
                                                "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                                            5, 1,10 );;
        CustomSet customSet = new CustomSet("Test", new BigDecimal(1.0),
                5, 2, 6);
        Inventory.addSet(officialSet);
        Inventory.addSet(customSet);

        assertEquals(SetDAO.getAll().size()+2, Inventory.getAllSets().size());

    }

    @Test
    void addCourse() {
        CustomSet customSet = new CustomSet("Test", new BigDecimal(1.0),
                5, 2, 6);

        LocalDateTime dateTime = LocalDateTime.now();
        Course course = new Course("Test Course",
                1, dateTime , 1,
                10 , customSet);

        Inventory.addCourse(course);

        assertEquals(CourseDAO.getAll().size()+1, Inventory.getAllCourses().size());
    }

    @Test
    void lookupMiniatureById() {
        Miniature result = Inventory.lookupMiniature(1);
        assertEquals("Rouge In Rogue", result.getName());
    }

    @Test
    void lookupMiniatureByNameSingleResult() {
        ObservableList<Miniature> result = Inventory.lookupMiniature("Rogue");
        assertEquals("Rouge In Rogue", result.get(0).getName());
    }

    @Test
    void lookupMiniatureByNameMultipleResults() {
        ObservableList<Miniature> result = Inventory.lookupMiniature("All-Terrain");
        assertEquals("All-Terrain Troubles: Dodgy Docks", result.get(0).getName());
        assertEquals("All-terrain Troubles: Caves and Cliffs", result.get(1).getName());
    }

    @Test
    void lookupSetById() {
        OfficialSet resultOfficial = (OfficialSet) Inventory.lookupSet(1);
        CustomSet resultCustom = (CustomSet) Inventory.lookupSet(4);
        assertEquals("The Journey Beings: Complete Adventuring Party", resultOfficial.getName());
        assertEquals("Wizards Cauldron Magicians Bundle", resultCustom.getName());
    }

    @Test
    void lookupSetByNameSingleResult() {
        ObservableList<MiniatureSet> result = Inventory.lookupSet("All-Terrain");
        assertEquals("All-Terrain Troubles: The Complete Set", result.get(0).getName());
    }

    @Test
    void lookupSetByNameMultipleResults() {
        ObservableList<MiniatureSet> result = Inventory.lookupSet("Mag");
        assertEquals("Wizards Cauldron Magicians Bundle", result.get(0).getName());
        assertEquals("Mages College Kids Miniature Painting Set", result.get(1).getName());
    }

    @Test
    void lookupCourseById() {
        Course result = Inventory.lookupCourse(1);
        assertEquals("Mages College Kids Miniature Painting", result.getName());
    }

    @Test
    void lookupCourseByNameSingleResult() {
        ObservableList<Course> result = Inventory.lookupCourse("Terrain");
        assertEquals("Terrain Tips and Tricks", result.get(0).getName());
    }

    @Test
    void lookupCourseByNameMultipleResults() {
        ObservableList<Course> result = Inventory.lookupCourse("T");
        assertEquals("Mages College Kids Miniature Painting", result.get(0).getName());
        assertEquals("Terrain Tips and Tricks", result.get(1).getName());
    }

    @Test
    void updateMiniature() {
        Miniature miniature = new Miniature(0,"Test",
                "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                5, 1,10 );
        Miniature update = new Miniature(0,"Update",
                "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                5, 1,10 );
        Inventory.addMiniature(miniature);
        Inventory.updateMiniature(update);
        assertEquals("Update", Inventory.getAllMiniatures().get(5).getName());
    }

    @Test
    void updateSet() {

        OfficialSet set = new OfficialSet(0,"Test",
                "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                5, 1,10 );
        OfficialSet update = new OfficialSet(0,"Update",
                "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                5, 1,10 );
        Inventory.addSet(set);
        Inventory.updateSet(update);
        assertEquals("Update", Inventory.getAllSets().get(6).getName());

    }

    @Test
    void updateCourse() {
        CustomSet customSet = new CustomSet("Test", new BigDecimal(1.0),
                5, 2, 6);

        LocalDateTime dateTime = LocalDateTime.now();
        Course course = new Course("Test", 1, dateTime , 1, 10, customSet);
        Course update = new Course("Update", 1, dateTime , 1, 10, customSet);

        Inventory.addCourse(course);
        Inventory.updateCourse(update);

        assertEquals("Update", Inventory.getAllCourses().get(2).getName());
    }

    @Test
    void deleteMiniature() {
        Miniature miniature = new Miniature(0,"Test",
                "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                5, 1,10 );
        Inventory.addMiniature(miniature);
        Inventory.deleteMiniature(miniature);
        assertEquals(MiniatureDAO.getAll().size(), Inventory.getAllMiniatures().size());
    }

    @Test
    void deleteSet() {
        OfficialSet officialSet = new OfficialSet( 0,"Test",
                "Test", "Test", new BigDecimal(1.0), new BigDecimal(1.0),
                5, 1,10 );;
        CustomSet customSet = new CustomSet("Test", new BigDecimal(1.0),
                5, 2, 6);
        Inventory.addSet(officialSet);
        Inventory.addSet(customSet);
        Inventory.deleteSet(officialSet);
        Inventory.deleteSet(customSet);

        assertEquals(SetDAO.getAll().size(), Inventory.getAllSets().size());
    }

    @Test
    void deleteCourse() {

        CustomSet customSet = new CustomSet("Test", new BigDecimal(1.0),
                5, 2, 6);

        LocalDateTime dateTime = LocalDateTime.now();
        Course course = new Course("Test", 1, dateTime , 1, 10, customSet);

        Inventory.addCourse(course);
        Inventory.deleteCourse(course);

        assertEquals(CourseDAO.getAll().size(), Inventory.getAllCourses().size());

    }

    @Test
    void getAllMiniatures() {
        assertEquals(MiniatureDAO.getAll().size(), Inventory.getAllMiniatures().size());
    }

    @Test
    void getAllSets() {
        assertEquals(SetDAO.getAll().size(), Inventory.getAllSets().size());
    }

    @Test
    void getAllCourses() {
        assertEquals(CourseDAO.getAll().size(), Inventory.getAllCourses().size());
    }
}