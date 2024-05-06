/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.dao;

/**
 *
 * @author Maryam
 */
import csa.healthsystem.model.Billing;
import csa.healthsystem.model.Doctor;
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
 * Data Access Object (DAO) for managing Billing entities.
 */
public class BillingDAO {
    private static final Logger LOGGER = Logger.getLogger(BillingDAO.class.getName());

    private static final List<Billing> billings = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    static {
        // Sample billings
        Patient patient1 = new Patient(1000, "John Doe", "0768899123", "123 Main St", "Allergic to penicillin", "Stable");
        Doctor doctor1 = new Doctor(1, "Dr. Smith", "0768899124", "456 Elm St", "Cardiologist");
        billings.add(new Billing(nextId.getAndIncrement(), patient1, doctor1, 500.0, 200.0, 300.0));
        
        Patient patient2 = new Patient(1001, "Alice Smith", "0768899125", "456 Oak St", "Asthmatic", "Critical");
        Doctor doctor2 = new Doctor(2, "Dr. Johnson", "0768899126", "789 Oak St", "Dermatologist");
        billings.add(new Billing(nextId.getAndIncrement(), patient2, doctor2, 750.0, 300.0, 450.0));
        
        Patient patient3 = new Patient(1002, "Bob Johnson", "0768899127", "789 Pine St", "Diabetic", "Stable");
        Doctor doctor3 = new Doctor(3, "Dr. Brown", "0768899128", "987 Maple St", "Pediatrician");
        billings.add(new Billing(nextId.getAndIncrement(), patient3, doctor3, 300.0, 0.0, 300.0));
    }

    /**
     * Retrieves all billings.
     * @return List of all billings
     */
    public List<Billing> getAllBillings() {
        LOGGER.log(Level.INFO, "Retrieving all billings");
        return new ArrayList<>(billings); // Return a copy of the list to prevent modification
    }

    /**
     * Retrieves a billing by ID.
     * @param id ID of the billing to retrieve
     * @return The billing with the specified ID
     * @throws NotFoundException if no billing with the specified ID is found
     */
    public Billing getBillingById(int id) {
        LOGGER.log(Level.INFO, "Retrieving billing by ID: " + id);
        for (Billing billing : billings) {
            if (billing.getId() == id) {
                LOGGER.log(Level.INFO, "Billing with ID " + id + " found: " + billing);
                return billing;
            }
        }
        LOGGER.log(Level.WARNING, "Billing with ID " + id + " not found");
        throw new NotFoundException("Billing with ID " + id + " not found");
    }

    /**
     * Adds a new billing.
     * @param billing The billing to add
     * @throws DuplicateException if a billing with the same ID already exists
     * @throws InvalidDataException if the billing data is invalid
     * @throws DatabaseException if a database error occurs
     */
    public void addBilling(Billing billing) {
        LOGGER.log(Level.INFO, "Adding new billing: " + billing);
        String validationError = ValidationCheckerException.validateBilling(billing);
        if (validationError != null) {
            LOGGER.warning("Invalid billing data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        if (isDuplicateBilling(billing.getId())) {
            throw new DuplicateException("Billing with ID " + billing.getId() + " already exists");
        }
        billing.setId(nextId.getAndIncrement());
        billings.add(billing);
    }
    
    // Helper method to check if a billing with the given ID already exists
    private boolean isDuplicateBilling(int id) {
        return billings.stream().anyMatch(billing -> billing.getId() == id);
    }

    /**
     * Updates an existing billing.
     * @param id ID of the billing to update
     * @param updatedBilling Updated billing information
     * @throws NotFoundException if the specified billing is not found
     * @throws InvalidDataException if the updated billing data is invalid
     * @throws DatabaseException if a database error occurs
     */
    public void updateBilling(int id, Billing updatedBilling) {
        LOGGER.log(Level.INFO, "Updating billing with ID: " + id);
        Billing existingBilling = getBillingById(id);
        String validationError = ValidationCheckerException.validateBilling(updatedBilling);
        if (validationError != null) {
            LOGGER.warning("Invalid billing data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        existingBilling.setPatient(updatedBilling.getPatient());
        existingBilling.setDoctor(updatedBilling.getDoctor());
        existingBilling.setInvoiceAmount(updatedBilling.getInvoiceAmount());
        existingBilling.setPaymentAmount(updatedBilling.getPaymentAmount());
        existingBilling.setOutstandingBalance(updatedBilling.getOutstandingBalance());
        LOGGER.log(Level.INFO, "Billing updated: " + existingBilling);
    }

    /**
     * Deletes a billing by ID.
     * @param id ID of the billing to delete
     * @throws NotFoundException if the specified billing is not found
     * @throws DatabaseException if a database error occurs
     */
    public void deleteBilling(int id) {
        LOGGER.log(Level.INFO, "Deleting billing with ID: " + id);
        Billing billingToDelete = getBillingById(id);
        billings.remove(billingToDelete);
        LOGGER.log(Level.INFO, "Billing deleted: " + billingToDelete);
    }
}
