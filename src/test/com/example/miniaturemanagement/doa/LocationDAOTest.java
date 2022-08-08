package com.example.miniaturemanagement.doa;

import com.example.miniaturemanagement.doa.model.LocationDAO;
import com.example.miniaturemanagement.model.businessInfo.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.miniaturemanagement.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class LocationDAOTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        makeConnection();
    }

    @Test
    void successfullyGetsLocationFromDatabase() {

        Location location = LocationDAO.get(1);
        assertEquals("Third Coast Gaming", location.getName());

    }

    @Test
    void successfullyGetsAllLocationsFromDatabase() {
        assertEquals(3, LocationDAO.getAll().size());
    }

}