package com.accenture.javadojo.michael.humphrey_orgchart.dao;

import java.io.Serializable;
import java.util.List;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 * This interface handles accessing records from a persistent storage
 *
 * @author michael.humphrey
 *
 */

public interface PersonDao<T, PK extends Serializable> {

    /**
     * Get all person records in the storage
     *
     * @return the list of persons contained in the storage
     */
    List<Person> findAll();

    /**
     * Retrieve person with specified ID
     *
     * @return the list of records found
     */
    Person findById(PK key);

    /**
     * Insert a person into the storage
     *
     * @param p person to be inserted into storage
     * @return true if person inserted
     */
    boolean insertPerson(T objectToInsert);

    /**
     * Delete a person from the storage
     *
     * @param p person to be deleted into storage
     * @return true if person deleted
     */
    boolean deletePerson(PK key);

    /**
     * Update a current person in the storage
     *
     * @param p person to be updated in storage
     * @return true if the update was successful
     */
    boolean updatePerson(T updatedObject);

}
