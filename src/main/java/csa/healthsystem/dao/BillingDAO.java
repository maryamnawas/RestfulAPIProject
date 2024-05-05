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
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing Billing entities.
 */
public class BillingDAO {
    private static final Logger LOGGER = Logger.getLogger(BillingDAO.class.getName());

    private List<Billing> billings;
    private int nextId;

    /**
     * Constructs a new BillingDAO.
     */
    public BillingDAO() {
        this.billings = new ArrayList<>();
        this.nextId = 1; // Initialize ID counter
        
//        // Sample billings
//        billings.add(new Billing(1, "John Doe", 500.0, 200.0, 300.0));
//        billings.add(new Billing(2, "Alice Smith", 750.0, 300.0, 450.0));
//        billings.add(new Billing(3, "Bob Johnson", 300.0, 0.0, 300.0));
    }
    
    /**
     * Retrieves all billings.
     * @return List of all billings
     */
    public List<Billing> getAllBillings() {
        LOGGER.log(Level.INFO, "Retrieving all billings");
        return billings;
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
     */
    public void addBilling(Billing billing) {
        LOGGER.log(Level.INFO, "Adding new billing: " + billing);
        if (isDuplicateBilling(billing.getId())) {
            throw new DuplicateException("Billing with ID " + billing.getId() + " already exists");
        }
        billing.setId(nextId++);
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
     */
    public void updateBilling(int id, Billing updatedBilling) {
        LOGGER.log(Level.INFO, "Updating billing with ID: " + id);
        Billing existingBilling = getBillingById(id);
        existingBilling.setPatient(updatedBilling.getPatient());
        existingBilling.setInvoiceAmount(updatedBilling.getInvoiceAmount());
        existingBilling.setPaymentAmount(updatedBilling.getPaymentAmount());
        existingBilling.setOutstandingBalance(updatedBilling.getOutstandingBalance());
        LOGGER.log(Level.INFO, "Billing updated: " + existingBilling);
    }

    /**
     * Deletes a billing by ID.
     * @param id ID of the billing to delete
     * @throws NotFoundException if the specified billing is not found
     */
    public void deleteBilling(int id) {
        LOGGER.log(Level.INFO, "Deleting billing with ID: " + id);
        Billing billingToDelete = getBillingById(id);
        billings.remove(billingToDelete);
        LOGGER.log(Level.INFO, "Billing deleted: " + billingToDelete);
    }
}
