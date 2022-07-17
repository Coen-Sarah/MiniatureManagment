package com.example.inventorycapstone.util;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.example.inventorycapstone.util.HashController.comparePassword;
import static org.junit.jupiter.api.Assertions.*;

class HashControllerTest {

    @Test
    void returnsFalseWhenPasswordIsIncorrect() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String correctPassword = "admin";
        String incorrectPassword = "Admin";
        String passwordHash = "c5678edf280c3847cd21477658e1908d";
        String passwordSalt = "6069c9214118c5a0927fe286e97023cd";
        assertEquals(false, comparePassword(incorrectPassword,passwordHash,passwordSalt));
    }

    @Test
    void returnsTrueWhenPasswordIsCorrect() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String correctPassword = "admin";
        String passwordHash = "c5678edf280c3847cd21477658e1908d";
        String passwordSalt = "6069c9214118c5a0927fe286e97023cd";
        assertEquals(true, comparePassword(correctPassword,passwordHash,passwordSalt));
    }

}