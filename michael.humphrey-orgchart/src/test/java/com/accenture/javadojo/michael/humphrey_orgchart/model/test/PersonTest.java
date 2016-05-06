package com.accenture.javadojo.michael.humphrey_orgchart.model.test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

public class PersonTest {

    private static final Logger log = LoggerFactory.getLogger(PersonTest.class);

    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    private Person person5;
    private Person person6;
    private Person person7;
    private Person person8;
    private Person emptyPerson;

    @Before
    public void setup() {

        log.debug("---- Entering PersonTest::setup()");

        person1 =
            new Person(1100L, "Humphrey", "Michael", "2055550000",
                "mike@gmail.com", null, null, "mike");
        person2 =
            new Person(2200L, "Humphrey", "Mike", "2055550000",
                "mike@gmail.com", null, null, "mike");
        person3 =
            new Person(1100L, "Humphrey", "Michael", "2055550000",
                "mike@gmail.com", null, null, "mike");
        person4 =
            new Person(3200L, "Humphrey", "Michael", "2055550000",
                "mike@gmail.com", null, null, "mike");
        person5 =
            new Person(3200L, "Smith", "Bob", "2055550000", "mike@gmail.com",
                null, null, "mike");

        person6 = null;

        person7 =
            new Person(3200L, "Bezos", "Bob", "2055550000", "mike@gmail.com",
                null, null, "mike");
        person8 =
            new Person(3200L, "Musk", "Bob", "2055550000", "mike@gmail.com",
                null, null, "mike");
        emptyPerson = new Person();

        log.debug("---- Exiting PersonTest::setup()");

    }

    @Test
    public void equalsTest() {

        log.debug("---- Entering PersonTest::equalsTest()");

        Person samePerson = person1;
        Person personNullID1 = new Person();
        Person personNullID2 = new Person();

        int[] num = {1}; // used to test against a person object

        // test to ensure two person objects with the same ID returns true
        assertTrue(person1.equals(samePerson));
        assertTrue(samePerson.equals(person3));

        // test to ensure one person object with the two diff refs returns true
        assertTrue(person1.equals(person3));
        assertTrue(person1.equals(person3));

        // test to ensure two person objects with different ID's returns false
        assertFalse(person1.equals(person2));
        assertFalse(person2.equals(person1));

        // test to ensure null object and object of a different type returns false
        assertFalse(person1.equals(person6));
        assertFalse(person1.equals(num));

        // test when both person objects have a null ID
        assertThat(personNullID1.equals(personNullID2)).isFalse();
        assertThat(personNullID2.equals(personNullID1)).isFalse();

        log.debug("---- Exiting PersonTest::equalsTest()");
    }

    @Test
    public void hashCodeTest() {

        log.debug("---- Entering PersonTest::hashCodeTest()");

        // ensure that hashcode method returns the same result for two objects of the same type
        assertEquals(person1.hashCode(), person3.hashCode());
        assertEquals(person3.hashCode(), person1.hashCode());

        log.debug("---- Exiting PersonTest::hashCodeTest()");
    }

    @Test
    public void toStringTest() {

        log.debug("---- Entering PersonTest::toStringTest()");

        assertEquals(person1.toString(), "1100: Humphrey, Michael");
        assertEquals(emptyPerson.toString(), "null: , ");

        log.debug("---- Exiting PersonTest::toStringTest()");
    }

    @Test
    public void compareToTest() {

        log.debug("---- Entering PersonTest::compareToTest()");

        // comparison when all fields equal
        assertEquals(person1.compareTo(person3), 0);
        assertEquals(person3.compareTo(person1), 0);

        // comparison based on first name
        assertThat(person1.compareTo(person2)).isLessThan(0);
        assertThat(person2.compareTo(person1)).isGreaterThan(0);

        // comparison based on id
        assertThat(person1.compareTo(person4)).isLessThan(0);
        assertThat(person4.compareTo(person1)).isGreaterThan(0);

        // comparison based on last name
        assertThat(person4.compareTo(person5)).isLessThan(0);
        assertThat(person5.compareTo(person4)).isGreaterThan(0);

        // comparison based on last name
        assertThat(person7.compareTo(person8)).isLessThan(0);
        assertThat(person8.compareTo(person7)).isGreaterThan(0);

        log.debug("---- Exiting PersonTest::compareToTest()");

    }
}

