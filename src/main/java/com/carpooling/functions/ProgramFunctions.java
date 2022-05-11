package com.carpooling.functions;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.carpooling.models.Reservation;
import com.carpooling.models.User;

public class ProgramFunctions {

    private static FileManager fileData;

    public ProgramFunctions() {

        fileData = new FileManager();
        fileData.importFiles();
    }

    public void start() {

        // user input choice
        Scanner scanner = new Scanner(System.in);
        int userInput = 0;

        do {

            System.out.println("Enter 1: Show all trips");
            System.out.println("Enter 2: Book a trip");
            System.out.println("Enter 3: Delete a reservation");
            System.out.println("Enter 4: Add a new user");
            System.out.println("Enter 5: Export a file with available trips");
            System.out.println("Enter 0: EXIT\n");

            userInput = Integer.parseInt(scanner.nextLine());

            // case "not a valid number"
            if ((userInput) < 0 || (userInput) > 5) {

                System.out.println("Please enter a valid number!\n");
                continue;
            }

            // displaying all functions
            switch (userInput) {
                case 1:

                    printAllTrips();
                    break;

                case 2:

                    bookATrip(scanner);
                    break;

                case 3:

                    deleteReservation(scanner);
                    ;
                    break;

                case 4:

                    addUser(scanner);
                    break;

                case 5:

                    writeCSVFile();
                    break;

                default:

                    break;
            }
        } while (userInput != 0);

        scanner.close();
    }

    private static void printAllTrips() {

        System.out.println("\n\nID | Date | Duration(hours) | Departure City | Arrival City | Available\n");

        fileData.getTripsCollection().stream().forEach(t -> System.out.println(t.toString()));
    }

    private static void addUser(Scanner scanner) {

        String[] newUserData = new String[6];
        boolean condition = false;

        // collecting user data
        do {
            System.out.println("\nEnter new user ID:");
            newUserData[0] = scanner.nextLine();

            // checking if the user is already in the system
            if (fileData.containsUser(Integer.parseInt(newUserData[0]))) {

                System.out.println("This user ID already exists.\n");
                condition = false;
            } else {
                condition = true;
            }

        } while (condition == false);

        System.out.println("\nEnter your first name:");
        newUserData[1] = scanner.nextLine();
        System.out.println("\nEnter your last name:");
        newUserData[2] = scanner.nextLine();
        System.out.println("\nEnter your birth date:");
        newUserData[3] = scanner.nextLine();
        System.out.println("\nEnter your address:");
        newUserData[4] = scanner.nextLine();
        System.out.println("\nEnter your document ID:");
        newUserData[5] = scanner.nextLine();

        fileData.addUser(new User(Integer.parseInt(newUserData[0]), newUserData[1], newUserData[2], newUserData[3],
                newUserData[4], newUserData[5]));
        System.out.println("\nNEW USER ADDED: " + fileData.getUsersMap().get(Integer.parseInt(newUserData[0])) + "\n");

    }

    private void bookATrip(Scanner scanner) {

        System.out.println("\nEnter trip ID:");
        int tripId = Integer.parseInt(scanner.nextLine());
        System.out.println("\nEnter your user ID:");
        int userId = Integer.parseInt(scanner.nextLine());

        if ((fileData.getTripsMap().containsKey(tripId)) && (fileData.getTripsMap().get(tripId).getAvailable())
                && (fileData.containsUser(userId))) {

            Reservation reservation = new Reservation(fileData.getReservationsCollection().size() + 1,
                    fileData.getUsersMap().get(userId), fileData.getTripsMap().get(tripId));
            fileData.addReservation(reservation);
            fileData.getTripsMap().get(tripId).setAvailable(false);

            System.out.println("\nNEW RESERVATION ADDED: your reservation ID is "
                    + fileData.getReservationsCollection().size() + "\n");
        } else {

            System.out.println("\nCANNOT BOOK: CHECK USER ID/ TRIP ID/ AVAILABILITY\n");
        }
    }

    private void deleteReservation(Scanner scanner) {

        System.out.println("\nEnter the reservation ID you want to remove:");
        int reservationID = Integer.parseInt(scanner.nextLine());

        if (fileData.getReservationsMap().containsKey(reservationID)) {

            fileData.removeReservation(reservationID);
            System.out.println("\nRESERVATION (ID:" + reservationID + ") REMOVED\n");
        } else {

            System.out.println("\nID NOT FOUND\n");
        }
    }

    public void writeCSVFile() {

        List<String> tripsFiltered = fileData.getTripsCollection().stream().filter(t -> t.getAvailable())
                .map(t -> t.toStringComplete()).collect(Collectors.toList());

        FileWriter csvWriter;
        try {

            String fileName = new SimpleDateFormat("'trips_'dd_MM_yyyy'.csv'").format(new Date());
            csvWriter = new FileWriter(fileName);

            csvWriter.append("ID");
            csvWriter.append(";");
            csvWriter.append("Date");
            csvWriter.append(";");
            csvWriter.append("Duration(hours)");
            csvWriter.append(";");
            csvWriter.append("Departure city");
            csvWriter.append(";");
            csvWriter.append("Arrival city");
            csvWriter.append("\n");

            for (String row : tripsFiltered) {

                csvWriter.append(row);
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("\nNew file: " + fileName + " created\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
