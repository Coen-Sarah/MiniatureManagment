package com.example.inventorycapstone.doa.model;

import com.example.inventorycapstone.model.Miniature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.inventorycapstone.doa.database.Query.getResult;
import static com.example.inventorycapstone.doa.database.Query.makeQuery;

public class MiniatureDAO {

    public static final String TABLE_NAME = "Miniature";
    public static final String ID = "MiniatureId";
    public static final String NAME = "MiniatureName";
    public static final String BRAND = "Brand";
    public static final String SUPPLIER = "Supplier";
    public static final String WHOLESALE = "WholesalePrice";
    public static final String RETAIL_MARKUP = "RetailMarkup";
    public static final String LOW_STOCK = "LowStockAmount";
    public static final String OVER_STOCK = "OverStockAmount";
    public static final String CURRENT_STOCK = "CurrentStock";

    public static int add(Miniature miniature) {
        int miniatureId = 0;
        String createMiniatureQuery =
                "INSERT INTO " + TABLE_NAME + "(" +
                        NAME + "," +
                        BRAND + "," +
                        SUPPLIER + "," +
                        WHOLESALE + "," +
                        RETAIL_MARKUP + "," +
                        LOW_STOCK + "," +
                        OVER_STOCK + "," +
                        CURRENT_STOCK +
                        " ) VALUES (" +
                        "\""+ miniature.getName() +
                        "\", \""+ miniature.getBrand() +
                        "\", \""+ miniature.getSupplier() +
                        "\", \""+ miniature.getWholeSalePrice() +
                        "\", \""+ miniature.getRetailMarkup() +
                        "\", \""+ miniature.getLowStockAmount() +
                        "\", \""+ miniature.getOverStockAmount() +
                        "\",\""+ miniature.getCurrentStock() + "\");";

        makeQuery(createMiniatureQuery);

        try {
            ResultSet insertResult = getResult();
            if (insertResult.next()) {
                miniatureId = insertResult.getInt(1);
            }
            insertResult.close();
            return miniatureId;
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }

    }

    public static Miniature get(int id) {

        Miniature miniature;

        try {
            String getMiniatureString =
                    "SELECT * FROM " + TABLE_NAME +
                            " WHERE " + ID + " = \""+ id +"\";";
            makeQuery(getMiniatureString);
            ResultSet miniatureResults = getResult();

            miniatureResults.next();
            miniature = new Miniature(
                    miniatureResults.getInt(ID),
                    miniatureResults.getString(NAME),
                    miniatureResults.getString(BRAND),
                    miniatureResults.getString(SUPPLIER),
                    miniatureResults.getBigDecimal(WHOLESALE),
                    miniatureResults.getBigDecimal(RETAIL_MARKUP),
                    miniatureResults.getInt(LOW_STOCK),
                    miniatureResults.getInt(OVER_STOCK),
                    miniatureResults.getInt(CURRENT_STOCK));
            return miniature;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Miniature> getAll(){

        ObservableList<Miniature> allMiniatures = FXCollections.observableArrayList();
        Miniature miniature;
        try {
            String getMiniatureString =
                    "SELECT * FROM " + TABLE_NAME;
            makeQuery(getMiniatureString);
            ResultSet miniatureResults = getResult();

            while (miniatureResults.next()) {
                miniature = new Miniature(
                        miniatureResults.getInt(ID),
                        miniatureResults.getString(NAME),
                        miniatureResults.getString(BRAND),
                        miniatureResults.getString(SUPPLIER),
                        miniatureResults.getBigDecimal(WHOLESALE),
                        miniatureResults.getBigDecimal(RETAIL_MARKUP),
                        miniatureResults.getInt(LOW_STOCK),
                        miniatureResults.getInt(OVER_STOCK),
                        miniatureResults.getInt(CURRENT_STOCK));
                allMiniatures.add(miniature);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allMiniatures;
    }

    public static void update(Miniature miniature){
        String updateMiniatureQuery =
                "UPDATE "+ TABLE_NAME +" SET " +
                        ID + " = "+ miniature.getId()+ ",\n" +
                        NAME + " = \""+ miniature.getName()+ "\",\n" +
                        BRAND + " = \""+ miniature.getBrand()+ "\",\n" +
                        SUPPLIER + " = \""+ miniature.getSupplier()+ "\",\n" +
                        WHOLESALE + " = "+ miniature.getWholeSalePrice()+ ",\n" +
                        RETAIL_MARKUP + " = "+ miniature.getRetailMarkup()+ ",\n" +
                        LOW_STOCK + " = "+ miniature.getLowStockAmount()+ ",\n" +
                        OVER_STOCK + " = "+ miniature.getOverStockAmount()+ ",\n" +
                        CURRENT_STOCK + " = " + miniature.getCurrentStock()+
                        " WHERE " + ID + " = " + miniature.getId() + ";";
        makeQuery(updateMiniatureQuery);
    }

    public static void delete(Miniature miniature){
        String deleteMiniatureQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + ID + " = "+ miniature.getId() + ";";
        System.out.println(deleteMiniatureQuery);
        makeQuery(deleteMiniatureQuery);
    }
    
}
