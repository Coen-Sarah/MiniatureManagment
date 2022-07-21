package com.example.miniaturemanagement.util;

import java.io.*;
import java.util.ArrayList;

public class CSVParser {

    static PrintWriter writer;

    public static ArrayList<String[]> readCSV(String fileName){
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

    public static void startCSVWriter(String directoryName,String fileName){
        try {
            File directory = new File(directoryName);
            directory.mkdir();
            writer = new PrintWriter(new BufferedWriter(new FileWriter(directoryName + "/" + fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLine(String[] items){
        String contents = "";
        for(int i = 0; i < items.length - 1; i++){
            contents += items[i] + ",";
        }
        contents += items[items.length-1];
        writer.println(contents);
    }

    public static void closeCSV(){
        writer.close();
    }

}
