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
import csa.healthsystem.dao.DoctorDAO;
import csa.healthsystem.model.Doctor;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.DatabaseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resource class for managing doctors.
 * Handles HTTP requests related to doctor entities.
 */
@Path("/doctors")
public class DoctorResource {
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private static final Logger LOGGER = Logger.getLogger(DoctorResource.class.getName());

    /**
     * Retrieves all doctors.
     * @return Response containing list of doctors in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDoctors() {
        LOGGER.log(Level.INFO, "Retrieving all doctors");
        try {
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            return Response.ok(doctors).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving doctors: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Retrieves a doctor by its ID.
     * @param id The ID of the doctor to retrieve
     * @return Response containing the doctor in JSON format if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorById(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Retrieving doctor by ID: " + id);
            Doctor doctor = doctorDAO.getDoctorById(id);
            LOGGER.log(Level.INFO, "Doctor with ID " + id + " found: " + doctor);
            return Response.ok(doctor).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Doctor with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving doctor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Adds a new doctor.
     * @param doctor The doctor to add
     * @return Response with status 201 if successful, or 409 if doctor with the same ID already exists
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        try {
            LOGGER.log(Level.INFO, "Adding new doctor: " + doctor);
            doctorDAO.addDoctor(doctor);
            return Response.status(Response.Status.CREATED).build();
        } catch (DuplicateException e) {
            LOGGER.log(Level.WARNING, "Failed to add doctor: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid doctor data: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while adding doctor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Updates an existing doctor.
     * @param id The ID of the doctor to update
     * @param updatedDoctor The updated doctor object
     * @return Response with status 200 if successful, or 404 if doctor not found
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("id") int id, Doctor updatedDoctor) {
        try {
            LOGGER.log(Level.INFO, "Updating doctor with ID: " + id);
            doctorDAO.updateDoctor(id, updatedDoctor);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to update doctor with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid doctor data for updating: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while updating doctor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    /**
     * Deletes a doctor by its ID.
     * @param id The ID of the doctor to delete
     * @return Response with status 200 if successful, or 404 if doctor not found
     */
    @DELETE
    @Path("/{id}")
    public Response deleteDoctor(@PathParam("id") int id) {
        try {
            LOGGER.log(Level.INFO, "Deleting doctor with ID: " + id);
            doctorDAO.deleteDoctor(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to delete doctor with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while deleting doctor: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }
}
