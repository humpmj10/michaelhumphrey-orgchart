package com.accenture.javadojo.michael.humphrey_orgchart.services.test;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.InputSource;

import com.accenture.javadojo.michael.humphrey_orgchart.config.SpringConfig;
import com.accenture.javadojo.michael.humphrey_orgchart.dao.PersonDao;
import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.FileUtility;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class PersonDaoJpaTest
extends DatabaseTestCase {

    private static final Logger log = LoggerFactory
        .getLogger(PersonDaoJpaTest.class);

    @Inject
    private PersonDao<Person, Long> personDaoJpa;
    @Inject
    private DataSource dataSource;

    public PersonDaoJpaTest() {

        // Default constructor for bean creation
    }

    @Override
    @Before
    public void setUp() throws Exception {

        IDatabaseConnection connection = this.getConnection();
        IDataSet dataSet = this.getDataSet();

        // This is needed to let DBUnit know that test is using mysql, warning appears otherwise
        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
            new MySqlDataTypeFactory());

        try {
            DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    @Test
    public void testFindAll() {

        log.debug("---- Entering PersonDaoJdbcTest::testFindAll()");

        List<Person> resultPeople;

        resultPeople = personDaoJpa.findAll();

        assertThat(resultPeople.toString()).isEqualTo("[1: Humphrey, Drew]");

        log.debug("---- Exiting PersonDaoJdbcTest::testFindAll()");
    }

    @Test
    public void testFindById() {

        log.debug("---- Entering PersonDaoJdbcTest::testFindById()");

        Person newPerson = new Person(3L, 3L, "Bogart", "Billy");
        assertThat(personDaoJpa.insertPerson(newPerson)).isTrue();

        Person p = personDaoJpa.findById(3L);

        assertThat(p.getPersonnelId()).isEqualTo(3L);
        assertThat(p.getLastName()).isEqualTo("Bogart");
        assertThat(p.getFirstName()).isEqualTo("Billy");
        assertThat(p.getPhoneNumber()).isEqualTo("");
        assertThat(p.getEmail()).isEqualTo("");
        assertThat(p.getLogin()).isEqualTo("");
        assertThat(p.getPassword()).isEqualTo("");


        log.debug("---- Exiting PersonDaoJdbcTest::testFindById()");
    }

    @Test
    public void testInsertPerson() {

        log.debug("---- Entering PersonDaoJdbcTest::testInsertPerson()");

        // Insert valid person
        Person p = new Person(2L, 2L, "Humphrey", "Mike");
        assertThat(personDaoJpa.insertPerson(p)).isTrue();

        assertThat(personDaoJpa.findById(2L)).isEqualTo(p);

        // Try to insert invalid person
        p = new Person();
        assertThat(personDaoJpa.insertPerson(p)).isFalse();
        assertThat(personDaoJpa.findAll().toString()).isEqualTo(
            "[1: Humphrey, Drew, 2: Humphrey, Mike]");

        log.debug("---- Exiting PersonDaoJdbcTest::testInsertPerson()");

    }

    @Test
    public void testDeletePerson() {

        log.debug("---- Entering PersonDaoJdbcTest::testDeletePerson()");

        assertThat(personDaoJpa.deletePerson(1L)).isTrue();
        assertThat(personDaoJpa.deletePerson(1L)).isFalse();

        assertThat(personDaoJpa.findAll().toString()).isEqualTo("[]");

        log.debug("---- Exiting PersonDaoJdbcTest::testDeletePerson()");
    }

    @Test
    public void testUpdatePerson() {

        log.debug("---- Entering PersonDaoJdbcTest::testUpdatePerson()");

        Person updatedPerson = new Person(1L, 1L, "Elvis", "Presley");

        assertThat(personDaoJpa.updatePerson(updatedPerson)).isTrue();
        Person p = personDaoJpa.findById(1L);

        assertThat(p.getPersonnelId()).isEqualTo(1);
        assertThat(p.getLastName()).isEqualTo("Elvis");
        assertThat(p.getFirstName()).isEqualTo("Presley");
        assertThat(p.getPhoneNumber()).isEqualTo("");
        assertThat(p.getEmail()).isEqualTo("");
        assertThat(p.getLogin()).isEqualTo("");
        assertThat(p.getPassword()).isEqualTo("");

        log.debug("---- Exiting PersonDaoJdbcTest::testUpdatePerson()");
    }

    // getters/setters

    @Override
    protected IDatabaseConnection getConnection() throws Exception {

        return new DatabaseConnection(dataSource.getConnection());
    }

    public PersonDao<Person, Long> getPersonDaoJdbc() {

        return personDaoJpa;
    }

    public void setPersonDaoJdbc(PersonDao<Person, Long> personDaoJdbc) {

        this.personDaoJpa = personDaoJdbc;
    }

    public DataSource getDataSource() {

        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {

        return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
            FileUtility.findFileFromPath("config/personDataBase.xml"))));

    }

}
