/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.model;

/**
 *
 * @author Maryam
 */

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Billing class represents the billing information for a patient.
 */
public class Billing {
    private static final Logger LOGGER = Logger.getLogger(Billing.class.getName());

    private int id; 
    private Patient patient;
    private double invoiceAmount;
    private double paymentAmount;
    private double outstandingBalance;

     // Default constructor
    public Billing() {}
    
    /**
     * Constructor to initialize a Billing object with the provided attributes.
     * @param id The ID of the billing.
     * @param patient The patient associated with the billing.
     * @param invoiceAmount The total amount to be invoiced.
     * @param paymentAmount The amount already paid.
     * @param outstandingBalance The outstanding balance.
     */
    public Billing(int id, Patient patient, double invoiceAmount, double paymentAmount, double outstandingBalance) {
        this.id = id;
        this.patient = patient;
        this.invoiceAmount = invoiceAmount;
        this.paymentAmount = paymentAmount;
        this.outstandingBalance = outstandingBalance;
    }

    // Getters and setters
    public int getId() {
        LOGGER.log(Level.INFO, "Getting ID: " + id);
        return id;
    }

    public void setId(int id) {
        LOGGER.log(Level.INFO, "Setting ID: " + id);
        this.id = id;
    }

    /**
     * Get the patient associated with the billing.
     * @return The patient associated with the billing.
     */
    public Patient getPatient() {
        LOGGER.log(Level.INFO, "Getting patient: " + patient);
        return patient;
    }

    /**
     * Set the patient associated with the billing.
     * @param patient The patient associated with the billing to set.
     */
    public void setPatient(Patient patient) {
        LOGGER.log(Level.INFO, "Setting patient: " + patient);
        this.patient = patient;
    }

    /**
     * Get the total amount to be invoiced.
     * @return The total amount to be invoiced.
     */
    public double getInvoiceAmount() {
        LOGGER.log(Level.INFO, "Getting invoice amount: " + invoiceAmount);
        return invoiceAmount;
    }

    /**
     * Set the total amount to be invoiced.
     * @param invoiceAmount The total amount to be invoiced to set.
     */
    public void setInvoiceAmount(double invoiceAmount) {
        LOGGER.log(Level.INFO, "Setting invoice amount: " + invoiceAmount);
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * Get the amount already paid.
     * @return The amount already paid.
     */
    public double getPaymentAmount() {
        LOGGER.log(Level.INFO, "Getting payment amount: " + paymentAmount);
        return paymentAmount;
    }

    /**
     * Set the amount already paid.
     * @param paymentAmount The amount already paid to set.
     */
    public void setPaymentAmount(double paymentAmount) {
        LOGGER.log(Level.INFO, "Setting payment amount: " + paymentAmount);
        this.paymentAmount = paymentAmount;
    }

    /**
     * Get the outstanding balance.
     * @return The outstanding balance.
     */
    public double getOutstandingBalance() {
        LOGGER.log(Level.INFO, "Getting outstanding balance: " + outstandingBalance);
        return outstandingBalance;
    }

    /**
     * Set the outstanding balance.
     * @param outstandingBalance The outstanding balance to set.
     */
    public void setOutstandingBalance(double outstandingBalance) {
        LOGGER.log(Level.INFO, "Setting outstanding balance: " + outstandingBalance);
        this.outstandingBalance = outstandingBalance;
    }
}
