package com.accenture.javadojo.michael.humphrey_orgchart.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.PersonQueries;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.Validator;

/**
 * JPA implementation of PersonDao.
 *
 * @author michael.humphrey
 *
 */
public class PersonDaoJpa
implements PersonDao<Person, Long> {

    @PersistenceUnit
    @Inject
    protected EntityManager em;

    public PersonDaoJpa() {

    }

    /**
     * Find all records in storage.
     *
     * @return a list of person objects found in storage
     */
    @Override
    public List<Person> findAll() {

        Query query = em.createQuery(PersonQueries.GET_ALL_JPA);
        @SuppressWarnings("unchecked")
        List<Person> results = query.getResultList();
        return results;
    }

    /**
     * Search storage for a record with a specific personnelId
     *
     * @param id the personnelId for the target record
     * @return the person object found with personnelId specified
     */
    @Override
    public Person findById(Long id) {

        return em.find(Person.class, Long.valueOf(id));
    }

    /**
     * Insert a person record into storage
     *
     * @param person the person object to insert
     * @return true if the insertion was successful
     */
    @Override
    public boolean insertPerson(Person person) {

        if (Validator.personIsValid(person)) {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return true;

        }
        return false;
    }

    /**
     * Delete a record in storage with a specified id
     *
     * @param id the id of the target record to be deleted
     * @return true if the deletion was successful
     */
    @Override
    public boolean deletePerson(Long id) {

        Person person = findById(id);

        if (person != null) {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }

    /**
     * Update a record currently in storage
     *
     * @param id the id of the target record to be updated
     * @param updatedPerson the updated person object
     * @return true if the update was successful
     */
    @Override
    public boolean updatePerson(Person updatedPerson) {

        if (Validator.personIsValid(updatedPerson)) {
            em.getTransaction().begin();
            em.merge(updatedPerson);
            em.getTransaction().commit();
            return true;
        }

        return false;
    }

}
