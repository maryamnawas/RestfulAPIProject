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
import csa.healthsystem.model.Doctor;
import csa.healthsystem.dao.AppointmentDAO;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.model.Appointment;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicalRecordDAO {
    private static final Logger LOGGER = Logger.getLogger(MedicalRecordDAO.class.getName());

    private static final List<MedicalRecord> medicalRecords = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private final PatientDAO patientDAO = new PatientDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();

    public MedicalRecordDAO() throws ParseException {
        // Ensure sample medical records are added during initialization
        addSampleMedicalRecords();
    }

    private void addSampleMedicalRecords() throws ParseException {
        try {
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            List<Appointment> appointments = appointmentDAO.getAllAppointments();

            for (Appointment appointment : appointments) {
                Patient patient = appointment.getPatient();
                Doctor doctor = appointment.getDoctor();
                String appointmentDate = appointment.getAppointmentDate();
                String reason = ""; // Placeholder for reason
                String treatments = ""; // Placeholder for treatments

                // Determining reason and treatments based on patient's condition
                if (patient.getMedicalHistory().contains("Heart")) {
                    reason = "Heart problem";
                    treatments = "Medication A, Exercise";
                } else if (patient.getMedicalHistory().contains("Diabetes")) {
                    reason = "Diabetes-related issue";
                    treatments = "Insulin therapy, Diet control";
                } else if (patient.getMedicalHistory().contains("Allergy")) {
                    reason = "Allergic reaction";
                    treatments = "Antihistamines, Avoid allergen";
                }

                // Sample medical records using appointment information and condition-based reason and treatments
                MedicalRecord medicalRecord = new MedicalRecord(nextId.getAndIncrement(), patient, doctor, appointmentDate, reason, treatments);

                // Add the medical record to the list
                medicalRecords.add(medicalRecord);
            }
            LOGGER.log(Level.INFO, "Sample medical records added: " + medicalRecords);
        } catch (NotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error adding sample medical records: " + e.getMessage());
        }
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return new ArrayList<>(medicalRecords);
    }

    public MedicalRecord getMedicalRecordById(int id) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId() == id) {
                return medicalRecord;
            }
        }
        throw new NotFoundException("Medical Record with ID " + id + " not found");
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecord.setId(nextId.getAndIncrement());
        medicalRecords.add(medicalRecord);
    }

    public void updateMedicalRecord(int id, MedicalRecord updatedMedicalRecord) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId() == id) {
                medicalRecord.setPatient(updatedMedicalRecord.getPatient());
                medicalRecord.setDoctor(updatedMedicalRecord.getDoctor());
                medicalRecord.setDate(updatedMedicalRecord.getDate());
                medicalRecord.setDiagnosis(updatedMedicalRecord.getDiagnosis());
                medicalRecord.setTreatments(updatedMedicalRecord.getTreatments());
                return;
            }
        }
        throw new NotFoundException("Medical Record with ID " + id + " not found");
    }

    public void deleteMedicalRecord(int id) {
        for (Iterator<MedicalRecord> iterator = medicalRecords.iterator(); iterator.hasNext();) {
            MedicalRecord medicalRecord = iterator.next();
            if (medicalRecord.getId() == id) {
                iterator.remove();
                return;
            }
        }
        throw new NotFoundException("Medical Record with ID " + id + " not found");
    }
}
