package com.example.miniaturemanagement.doa;

import com.example.miniaturemanagement.doa.model.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.miniaturemanagement.doa.database.DBConnection.makeConnection;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        makeConnection();
    }

    @Test
    void successfullyGetsUserFromDatabase() {
        String[] user = UserDAO.get("admin");
        assertEquals(3, user.length);
        assertEquals("admin", user[0]);

    }

}