package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.businessInfo.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.inventorycapstone.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        makeConnection();
    }

    @Test
    void successfullyGetsEmployeeFromDatabase() {
        String[] user = UserDAO.get("admin");
        assertEquals(3, user.length);
        assertEquals("admin", user[0]);

    }

}