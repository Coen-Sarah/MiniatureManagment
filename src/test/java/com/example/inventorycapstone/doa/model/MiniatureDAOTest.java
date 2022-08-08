package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.Miniature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class MiniatureDAOTest {
    private static Miniature miniature;
    private static int miniatureId;

    @BeforeAll
    static void beforeAll() throws Exception {
        makeConnection();
    }

    @Test
    void successfullyAddsMiniatureToDatabase() {
        miniature = new Miniature("UnitTest", "Test", "Test",
                new BigDecimal(10.00),new BigDecimal(00.50), 5, 3, 8);
        miniatureId = MiniatureDAO.add(miniature);
        assertTrue(miniatureId != -1);
    }

    @Test
    void successfullyGetsMiniatureFromDatabase() {
        miniature = new Miniature("UnitTest", "Test", "Test",
                new BigDecimal(10.00),new BigDecimal(00.50), 5, 3, 8);
        miniatureId = MiniatureDAO.add(miniature);
        miniature.setId(miniatureId);
        Miniature getMiniature = MiniatureDAO.get(miniatureId);
        assertEquals(getMiniature.getId(), miniature.getId());
        assertEquals(getMiniature.getName(), miniature.getName());
        assertEquals(getMiniature.getBrand(), miniature.getBrand());
    }

    @Test
    void successfullyUpdatesMiniatureInDatabase() {
        miniature = new Miniature("UnitTest", "Test", "Test",
                new BigDecimal(10.00),new BigDecimal(00.50), 5, 3, 8);
        miniatureId = MiniatureDAO.add(miniature);
        miniature.setId(miniatureId);
        miniature.setName("Update");
        MiniatureDAO.update(miniature);

        assertEquals("Update", MiniatureDAO.get(miniatureId).getName());
    }

    @Test
    void successfullyDeletesMiniatureFromDatabase() {
        miniature = new Miniature("UnitTest", "Test", "Test",
                new BigDecimal(10.00),new BigDecimal(00.50), 5, 3, 8);
        miniatureId = MiniatureDAO.add(miniature);
        miniature.setId(miniatureId);
        MiniatureDAO.delete(miniature);
    }
}