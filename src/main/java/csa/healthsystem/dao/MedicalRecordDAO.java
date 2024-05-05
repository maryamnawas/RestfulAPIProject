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
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing MedicalRecord entities.
 */
public class MedicalRecordDAO {
    private static final Logger LOGGER = Logger.getLogger(MedicalRecordDAO.class.getName());

    private List<MedicalRecord> medicalRecords;
    private int nextId;

    /**
     * Constructs a new MedicalRecordDAO.
     */
    public MedicalRecordDAO() {
        this.medicalRecords = new ArrayList<>();
        this.nextId = 1; // Initialize ID counter
//        // Adding sample medical records
//        MedicalRecord record1 = new MedicalRecord(1, "Patient1", "Diagnosis1", "Treatment1");
//        MedicalRecord record2 = new MedicalRecord(2, "Patient2", "Diagnosis2", "Treatment2");
//        MedicalRecord record3 = new MedicalRecord(3, "Patient3", "Diagnosis3", "Treatment3");
//        medicalRecords.add(record1);
//        medicalRecords.add(record2);
//        medicalRecords.add(record3);
    }

    /**
     * Retrieves all medical records.
     * @return List of all medical records
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        LOGGER.log(Level.INFO, "Retrieving all medical records");
        return medicalRecords;
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
     */
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        LOGGER.log(Level.INFO, "Adding new medical record: " + medicalRecord);
        if (isDuplicateMedicalRecord(medicalRecord.getId())) {
            throw new DuplicateException("Medical record with ID " + medicalRecord.getId() + " already exists");
        }
        medicalRecord.setId(nextId++);
        medicalRecords.add(medicalRecord);
    }
    
    // Helper method to check if a medical record with the given ID already exists
    private boolean isDuplicateMedicalRecord(int id) {
        return medicalRecords.stream().anyMatch(medicalRecord -> medicalRecord.getId() == id);
    }

    /**
     * Updates an existing medical record.
     * @param id ID of the medical record to update
     * @param updatedMedicalRecord Updated medical record information
     * @throws NotFoundException if the specified medical record is not found
     */
    public void updateMedicalRecord(int id, MedicalRecord updatedMedicalRecord) {
        LOGGER.log(Level.INFO, "Updating medical record with ID: " + id);
        MedicalRecord existingMedicalRecord = getMedicalRecordById(id);
        existingMedicalRecord.setPatient(updatedMedicalRecord.getPatient());
        existingMedicalRecord.setDiagnoses(updatedMedicalRecord.getDiagnoses());
        existingMedicalRecord.setTreatments(updatedMedicalRecord.getTreatments());
        LOGGER.log(Level.INFO, "Medical record updated: " + existingMedicalRecord);
    }

    /**
     * Deletes a medical record by ID.
     * @param id ID of the medical record to delete
     * @throws NotFoundException if the specified medical record is not found
     */
    public void deleteMedicalRecord(int id) {
        LOGGER.log(Level.INFO, "Deleting medical record with ID: " + id);
        MedicalRecord medicalRecordToDelete = getMedicalRecordById(id);
        medicalRecords.remove(medicalRecordToDelete);
        LOGGER.log(Level.INFO, "Medical record deleted: " + medicalRecordToDelete);
    }
    
}
