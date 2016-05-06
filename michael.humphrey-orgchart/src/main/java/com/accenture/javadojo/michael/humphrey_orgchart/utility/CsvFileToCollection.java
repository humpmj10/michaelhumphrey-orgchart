package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.model.PersonCollection;

/**
 * Utility class that reads a CSV file and places each row as an object into a person collection.
 *
 * @author michael.humphrey
 *
 */
public final class CsvFileToCollection {

    // Class should not be extended and made final for security reasons
    private static final Logger log = LoggerFactory
        .getLogger(CsvFileToCollection.class);

    private CsvFileToCollection() {

        // Private empty constructor to ensure that class is not instantiated
    }

    /**
     * Read the CSV file and enter person objects into collection
     *
     * @param filePath path to file to be read
     * @return the collection of person objects read from the CSV file
     */
    public static PersonCollection readPersons(String filePath)
        throws IOException {

        PersonCollection persons = null;

        persons = getDataFromCSVFile(FileUtility.findFileFromPath(filePath));

        return persons;
    }

    /**
     * Helper method that reads the CSV file and enters each person object into the collection
     *
     * @param csvFileReader the reader object
     * @return the collection of person objects read from the CSV file
     */
    private static PersonCollection getDataFromCSVFile(BufferedReader csvFileReader)
        throws IOException {

        PersonCollection persons = new PersonCollection();

        ICsvBeanReader beanReader = null;

        try {
            beanReader =
                new CsvBeanReader(csvFileReader,
                    CsvPreference.STANDARD_PREFERENCE);

            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            // check to see if file is empty
            if (header == null) {
                log.debug("Csv file is empty, unable to parse.");
                throw new IOException();
            }

            Person person;
            while ((person = beanReader.read(Person.class, header, processors)) != null) {
                persons.addPerson(person);
            }

            return persons;

        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
        }

    }

    /**
     * Helper method to assist Super CSV process the file.
     *
     * @return processor for each field in the CSV header and file
     */
    private static CellProcessor[] getProcessors() {

        final String emailRegex =
            "^[a-zA-Z0-9_!#$%&*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");
        final String DATE_FORMAT = "MM/dd/yyyy";

        final CellProcessor[] processors =
            new CellProcessor[] {new Optional(new ParseLong()), // Personnel ID
            new Optional(), // first name
            new Optional(), // last name
            new Optional(), // phone number
            new StrRegEx(emailRegex), // email
            new ParseDate(DATE_FORMAT, true, Locale.US), // start date
            new Optional(new ParseDate(DATE_FORMAT, true, Locale.US)), // end date
            new Optional(), // login
            new Optional(), // password
        };

        return processors;
    }

}
