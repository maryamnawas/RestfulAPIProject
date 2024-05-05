/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.model;

/**
 *
 * @author Maryam
 */

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a generic person with basic attributes such as ID, name, contact information, and address.
 */
public class Person {
    private static final Logger LOGGER = Logger.getLogger(Person.class.getName());

    private int id;
    private String name;
    private String contactInformation;
    private String address;
    
    public Person(){}
    /**
     * Constructs a Person object with the specified attributes.
     * @param id The unique identifier for the person
     * @param name The name of the person
     * @param contactInformation The contact information of the person
     * @param address The address of the person
     */
    public Person(int id, String name, String contactInformation, String address) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
        this.address = address;
    }

    // Getters and setters for id, name, contactInformation, and address

    /**
     * Gets the ID of the person.
     * @return The ID of the person
     */
    public int getId() {
        LOGGER.log(Level.INFO, "Getting ID: " + id);
        return id;
    }

    /**
     * Sets the ID of the person.
     * @param id The ID to set
     */
    public void setId(int id) {
        LOGGER.log(Level.INFO, "Setting ID: " + id);
        this.id = id;
    }

    /**
     * Gets the name of the person.
     * @return The name of the person
     */
    public String getName() {
        LOGGER.log(Level.INFO, "Getting name: " + name);
        return name;
    }

    /**
     * Sets the name of the person.
     * @param name The name to set
     */
    public void setName(String name) {
        LOGGER.log(Level.INFO, "Setting name: " + name);
        this.name = name;
    }

    /**
     * Gets the contact information of the person.
     * @return The contact information of the person
     */
    public String getContactInformation() {
        LOGGER.log(Level.INFO, "Getting contact information: " + contactInformation);
        return contactInformation;
    }

    /**
     * Sets the contact information of the person.
     * @param contactInformation The contact information to set
     */
    public void setContactInformation(String contactInformation) {
        LOGGER.log(Level.INFO, "Setting contact information: " + contactInformation);
        this.contactInformation = contactInformation;
    }

    /**
     * Gets the address of the person.
     * @return The address of the person
     */
    public String getAddress() {
        LOGGER.log(Level.INFO, "Getting address: " + address);
        return address;
    }

    /**
     * Sets the address of the person.
     * @param address The address to set
     */
    public void setAddress(String address) {
        LOGGER.log(Level.INFO, "Setting address: " + address);
        this.address = address;
    }
}
