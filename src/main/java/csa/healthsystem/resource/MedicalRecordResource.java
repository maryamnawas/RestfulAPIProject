/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.resource;

/**
 *
 * @author Maryam
 */
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import csa.healthsystem.dao.MedicalRecordDAO;
import csa.healthsystem.model.MedicalRecord;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.DatabaseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resource class for managing medical records.
 * Handles HTTP requests related to medical record entities.
 */
@Path("/medical-records")
public class MedicalRecordResource {
    private final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
    private static final Logger LOGGER = Logger.getLogger(MedicalRecordResource.class.getName());

    /**
     * Retrieves all medical records.
     * @return Response containing list of medical records in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMedicalRecords() {
        LOGGER.log(Level.INFO, "Retrieving all medical records");
        try {
            List<MedicalRecord> medicalRecords = medicalRecordDAO.getAllMedicalRecords();
            return Response.ok(medicalRecords).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving medical records: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Retrieves a medical record by its ID.
     * @param id The ID of the medical record to retrieve
     * @return Response containing the medical record in JSON format if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordById(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Retrieving medical record by ID: " + id);
            MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordById(id);
            LOGGER.log(Level.INFO, "Medical record with ID " + id + " found: " + medicalRecord);
            return Response.ok(medicalRecord).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Medical record with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Adds a new medical record.
     * @param medicalRecord The medical record to add
     * @return Response with status 201 if successful, or 409 if medical record with the same ID already exists
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        try {
            LOGGER.log(Level.INFO, "Adding new medical record: " + medicalRecord);
            medicalRecordDAO.addMedicalRecord(medicalRecord);
            return Response.status(Response.Status.CREATED).build();
        } catch (DuplicateException e) {
            LOGGER.log(Level.WARNING, "Failed to add medical record: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid medical record data: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while adding medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Updates an existing medical record.
     * @param id The ID of the medical record to update
     * @param updatedMedicalRecord The updated medical record object
     * @return Response with status 200 if successful, or 404 if medical record not found
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("id") int id, MedicalRecord updatedMedicalRecord) {
        try {
            LOGGER.log(Level.INFO, "Updating medical record with ID: " + id);
            medicalRecordDAO.updateMedicalRecord(id, updatedMedicalRecord);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to update medical record with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid medical record data for updating: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while updating medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Deletes a medical record by its ID.
     * @param id The ID of the medical record to delete
     * @return Response with status 200 if successful, or 404 if medical record not found
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMedicalRecord(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Deleting medical record with ID: " + id);
            medicalRecordDAO.deleteMedicalRecord(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to delete medical record with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while deleting medical record: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }
}
