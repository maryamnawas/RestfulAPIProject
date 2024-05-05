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
 * Doctor class represents a medical doctor.
 * Inherits from the Person class.
 */
public class Doctor extends Person {
    private static final Logger LOGGER = Logger.getLogger(Doctor.class.getName());
    private static int id = 1;

    // Additional attribute specific to Doctor
    private String specialization;

    // Default constructor
    public Doctor() {
        super();
    }
    
    /**
     * Constructor to initialize a Doctor object with the provided attributes.
     * Inherits the ID field from the Person class.
     * @param id The ID of the doctor.
     * @param name The name of the doctor.
     * @param contactInformation The contact information of the doctor.
     * @param address The address of the doctor.
     * @param specialization The specialization of the doctor.
     */
    public Doctor(int id, String name, String contactInformation, String address, String specialization) {
        // Calling the constructor of the superclass (Person) to initialize inherited attributes
        super(id++, name, contactInformation, address);
        // Initializing additional attribute specific to Doctor
        this.specialization = specialization;
    }

    /**
     * Get the specialization of the doctor.
     * @return The specialization of the doctor.
     */
    public String getSpecialization() {
        LOGGER.log(Level.INFO, "Getting specialization: " + specialization);
        return specialization;
    }

    /**
     * Set the specialization of the doctor.
     * @param specialization The specialization of the doctor to set.
     */
    public void setSpecialization(String specialization) {
        LOGGER.log(Level.INFO, "Setting specialization: " + specialization);
        this.specialization = specialization;
    }
}
