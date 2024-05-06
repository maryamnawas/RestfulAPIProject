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
import csa.healthsystem.exception.DatabaseException;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.model.Patient;
import csa.healthsystem.model.Doctor;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAO {
    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());

    private static final List<Appointment> appointments = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private final PatientDAO patientDAO = new PatientDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();

    public AppointmentDAO() throws ParseException {
        // Adding sample appointments
        addSampleAppointments();
    }

    private void addSampleAppointments() {
        try {
            // Fetch patients and doctors from DAO
            Patient patient1 = patientDAO.getPatientById(1000);
            Patient patient2 = patientDAO.getPatientById(1001);
            Patient patient3 = patientDAO.getPatientById(1002);
            Doctor doctor1 = doctorDAO.getDoctorById(1);
            Doctor doctor2 = doctorDAO.getDoctorById(2);
            Doctor doctor3 = doctorDAO.getDoctorById(3);

            // Define appointment data
            String[] appointmentDates = {"2024-05-10", "2024-05-12", "2024-05-15"};
            String[] appointmentTimes = {"10:00 AM", "11:30 AM", "02:00 PM"};

            // Create appointments for each combination of patient and doctor
            for (int i = 0; i < 3; i++) {
                String appointmentDate = appointmentDates[i];
                String appointmentTime = appointmentTimes[i];

                Appointment appointment1 = new Appointment(nextId.getAndIncrement(), patient1, doctor1, appointmentDate, appointmentTime, "Diagnostic Test");
                Appointment appointment2 = new Appointment(nextId.getAndIncrement(), patient2, doctor2, appointmentDate, appointmentTime, "Regular Checkup");
                Appointment appointment3 = new Appointment(nextId.getAndIncrement(), patient3, doctor3, appointmentDate, appointmentTime, "Follow-up Consultation");

                appointments.add(appointment1);
                appointments.add(appointment2);
                appointments.add(appointment3);
            }
        } catch (NotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error adding sample appointments: " + e.getMessage());
        }
    }


    public List<Appointment> getAllAppointments() {
        LOGGER.log(Level.INFO, "Retrieving all appointments");
        return new ArrayList<>(appointments); // Return a copy of the list to prevent modification
    }

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

    public void addAppointment(Appointment appointment) {
        LOGGER.log(Level.INFO, "Adding new appointment: " + appointment);
        appointment.setId(nextId.getAndIncrement());
        appointments.add(appointment);
    }

    public void updateAppointment(int id, Appointment updatedAppointment) {
        LOGGER.log(Level.INFO, "Updating appointment with ID: " + id);
        Appointment existingAppointment = getAppointmentById(id);
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existingAppointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
        existingAppointment.setPurpose(updatedAppointment.getPurpose());
        LOGGER.log(Level.INFO, "Appointment updated: " + existingAppointment);
    }

    public void deleteAppointment(int id) {
        LOGGER.log(Level.INFO, "Deleting appointment with ID: " + id);
        Appointment appointmentToDelete = getAppointmentById(id);
        appointments.remove(appointmentToDelete);
        LOGGER.log(Level.INFO, "Appointment deleted: " + appointmentToDelete);
    }
}
