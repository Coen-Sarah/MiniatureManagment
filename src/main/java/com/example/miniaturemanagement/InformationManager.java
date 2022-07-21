package com.example.miniaturemanagement;

import com.example.miniaturemanagement.doa.model.EmployeeDAO;
import com.example.miniaturemanagement.doa.model.LocationDAO;
import com.example.miniaturemanagement.doa.model.UserDAO;
import com.example.miniaturemanagement.model.businessInfo.Employee;
import com.example.miniaturemanagement.model.businessInfo.Location;
import com.example.miniaturemanagement.util.HashEncoder;

import java.util.Scanner;

import static com.example.miniaturemanagement.doa.database.DBConnection.makeConnection;
import static com.example.miniaturemanagement.util.HashEncoder.toHex;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class InformationManager {
    private static Scanner scanner;
    public static void main(String args[]) throws Exception {
        makeConnection();
        scanner = new Scanner(System.in);
        do {
            System.out.println("Please enter:\n"+
                    "1 to add a new application User\n" +
                    "2 to add a new Location or\n" +
                    "3 to Add a new Employee");
            String input = scanner.next();
            scanner.nextLine();
            if(isNumeric(input)){
                if(Integer.parseInt(input) == 1){
                    addUser();
                    break;
                } else if(Integer.parseInt(input) == 2){
                    addLocation();
                    break;
                } else if(Integer.parseInt(input) == 3){
                    addEmployee();
                    break;
                }
            }
            System.out.println("Please enter a valid number.");
        } while(true);
    }

    private static void addUser() {
        System.out.println("Please enter the username.");
        String user = scanner.nextLine();
        System.out.println("Please enter the password.");
        String pass = scanner.nextLine();

        try {

            byte[][] encoded = HashEncoder.encodePassword(pass);
            UserDAO.add(user, toHex(encoded[1]), toHex(encoded[0]));
            System.out.println(user + " has been added to the database.");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("There was an error while attempting to add a new user.");
            System.out.println("Please check the database to see if the user was successfully added and try again.");
        }
    }

    private static void addLocation() {
        System.out.println("Please enter the shop name.");
        String name = scanner.nextLine().trim();
        System.out.println("Please enter the shop address.");
        String address = scanner.nextLine().trim();
        LocationDAO.add(new Location(name,address));
        System.out.println(name + " at " + address + " has been added to the database.");
    }

    private static void addEmployee(){
            System.out.println("Please enter the employee's name.");
            String name = scanner.nextLine().trim();
            String locationId;
            do {
                System.out.println("Please enter the employee's location id number.");
                locationId = scanner.nextLine().trim();
            }while(!isNumeric(locationId));
            EmployeeDAO.add(new Employee(Integer.parseInt(locationId), name));
            System.out.println(name + " has been added to the database.");
    }
}
