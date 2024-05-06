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
import csa.healthsystem.exception.DatabaseException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/medical-records")
public class MedicalRecordResource {
    private final MedicalRecordDAO medicalRecordDAO;
    private static final Logger LOGGER = Logger.getLogger(MedicalRecordResource.class.getName());

    public MedicalRecordResource() throws ParseException {
        this.medicalRecordDAO = new MedicalRecordDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMedicalRecords() {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordDAO.getAllMedicalRecords();
            return Response.ok(medicalRecords).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving medical records: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordById(@PathParam("id") int id) {
        try {
            MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordById(id);
            return Response.ok(medicalRecord).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        try {
            medicalRecordDAO.addMedicalRecord(medicalRecord);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error occurred").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("id") int id, MedicalRecord updatedMedicalRecord) {
        try {
            medicalRecordDAO.updateMedicalRecord(id, updatedMedicalRecord);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMedicalRecord(@PathParam("id") int id) {
        try {
            medicalRecordDAO.deleteMedicalRecord(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
