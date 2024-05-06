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
import csa.healthsystem.dao.AppointmentDAO;
import csa.healthsystem.model.Appointment;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DatabaseException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appointments")
public class AppointmentResource {
    private final AppointmentDAO appointmentDAO;
    private static final Logger LOGGER = Logger.getLogger(AppointmentResource.class.getName());

    public AppointmentResource() throws ParseException {
        this.appointmentDAO = new AppointmentDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAppointments() {
        LOGGER.log(Level.INFO, "Retrieving all appointments");
        try {
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            return Response.ok(appointments).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving appointments: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentById(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Retrieving appointment by ID: " + id);
            Appointment appointment = appointmentDAO.getAppointmentById(id);
            LOGGER.log(Level.INFO, "Appointment with ID " + id + " found: " + appointment);
            return Response.ok(appointment).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Appointment with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment) {
        try {
            LOGGER.log(Level.INFO, "Adding new appointment: " + appointment);
            appointmentDAO.addAppointment(appointment);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while adding appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") int id, Appointment updatedAppointment) {
        try {
            LOGGER.log(Level.INFO, "Updating appointment with ID: " + id);
            appointmentDAO.updateAppointment(id, updatedAppointment);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to update appointment with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while updating appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAppointment(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Deleting appointment with ID: " + id);
            appointmentDAO.deleteAppointment(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to delete appointment with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while deleting appointment: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }
}
