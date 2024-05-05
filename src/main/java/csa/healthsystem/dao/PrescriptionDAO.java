/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.dao;

/**
 *
 * @author Maryam 
 */
import csa.healthsystem.model.Prescription;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing Prescription entities.
 */
public class PrescriptionDAO {
    private static final Logger LOGGER = Logger.getLogger(PrescriptionDAO.class.getName());

    private List<Prescription> prescriptions;
    private int nextId;

    /**
     * Constructs a new PrescriptionDAO.
     */
    public PrescriptionDAO() {
        this.prescriptions = new ArrayList<>();
        this.nextId = 1; // Initialize ID counter
    }

    /**
     * Retrieves all prescriptions.
     * @return List of all prescriptions
     */
    public List<Prescription> getAllPrescriptions() {
        LOGGER.log(Level.INFO, "Retrieving all prescriptions");
        return prescriptions;
    }

    /**
     * Retrieves a prescription by ID.
     * @param id ID of the prescription to retrieve
     * @return The prescription with the specified ID
     * @throws NotFoundException if no prescription with the specified ID is found
     */
    public Prescription getPrescriptionById(int id) {
        LOGGER.log(Level.INFO, "Retrieving prescription by ID: " + id);
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == id) {
                LOGGER.log(Level.INFO, "Prescription with ID " + id + " found: " + prescription);
                return prescription;
            }
        }
        LOGGER.log(Level.WARNING, "Prescription with ID " + id + " not found");
        throw new NotFoundException("Prescription with ID " + id + " not found");
    }

    /**
     * Adds a new prescription.
     * @param prescription The prescription to add
     * @throws DuplicateException if a prescription with the same ID already exists
     */
     public void addPrescription(Prescription prescription) {
        LOGGER.log(Level.INFO, "Adding new prescription: " + prescription);
        if (isDuplicatePrescription(prescription.getId())) {
            throw new DuplicateException("Prescription with ID " + prescription.getId() + " already exists");
        }
        prescription.setId(nextId++);
        prescriptions.add(prescription);
    }
        
    // Helper method to check if a prescription with the given ID already exists
    private boolean isDuplicatePrescription(int id) {
        return prescriptions.stream().anyMatch(prescription -> prescription.getId() == id);
    }

    /**
     * Updates an existing prescription.
     * @param id ID of the prescription to update
     * @param updatedPrescription Updated prescription information
     * @throws NotFoundException if the specified prescription is not found
     */
    public void updatePrescription(int id, Prescription updatedPrescription) {
        LOGGER.log(Level.INFO, "Updating prescription with ID: " + id);
        Prescription existingPrescription = getPrescriptionById(id);
        existingPrescription.setPatient(updatedPrescription.getPatient());
        existingPrescription.setDoctor(updatedPrescription.getDoctor());
        existingPrescription.setMedication(updatedPrescription.getMedication());
        existingPrescription.setDosage(updatedPrescription.getDosage());
        existingPrescription.setInstructions(updatedPrescription.getInstructions());
        existingPrescription.setDurationInDays(updatedPrescription.getDurationInDays());
        LOGGER.log(Level.INFO, "Prescription updated: " + existingPrescription);
    }

    /**
     * Deletes a prescription by ID.
     * @param id ID of the prescription to delete
     * @throws NotFoundException if the specified prescription is not found
     */
    public void deletePrescription(int id) {
        LOGGER.log(Level.INFO, "Deleting prescription with ID: " + id);
        Prescription prescriptionToDelete = getPrescriptionById(id);
        prescriptions.remove(prescriptionToDelete);
        LOGGER.log(Level.INFO, "Prescription deleted: " + prescriptionToDelete);
    }
}
