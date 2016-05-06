package com.accenture.javadojo.michael.humphrey_orgchart.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.javadojo.michael.humphrey_orgchart.dao.PersonDao;
import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.Validator;

/**
 * This service layer class uses a DAO implementation to access storage and perform CRUD operations.
 *
 * @author michael.humphrey
 *
 */
@Transactional
public class PersonServiceImpl
implements PersonService {

    @Inject
    @Qualifier("personDaoJpa")
    private PersonDao<Person, Long> personDao;

    public PersonServiceImpl(PersonDao<Person, Long> personDao) {

        this.personDao = personDao;
    }

    public PersonServiceImpl() {

    }

    /**
     * Get all person records in the storage
     *
     * @return the list of persons contained in the storage
     */
    @Override
    public List<Person> findAll() {

        return personDao.findAll();
    }

    /**
     * Retrieve a person with specified ID
     *
     * @return the list of records found
     */
    @Override
    public Person findById(String id) {

        if (Validator.personnelIsValid(id)) {
            return personDao.findById(Long.parseLong(id));
        } else {
            return null;
        }
    }

    /**
     * Insert a person into the storage
     *
     * @param p person to be inserted into storage
     * @return true if person inserted
     */
    @Override
    public boolean insertPerson(Person p) {

        if (Validator.personIsValid(p)) {
            return personDao.insertPerson(p);
        } else {
            return false;
        }
    }

    /**
     * Delete a person from the storage
     *
     * @param id id of person to be deleted into storage
     * @return true if person deleted
     */
    @Override
    public boolean deletePerson(String id) {

        if (Validator.personnelIsValid(id)) {
            return personDao.deletePerson(Long.parseLong(id));
        } else {
            return false;
        }
    }

    /**
     * Update a current person in the storage
     *
     * @param updatedPerson person to be updated in storage
     * @return true if the update was successful
     */
    @Override
    public boolean updatePerson(Person updatedPerson) {

        return personDao.updatePerson(updatedPerson);

    }

    // getters/setters

    public PersonDao<Person, Long> getPersonDAO() {

        return personDao;
    }

    public void setPersonDao(PersonDao<Person, Long> personDao) {

        this.personDao = personDao;
    }
}
