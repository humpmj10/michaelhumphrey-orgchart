package com.accenture.javadojo.michael.humphrey_orgchart.services;

import java.util.List;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 * Service that uses DAO class to access data in the storage
 */
public interface PersonService {

    /**
     * Get all person records in the storage
     *
     * @return the list of persons contained in the storage
     */
    List<Person> findAll();

    /**
     * Retrieve a person with specified ID
     *
     * @return the list of records found
     */
    Person findById(String personellID);

    /**
     * Insert a person into the storage
     *
     * @param p person to be inserted into storage
     * @return true if person inserted
     */
    boolean insertPerson(Person p);

    /**
     * Delete a person from the storage
     *
     * @param personnelId id of person to be deleted into storage
     * @return true if person deleted
     */
    boolean deletePerson(String personnelId);

    /**
     * Update a current person in the storage
     *
     * @param p person to be updated in storage
     * @return true if the update was successful
     */
    boolean updatePerson(Person updatedPerson);

}
