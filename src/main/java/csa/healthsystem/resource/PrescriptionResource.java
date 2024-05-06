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
import csa.healthsystem.dao.PrescriptionDAO;
import csa.healthsystem.model.Prescription;
import csa.healthsystem.exception.DatabaseException;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.DuplicateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/prescriptions")
public class PrescriptionResource {
    private final PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    private static final Logger LOGGER = Logger.getLogger(PrescriptionResource.class.getName());

    /**
     * Retrieves all prescriptions.
     * @return Response containing list of prescriptions in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPrescriptions() {
        LOGGER.log(Level.INFO, "Retrieving all prescriptions");
        try {
            List<Prescription> prescriptions = prescriptionDAO.getAllPrescriptions();
            return Response.ok(prescriptions).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error occurred while retrieving prescriptions: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Retrieves a prescription by its ID.
     * @param id The ID of the prescription to retrieve
     * @return Response containing the prescription in JSON format if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptionById(@PathParam("id") int id) {
        LOGGER.log(Level.INFO, "Retrieving prescription by ID: " + id);
        try {
            Prescription prescription = prescriptionDAO.getPrescriptionById(id);
            LOGGER.log(Level.INFO, "Prescription with ID " + id + " found: " + prescription);
            return Response.ok(prescription).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Prescription with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error occurred while retrieving prescription with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Adds a new prescription.
     * @param prescription The prescription to add
     * @return Response with status 201 if successful
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) {
        LOGGER.log(Level.INFO, "Adding new prescription: " + prescription);
        try {
            prescriptionDAO.addPrescription(prescription);
            return Response.status(Response.Status.CREATED).build();
        } catch (DuplicateException e) {
            LOGGER.log(Level.WARNING, "Duplicate prescription ID: " + prescription.getId());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid prescription data: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error occurred while adding prescription: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Updates an existing prescription.
     * @param id The ID of the prescription to update
     * @param updatedPrescription The updated prescription object
     * @return Response with status 200 if successful, or 404 if prescription not found
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("id") int id, Prescription updatedPrescription) {
        LOGGER.log(Level.INFO, "Updating prescription with ID: " + id);
        try {
            prescriptionDAO.updatePrescription(id, updatedPrescription);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to update prescription with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid prescription data for updating: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error occurred while updating prescription with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Deletes a prescription by its ID.
     * @param id The ID of the prescription to delete
     * @return Response with status 200 if successful, or 404 if prescription not found
     */
    @DELETE
    @Path("/{id}")
    public Response deletePrescription(@PathParam("id") int id) {
        LOGGER.log(Level.INFO, "Deleting prescription with ID: " + id);
        try {
            prescriptionDAO.deletePrescription(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to delete prescription with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error occurred while deleting prescription with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
}
