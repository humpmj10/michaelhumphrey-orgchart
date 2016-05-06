package com.accenture.javadojo.michael.humphrey_orgchart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.PersonQueries;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.Validator;

/**
 * Data access class that interfaces with a mysql database to create, update, delete and retrieve
 * person records stored.
 *
 * @author michael.humphrey
 *
 */
public class PersonDaoJdbc
implements PersonDao<Person, Long> {

    private static final Logger log = LoggerFactory
        .getLogger(PersonDaoJdbc.class);

    @Inject
    private DataSource dataSource;

    private Connection conn;

    /**
     * Bean default constructor
     */
    public PersonDaoJdbc() {

    }

    /**
     * Init method to get datasource connection
     */
    public void beanInit() {

        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Get all person records in the storage
     *
     * @return the list of persons contained in the storage
     */
    @Override
    public List<Person> findAll() {

        ArrayList<Person> people = new ArrayList<Person>();
        Person p;
        PreparedStatement stmt;
        ResultSet rs;

        openConnection();

        try {
            stmt = conn.prepareStatement(PersonQueries.GET_ALL);
            rs = stmt.executeQuery(PersonQueries.GET_ALL);
            log.debug("Ran " + PersonQueries.GET_ALL + " query");
            while (rs.next()) {
                p = extractPersonFromResultSet(rs);
                people.add(p);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            closeConnection();
        }

        return people;
    }

    /**
     * Get a list of all records with specified ID
     *
     * @return the list of records found
     */
    @Override
    public Person findById(Long Id) {

        Person targetPerson = null;
        PreparedStatement stmt;
        ResultSet rs;

        if (Validator.personnelIsValid(Id.toString())) {
            try {
                openConnection();
                log.debug("Ran " + PersonQueries.GET_BY_ID + " query");
                stmt = conn.prepareStatement(PersonQueries.GET_BY_ID);
                stmt.setLong(1, Id);

                rs = stmt.executeQuery();

                if (rs.next() != false) {
                    targetPerson = extractPersonFromResultSet(rs);
                }
                return targetPerson;
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                return targetPerson;
            } finally {
                closeConnection();
            }
        }
        return targetPerson;

    }

    /**
     * Insert a person into the storage
     *
     * @param person person to be inserted into storage
     * @return true if person inserted
     */
    @Override
    public boolean insertPerson(Person person) {

        PreparedStatement stmt;

        if (Validator.personIsValid(person)) {
            openConnection();
            log.debug("Ran " + PersonQueries.INSERT + " query");
            try {
                stmt = conn.prepareStatement(PersonQueries.INSERT);
                setStatementParams(person, stmt);
                stmt.execute();
                int result = stmt.getUpdateCount();
                if (result < 1) {
                    return false;
                }
                return true;
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                return false;
            } finally {
                closeConnection();
            }

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
    public boolean deletePerson(Long id) {

        if (Validator.personnelIsValid(id.toString())) {
            PreparedStatement stmt;
            openConnection();
            log.debug("Ran " + PersonQueries.DELETE + " query");
            try {
                stmt = conn.prepareStatement(PersonQueries.DELETE);
                stmt.setLong(1, id);
                stmt.execute();
                int result = stmt.getUpdateCount();
                if (result < 1) {
                    return false;
                }
                return true;
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                return false;
            } finally {
                closeConnection();
            }
        }

        return false;
    }

    /**
     * Update a current person in the storage
     *
     * @param updatedPerson to be updated in storage
     * @return true if the update was successful
     */
    @Override
    public boolean updatePerson(Person updatedPerson) {

        if (Validator.personIsValid(updatedPerson)) {

            PreparedStatement stmt;
            openConnection();
            log.debug("Ran " + PersonQueries.UPDATE + " query");
            try {
                stmt = conn.prepareStatement(PersonQueries.UPDATE);
                setStatementParams(updatedPerson, stmt);
                stmt.setLong(11, updatedPerson.getId());
                stmt.execute();
                int result = stmt.getUpdateCount();
                if (result < 1) {
                    return false;
                }
                return true;
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                return false;
            } finally {
                closeConnection();
            }
        }

        return false;
    }

    /**
     * Helper method that sets the stmt parameters from person fields
     *
     * @param p
     * @throws SQLException
     */
    private void setStatementParams(Person p, PreparedStatement stmt)
        throws SQLException {

        stmt.setLong(1, p.getId());
        stmt.setLong(2, p.getPersonnelId());
        stmt.setString(3, p.getLastName());
        stmt.setString(4, p.getFirstName());
        stmt.setString(5, p.getPhoneNumber());
        stmt.setString(6, p.getEmail());

        if (p.getStartDate() != null) {
            stmt.setDate(7, new java.sql.Date(p.getStartDate().getTime()));
        } else {
            stmt.setDate(7, null);
        }
        if (p.getEndDate() != null) {
            stmt.setDate(8, new java.sql.Date(p.getEndDate().getTime()));
        } else {
            stmt.setDate(8, null);
        }
        stmt.setString(9, p.getLogin());
        stmt.setString(10, p.getPassword());

    }

    /**
     * Helper method to extact person properties from result set
     *
     * @return the newly created person object
     * @throws SQLException
     */
    private Person extractPersonFromResultSet(ResultSet rs) throws SQLException {

        Person p;
        p = new Person();
        p.setId(rs.getLong("id"));
        p.setPersonnelId(rs.getLong("personnelId"));
        p.setLastName(rs.getString("lastName"));
        p.setFirstName(rs.getString("firstName"));
        p.setPhoneNumber(rs.getString("phoneNumber"));
        p.setEmail(rs.getString("email"));
        if (rs.getDate("startDate") != null) {
            p.setStartDate(new Date(rs.getDate("startDate").getTime()));
        }
        if (rs.getDate("endDate") != null) {
            p.setEndDate(new Date(rs.getDate("endDate").getTime()));
        }
        p.setLogin(rs.getString("login"));
        p.setPassword(rs.getString("password"));

        return p;
    }

    /**
     * Open the connection with the database
     */
    private void openConnection() {

        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Helper method to insure that connection, resultSet and preparedStatment are all closed after
     * connecting with database
     *
     * @param conn the connection object to close
     * @param rs the result set to close
     * @param ps the preparedStatement to close
     */
    private void closeConnection() {

        if (conn != null) {
            try {

                conn.close();
            } catch (SQLException sqlEx) {
                log.error(sqlEx.getMessage(), sqlEx);
            }
        }

    }

    // getters/setters

    public DataSource getDataSource() {

        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public Connection getConn() {

        return conn;
    }

    public void setConn(Connection conn) {

        this.conn = conn;
    }

}
