package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.businessInfo.Employee;
import com.example.inventorycapstone.model.businessInfo.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        makeConnection();
    }

    @Test
    void successfullyGetsEmployeeFromDatabase() {
        Employee employee = EmployeeDAO.get(1);
        assertEquals("Jeff Jeffinson", employee.getName());

    }

    @Test
    void successfullyGetsAllEmployeesFromDatabase() {
        assertEquals(5, EmployeeDAO.getAll().size());
    }

    @Test
    void successfullyGetsAllEmployeesAtOneLocationsFromDatabase() {
        assertEquals(2, EmployeeDAO.getAllFromLocation(1).size());
    }

}