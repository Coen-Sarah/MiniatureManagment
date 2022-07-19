package com.example.miniaturemanagement.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVParser {

    public static ArrayList<String[]> parseCSV(String fileName){
        ArrayList<String[]> CSVRows = new ArrayList<String[]>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            String csvSplitBy = ",";

            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                CSVRows.add(row);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return CSVRows;
    }

}
