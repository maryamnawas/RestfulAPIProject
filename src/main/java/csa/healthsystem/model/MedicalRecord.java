/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.model;

/**
 *
 * @author Maryam
 */
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MedicalRecord class represents a patient's medical record.
 */
public class MedicalRecord {
    private static final Logger LOGGER = Logger.getLogger(MedicalRecord.class.getName());

    private int id; // New ID attribute
    private Patient patient;
    private List<String> diagnoses;
    private List<String> treatments;

     // Default constructor
    public MedicalRecord() {}
    
    /**
     * Constructor to initialize a MedicalRecord object with the provided attributes.
     * @param id The ID of the medical record.
     * @param patient The patient associated with the medical record.
     * @param diagnoses The list of diagnoses in the medical record.
     * @param treatments The list of treatments in the medical record.
     */
    public MedicalRecord(int id, Patient patient, List<String> diagnoses, List<String> treatments) {
        this.id = id;
        this.patient = patient;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
    }

    // Getters and setters

    /**
     * Get the ID of the medical record.
     * @return The ID of the medical record.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the medical record.
     * @param id The ID of the medical record to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the patient associated with the medical record.
     * @return The patient associated with the medical record.
     */
    public Patient getPatient() {
        LOGGER.log(Level.INFO, "Getting patient: " + patient);
        return patient;
    }

    /**
     * Set the patient associated with the medical record.
     * @param patient The patient to set.
     */
    public void setPatient(Patient patient) {
        LOGGER.log(Level.INFO, "Setting patient: " + patient);
        this.patient = patient;
    }

    /**
     * Get the list of diagnoses in the medical record.
     * @return The list of diagnoses in the medical record.
     */
    public List<String> getDiagnoses() {
        LOGGER.log(Level.INFO, "Getting diagnoses: " + diagnoses);
        return diagnoses;
    }

    /**
     * Set the list of diagnoses in the medical record.
     * @param diagnoses The list of diagnoses to set.
     */
    public void setDiagnoses(List<String> diagnoses) {
        LOGGER.log(Level.INFO, "Setting diagnoses: " + diagnoses);
        this.diagnoses = diagnoses;
    }

    /**
     * Get the list of treatments in the medical record.
     * @return The list of treatments in the medical record.
     */
    public List<String> getTreatments() {
        LOGGER.log(Level.INFO, "Getting treatments: " + treatments);
        return treatments;
    }

    /**
     * Set the list of treatments in the medical record.
     * @param treatments The list of treatments to set.
     */
    public void setTreatments(List<String> treatments) {
        LOGGER.log(Level.INFO, "Setting treatments: " + treatments);
        this.treatments = treatments;
    }
}