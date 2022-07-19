package com.example.miniaturemanagement.doa.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.miniaturemanagement.doa.database.Query.getResult;
import static com.example.miniaturemanagement.doa.database.Query.makeQuery;

public class UserDAO {

    public static final String TABLE_NAME = "User";
    public static final String ID = "UserId";
    public static final String NAME = "UserName";
    public static final String PASSWORD_HASH = "PasswordHash";
    public static final String PASSWORD_SALT = "PasswordSalt";

    public static void add(int id, String userName, String passwordHash, String passwordSalt){
        String createLocationQuery =
                "INSERT INTO " + TABLE_NAME + "(" +
                        ID + "," +
                        NAME + "," +
                        PASSWORD_HASH + "," +
                        PASSWORD_SALT +
                        " ) VALUES (" +
                        "\""+ id +
                        "\", \""+ userName +
                        "\", \""+ passwordHash +
                        "\",\""+ passwordSalt + "\");";

        makeQuery(createLocationQuery);
    }

    public static String[] get(String userName){
        String getUserInfoQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + NAME + " = \"" + userName + "\";";
        try {
            makeQuery(getUserInfoQuery);
            ResultSet userResults = getResult();

            userResults.next();
            return new String[]{userResults.getString(NAME),userResults.getString(PASSWORD_HASH), userResults.getString(PASSWORD_SALT)};
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
