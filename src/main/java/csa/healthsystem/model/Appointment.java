/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.model;

/**
 *
 * @author Maryam
 */
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Appointment class represents an appointment scheduled between a patient and a doctor.
 */
public class Appointment {
    private static final Logger LOGGER = Logger.getLogger(Appointment.class.getName());

    private int id; // New ID attribute
    private Date date;
    private String time;
    private Patient patient;
    private Doctor doctor;
    
    // Default constructor
    public Appointment() {}
    
    /**
     * Constructor to initialize an Appointment object with the provided attributes.
     * @param id The ID of the appointment.
     * @param date The date of the appointment.
     * @param time The time of the appointment.
     * @param patient The patient scheduled for the appointment.
     * @param doctor The doctor scheduled for the appointment.
     */
    public Appointment(int id, Date date, String time, Patient patient, Doctor doctor) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.patient = patient;
        this.doctor = doctor;
    }

    // Getters and setters
    
    /**
     * Get the id of the appointment.
     * @return The id of the appointment.
     */
    public int getId() {
        LOGGER.log(Level.INFO, "Getting ID: " + id);
        return id;
    }

    /**
     * Set the id of the appointment.
     * @param id The date of the appointment to set.
     */
    public void setId(int id) {
        LOGGER.log(Level.INFO, "Setting ID: " + id);
        this.id = id;
    }

    /**
     * Get the date of the appointment.
     * @return The date of the appointment.
     */
    public Date getDate() {
        LOGGER.log(Level.INFO, "Getting date: " + date);
        return date;
    }

    /**
     * Set the date of the appointment.
     * @param date The date of the appointment to set.
     */
    public void setDate(Date date) {
        LOGGER.log(Level.INFO, "Setting date: " + date);
        this.date = date;
    }

    /**
     * Get the time of the appointment.
     * @return The time of the appointment.
     */
    public String getTime() {
        LOGGER.log(Level.INFO, "Getting time: " + time);
        return time;
    }

    /**
     * Set the time of the appointment.
     * @param time The time of the appointment to set.
     */
    public void setTime(String time) {
        LOGGER.log(Level.INFO, "Setting time: " + time);
        this.time = time;
    }

    /**
     * Get the patient scheduled for the appointment.
     * @return The patient scheduled for the appointment.
     */
    public Patient getPatient() {
        LOGGER.log(Level.INFO, "Getting patient: " + patient);
        return patient;
    }

    /**
     * Set the patient scheduled for the appointment.
     * @param patient The patient scheduled for the appointment to set.
     */
    public void setPatient(Patient patient) {
        LOGGER.log(Level.INFO, "Setting patient: " + patient);
        this.patient = patient;
    }

    /**
     * Get the doctor scheduled for the appointment.
     * @return The doctor scheduled for the appointment.
     */
    public Doctor getDoctor() {
        LOGGER.log(Level.INFO, "Getting doctor: " + doctor);
        return doctor;
    }

    /**
     * Set the doctor scheduled for the appointment.
     * @param doctor The doctor scheduled for the appointment to set.
     */
    public void setDoctor(Doctor doctor) {
        LOGGER.log(Level.INFO, "Setting doctor: " + doctor);
        this.doctor = doctor;
    }
}
