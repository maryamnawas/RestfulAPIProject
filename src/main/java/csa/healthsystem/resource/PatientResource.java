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
import csa.healthsystem.dao.PatientDAO;
import csa.healthsystem.model.Patient;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.DatabaseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/patients")
public class PatientResource {
    private final PatientDAO patientDAO = new PatientDAO();
    private static final Logger LOGGER = Logger.getLogger(PatientResource.class.getName());

    /**
     * Retrieves all patients.
     * @return Response containing list of patients in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPatients() {
        LOGGER.log(Level.INFO, "Retrieving all patients");
        try {
            List<Patient> patients = patientDAO.getAllPatients();
            return Response.ok(patients).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while retrieving patients: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Retrieves a patient by ID.
     * @param id The ID of the patient to retrieve
     * @return Response containing the patient in JSON format if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("id") int id) {
        LOGGER.log(Level.INFO, "Retrieving patient by ID: " + id);
        try {
            Patient patient = patientDAO.getPatientById(id);
            LOGGER.log(Level.INFO, "Patient with ID " + id + " found: " + patient);
            return Response.ok(patient).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Patient with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while retrieving patient with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Adds a new patient.
     * @param patient The patient to add
     * @return Response with status 201 if successful, or appropriate error status otherwise
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        LOGGER.log(Level.INFO, "Adding new patient: " + patient);
        try {
            patientDAO.addPatient(patient);
            return Response.status(Response.Status.CREATED).build();
        } catch (DuplicateException e) {
            LOGGER.log(Level.WARNING, "Failed to add patient: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid patient data: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding patient: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Updates an existing patient.
     * @param id The ID of the patient to update
     * @param updatedPatient The updated patient object
     * @return Response with status 200 if successful, or appropriate error status otherwise
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("id") int id, Patient updatedPatient) {
        LOGGER.log(Level.INFO, "Updating patient with ID: " + id);
        try {
            patientDAO.updatePatient(id, updatedPatient);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to update patient with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid patient data for updating: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating patient with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Deletes a patient by ID.
     * @param id The ID of the patient to delete
     * @return Response with status 200 if successful, or appropriate error status otherwise
     */
    @DELETE
    @Path("/{id}")
    public Response deletePatient(@PathParam("id") int id) {
        LOGGER.log(Level.INFO, "Deleting patient with ID: " + id);
        try {
            patientDAO.deletePatient(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to delete patient with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting patient with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
}
