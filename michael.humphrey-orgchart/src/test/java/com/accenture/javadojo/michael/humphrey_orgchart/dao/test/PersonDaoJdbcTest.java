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
public class PersonDaoJdbcTest
extends DatabaseTestCase {

    private static final Logger log = LoggerFactory
        .getLogger(PersonDaoJdbcTest.class);

    @Inject
    private PersonDao<Person, Long> personDaoJdbc;
    @Inject
    private DataSource dataSource;

    public PersonDaoJdbcTest() {

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

        resultPeople = personDaoJdbc.findAll();

        assertThat(resultPeople.toString()).isEqualTo("[1: Humphrey, Drew]");

        log.debug("---- Exiting PersonDaoJdbcTest::testFindAll()");
    }

    @Test
    public void testFindById() {

        log.debug("---- Entering PersonDaoJdbcTest::testFindById()");

        Person p = personDaoJdbc.findById(1L);

        assertThat(p.getPersonnelId()).isEqualTo(1L);
        assertThat(p.getLastName()).isEqualTo("Humphrey");
        assertThat(p.getFirstName()).isEqualTo("Drew");
        assertThat(p.getPhoneNumber()).isEqualTo("4257093421");
        assertThat(p.getEmail()).isEqualTo("Smith@gmail.com");
        assertThat(p.getLogin()).isEqualTo("drew.humphrey");
        assertThat(p.getPassword()).isEqualTo("password");

        assertThat(personDaoJdbc.findById(2L)).isNull();

        log.debug("---- Exiting PersonDaoJdbcTest::testFindById()");
    }

    @Test
    public void testInsertPerson() {

        log.debug("---- Entering PersonDaoJdbcTest::testInsertPerson()");

        // Insert valid person
        Person p = new Person(2L, 2L, "Humphrey", "Mike");
        assertThat(personDaoJdbc.insertPerson(p)).isTrue();

        assertThat(personDaoJdbc.findAll().toString()).isEqualTo(
            "[1: Humphrey, Drew, 2: Humphrey, Mike]");

        // Try to insert invalid person
        p = new Person();
        assertThat(personDaoJdbc.insertPerson(p)).isFalse();
        assertThat(personDaoJdbc.findAll().toString()).isEqualTo(
            "[1: Humphrey, Drew, 2: Humphrey, Mike]");

        log.debug("---- Exiting PersonDaoJdbcTest::testInsertPerson()");

    }

    @Test
    public void testDeletePerson() {

        log.debug("---- Entering PersonDaoJdbcTest::testDeletePerson()");

        assertThat(personDaoJdbc.deletePerson(1L)).isTrue();
        assertThat(personDaoJdbc.deletePerson(1L)).isFalse();
        assertThat(personDaoJdbc.deletePerson(2L)).isFalse();

        assertThat(personDaoJdbc.findAll().toString()).isEqualTo("[]");

        log.debug("---- Exiting PersonDaoJdbcTest::testDeletePerson()");
    }

    @Test
    public void testUpdatePerson() {

        log.debug("---- Entering PersonDaoJdbcTest::testUpdatePerson()");

        Person updatedPerson = new Person(1L, 1L, "Elvis", "Presley");
        Person invalidPerson = new Person();

        assertThat(personDaoJdbc.updatePerson(updatedPerson)).isTrue();
        Person p = personDaoJdbc.findById(1L);

        assertThat(p.getPersonnelId()).isEqualTo(1L);
        assertThat(p.getLastName()).isEqualTo("Elvis");
        assertThat(p.getFirstName()).isEqualTo("Presley");
        assertThat(p.getPhoneNumber()).isEqualTo("");
        assertThat(p.getEmail()).isEqualTo("");
        assertThat(p.getLogin()).isEqualTo("");
        assertThat(p.getPassword()).isEqualTo("");

        assertThat(personDaoJdbc.updatePerson(invalidPerson)).isFalse();

        log.debug("---- Exiting PersonDaoJdbcTest::testUpdatePerson()");
    }

    // getters/setters

    @Override
    protected IDatabaseConnection getConnection() throws Exception {

        return new DatabaseConnection(dataSource.getConnection());
    }

    public PersonDao<Person, Long> getPersonDaoJdbc() {

        return personDaoJdbc;
    }

    public void setPersonDaoJdbc(PersonDao<Person, Long> personDaoJdbc) {

        this.personDaoJdbc = personDaoJdbc;
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
