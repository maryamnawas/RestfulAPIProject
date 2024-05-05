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
 * Patient class represents a patient in the system.
 */
public class Patient extends Person {
    private static final Logger LOGGER = Logger.getLogger(Patient.class.getName());
    private static int id = 1000;

    // Additional attributes specific to Patient
    private String medicalHistory;
    private String currentHealthStatus;
    
    // Default constructor
    public Patient() {
        super();
    }

    /**
     * Constructor to initialize a Patient object with the provided attributes.
     * @param id The ID of the patient.
     * @param name The name of the patient.
     * @param contactInformation The contact information of the patient.
     * @param address The address of the patient.
     * @param medicalHistory The medical history of the patient.
     * @param currentHealthStatus The current health status of the patient.
     */
    public Patient(int id, String name, String contactInformation, String address, String medicalHistory, String currentHealthStatus) {
        // Calling the constructor of the superclass (Person) to initialize inherited attributes
        super(id++, name, contactInformation, address);
        // Initializing additional attributes specific to Patient
        this.medicalHistory = medicalHistory;
        this.currentHealthStatus = currentHealthStatus;
    }

    // Getters and setters for additional attributes

    /**
     * Get the medical history of the patient.
     * @return The medical history of the patient.
     */
    public String getMedicalHistory() {
        LOGGER.log(Level.INFO, "Getting medical history: " + medicalHistory);
        return medicalHistory;
    }

    /**
     * Set the medical history of the patient.
     * @param medicalHistory The medical history to set.
     */
    public void setMedicalHistory(String medicalHistory) {
        LOGGER.log(Level.INFO, "Setting medical history: " + medicalHistory);
        this.medicalHistory = medicalHistory;
    }

    /**
     * Get the current health status of the patient.
     * @return The current health status of the patient.
     */
    public String getCurrentHealthStatus() {
        LOGGER.log(Level.INFO, "Getting current health status: " + currentHealthStatus);
        return currentHealthStatus;
    }

    /**
     * Set the current health status of the patient.
     * @param currentHealthStatus The current health status to set.
     */
    public void setCurrentHealthStatus(String currentHealthStatus) {
        LOGGER.log(Level.INFO, "Setting current health status: " + currentHealthStatus);
        this.currentHealthStatus = currentHealthStatus;
    }
}
