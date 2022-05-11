package com.carpooling.models;

public class User {
    
    private int userID;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String address;
    private String documentID;

	public User(int userID, String firstName, String lastName, String birthDate, String address, String documentID) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.address = address;
		this.documentID = documentID;
	}

	//getters
	public int getUserID() {
		return this.userID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getBirthDate() {
		return this.birthDate;
	}

	public String getAddress() {
		return this.address;
	}

	public String getDocumentID() {
		return this.documentID;
	}

	@Override
	public String toString() {
		
		return userID + " " +
				firstName + " " +
				lastName + " " +
				birthDate + " " +
				address + " " +
				documentID;
	}
}
