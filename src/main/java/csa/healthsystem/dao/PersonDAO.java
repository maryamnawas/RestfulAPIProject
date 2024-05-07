/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csa.healthsystem.dao;

/**
 *
 * @author Maryam
 */
import csa.healthsystem.model.Person;
import csa.healthsystem.exception.NotFoundException;
import csa.healthsystem.exception.DuplicateException;
import csa.healthsystem.exception.InvalidDataException;
import csa.healthsystem.exception.ValidationCheckerException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing Person entities.
 */
public class PersonDAO {
    private static final Logger LOGGER = Logger.getLogger(PersonDAO.class.getName());

    private static final List<Person> persons = new ArrayList<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);

    static {
        // Adding sample persons
        Person person1 = new Person(nextId.getAndIncrement(), "John Doe", "1234567890", "123 Main St");
        Person person2 = new Person(nextId.getAndIncrement(), "Alice Smith", "2345678901", "456 Elm St");
        Person person3 = new Person(nextId.getAndIncrement(), "Bob Johnson", "3456789012", "789 Oak St");
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
    }

    /**
     * Retrieves all persons.
     * @return List of all persons
     */
    public List<Person> getAllPersons() {
        LOGGER.log(Level.INFO, "Retrieving all persons");
        return new ArrayList<>(persons); // Return a copy of the list to prevent modification
    }

    /**
     * Retrieves a person by ID.
     * @param id ID of the person to retrieve
     * @return The person with the specified ID
     * @throws NotFoundException if no person with the specified ID is found
     */
    public Person getPersonById(int id) {
        LOGGER.log(Level.INFO, "Retrieving person by ID: " + id);
        for (Person person : persons) {
            if (person.getId() == id) {
                LOGGER.log(Level.INFO, "Person with ID " + id + " found: " + person);
                return person;
            }
        }
        LOGGER.log(Level.WARNING, "Person with ID " + id + " not found");
        throw new NotFoundException("Person with ID " + id + " not found");
    }

    /**
     * Adds a new person.
     * @param person The person to add
     * @throws DuplicateException if a person with the same ID already exists
     * @throws InvalidDataException if the person data is invalid
     */
    public void addPerson(Person person) {
        LOGGER.log(Level.INFO, "Adding new person: " + person);
        String validationError = ValidationCheckerException.validatePerson(person);
        if (validationError != null) {
            LOGGER.warning("Invalid person data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        if (isDuplicatePerson(person.getId())) {
            throw new DuplicateException("Person with ID " + person.getId() + " already exists");
        }
        person.setId(nextId.getAndIncrement());
        persons.add(person);
    }
    
    // Helper method to check if a person with the given ID already exists
    private boolean isDuplicatePerson(int id) {
        return persons.stream().anyMatch(person -> person.getId() == id);
    }

    /**
     * Updates an existing person.
     * @param id ID of the person to update
     * @param updatedPerson Updated person information
     * @throws NotFoundException if the specified person is not found
     * @throws InvalidDataException if the updated person data is invalid
     */
    public void updatePerson(int id, Person updatedPerson) {
        LOGGER.log(Level.INFO, "Updating person with ID: " + id);
        Person existingPerson = getPersonById(id);
        String validationError = ValidationCheckerException.validatePerson(updatedPerson);
        if (validationError != null) {
            LOGGER.warning("Invalid updated person data: " + validationError);
            throw new InvalidDataException(validationError);
        }
        existingPerson.setName(updatedPerson.getName());
        existingPerson.setContactInformation(updatedPerson.getContactInformation());
        existingPerson.setAddress(updatedPerson.getAddress());
        LOGGER.log(Level.INFO, "Person updated: " + existingPerson);
    }

    /**
     * Deletes a person by ID.
     * @param id ID of the person to delete
     * @throws NotFoundException if the specified person is not found
     */
    public void deletePerson(int id) {
        LOGGER.log(Level.INFO, "Deleting person with ID: " + id);
        Person personToDelete = getPersonById(id);
        persons.remove(personToDelete);
        LOGGER.log(Level.INFO, "Person deleted: " + personToDelete);
    }
}
