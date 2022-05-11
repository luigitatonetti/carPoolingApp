package com.carpooling.models;

public class Reservation {
    
    private int reservationID;
    private User user;
    private Trip trip;

    public Reservation(int reservationID, User user, Trip trip) {
        this.reservationID = reservationID;
        this.user = user;
        this.trip = trip;
    }

    //getters
    public int getReservationID() {
        return this.reservationID;
    }

    public User getUser() {
        return this.user;
    }

    public Trip getTrip() {
        return this.trip;
    }
    
}
