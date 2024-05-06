/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.dao;

/**
 *
 * @author Maryam
 */
import csa.healthsystem.model.Doctor;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing Doctor entities.
 */
public class DoctorDAO {
    private static final Logger LOGGER = Logger.getLogger(DoctorDAO.class.getName());

    private static final List<Doctor> doctors = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    static {
        // Adding sample doctors
        doctors.add(new Doctor(nextId.getAndIncrement(), "Dr. John Doe", "123-456-7890", "123 Main St", "Cardiologist"));
        doctors.add(new Doctor(nextId.getAndIncrement(), "Dr. Alice Smith", "456-789-0123", "456 Oak St", "Endocrinologist"));
        doctors.add(new Doctor(nextId.getAndIncrement(), "Dr. Bob Johnson", "789-012-3456", "789 Pine St", "Dermatologist"));
    }

    /**
     * Retrieves all doctors.
     * @return List of all doctors
     */
    public List<Doctor> getAllDoctors() {
        LOGGER.log(Level.INFO, "Retrieving all doctors");
        return new ArrayList<>(doctors); // Return a copy of the list to prevent modification
    }

    /**
     * Retrieves a doctor by ID.
     * @param id ID of the doctor to retrieve
     * @return The doctor with the specified ID
     * @throws NotFoundException if no doctor with the specified ID is found
     */
    public Doctor getDoctorById(int id) {
        LOGGER.log(Level.INFO, "Retrieving doctor by ID: " + id);
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id) {
                LOGGER.log(Level.INFO, "Doctor with ID " + id + " found: " + doctor);
                return doctor;
            }
        }
        LOGGER.log(Level.WARNING, "Doctor with ID " + id + " not found");
        throw new NotFoundException("Doctor with ID " + id + " not found");
    }

    /**
     * Adds a new doctor.
     * @param doctor The doctor to add
     * @throws DuplicateException if a doctor with the same ID already exists
     */
    public void addDoctor(Doctor doctor) {
        LOGGER.log(Level.INFO, "Adding new doctor: " + doctor);
        if (isDuplicateDoctor(doctor.getId())) {
            throw new DuplicateException("Doctor with ID " + doctor.getId() + " already exists");
        }
        doctor.setId(nextId.getAndIncrement());
        doctors.add(doctor);
    }

    /**
     * Updates an existing doctor.
     * @param id ID of the doctor to update
     * @param updatedDoctor Updated doctor information
     * @throws NotFoundException if the specified doctor is not found
     */
    public void updateDoctor(int id, Doctor updatedDoctor) {
        LOGGER.log(Level.INFO, "Updating doctor with ID: " + id);
        Doctor existingDoctor = getDoctorById(id);
        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setContactInformation(updatedDoctor.getContactInformation());
        existingDoctor.setAddress(updatedDoctor.getAddress());
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        LOGGER.log(Level.INFO, "Doctor updated: " + existingDoctor);
    }

    /**
     * Deletes a doctor by ID.
     * @param id ID of the doctor to delete
     * @throws NotFoundException if the specified doctor is not found
     */
    public void deleteDoctor(int id) {
        LOGGER.log(Level.INFO, "Deleting doctor with ID: " + id);
        Doctor doctorToDelete = getDoctorById(id);
        doctors.remove(doctorToDelete);
        LOGGER.log(Level.INFO, "Doctor deleted: " + doctorToDelete);
    }

    // Helper method to check if a doctor with the given ID already exists
    private boolean isDuplicateDoctor(int id) {
        return doctors.stream().anyMatch(doctor -> doctor.getId() == id);
    }
}
