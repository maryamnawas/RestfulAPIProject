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
import csa.healthsystem.model.Doctor;
import csa.healthsystem.model.Patient;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.ValidationCheckerException;
import csa.healthsystem.exception.DatabaseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing Prescription entities.
 */
public class PrescriptionDAO {
    private static final Logger LOGGER = Logger.getLogger(PrescriptionDAO.class.getName());

    private static final List<Prescription> prescriptions = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    static{
        Patient patient1 = new Patient(1, "John Doe", "0768899123", "123 Main St", "Allergic to penicillin", "Stable");
        Doctor doctor1 = new Doctor(101, "Dr. Smith", "0768899124", "456 Elm St", "Cardiologist");
        Prescription prescription1 = new Prescription(nextId.getAndIncrement(), patient1, doctor1, "Aspirin", "10mg", "Take once daily with food", 30);
        prescriptions.add(prescription1);

        Patient patient2 = new Patient(2, "Alice Smith", "0768899125", "456 Oak St", "Asthmatic", "Critical");
        Doctor doctor2 = new Doctor(102, "Dr. Johnson", "0768899126", "789 Oak St", "Dermatologist");
        Prescription prescription2 = new Prescription(nextId.getAndIncrement(), patient2, doctor2, "Lipitor", "20mg", "Take at bedtime", 60);
        prescriptions.add(prescription2);

        Patient patient3 = new Patient(3, "Bob Johnson", "0768899127", "789 Pine St", "Diabetic", "Stable");
        Doctor doctor3 = new Doctor(103, "Dr. Brown", "0768899128", "987 Maple St", "Pediatrician");
        Prescription prescription3 = new Prescription(nextId.getAndIncrement(), patient3, doctor3, "Insulin", "10 units", "Inject subcutaneously before meals", 90);
        prescriptions.add(prescription3);
    }
    /**
     * Retrieves all prescriptions.
     * @return List of all prescriptions
     */
    public List<Prescription> getAllPrescriptions() {
        LOGGER.log(Level.INFO, "Retrieving all prescriptions");
        return new ArrayList<>(prescriptions); // Return a copy of the list to prevent modification
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
     * @throws InvalidDataException if the prescription data is invalid
     * @throws DatabaseException if a database error occurs
     */
    public void addPrescription(Prescription prescription) {
        LOGGER.log(Level.INFO, "Adding new prescription: " + prescription);
        String validationError = ValidationCheckerException.validatePrescription(prescription);
        if (validationError != null) {
            LOGGER.warning("Invalid prescription data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        if (isDuplicatePrescription(prescription.getId())) {
            throw new DuplicateException("Prescription with ID " + prescription.getId() + " already exists");
        }
        prescription.setId(nextId.getAndIncrement());
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
     * @throws InvalidDataException if the updated prescription data is invalid
     * @throws DatabaseException if a database error occurs
     */
    public void updatePrescription(int id, Prescription updatedPrescription) {
        LOGGER.log(Level.INFO, "Updating prescription with ID: " + id);
        Prescription existingPrescription = getPrescriptionById(id);
        String validationError = ValidationCheckerException.validatePrescription(updatedPrescription);
        if (validationError != null) {
            LOGGER.warning("Invalid prescription data: " + validationError);
            throw new InvalidDataException(validationError);
        }
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
     * @throws DatabaseException if a database error occurs
     */
    public void deletePrescription(int id) {
        LOGGER.log(Level.INFO, "Deleting prescription with ID: " + id);
        Prescription prescriptionToDelete = getPrescriptionById(id);
        prescriptions.remove(prescriptionToDelete);
        LOGGER.log(Level.INFO, "Prescription deleted: " + prescriptionToDelete);
    }
}
