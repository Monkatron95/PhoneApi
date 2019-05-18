package com.joshuamonk.phoneapi.model.entity;

import java.util.List;

/**
 * Class to define customer entity for storage
 */
public class Customer {
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String contactNumber;

    private List<String> associatedNumbers;

    public Customer(long id, String firstName, String lastName, String address, String contactNumber, List<String> associatedNumbers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.associatedNumbers = associatedNumbers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<String> getAssociatedNumbers() {
        return associatedNumbers;
    }

    public void setAssociatedNumbers(List<String> associatedNumbers) {
        this.associatedNumbers = associatedNumbers;
    }
}
