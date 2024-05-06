/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.exception;

/**
 *
 * @author Maryam
 */
import csa.healthsystem.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationCheckerException {

    /**
     * Validates general person data.
     * 
     * @param person The Person object to be validated.
     * @return A String message indicating any validation errors, or null if no errors.
     */
    public static String validatePerson(Person person) {
        if (person == null || isStringNullOrEmpty(person.getName()) || isStringNullOrEmpty(person.getName())) {
            return "Invalid person data: name is required";
        }
        if (!isValidContactInfo(person.getContactInformation())) {
            return "Invalid person data: invalid phone number";
        }
        if (isStringNullOrEmpty(person.getAddress())) {
            return "Invalid person data: address is required";
        }
        return null; // Indicates no validation errors
    }

    /**
     * Validates Patient object.
     * 
     * @param patient The Patient object to be validated.
     * @return A String message indicating any validation errors, or null if no errors.
     */
    public static String validatePatient(Patient patient) {

        // Additional validation specific to Patient class attributes
        if (patient == null || validatePerson(patient) != null) {
            return "Invalid patient data: name is required";
        }

        if (isStringNullOrEmpty(patient.getMedicalHistory())) {
            return "Invalid patient data: medical history is required";
        }
        if (isStringNullOrEmpty(patient.getCurrentHealthStatus())) {
            return "Invalid patient data: current health status is required";
        }
        return null; // Indicates no validation errors
    }

    /**
     * Validates Doctor object.
     * 
     * @param doctor The Doctor object to be validated.
     * @return A String message indicating any validation errors, or null if no errors.
     */
    public static String validateDoctor(Doctor doctor) {
        if (doctor == null || validatePerson(doctor) != null) {
            return "Invalid patient data: name is required";
        }
        // Additional validation specific to Doctor class attributes
        if (isStringNullOrEmpty(doctor.getSpecialization())) {
            return "Invalid patient data: specialization is required";
        }
        return null; // Indicates no validation errors
    }

    /**
     * Validates Appointment object.
     * 
     * @param appointment The Appointment object to be validated.
     * @return A String message indicating any validation errors, or null if no errors.
     */
    public static String validateAppointment(Appointment appointment) {
        if (appointment == null || validatePatient(appointment.getPatient()) != null || validateDoctor(appointment.getDoctor()) != null) {
            return "Invalid appointment data: appointment detail is null or invalid input"
                    + " Pls check patient/doctor information ";
        }
        if (!isValidDate(appointment.getDate())) {
            return "Invalid appointment data: date is required in the format YYYY-MM-DD";
        }

        return null; // Indicates no validation errors
    }

    /**
     * Validates Billing object.
     * 
     * @param billing The Billing object to be validated.
     * @return A String message indicating any validation errors, or null if no errors.
     */
    public static String validateBilling(Billing billing) {
        if (billing == null || validatePatient(billing.getPatient()) != null || validateDoctor(billing.getDoctor()) != null) {
            return "Invalid appointment data: appointment object is null"
                    + " Pls check patient/doctor information ";
        }

        if (!isValidBoolean(billing.getInvoiceAmount()) || !isValidBoolean(billing.getPaymentAmount()) || !isValidBoolean(billing.getOutstandingBalance())) {
            return "Invalid billing data: Amount should be greater than zero";
        }

        return null; // Indicates no validation errors
    }

    /**
     * Validates MedicalRecord object.
     * 
     * @param medicalRecord The MedicalRecord object to be validated.
     * @return A String message indicating any validation errors, or null if no errors.
     */
    public static String validateMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord == null || validatePatient(medicalRecord.getPatient()) != null) {
            return "Invalid appointment data: appointment object is null"
                    + " Pls check patient information ";
        }

        if (isStringNullOrEmpty(medicalRecord.getDiagnoses())) {
            return "Invalid medical record data: diagnoses is required";
        }
        if (isStringNullOrEmpty(medicalRecord.getTreatments())) {
            return "Invalid medical record data: treatment is required";
        }
        return null; // Indicates no validation errors
    }

    /**
     * Validates Prescription object.
     * 
     * @param prescription The Prescription object to be validated.
     * @return A String message indicating any validation errors, or null if no errors.
     */
    public static String validatePrescription(Prescription prescription) {
        if (prescription == null || validatePatient(prescription.getPatient()) != null) {
            return "Invalid appointment data: appointment object is null"
                    + " Pls check patient information ";
        }
        if (isStringNullOrEmpty(prescription.getMedication())) {
            return "Invalid prescription data: medication name is required";
        }
        if (isStringNullOrEmpty(prescription.getDosage())) {
            return "Invalid prescription data: dosage information is required";
        }
        if (isStringNullOrEmpty(prescription.getInstructions())) {
            return "Invalid prescription data: instructions are required";
        }
        if (!isValidInt(prescription.getDurationInDays())) {
            return "Invalid prescription data: duration must be greater than 0 days";
        }
        return null; // Indicates no validation errors
    }

    /**
     * Checks if a String is null or empty.
     * 
     * @param str The String to be checked.
     * @return True if the String is null or empty, otherwise false.
     */
    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a double value is valid (greater than 0).
     * 
     * @param num The double value to be checked.
     * @return True if the value is greater than 0, otherwise false.
     */
    public static boolean isValidBoolean(double num) {
        return num > 0.0;
    }

    /**
     * Checks if an integer value is valid (greater than 0).
     * 
     * @param num The integer value to be checked.
     * @return True if the value is greater than 0, otherwise false.
     */
    public static boolean isValidInt(int num) {
        return num > 0;
    }

    /**
     * Checks if a phone number is valid (10 digits).
     * 
     * @param phoneNumber The phone number to be checked.
     * @return True if the phone number is valid, otherwise false.
     */
    public static boolean isValidContactInfo(String phoneNumber) {
        // Check if phone number is not null and has 10 digits
        return phoneNumber != null && phoneNumber.length() == 10 && phoneNumber.matches("\\d{10}");
    }

    /**
     * Checks if a date string is valid (in the format "dd-MM-yyyy").
     * 
     * @param dateString The date string to be checked.
     * @return True if the date string is valid, otherwise false.
     */
    public static boolean isValidDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);
            return date != null; // If parsing is successful, return true
        } catch (ParseException e) {
            return false; // If parsing fails, return false
        }
    }
}
