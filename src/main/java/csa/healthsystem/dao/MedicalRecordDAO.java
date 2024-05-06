/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.dao;

/**
 *
 * @author Maryam
 */
import csa.healthsystem.model.MedicalRecord;
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
 * Data Access Object (DAO) for managing MedicalRecord entities.
 */
public class MedicalRecordDAO {
    private static final Logger LOGGER = Logger.getLogger(MedicalRecordDAO.class.getName());

    private static final List<MedicalRecord> medicalRecords = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    static {
        // Sample medical records
        Patient patient1 = new Patient(1000, "John Doe", "0768899123", "123 Main St", "Allergic to penicillin", "Stable");
        medicalRecords.add(new MedicalRecord(nextId.getAndIncrement(), patient1, "Flu", "Prescription: Rest and plenty of fluids"));

        Patient patient2 = new Patient(1001, "Alice Smith", "0768899124", "456 Oak St", "Asthmatic", "Critical");
        medicalRecords.add(new MedicalRecord(nextId.getAndIncrement(), patient2, "Broken arm", "Treatment: Cast applied"));

        Patient patient3 = new Patient(1002, "Bob Johnson", "0768899125", "789 Pine St", "Diabetic", "Stable");
        medicalRecords.add(new MedicalRecord(nextId.getAndIncrement(), patient3, "Headache", "Prescription: Pain reliever"));
    }

    /**
     * Retrieves all medical records.
     * @return List of all medical records
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        LOGGER.log(Level.INFO, "Retrieving all medical records");
        return new ArrayList<>(medicalRecords); // Return a copy of the list to prevent modification
    }

    /**
     * Retrieves a medical record by ID.
     * @param id ID of the medical record to retrieve
     * @return The medical record with the specified ID
     * @throws NotFoundException if no medical record with the specified ID is found
     */
    public MedicalRecord getMedicalRecordById(int id) {
        LOGGER.log(Level.INFO, "Retrieving medical record by ID: " + id);
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId() == id) {
                LOGGER.log(Level.INFO, "Medical record with ID " + id + " found: " + medicalRecord);
                return medicalRecord;
            }
        }
        LOGGER.log(Level.WARNING, "Medical record with ID " + id + " not found");
        throw new NotFoundException("Medical record with ID " + id + " not found");
    }

    /**
     * Adds a new medical record.
     * @param medicalRecord The medical record to add
     * @throws DuplicateException if a medical record with the same ID already exists
     * @throws InvalidDataException if the medical record data is invalid
     * @throws DatabaseException if a database error occurs
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        LOGGER.log(Level.INFO, "Adding new medical record: " + medicalRecord);
        String validationError = ValidationCheckerException.validateMedicalRecord(medicalRecord);
        if (validationError != null) {
            LOGGER.warning("Invalid medical record data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        if (isDuplicateMedicalRecord(medicalRecord.getId())) {
            throw new DuplicateException("Medical record with ID " + medicalRecord.getId() + " already exists");
        }
        medicalRecord.setId(nextId.getAndIncrement());
        medicalRecords.add(medicalRecord);
    }
    
    // Helper method to check if a medical record with the given ID already exists
    private boolean isDuplicateMedicalRecord(int id) {
        return medicalRecords.stream().anyMatch(record -> record.getId() == id);
    }

    /**
     * Updates an existing medical record.
     * @param id ID of the medical record to update
     * @param updatedRecord Updated medical record information
     * @throws NotFoundException if the specified medical record is not found
     * @throws InvalidDataException if the updated medical record data is invalid
     * @throws DatabaseException if a database error occurs
     */
    public void updateMedicalRecord(int id, MedicalRecord updatedRecord) {
        LOGGER.log(Level.INFO, "Updating medical record with ID: " + id);
        MedicalRecord existingRecord = getMedicalRecordById(id);
        String validationError = ValidationCheckerException.validateMedicalRecord(updatedRecord);
        if (validationError != null) {
            LOGGER.warning("Invalid medical record data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        existingRecord.setPatient(updatedRecord.getPatient());
        existingRecord.setDiagnoses(updatedRecord.getDiagnoses());
        existingRecord.setTreatments(updatedRecord.getTreatments());
        LOGGER.log(Level.INFO, "Medical record updated: " + existingRecord);
    }

    /**
     * Deletes a medical record by ID.
     * @param id ID of the medical record to delete
     * @throws NotFoundException if the specified medical record is not found
     * @throws DatabaseException if a database error occurs
     */
    public void deleteMedicalRecord(int id) {
        LOGGER.log(Level.INFO, "Deleting medical record with ID: " + id);
        MedicalRecord recordToDelete = getMedicalRecordById(id);
        medicalRecords.remove(recordToDelete);
        LOGGER.log(Level.INFO, "Medical record deleted: " + recordToDelete);
    }
}
