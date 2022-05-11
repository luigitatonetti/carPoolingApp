package com.carpooling.functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeMap;

import com.carpooling.models.Reservation;
import com.carpooling.models.Trip;
import com.carpooling.models.User;

public class FileManager {

    private TreeMap<Integer, User> users;
    private TreeMap<Integer, Trip> trips;
    private TreeMap<Integer, Reservation> reservations;

    public FileManager() {
        users = new TreeMap<>();
        trips = new TreeMap<>();
        reservations = new TreeMap<>();
    }

    //import of "utenti.csv", "viaggi.csv", "prenotazioni.csv"
    public void importFiles() {

        try {

            //reading "utenti.csv"
            BufferedReader csvReader = new BufferedReader(
                                            new FileReader("utenti.csv"));

            String row = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");

               
                //creates a new user from data and saves it in a map
                User user = new User(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5]);
                users.put(Integer.parseInt(data[0]), user);
            }

            csvReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        try {

            //reading "viaggi.csv"
            BufferedReader csvReader = new BufferedReader(
                                            new FileReader("viaggi.csv"));

            String row = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                

                //creates a new trip from data and saves it in a map
                Trip trip = new Trip(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]);
                trips.put(Integer.parseInt(data[0]), trip);
            }

            csvReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        try {

            //reading "prenotazioni.csv"
            BufferedReader csvReader = new BufferedReader(
                                            new FileReader("prenotazioni.csv"));

            String row = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");

               
                //creates a new reservation from data and saves it in a map
                Reservation reservation = new Reservation(Integer.parseInt(data[0]), users.get(Integer.parseInt(data[2])), trips.get(Integer.parseInt(data[1])));
                reservations.put(Integer.parseInt(data[0]), reservation);
            }

            csvReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //getters
    public Collection<User> getUsersCollection() {
        return this.users.values();
    }

    public Collection<Trip> getTripsCollection() {
        return this.trips.values();
    }

    public Collection<Reservation> getReservationsCollection() {
        return this.reservations.values();
    }

    public boolean containsUser(Integer u) {

        if (users.containsKey(u)) {
            return true;
        } else {
            return false;
        }
    }

    public void addUser(User u) {
        
        users.put(u.getUserID(), u);
    }

    public TreeMap<Integer,User> getUsersMap() {
        return this.users;
    }

    public TreeMap<Integer,Trip> getTripsMap() {
        return this.trips;
    }

    public TreeMap<Integer,Reservation> getReservationsMap() {
        return this.reservations;
    }

    public void addReservation(Reservation r) {

        reservations.put(reservations.size() + 1, r);
    }

    public void removeReservation(int id) {
        reservations.remove(id);
    }
}
