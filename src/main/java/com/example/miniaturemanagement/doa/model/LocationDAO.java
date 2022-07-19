package com.example.miniaturemanagement.doa.model;

import com.example.miniaturemanagement.model.businessInfo.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.miniaturemanagement.doa.database.Query.getResult;
import static com.example.miniaturemanagement.doa.database.Query.makeQuery;

public class LocationDAO {

    public static final String TABLE_NAME = "Location";
    public static final String ID = "LocationId";
    public static final String NAME = "LocationName";
    public static final String ADDRESS = "Address";

    public static void add(Location location) {
        String createLocationQuery =
                "INSERT INTO " + TABLE_NAME + "(" +
                        ID + "," +
                        NAME + "," +
                        ADDRESS +
                        " ) VALUES (" +
                        "\""+ location.getId() +
                        "\", \""+ location.getName() +
                        "\",\""+ location.getAddress() + "\");";

        makeQuery(createLocationQuery);
    }

    public static Location get(int id) {

        Location location;

        try {
            String getLocationString =
                    "SELECT * FROM " + TABLE_NAME +
                            " WHERE " + ID + " = \""+ id +"\";";
            makeQuery(getLocationString);
            ResultSet locationResults = getResult();

            locationResults.next();
            location = new Location(
                            locationResults.getInt(ID),
                            locationResults.getString(NAME),
                            locationResults.getString(ADDRESS));
            return location;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Location> getAll(){

        ObservableList<Location> allLocations = FXCollections.observableArrayList();
        Location location;
        try {
            String getLocationString =
                    "SELECT * FROM " + TABLE_NAME;
            makeQuery(getLocationString);
            ResultSet locationResults = getResult();

            while (locationResults.next()) {
                location = new Location(locationResults.getInt(ID),
                        locationResults.getString(NAME),
                        locationResults.getString(ADDRESS));
                allLocations.add(location);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allLocations;
    }

    public static void update(Location location){
        String updateLocationQuery =
                "UPDATE Locations SET " +
                        ID + " = \""+ location.getId()+ "\",\n" +
                        NAME + " = \""+ location.getName()+ "\",\n" +
                        ADDRESS + " = \"" + location.getAddress()+ "\",\n" + ";";
        makeQuery(updateLocationQuery);
    }

    public static void delete(Location location){
        String deleteLocationQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE" + ID + "="+ location.getId();
        makeQuery(deleteLocationQuery);
    }

}
