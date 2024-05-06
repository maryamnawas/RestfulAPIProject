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
import csa.healthsystem.dao.BillingDAO;
import csa.healthsystem.model.Billing;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.DatabaseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/billings")
public class BillingResource {
    private final BillingDAO billingDAO = new BillingDAO();
    private static final Logger LOGGER = Logger.getLogger(BillingResource.class.getName());

    /**
     * Retrieves all billings.
     * @return Response containing list of billings in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBillings() {
        LOGGER.log(Level.INFO, "Retrieving all billings");
        try {
            List<Billing> billings = billingDAO.getAllBillings();
            return Response.ok(billings).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving billings: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Retrieves a billing by its ID.
     * @param id The ID of the billing to retrieve
     * @return Response containing the billing in JSON format if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingById(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Retrieving billing by ID: " + id);
            Billing billing = billingDAO.getBillingById(id);
            return Response.ok(billing).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Billing with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving billing: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Adds a new billing.
     * @param billing The billing to add
     * @return Response with status 201 if successful, or 409 if billing with the same ID already exists
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBilling(Billing billing) {
        try {
            LOGGER.log(Level.INFO, "Adding new billing: " + billing);
            billingDAO.addBilling(billing);
            return Response.status(Response.Status.CREATED).build();
        } catch (DuplicateException e) {
            LOGGER.log(Level.WARNING, "Duplicate billing ID: " + billing.getId());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid billing data: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while adding billing: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates an existing billing.
     * @param id The ID of the billing to update
     * @param updatedBilling The updated billing object
     * @return Response with status 200 if successful, or 404 if billing not found
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("id") int id, Billing updatedBilling) {
        try {
            LOGGER.log(Level.INFO, "Updating billing with ID: " + id);
            billingDAO.updateBilling(id, updatedBilling);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Billing with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid appointment data for updating: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while updating billing: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Deletes a billing by its ID.
     * @param id The ID of the billing to delete
     * @return Response with status 200 if successful, or 404 if billing not found
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBilling(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Deleting billing with ID: " + id);
            billingDAO.deleteBilling(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Billing with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while deleting billing: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
