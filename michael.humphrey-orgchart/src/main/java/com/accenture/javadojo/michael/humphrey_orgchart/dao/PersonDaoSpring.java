package com.accenture.javadojo.michael.humphrey_orgchart.dao;

import java.sql.Types;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.PersonQueries;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.PersonRowMapper;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.Validator;

public class PersonDaoSpring
    implements PersonDao<Person, Long> {

    private static final Logger log =
        LoggerFactory.getLogger(PersonDaoSpring.class);

    @Inject
    private JdbcTemplate jdbcTemplate;

    /**
     * Bean default constructor
     */
    public PersonDaoSpring() {

    }

    /**
     * Get all person records in the storage
     *
     * @return the list of persons contained in the storage
     */
    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {

        List<Person> people;

        people =
            jdbcTemplate.query(PersonQueries.GET_ALL, new PersonRowMapper());
        return people;
    }

    /**
     * Get a list of all records with specified ID
     *
     * @return the list of records found
     */
    @Override
    @Transactional(readOnly = true)
    public Person findById(Long id) {

        if (Validator.personnelIsValid(id.toString())) {
            try {
                Person p = jdbcTemplate.queryForObject(PersonQueries.GET_BY_ID,
                    new Object[] {id}, new PersonRowMapper());

                return p;
                // if resultSet returns no rows, exception is thrown
            } catch (EmptyResultDataAccessException e) {
                log.debug(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Insert a person into the storage
     *
     * @param person person to be inserted into storage
     * @return true if person inserted
     */
    @Override
    @Transactional(readOnly = false)
    public boolean insertPerson(Person person) {

        if (Validator.personIsValid(person)) {
            int result;

            // query args
            Object[] params = getParams(person);

            // query type to match args
            int[] types = getTypes();

            result = jdbcTemplate.update(PersonQueries.INSERT, params, types);

            return checkIfRowsWereAffected(result);
        } else {
            return false;
        }
    }

    /**
     * Delete a person from the storage
     *
     * @param p person to be deleted into storage
     * @return true if person deleted
     */
    @Override
    @Transactional(readOnly = false)
    public boolean deletePerson(Long id) {

        if (Validator.personnelIsValid(id.toString())) {
            int result;

            result =
                jdbcTemplate.update(PersonQueries.DELETE, new Object[] {id});

            return checkIfRowsWereAffected(result);
        } else {
            return false;
        }
    }

    /**
     * Update a current person in the storage
     *
     * @param person person to be updated in storage
     * @return true if the update was successful
     */
    @Override
    @Transactional(readOnly = false)
    public boolean updatePerson(Person person) {

        if (Validator.personIsValid(person)) {
            int result;

            // query args
            Object[] params = getParamsUpdate(person, person.getId());

            // query type to match args
            int[] types = getTypesUpdate();

            result = jdbcTemplate.update(PersonQueries.UPDATE, params, types);

            // result is the number of rows affected by sql statement
            return checkIfRowsWereAffected(result);
        } else {
            return false;
        }

    }

    /**
     * Helper method to see any rows were affected by the query, positive integer confirms that
     * query was successful
     */
    private boolean checkIfRowsWereAffected(int result) {

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper method to set types to match a person record in the table
     *
     * @return the type for each field in person
     */
    private int[] getTypes() {

        int[] types = new int[] {Types.INTEGER, Types.BIGINT, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE,
            Types.VARCHAR, Types.VARCHAR};
        return types;
    }

    /**
     * Helper method to get person parameters and set as objects
     *
     * @param p the person to get values for fields in table
     * @return array of parameters of a person
     */
    private Object[] getParams(Person p) {

        Object[] params = new Object[] {p.getId(), p.getPersonnelId(),
            p.getLastName(), p.getFirstName(), p.getPhoneNumber(), p.getEmail(),
            p.getStartDate(), p.getEndDate(), p.getLogin(), p.getPassword()};
        return params;
    }

    /**
     * Helper method to set types to match a person record in the table
     *
     * @return the type for each field in person
     */
    private int[] getTypesUpdate() {

        int[] types = new int[] {Types.INTEGER, Types.BIGINT, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE,
            Types.VARCHAR, Types.VARCHAR, Types.BIGINT};
        return types;
    }

    /**
     * Helper method to get person parameters and set as objects
     *
     * @param p the person to get values for fields in table
     * @return array of parameters of a person
     */
    private Object[] getParamsUpdate(Person p, Long key) {

        Object[] params = new Object[] {p.getId(), p.getPersonnelId(),
            p.getLastName(), p.getFirstName(), p.getPhoneNumber(), p.getEmail(),
            p.getStartDate(), p.getEndDate(), p.getLogin(), p.getPassword(),
            key};
        return params;
    }
}
