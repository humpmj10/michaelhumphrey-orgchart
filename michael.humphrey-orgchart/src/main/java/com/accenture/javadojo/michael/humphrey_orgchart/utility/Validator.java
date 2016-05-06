package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 * Utility class that validates input.
 *
 * @author michael.humphrey
 *
 */
public final class Validator {

    /**
     * This list contains the valid formats for date that will be used for validation
     */
    private static List<String> dateFormats = new ArrayList<String>() {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            add("M/dd/yyyy");
            add("dd.M.yyyy");
            add("M/dd/yyyy hh:mm:ss a");
            add("dd.M.yyyy hh:mm:ss a");
            add("dd.MMM.yyyy");
            add("dd-MMM-yyyy");
            add("dd-MM-yyyy");
            add("yyyy-MM-dd");
        }
    };

    private Validator() {

        // prevent class from being instantiated
    }

    /**
     * Validate a phone number, ignoring punctuation and in US format
     *
     * @param phoneNumber the phone number to check
     * @return true if the phone number provided is valid
     */
    public static boolean phoneNumIsValid(String phoneNumber) {

        boolean isValid = false;

        String expression = "^\\(?(\\d{3})\\)?[-. ]?(\\d{3})[-. ]?(\\d{4})$";
        isValid = patternIsValid(phoneNumber, expression);
        return isValid;
    }

    /**
     * Validate an email address
     *
     * @param email the email to validate
     * @return true if the email is valid
     */
    public static boolean emailIsValid(String email) {

        boolean isValid = false;

        String expression =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        isValid = patternIsValid(email, expression);

        return isValid;
    }

    /**
     * Validate a date in the following formats: M/dd/yyyy, dd.M.yyyy, M/dd/yyyy hh:mm:ss a,
     * dd.M.yyyy hh:mm:ss a, dd.MMM.yyyy, dd-MMM-yyyy, dd-MM-yyyy, yyyy-MM-dd formats
     *
     * @param date the date to validate
     * @return true if the date matches format
     */
    public static boolean dateIsValid(String date) {

        boolean isValid = false;

        if (testDateFormat(date) != null) {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Validate a person object, ID cannot be null, First and Last names cannot be null. Validate
     * phone number and email, if not null. Validate that start date is not after today, and end
     * date is after today if not null. The final check is that end date is after start date.
     *
     * @param p the person to validate
     * @return true if the person is valid according to the criteria
     */
    public static boolean personIsValid(Object person) {

        boolean isValid = true;

        // check to see if p is null
        if (person == null || !(person instanceof Person)) {
            isValid = false;
        } else {
            Person p = (Person) person;
            // check to make sure ID is not null
            if (p.getPersonnelId() != null) {

            } else {
                isValid = false;
            }
            // check to make sure neither first name nor last name is null
            if (p.getFirstName().isEmpty() || p.getLastName().isEmpty()) {
                isValid = false;
            }
            // check to make sure that if phone number is not null it is valid
            if (!p.getPhoneNumber().isEmpty()
                && phoneNumIsValid(p.getPhoneNumber()) == false) {
                isValid = false;
            }
            // check to make sure that if email is not null it is valid
            if (!p.getEmail().isEmpty() && emailIsValid(p.getEmail()) == false) {
                isValid = false;
            }

            // check to make sure that if start date is not null it's not after today and is before
            // end date
            if (p.getStartDate() != null) {
                if (p.getStartDate().after(new Date())) {
                    isValid = false;
                }
                if (p.getEndDate() != null) {
                    if (p.getStartDate().before(p.getEndDate())) {
                        isValid = false;
                    }
                }
            }
            // check to make sure that if end date is not null is after today
            if (p.getEndDate() != null) {
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, 1);
                if (p.getEndDate().before(dt)) {
                    isValid = false;
                }
            }

        }
        return isValid;
    }

    /**
     * Method to confirm that personnel ID is valid
     *
     * @param personnelId the ID to check
     * @return true if the ID is only digits with a length between 1 and 20
     */
    public static boolean personnelIsValid(String personnelId) {

        boolean isValid = true;
        String regex = "[0-9]+";

        if (1 > personnelId.length() || personnelId.length() > 20) {
            isValid = false;
        }
        if (!personnelId.matches(regex)) {
            isValid = false;
        }

        return isValid;

    }

    /**
     * Helper method that takes regex expression and determines if string is valid
     *
     * @param propertyToValidate one of the properties to valid
     * @param expression the regex expression
     * @return true if regex expression matches the propertyToValidate
     */
    private static boolean patternIsValid(String propertyToValidate,
                                          String expression) {

        boolean isValid = false;

        CharSequence inputStr = propertyToValidate;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Takes a string and determines if it matches date pattern
     *
     * @param date the date to compare to valid formats
     * @return the date object created from dateInput
     */
    public static Date testDateFormat(String date) {

        Date validDate = null;
        for (String stringDate : dateFormats) {
            try {
                DateFormat df = new SimpleDateFormat(stringDate);
                df.setLenient(false);
                return df.parse(date);
            } catch (ParseException e) {

            }
        }
        return validDate;
    }
}
