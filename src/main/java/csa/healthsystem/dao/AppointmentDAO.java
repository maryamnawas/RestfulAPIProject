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
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing Appointment entities.
 */
public class AppointmentDAO {
    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());

    private List<Appointment> appointments;
    private int nextId;

    /**
     * Constructs a new AppointmentDAO.
     */
    public AppointmentDAO() {
        this.appointments = new ArrayList<>();
        this.nextId = 1; // Initialize ID counter
        
//        // Sample appointments
//        appointments.add(new Appointment(1, "2024-05-05", "09:00", "John Doe", "Dr. Smith"));
//        appointments.add(new Appointment(2, "2024-05-06", "10:30", "Alice Smith", "Dr. Johnson"));
//        appointments.add(new Appointment(3, "2024-05-07", "11:45", "Bob Johnson", "Dr. Brown"));
    }

    /**
     * Retrieves all appointments.
     * @return List of all appointments
     */
    public List<Appointment> getAllAppointments() {
        LOGGER.log(Level.INFO, "Retrieving all appointments");
        return appointments;
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
     */
    public void addAppointment(Appointment appointment) {
        LOGGER.log(Level.INFO, "Adding new appointment: " + appointment);
        if (isDuplicateAppointment(appointment.getId())) {
            throw new DuplicateException("Appointment with ID " + appointment.getId() + " already exists");
        }
        appointment.setId(nextId++);
        appointments.add(appointment);
    }
    
    // Helper method to check if an appointment with the given ID already exists
    private boolean isDuplicateAppointment(int id) {
        return appointments.stream().anyMatch(appointment -> appointment.getId() == id);
    }
    
    /**
     * Updates an existing appointment.
     * @param id ID of the appointment to update
     * @param updatedAppointment Updated appointment information
     * @throws NotFoundException if the specified appointment is not found
     */
    public void updateAppointment(int id, Appointment updatedAppointment) {
        LOGGER.log(Level.INFO, "Updating appointment with ID: " + id);
        Appointment existingAppointment = getAppointmentById(id);
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
}
