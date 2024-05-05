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
 * Represents a prescription with details such as ID, patient, doctor, medication, dosage, instructions, and duration.
 */
public class Prescription {
    private static final Logger LOGGER = Logger.getLogger(Prescription.class.getName());

    private int id; // New ID attribute
    private Patient patient;
    private Doctor doctor;
    private String medication;
    private String dosage;
    private String instructions;
    private int durationInDays;

     // Default constructor
    public Prescription() {}
    
    /**
     * Constructs a Prescription object with the specified attributes.
     * @param id The unique identifier for the prescription
     * @param patient The patient for whom the prescription is issued
     * @param doctor The doctor who issued the prescription
     * @param medication The medication prescribed
     * @param dosage The dosage of the medication
     * @param instructions The instructions for taking the medication
     * @param durationInDays The duration of the prescription in days
     */
    public Prescription(int id, Patient patient, Doctor doctor, String medication, String dosage, String instructions, int durationInDays) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
        this.durationInDays = durationInDays;
    }

    // Getters and setters

    /**
     * Gets the ID of the prescription.
     * @return The ID of the prescription
     */
    public int getId() {
        LOGGER.log(Level.INFO, "Getting ID: " + id);
        return id;
    }

    /**
     * Sets the ID of the prescription.
     * @param id The ID to set
     */
    public void setId(int id) {
        LOGGER.log(Level.INFO, "Setting ID: " + id);
        this.id = id;
    }

    /**
     * Gets the patient for whom the prescription is issued.
     * @return The patient object
     */
    public Patient getPatient() {
        LOGGER.log(Level.INFO, "Getting patient: " + patient);
        return patient;
    }

    /**
     * Sets the patient for whom the prescription is issued.
     * @param patient The patient object to set
     */
    public void setPatient(Patient patient) {
        LOGGER.log(Level.INFO, "Setting patient: " + patient);
        this.patient = patient;
    }

    /**
     * Gets the doctor who issued the prescription.
     * @return The doctor object
     */
    public Doctor getDoctor() {
        LOGGER.log(Level.INFO, "Getting doctor: " + doctor);
        return doctor;
    }

    /**
     * Sets the doctor who issued the prescription.
     * @param doctor The doctor object to set
     */
    public void setDoctor(Doctor doctor) {
        LOGGER.log(Level.INFO, "Setting doctor: " + doctor);
        this.doctor = doctor;
    }

    /**
     * Gets the medication prescribed in the prescription.
     * @return The medication
     */
    public String getMedication() {
        LOGGER.log(Level.INFO, "Getting medication: " + medication);
        return medication;
    }

    /**
     * Sets the medication prescribed in the prescription.
     * @param medication The medication to set
     */
    public void setMedication(String medication) {
        LOGGER.log(Level.INFO, "Setting medication: " + medication);
        this.medication = medication;
    }

    /**
     * Gets the dosage of the medication prescribed in the prescription.
     * @return The dosage
     */
    public String getDosage() {
        LOGGER.log(Level.INFO, "Getting dosage: " + dosage);
        return dosage;
    }

    /**
     * Sets the dosage of the medication prescribed in the prescription.
     * @param dosage The dosage to set
     */
    public void setDosage(String dosage) {
        LOGGER.log(Level.INFO, "Setting dosage: " + dosage);
        this.dosage = dosage;
    }

    /**
     * Gets the instructions for taking the medication prescribed in the prescription.
     * @return The instructions
     */
    public String getInstructions() {
        LOGGER.log(Level.INFO, "Getting instructions: " + instructions);
        return instructions;
    }

    /**
     * Sets the instructions for taking the medication prescribed in the prescription.
     * @param instructions The instructions to set
     */
    public void setInstructions(String instructions) {
        LOGGER.log(Level.INFO, "Setting instructions: " + instructions);
        this.instructions = instructions;
    }

    /**
     * Gets the duration of the prescription in days.
     * @return The duration in days
     */
    public int getDurationInDays() {
        LOGGER.log(Level.INFO, "Getting duration in days: " + durationInDays);
        return durationInDays;
    }

    /**
     * Sets the duration of the prescription in days.
     * @param durationInDays The duration in days to set
     */
    public void setDurationInDays(int durationInDays) {
        LOGGER.log(Level.INFO, "Setting duration in days: " + durationInDays);
        this.durationInDays = durationInDays;
    }
}
