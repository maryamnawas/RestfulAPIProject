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
import csa.healthsystem.dao.PersonDAO;
import csa.healthsystem.model.Person;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.DatabaseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/persons")
public class PersonResource {
    private final PersonDAO personDAO = new PersonDAO();
    private static final Logger LOGGER = Logger.getLogger(PersonResource.class.getName());

    /**
     * Retrieves all persons.
     * @return Response containing list of persons in JSON format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        LOGGER.log(Level.INFO, "Retrieving all persons");
        try {
            List<Person> persons = personDAO.getAllPersons();
            return Response.ok(persons).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while retrieving persons: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Retrieves a person by ID.
     * @param id The ID of the person to retrieve
     * @return Response containing the person in JSON format if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonById(@PathParam("id") int id) {
        LOGGER.log(Level.INFO, "Retrieving person by ID: " + id);
        try {
            Person person = personDAO.getPersonById(id);
            LOGGER.log(Level.INFO, "Person with ID " + id + " found: " + person);
            return Response.ok(person).build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Person with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while retrieving person with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Adds a new person.
     * @param person The person to add
     * @return Response with status 201 if successful, or appropriate error status otherwise
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        LOGGER.log(Level.INFO, "Adding new person: " + person);
        try {
            personDAO.addPerson(person);
            return Response.status(Response.Status.CREATED).build();
        } catch (DuplicateException e) {
            LOGGER.log(Level.WARNING, "Failed to add person: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid person data: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while adding person: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Updates an existing person.
     * @param id The ID of the person to update
     * @param updatedPerson The updated person object
     * @return Response with status 200 if successful, or appropriate error status otherwise
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") int id, Person updatedPerson) {
        LOGGER.log(Level.INFO, "Updating person with ID: " + id);
        try {
            personDAO.updatePerson(id, updatedPerson);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to update person with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (InvalidDataException e) {
            LOGGER.log(Level.WARNING, "Invalid person data for updating: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while updating person with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

    /**
     * Deletes a person by ID.
     * @param id The ID of the person to delete
     * @return Response with status 200 if successful, or appropriate error status otherwise
     */
    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") int id) {
        LOGGER.log(Level.INFO, "Deleting person with ID: " + id);
        try {
            personDAO.deletePerson(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to delete person with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting person with ID " + id + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
}
