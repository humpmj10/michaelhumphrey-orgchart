package com.accenture.javadojo.michael.humphrey_orgchart.util.test;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.model.PersonCollection;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.ActivePersonnelIterator;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.CsvFileToCollection;

public class ActivePersonnelIteratorTest {

    private static final Logger log = LoggerFactory
        .getLogger(ActivePersonnelIteratorTest.class);

    private static final String CSV_FILE_10_ROWS = "CSV/personnel_10.csv";

    private PersonCollection persons = new PersonCollection();
    private List<Person> personList;
    private List<Person> emptyList;
    // private Iterator<Person> personItr;
    private ActivePersonnelIterator customItr;
    private Person currentPerson;

    @Before
    public void setUp() throws Exception {

        log.debug("---- Entering ActivePersonnelIteratorTest::setUp()");

        persons = CsvFileToCollection.readPersons(CSV_FILE_10_ROWS);
        personList = new ArrayList<Person>(persons.getPersons());
        customItr = new ActivePersonnelIterator(personList.iterator());
        setCurrentPerson(new Person());
        emptyList = new ArrayList<Person>();

        log.debug("---- Exiting ActivePersonnelIteratorTest::setUp()");

    }

    @Test
    public void hasNextWithEmptyCollTest() {

        log.debug("---- Entering ActivePersonnelIteratorTest::hasNextWithEmptyCollTest()");

        customItr = new ActivePersonnelIterator(emptyList.iterator());

        assertThat(customItr.hasNext()).isFalse();
        assertThat(customItr.hasNext()).isFalse();

        log.debug("---- Exiting ActivePersonnelIteratorTest::hasNextWithEmptyCollTest()");
    }

    @Test
    public void hasNextOneItemTest() {

        log.debug("---- Entering ActivePersonnelIteratorTest::hasNextOneItemTest()");

        emptyList.add(new Person());
        customItr = new ActivePersonnelIterator(emptyList.iterator());

        assertThat(customItr.hasNext()).isTrue();
        assertThat(customItr.hasNext()).isTrue();
        assertThat(customItr.hasNext()).isTrue();

        log.debug("---- Exiting ActivePersonnelIteratorTest::hasNextOneItemTest()");
    }

    @Test(expected = NoSuchElementException.class)
    public void nextEmptyCollTest() {

        log.debug("---- Entering ActivePersonnelIteratorTest::nextEmptyCollTest()");

        customItr = new ActivePersonnelIterator(emptyList.iterator());

        assertThat(customItr.hasNext()).isFalse();
        customItr.next(); // call next on empty collection to cause exception

        log.debug("---- Exiting ActivePersonnelIteratorTest::nextEmptyCollTest()");
    }

    @Test
    public void nextTest() {

        log.debug("---- Entering ActivePersonnelIteratorTest::nextTest()");

        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(0));
        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(1));
        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(2));
        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(4));
        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(5));
        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(6));
        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(7));
        assertThat(setCurrentPerson(customItr.next())).isEqualTo(
            personList.get(8));

        // assertThat(customItr.hasNext()).isFalse();
        // currentPerson = customItr.next();

        log.debug("---- Exiting ActivePersonnelIteratorTest::nextTest()");
    }

    @Test(expected = IllegalStateException.class)
    public void removeObjTwiceTest() {

        log.debug("---- Entering ActivePersonnelIteratorTest::removeObjTwiceTest()");

        customItr.next();
        customItr.remove();
        customItr.remove();

        log.debug("---- Exiting ActivePersonnelIteratorTest::removeObjTwiceTest()");
    }

    @Test
    public void removeTest() {

        log.debug("---- Entering ActivePersonnelIteratorTest::removeTest()");

        emptyList = new ArrayList<Person>();
        emptyList.add(new Person(1L, "Humphrey", "Mike"));
        emptyList.add(new Person(2L, "Bobby", "Mike"));

        customItr = new ActivePersonnelIterator(emptyList.iterator());

        assertThat(emptyList.size()).isEqualTo(2);
        assertThat(emptyList.get(0).toString()).isEqualTo("1: Humphrey, Mike");
        assertThat(emptyList.get(1).toString()).isEqualTo("2: Bobby, Mike");

        customItr.next();
        customItr.remove();

        assertThat(emptyList.size()).isEqualTo(1);
        assertThat(emptyList.get(0).toString()).isEqualTo("2: Bobby, Mike");

        log.debug("---- Exiting ActivePersonnelIteratorTest::removeTest()");

    }

    public Person getCurrentPerson() {

        return currentPerson;
    }

    public Person setCurrentPerson(Person currentPerson) {

        this.currentPerson = currentPerson;
        return currentPerson;
    }

}
