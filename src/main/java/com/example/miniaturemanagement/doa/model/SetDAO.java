package com.example.miniaturemanagement.doa.model;

import com.example.miniaturemanagement.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.miniaturemanagement.doa.database.Query.getResult;
import static com.example.miniaturemanagement.doa.database.Query.makeQuery;

public class SetDAO {

    public static final String TABLE_NAME = "MiniatureSet";
    public static final String ID = "SetId";
    public static final String NAME = "SetName";
    public static final String RETAIL_MARKUP = "RetailMarkup";
    public static final String LOW_STOCK = "LowStockAmount";
    public static final String OVER_STOCK = "OverStockAmount";
    public static final String CURRENT_STOCK = "CurrentStock";
    public static final String IS_OFFICIAL_SET = "IsOfficialSet";
    public static final String IS_CLASS_SET = "IsClassSet";

    public static final String OFFICIAL_SET_TABLE = "OfficialSet";
    public static final String BRAND = "Brand";
    public static final String SUPPLIER = "Supplier";
    public static final String WHOLESALE = "WholesalePrice";

    public static final String CUSTOM_SET_TABLE = "CustomSetMiniatureLink";
    public static final String MINIATURE_ID = "MiniatureId";
    public static final String COUNT = "Count";

    static final int OFFICIAL_SET = 1;
    static final int NOT_OFFICIAL_SET = 0;

    static final int CLASS_SET = 1;
    static final int NOT_CLASS_SET = 0;

    public static int add(MiniatureSet set){
        return add(set, false);
    }

    public static int add(MiniatureSet set, boolean isClassSet) {
        int setId = -1;
        int setType = NOT_OFFICIAL_SET;
        int classSetType = NOT_CLASS_SET;
        if(set instanceof OfficialSet){
            setType = OFFICIAL_SET;
        } else {
            if(isClassSet){
                classSetType = CLASS_SET;
            }
        }

        String createSetQuery =
                "INSERT INTO " + TABLE_NAME + "(" +
                        NAME + "," +
                        RETAIL_MARKUP + "," +
                        LOW_STOCK + "," +
                        OVER_STOCK + "," +
                        CURRENT_STOCK + "," +
                        IS_OFFICIAL_SET + "," +
                        IS_CLASS_SET +
                        " ) VALUES (" +
                        "\""+ set.getName() +
                        "\", \""+ set.getRetailMarkup() +
                        "\", \""+ set.getLowStockAmount() +
                        "\", \""+ set.getOverStockAmount() +
                        "\", \""+ set.getCurrentStock() +
                        "\", \""+ setType +
                        "\",\""+ classSetType + "\");";

        makeQuery(createSetQuery);

        try {
            ResultSet insertResult = getResult();
            if (insertResult.next()) {
                setId = insertResult.getInt(1);
            }
            insertResult.close();
            if(set instanceof OfficialSet){
                addSetDetails((OfficialSet) set, setId);
            } else if (set instanceof CustomSet){
                addSetDetails((CustomSet) set, setId);
            }
            return setId;
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    private static void addSetDetails(OfficialSet set, int setId) {
        String createOfficialSetQuery =
                "INSERT INTO " + OFFICIAL_SET_TABLE + "(" +
                        ID + "," +
                        BRAND + "," +
                        SUPPLIER + "," +
                        WHOLESALE +
                        " ) VALUES (" +
                        "\""+ setId +
                        "\", \""+ set.getBrand() +
                        "\", \""+ set.getSupplier() +
                        "\",\""+ set.getWholeSalePrice() + "\");";

        makeQuery(createOfficialSetQuery);
    }

    private static void addSetDetails(CustomSet set, int setId) {
        String createCustomSetQuery =
                "INSERT INTO " + CUSTOM_SET_TABLE + "(" +
                        ID + "," +
                        MINIATURE_ID + "," +
                        COUNT +
                        " ) VALUES ";
        for (int i = 0; i < set.getNeededMiniatures().size(); i++) {
            createCustomSetQuery +=
                    "(\"" + setId +
                    "\", \"" + set.getNeededMiniatures().get(i).getMiniature().getId() +
                    "\",\"" + set.getNeededMiniatures().get(i).getCount() + "\"),";
        }
        createCustomSetQuery = createCustomSetQuery.substring(0,createCustomSetQuery.length() - 1) + ";";
        System.out.println(createCustomSetQuery);
        makeQuery(createCustomSetQuery);
    }

    public static MiniatureSet get(int id) {

        try {
            String getSetString =
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id + ";";

            makeQuery(getSetString);
            ResultSet setResults = getResult();

            setResults.next();
            if(setResults.getBoolean(IS_OFFICIAL_SET)){
                return getOfficialSetDetails(setResults.getInt(ID));
            }else{
                return getCustomSetDetails(setResults.getInt(ID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static OfficialSet getOfficialSetDetails(int setId) throws SQLException {

        String getOfficialSetQuery =
                "SELECT * FROM " + TABLE_NAME + ", " + OFFICIAL_SET_TABLE +
                " WHERE " + OFFICIAL_SET_TABLE + "." + ID + " = " + TABLE_NAME + "." + ID +
                        " AND " + TABLE_NAME + "." + ID + " = " + setId + ";";

        makeQuery(getOfficialSetQuery);
        ResultSet setResults = getResult();

        setResults.next();

        OfficialSet set = new OfficialSet(
                setResults.getInt(ID),
                setResults.getString(NAME),
                setResults.getString(BRAND),
                setResults.getString(SUPPLIER),
                setResults.getBigDecimal(WHOLESALE),
                setResults.getBigDecimal(RETAIL_MARKUP),
                setResults.getInt(CURRENT_STOCK),
                setResults.getInt(LOW_STOCK),
                setResults.getInt(OVER_STOCK)

        );
        return set;
    }

    private static CustomSet getCustomSetDetails(int setId) throws SQLException {
        String getCustomSetQuery =
                "SELECT * FROM " + TABLE_NAME +
                        " LEFT JOIN " + CUSTOM_SET_TABLE + " ON " +  CUSTOM_SET_TABLE + "." + ID + " = " + TABLE_NAME + "." + ID +
                        " WHERE " + TABLE_NAME + "." + ID + " = " + setId + ";";

        makeQuery(getCustomSetQuery);
        ResultSet setResults = getResult();

        setResults.next();


            CustomSet set = new CustomSet(
                    setResults.getInt(ID),
                    setResults.getString(NAME),
                    setResults.getBigDecimal(RETAIL_MARKUP),
                    setResults.getInt(CURRENT_STOCK),
                    setResults.getInt(LOW_STOCK),
                    setResults.getInt(OVER_STOCK));

        do{
            if(setResults.getInt(COUNT) != 0){
                set.addMiniature(new NeededMiniature(MiniatureDAO.get(setResults.getInt(MINIATURE_ID)), setResults.getInt(COUNT)));
            }
        } while (setResults.next());


        return set;
    }

    public static ObservableList<MiniatureSet> getAll(){

        ObservableList<MiniatureSet> allSets = FXCollections.observableArrayList();
        try {
            String getSetString =
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + IS_CLASS_SET + " = false;";
            makeQuery(getSetString);
            ResultSet setResults = getResult();

            while(setResults.next()) {
                if (setResults.getBoolean(IS_OFFICIAL_SET)) {
                    allSets.add(getOfficialSetDetails(setResults.getInt(ID)));
                } else {
                    allSets.add(getCustomSetDetails(setResults.getInt(ID)));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return allSets;
    }

    public static ObservableList<MiniatureSet> getAllWithCourseSets(){

        ObservableList<MiniatureSet> allSets = FXCollections.observableArrayList();
        try {
            String getSetString =
                    "SELECT * FROM " + TABLE_NAME + ";";
            makeQuery(getSetString);
            ResultSet setResults = getResult();

            while(setResults.next()) {
                if (setResults.getBoolean(IS_OFFICIAL_SET)) {
                    allSets.add(getOfficialSetDetails(setResults.getInt(ID)));
                } else {
                    allSets.add(getCustomSetDetails(setResults.getInt(ID)));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return allSets;
    }

    public static void update(MiniatureSet set){
        String updateSetQuery =
                "UPDATE " + TABLE_NAME + " SET " +
                        NAME + " = \""+ set.getName()+ "\",\n" +
                        RETAIL_MARKUP + " = "+ set.getRetailMarkup()+ ",\n" +
                        LOW_STOCK + " = "+ set.getLowStockAmount()+ ",\n" +
                        OVER_STOCK + " = "+ set.getOverStockAmount()+ ",\n" +
                        CURRENT_STOCK + " = " + set.getCurrentStock()+ "" +
                        " WHERE " + ID + " = " + set.getId() + ";";
        System.out.println(updateSetQuery);
        makeQuery(updateSetQuery);

        if(set instanceof OfficialSet){
            updateOfficialSetDetails((OfficialSet) set);
        } else {
            updateCustomSetDetails((CustomSet) set);
        }
    }

    private static void updateOfficialSetDetails(OfficialSet set) {
        String updateOfficialSetQuery =
                "UPDATE " + OFFICIAL_SET_TABLE + " SET " +
                        BRAND + " = \""+ set.getBrand()+ "\",\n" +
                        SUPPLIER + " = \""+ set.getSupplier()+ "\",\n" +
                        WHOLESALE + " = " + set.getWholeSalePrice()+ ";";
        makeQuery(updateOfficialSetQuery);
    }

    private static void updateCustomSetDetails(CustomSet set) {
        String updateCustomSetQuery =
                "DELETE FROM " + CUSTOM_SET_TABLE + " WHERE " + ID + " = " + set.getId() + ";";
        System.out.println(updateCustomSetQuery);
        makeQuery(updateCustomSetQuery);
        addSetDetails(set, set.getId());

    }

    public static void delete(MiniatureSet set){
        String deleteSetQuery = "DELETE FROM " + TABLE_NAME +
                " WHERE " + ID + " = "+ set.getId() + ";";
        makeQuery(deleteSetQuery);
    }
}
