/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.dao;

/**
 *
 * @author Maryam
 */
import csa.healthsystem.model.Appointment;
import csa.healthsystem.model.Doctor;
import csa.healthsystem.model.Patient;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.ValidationCheckerException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAO {
    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());

    private static final List<Appointment> appointments = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    static {
        // Sample appointments with associated doctor and patient instances
        Doctor doctor1 = new Doctor(1, "Dr. Smith", "0768899123", "456 Elm St", "Cardiologist");
        Patient patient1 = new Patient(1000, "John Doe", "0768899123", "123 Main St", "Allergic to penicillin", "Stable");
        appointments.add(new Appointment(nextId.getAndIncrement(), "2024-05-05", "09:00", patient1, doctor1));

        Doctor doctor2 = new Doctor(2, "Dr. Johnson", "0768899124", "789 Oak St", "Dermatologist");
        Patient patient2 = new Patient(1001, "Alice Smith", "0768899124", "456 Elm St", "Asthmatic", "Critical");
        appointments.add(new Appointment(nextId.getAndIncrement(), "2024-05-06", "10:30", patient2, doctor2));

        Doctor doctor3 = new Doctor(3, "Dr. Brown", "0768899125", "987 Maple St", "Pediatrician");
        Patient patient3 = new Patient(1002, "Bob Johnson", "0768899125", "789 Oak St", "Diabetic", "Stable");
        appointments.add(new Appointment(nextId.getAndIncrement(), "2024-05-07", "11:45", patient3, doctor3));
    }

    /**
     * Retrieves all appointments.
     * @return List of all appointments
     */
    public List<Appointment> getAllAppointments() {
        LOGGER.log(Level.INFO, "Retrieving all appointments");
        return new ArrayList<>(appointments); // Return a copy of the list to prevent modification
    }

    /**
     * Retrieves an appointment by ID.
     * @param id ID of the appointment to retrieve
     * @return The appointment with the specified ID
     * @throws NotFoundException if no appointment with the specified ID is found
     */
    public Appointment getAppointmentById(int id) {
        LOGGER.log(Level.INFO, "Retrieving appointment by ID: " + id);
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                LOGGER.log(Level.INFO, "Appointment with ID " + id + " found: " + appointment);
                return appointment;
            }
        }
        LOGGER.log(Level.WARNING, "Appointment with ID " + id + " not found");
        throw new NotFoundException("Appointment with ID " + id + " not found");
    }

    /**
     * Adds a new appointment.
     * @param appointment The appointment to add
     * @throws DuplicateException if an appointment with the same ID already exists
     * @throws InvalidDataException if the appointment data is invalid
     */
    public void addAppointment(Appointment appointment) {
        LOGGER.log(Level.INFO, "Adding new appointment: " + appointment);
        String validationError = ValidationCheckerException.validateAppointment(appointment);
        if (validationError != null) {
            LOGGER.warning("Invalid appointment data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        if (isDuplicateAppointment(appointment.getId())) {
            throw new DuplicateException("Appointment with ID " + appointment.getId() + " already exists");
        }
        appointment.setId(nextId.getAndIncrement());
        appointments.add(appointment);
    }

    /**
     * Updates an existing appointment.
     * @param id ID of the appointment to update
     * @param updatedAppointment Updated appointment information
     * @throws NotFoundException if the specified appointment is not found
     * @throws InvalidDataException if the updated appointment data is invalid
     */
    public void updateAppointment(int id, Appointment updatedAppointment) {
        LOGGER.log(Level.INFO, "Updating appointment with ID: " + id);
        Appointment existingAppointment = getAppointmentById(id);
        String validationError = ValidationCheckerException.validateAppointment(updatedAppointment);
        if (validationError != null) {
            LOGGER.warning("Invalid appointment data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        existingAppointment.setDate(updatedAppointment.getDate());
        existingAppointment.setTime(updatedAppointment.getTime());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        LOGGER.log(Level.INFO, "Appointment updated: " + existingAppointment);
    }

    /**
     * Deletes an appointment by ID.
     * @param id ID of the appointment to delete
     * @throws NotFoundException if the specified appointment is not found
     */
    public void deleteAppointment(int id) {
        LOGGER.log(Level.INFO, "Deleting appointment with ID: " + id);
        Appointment appointmentToDelete = getAppointmentById(id);
        appointments.remove(appointmentToDelete);
        LOGGER.log(Level.INFO, "Appointment deleted: " + appointmentToDelete);
    }

    // Helper method to check if an appointment with the given ID already exists
    private boolean isDuplicateAppointment(int id) {
        return appointments.stream().anyMatch(appointment -> appointment.getId() == id);
    }
}
