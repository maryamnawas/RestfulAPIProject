/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.dao;

/**
 *
 * @author Maryam
 */
import csa.healthsystem.model.Patient;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing Patient entities.
 */
public class PatientDAO {
    private static final Logger LOGGER = Logger.getLogger(PatientDAO.class.getName());

    private static final List<Patient> patients = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1000); // Start ID from 1000 for patients

    static {
        // Adding sample patients
        patients.add(new Patient(nextId.getAndIncrement(), "John Doe", "123-456-7890", "123 Main St", "Heart condition", "Stable"));
        patients.add(new Patient(nextId.getAndIncrement(), "Alice Smith", "456-789-0123", "456 Oak St", "Diabetes", "Under treatment"));
        patients.add(new Patient(nextId.getAndIncrement(), "Bob Johnson", "789-012-3456", "789 Elm St", "Allergy", "Recovering"));
    }

    /**
     * Retrieves all patients.
     * @return List of all patients
     */
    public List<Patient> getAllPatients() {
        LOGGER.log(Level.INFO, "Retrieving all patients");
        return new ArrayList<>(patients); // Return a copy of the list to prevent modification
    }

    /**
     * Retrieves a patient by ID.
     * @param id ID of the patient to retrieve
     * @return The patient with the specified ID
     * @throws NotFoundException if no patient with the specified ID is found
     */
    public Patient getPatientById(int id) {
        LOGGER.log(Level.INFO, "Retrieving patient by ID: " + id);
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                LOGGER.log(Level.INFO, "Patient with ID " + id + " found: " + patient);
                return patient;
            }
        }
        LOGGER.log(Level.WARNING, "Patient with ID " + id + " not found");
        throw new NotFoundException("Patient with ID " + id + " not found");
    }

    /**
     * Adds a new patient.
     * @param patient The patient to add
     * @throws DuplicateException if a patient with the same ID already exists
     */
    public void addPatient(Patient patient) {
        LOGGER.log(Level.INFO, "Adding new patient: " + patient);
        if (isDuplicatePatient(patient.getId())) {
            throw new DuplicateException("Patient with ID " + patient.getId() + " already exists");
        }

        // Assign doctor based on medical history
        String medicalHistory = patient.getMedicalHistory().toLowerCase();
        if (medicalHistory.contains("heart")) {
            // Assign cardiologist
            patient.setDoctorSpecialization("Cardiologist");
        } else if (medicalHistory.contains("allergy") || medicalHistory.contains("skin")) {
            // Assign dermatologist
            patient.setDoctorSpecialization("Dermatologist");
        } else if (medicalHistory.contains("diabetes")) {
            // Assign endocrinologist
            patient.setDoctorSpecialization("Endocrinologist");
        }

        patient.setId(nextId.getAndIncrement());
        patients.add(patient);
    }
    
    // Helper method to check if a patient with the given ID already exists
    private boolean isDuplicatePatient(int id) {
        return patients.stream().anyMatch(patient -> patient.getId() == id);
    }

    /**
     * Updates an existing patient.
     * @param id ID of the patient to update
     * @param updatedPatient Updated patient information
     * @throws NotFoundException if the specified patient is not found
     */
    public void updatePatient(int id, Patient updatedPatient) {
        LOGGER.log(Level.INFO, "Updating patient with ID: " + id);
        Patient existingPatient = getPatientById(id);
        existingPatient.setName(updatedPatient.getName());
        existingPatient.setContactInformation(updatedPatient.getContactInformation());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setMedicalHistory(updatedPatient.getMedicalHistory());
        existingPatient.setCurrentHealthStatus(updatedPatient.getCurrentHealthStatus());
        LOGGER.log(Level.INFO, "Patient updated: " + existingPatient);
    }

    /**
     * Deletes a patient by ID.
     * @param id ID of the patient to delete
     * @throws NotFoundException if the specified patient is not found
     */
    public void deletePatient(int id) {
        LOGGER.log(Level.INFO, "Deleting patient with ID: " + id);
        Patient patientToDelete = getPatientById(id);
        patients.remove(patientToDelete);
        LOGGER.log(Level.INFO, "Patient deleted: " + patientToDelete);
    }
}
