package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 *
 * @author Main
 *
 */
public class CSVReader {

	// Class should not be extended and made final for security reasons
	private static final Logger log = LoggerFactory.getLogger(CSVReader.class);

	private final static String DATE_FORMAT = "MM/dd/yyyy";

	/**
	 * This private helper method initializes the cell processor based on the
	 * order in the CSV file
	 *
	 * @return
	 */
	private static CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] {
				new NotNull(new ParseLong()), // personnelId
				new NotNull(), // lastName
				new NotNull(), // firstName
				new Optional(), // phoneNumber
				new Optional(), // email
				new Optional(new ParseDate(DATE_FORMAT)), // start date
				new Optional(new ParseDate(DATE_FORMAT)), // end date
				new Optional(), // login
				new Optional(), // password
		};

		return processors;

	}

	/**
	 * This method will read the CSV file using cellprocessors and return a Set
	 * of person objects
	 *
	 * @param fileName
	 *            the FileName to parse
	 * @return the resulting set of Person objects obtained from reading the csv
	 *         file
	 * @throws Exception
	 */
	public static Set<Person> readWithCsvBeanReader(String fileName) throws IOException {

		HashSet<Person> persons = new HashSet<Person>();
		ICsvBeanReader beanReader = null;
		try {
			beanReader = new CsvBeanReader(FileUtility.findFileFromPath(fileName), CsvPreference.STANDARD_PREFERENCE);
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			if (header == null) {
				log.debug("CSV file is empty, unable to parse.");
				throw new IOException();
			}

			Person p;
			while ((p = beanReader.read(Person.class, header, processors)) != null) {
				persons.add(p);
			}

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}

		return persons;
	}
}
