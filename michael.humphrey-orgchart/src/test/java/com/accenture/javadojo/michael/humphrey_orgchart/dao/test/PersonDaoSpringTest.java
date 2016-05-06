package com.accenture.javadojo.michael.humphrey_orgchart.dao.test;

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
public class PersonDaoSpringTest
extends DatabaseTestCase {

    private static final Logger log = LoggerFactory
        .getLogger(PersonDaoSpringTest.class);

    @Inject
    private PersonDao<Person, Long> personDaoSpring;
    @Inject
    private DataSource dataSource;


    public PersonDaoSpringTest() {

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

        log.debug("---- Entering PersonDaoSpringTest::testFindAll()");

        List<Person> resultPeople;

        resultPeople = personDaoSpring.findAll();

        assertThat(resultPeople.toString()).isEqualTo("[1: Humphrey, Drew]");

        log.debug("---- Exiting PersonDaoSpringTest::testFindAll()");
    }

    @Test
    public void testFindById() {

        log.debug("---- Entering PersonDaoSpringTest::testFindById()");

        Person p = personDaoSpring.findById(1L);

        assertThat(p.getPersonnelId()).isEqualTo(1L);
        assertThat(p.getLastName()).isEqualTo("Humphrey");
        assertThat(p.getFirstName()).isEqualTo("Drew");
        assertThat(p.getPhoneNumber()).isEqualTo("4257093421");
        assertThat(p.getEmail()).isEqualTo("Smith@gmail.com");
        assertThat(p.getLogin()).isEqualTo("drew.humphrey");
        assertThat(p.getPassword()).isEqualTo("password");

        assertThat(personDaoSpring.findById(2L)).isNull();

        log.debug("---- Exiting PersonDaoSpringTest::testFindById()");
    }

    @Test
    public void testInsertPerson() {

        log.debug("---- Entering PersonDaoSpringTest::testInsertPerson()");

        // Insert valid person
        Person p = new Person(2L, 2L, "Humphrey", "Mike");
        assertThat(personDaoSpring.insertPerson(p)).isTrue();

        assertThat(personDaoSpring.findAll().toString()).isEqualTo(
            "[1: Humphrey, Drew, 2: Humphrey, Mike]");

        // Try to insert invalid person
        p = new Person();
        assertThat(personDaoSpring.insertPerson(p)).isFalse();
        assertThat(personDaoSpring.findAll().toString()).isEqualTo(
            "[1: Humphrey, Drew, 2: Humphrey, Mike]");

        log.debug("---- Exiting PersonDaoSpringTest::testInsertPerson()");

    }

    @Test
    public void testDeletePerson() {

        log.debug("---- Entering PersonDaoSpringTest::testDeletePerson()");

        assertThat(personDaoSpring.deletePerson(1L)).isTrue();
        assertThat(personDaoSpring.deletePerson(1L)).isFalse();
        assertThat(personDaoSpring.deletePerson(2L)).isFalse();

        assertThat(personDaoSpring.findAll().toString()).isEqualTo("[]");

        log.debug("---- Exiting PersonDaoSpringTest::testDeletePerson()");
    }

    @Test
    public void testUpdatePerson() {

        log.debug("---- Entering PersonDaoSpringTest::testUpdatePerson()");

        Person updatedPerson = new Person(1L, 1L, "Elvis", "Presley");
        Person invalidPerson = new Person();

        assertThat(personDaoSpring.updatePerson(updatedPerson)).isTrue();
        Person p = personDaoSpring.findById(1L);

        assertThat(p.getPersonnelId()).isEqualTo(1L);
        assertThat(p.getLastName()).isEqualTo("Elvis");
        assertThat(p.getFirstName()).isEqualTo("Presley");
        assertThat(p.getPhoneNumber()).isEqualTo("");
        assertThat(p.getEmail()).isEqualTo("");
        assertThat(p.getLogin()).isEqualTo("");
        assertThat(p.getPassword()).isEqualTo("");

        assertThat(personDaoSpring.updatePerson(invalidPerson)).isFalse();

        log.debug("---- Exiting PersonDaoSpringTest::testUpdatePerson()");
    }

    // getters/setters

    @Override
    protected IDatabaseConnection getConnection() throws Exception {

        return new DatabaseConnection(dataSource.getConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {

        return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
            FileUtility.findFileFromPath("config/personDataBase.xml"))));

    }

}
