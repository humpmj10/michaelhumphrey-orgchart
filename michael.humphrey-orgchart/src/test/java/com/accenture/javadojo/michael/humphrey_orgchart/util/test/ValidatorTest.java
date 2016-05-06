package com.accenture.javadojo.michael.humphrey_orgchart.util.test;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.utility.Validator;

public class ValidatorTest {

    private static final Logger log = LoggerFactory
        .getLogger(ValidatorTest.class);

    private static final String VALID_PHONE = "4257708899";
    private static final String VALID_PHONE_1 = "425-770-8899";
    private static final String VALID_PHONE_2 = "425.770.8899";
    private static final String VALID_PHONE_3 = "(425) 770.8899";
    private static final String VALID_PHONE_4 = "(425) 770-8899";

    private static final String NON_VALID_PHONE = "770-8899";
    private static final String NON_VALID_PHONE_1 = "770-889A";
    private static final String NON_VALID_PHONE_2 = "7708899";

    private static final String VALID_EMAIL = "mike@gmail.com";
    private static final String VALID_EMAIL_1 = "123@me.com";
    private static final String NON_VALID_EMAIL = "Mike@gmail@com";

    private static final String VALID_DATE_FORMAT_1 = "10/09/2003";
    private static final String VALID_DATE_FORMAT_2 = "10.5.1950";
    private static final String VALID_DATE_FORMAT_3 = "7/21/2000 10:05:10 AM";
    private static final String VALID_DATE_FORMAT_4 = "10.3.2015 12:56:55 PM";
    private static final String VALID_DATE_FORMAT_5 = "4.Jul.2012";
    private static final String VALID_DATE_FORMAT_6 = "4-Aug-2002";
    private static final String VALID_DATE_FORMAT_7 = "4-7-2012";
    private static final String VALID_DATE_FORMAT_8 = "1940-5-31";
    private static final String NON_VALID_DATE = "10/0a/2003";
    private static final String NON_VALID_DATE_1 = "10/AB/2000";

    private static final String VALID_PERSONNEL_ID = "1";
    private static final String VALID_PERSONNEL_ID_1 = "10010010010010010010";
    private static final String NON_VALID_PERSONNEL_ID = "";
    private static final String NON_VALID_PERSONNEL_ID_1 =
        "100100100100100100101";
    private static final String NON_VALID_PERSONNEL_ID_2 = "100a000";

    private static final String EMPTY = "";

    // constants used to define 3 different days, in milliseconds
    private static final long YESTERDAY_MILLI = 172800000;
    private static final long TODAY_MILLI = 259200000;
    private static final Date YESTERDAY = new Date(YESTERDAY_MILLI);
    private static final Date TODAY = new Date(TODAY_MILLI);

    private Person nonValidPerson = new Person();
    private Person ValidPerson = new Person(1L, "Humphrey", "Mike");
    private Person ValidPersonWithDates = new Person(1L, "Humphrey", "Mike");

    @Before
    public void setUp() throws Exception {

        log.debug("---- Entering ValidatorTest::setUp()");

        ValidPerson.setPhoneNumber("425-770-6677");

        log.debug("---- Exiting ValidatorTest::setUp()");

    }

    @Test
    public void testPhoneNumIsValid() {

        log.debug("---- Entering ValidatorTest::testPhoneNumIsValid()");

        assertThat(Validator.phoneNumIsValid(VALID_PHONE)).isTrue();
        assertThat(Validator.phoneNumIsValid(VALID_PHONE_1)).isTrue();
        assertThat(Validator.phoneNumIsValid(VALID_PHONE_2)).isTrue();
        assertThat(Validator.phoneNumIsValid(VALID_PHONE_3)).isTrue();
        assertThat(Validator.phoneNumIsValid(VALID_PHONE_4)).isTrue();

        assertThat(Validator.phoneNumIsValid(NON_VALID_PHONE)).isFalse();
        assertThat(Validator.phoneNumIsValid(NON_VALID_PHONE_1)).isFalse();
        assertThat(Validator.phoneNumIsValid(NON_VALID_PHONE_2)).isFalse();
        assertThat(Validator.phoneNumIsValid(EMPTY)).isFalse();

        log.debug("---- Exiting ValidatorTest::testPhoneNumIsValid()");
    }

    @Test
    public void testEmailIsValid() {

        log.debug("---- Entering ValidatorTest::testEmailIsValid()");

        assertThat(Validator.emailIsValid(VALID_EMAIL)).isTrue();
        assertThat(Validator.emailIsValid(VALID_EMAIL_1)).isTrue();

        assertThat(Validator.emailIsValid(NON_VALID_EMAIL)).isFalse();
        assertThat(Validator.emailIsValid(EMPTY)).isFalse();

        log.debug("---- Exiting ValidatorTest::testEmailIsValid()");
    }

    @Test
    public void testDateIsValid() {

        log.debug("---- Entering ValidatorTest::testDateIsValid()");

        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_1)).isTrue();
        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_2)).isTrue();
        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_3)).isTrue();
        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_4)).isTrue();
        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_5)).isTrue();
        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_6)).isTrue();
        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_7)).isTrue();
        assertThat(Validator.dateIsValid(VALID_DATE_FORMAT_8)).isTrue();

        assertThat(Validator.dateIsValid(NON_VALID_DATE)).isFalse();
        assertThat(Validator.dateIsValid(NON_VALID_DATE_1)).isFalse();
        assertThat(Validator.dateIsValid(EMPTY)).isFalse();

        log.debug("---- Exiting ValidatorTest::testDateIsValid()");
    }

    @Test
    public void testPersonIsValid() {

        log.debug("---- Entering ValidatorTest::testPersonIsValid()");

        assertThat(Validator.personIsValid(ValidPerson)).isTrue();
        assertThat(Validator.personIsValid(ValidPersonWithDates)).isTrue();

        assertThat(Validator.personIsValid(nonValidPerson)).isFalse();
        nonValidPerson.setPersonnelId(1L); // first required field
        assertThat(Validator.personIsValid(nonValidPerson)).isFalse();
        nonValidPerson.setLastName("Humphrey"); // second required field
        assertThat(Validator.personIsValid(nonValidPerson)).isFalse();
        nonValidPerson.setFirstName("Mike"); // third required field
        assertThat(Validator.personIsValid(nonValidPerson)).isTrue(); // all required fields
        nonValidPerson.setPhoneNumber("ABC");
        assertThat(Validator.personIsValid(nonValidPerson)).isFalse();
        nonValidPerson.setEmail("Mike");
        nonValidPerson.setPhoneNumber("4257722000");
        assertThat(Validator.personIsValid(nonValidPerson)).isFalse();
        nonValidPerson.setEmail("mike@gmail.com");

        log.debug("---- Exiting ValidatorTest::testPersonIsValid()");
    }

    @Test
    public void testPersonIsValidWithDates() {

        log.debug("---- Entering ValidatorTest::testPersonIsValidWithDates()");

        nonValidPerson.setPersonnelId(1L);
        nonValidPerson.setLastName("Humphrey");
        nonValidPerson.setFirstName("Mike");

        assertThat(Validator.personIsValid(nonValidPerson)).isTrue();

        nonValidPerson.setStartDate(TODAY);
        nonValidPerson.setEndDate(YESTERDAY);

        assertThat(Validator.personIsValid(nonValidPerson)).isFalse();

        log.debug("---- Exiting ValidatorTest::testPersonIsValidWithDates()");
    }

    @Test
    public void testPersonnelId() {

        log.debug("---- Entering ValidatorTest::testPersonnelId()");

        assertThat(Validator.personnelIsValid(VALID_PERSONNEL_ID)).isTrue();
        assertThat(Validator.personnelIsValid(VALID_PERSONNEL_ID_1)).isTrue();

        assertThat(Validator.personnelIsValid(NON_VALID_PERSONNEL_ID))
        .isFalse();
        assertThat(Validator.personnelIsValid(NON_VALID_PERSONNEL_ID_1))
        .isFalse();
        assertThat(Validator.personnelIsValid(NON_VALID_PERSONNEL_ID_2))
        .isFalse();

        log.debug("---- Exiting ValidatorTest::testPersonnelId()");
    }
}
