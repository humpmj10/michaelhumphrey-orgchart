package com.accenture.javadojo.michael.humphrey_orgchart.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 * This class will represent an employee in the organization.
 *
 * @author michael.humphrey
 *
 */

@Entity(name = "person")
public class Person
implements Comparable<Person>, Serializable {

    private static final long serialVersionUID = 5595104048541970763L;

    private static final String EMPTY = "";

    // Constants for size validation
    private static final int NOT_EMPTY = 1;
    private static final int PHONE_NUMBER_MAX = 20;
    private static final int LAST_NAME_MAX = 20;
    private static final int FIRST_NAME_MAX = 20;
    private static final int EMAIL_MAX = 50;
    private static final int LOGIN_MAX = 30;
    private static final int PASSWORD_MAX = 20;

    final static int BEFORE = -1;
    final static int EQUAL = 0;
    final static int AFTER = 1;

    /**
     * Gets id(primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private Long personnelId;
    @Size(max = PHONE_NUMBER_MAX)
    private String phoneNumber;
    @Size(min = NOT_EMPTY, max = LAST_NAME_MAX)
    private String lastName;
    @Size(min = NOT_EMPTY, max = FIRST_NAME_MAX)
    private String firstName;
    @Size(max = EMAIL_MAX)
    private String email;
    @Size(max = LOGIN_MAX)
    private String login;
    @Size(max = PASSWORD_MAX)
    private String password;

    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;


    @Transient
    private Person manager;
    @Transient
    private Person careerCounselor;
    @Transient
    private Person hrRep;

    /**
     * Default constructor that init all private members. Used by convenience constructors
     *
     * @param personnelId the personnelId of the person
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param phoneNumber the phone number
     * @param email the email address
     * @param startDate the start date in the form MM/dd/yyyy
     * @param endDate the end date in the form MM/dd/yyyy
     * @param login the login
     * @param password the password
     * @param manager the manager of this person
     * @param careerCounselor the career counselor of this person
     * @param hrRep the hrRep of this person
     */
    public Person(Long id, Long personnelId, String lastName,
                  String firstName, String phoneNumber, String email,
                  Date startDate, Date endDate, String login, String password,
                  Person manager, Person careerCounselor, Person hrRep) {

        this.id = id;
        this.personnelId = personnelId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;

        if (startDate != null) {
            this.startDate = (Date) startDate.clone();
        } else {
            this.startDate = null;
        }
        if (endDate != null) {
            this.endDate = (Date) endDate.clone();
        } else {
            this.endDate = null;
        }

        this.login = login;
        this.password = password;
        this.manager = manager;
        this.careerCounselor = careerCounselor;
        this.hrRep = hrRep;
    }

    /**
     * Convenience constructor, creates a person with ID but all other fields empty
     *
     * @param personnelId the personnelId of the person
     */
    public Person(Long personnelId) {

        this(null, personnelId, EMPTY, EMPTY, EMPTY, EMPTY, null, null, EMPTY,
            EMPTY, null, null, null);
    }

    public Person(Long id, Long personnelId, String lastName,
                  String firstName) {

        this(id, personnelId, lastName, firstName, EMPTY, EMPTY, null, null,
            EMPTY, EMPTY, null, null, null);
    }

    public Person(Long personnelId, String lastName, String firstName) {

        this(null, personnelId, lastName, firstName, EMPTY, EMPTY, null, null,
            EMPTY,
            EMPTY, null, null, null);
    }

    /**
     * Convenience constructor. Useful for reading CSV file and creating a new person object.
     *
     * @param personnelId the personnelId of the person
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param phoneNumber the phone number
     * @param email the email address
     * @param startDate the start date in the form MM/dd/yyyy
     * @param endDate the end date in the form MM/dd/yyyy
     * @param login the login
     */
    public Person(Long personnelId, String lastName, String firstName,
                  String phoneNumber, String email, Date startDate,
                  Date endDate, String login) {

        this(null, personnelId, lastName, firstName, phoneNumber, email,
            startDate,
            endDate, login, EMPTY, null, null, null);

    }

    /**
     * Convenience constructor that creates an empty person object
     */
    public Person() {

        this(null, null, EMPTY, EMPTY, EMPTY, EMPTY, null, null, EMPTY, EMPTY,
            null, null, null);
    }

    /**
     * Returns a string that represents a employee in the personnel system in the follow format
     * {personnelID}: {last name}, {first name}
     *
     * @return string in the format {personnelID}: {last name}, {first name}
     */
    @Override
    public String toString() {

        StringBuilder str = new StringBuilder(); // using string builder to avoid case of ID being
        // null and throwing nullpointer
        str.append(this.getPersonnelId());
        str.append(": ");
        str.append(this.getLastName() + ", " + this.getFirstName());
        return str.toString();

    }

    /**
     * Compare this person object with another object to see if they are equal based on reference
     * and personnelID
     *
     * @return true if the two objects are the same object in memory of if both objects are person
     *         objects with the same ID member
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result =
            prime * result
            + ((personnelId == null) ? 0 : personnelId.hashCode());
        return result;
    }

    /**
     * Test whether this person object is equal to another obj, tests based on personnel ID
     *
     * @param the obj to compare to
     * @return true if this equals obj parameter
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        if (personnelId == null || other.personnelId == null) {
            return false;
        } else if (!personnelId.equals(other.personnelId)) {
            return false;
        }
        return true;
    }

    /**
     * Allows person object to be sorted based on natural ordering. Natural order is lexicographical
     * order by last name, first name and finally ID
     *
     * @param p the person which this object is compared to
     * @return int the value returned by comparing both person objects, will be less than zero if
     *         object 1 is before object2 and vice versa, returns 0 if the objects are equal in all
     *         fields
     *
     */
    @Override
    public int compareTo(Person p) {

        if (this == p) {
            return EQUAL;
        }

        int lastNameCompareValue =
            compareName(this.getLastName(), p.getLastName());
        int firstNameCompareValue =
            compareName(this.getFirstName(), p.getFirstName());
        int idCompareValue =
            comparePersonellID(this.getPersonnelId(), p.getPersonnelId());

        if (lastNameCompareValue != 0) {
            return lastNameCompareValue;
        } else if (firstNameCompareValue != 0) {
            return firstNameCompareValue;
        } else if (idCompareValue != 0) {
            return idCompareValue;
        } else {
            return EQUAL;
        }
    }

    /**
     * Helper method to assist in comparing name fields.
     *
     * @param name1 first String to compare
     * @param name2 second String to compare
     * @return int the value returned when comparing the two strings, uses lexicographical order
     */
    private int compareName(String name1, String name2) {

        int valueOfCompare = name1.compareToIgnoreCase(name2);
        return valueOfCompare;
    }

    /**
     * Helper method to assist in comparing ID fields.
     *
     * @param id1 first long value to compare
     * @param id2 second long value to compare
     * @return int the value return when comparing the two long parameters
     */
    private int comparePersonellID(Long id1, Long id2) {

        if (id1.compareTo(id2) < 0) {
            return BEFORE;
        } else if (id1.compareTo(id2) > 0) {
            return AFTER;
        } else {
            return EQUAL;
        }
    }

    // The rest of the methods in this class are getters and setters

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getPersonnelId() {

        return personnelId;
    }

    public void setPersonnelId(Long id) {

        this.personnelId = id;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public Date getStartDate() {

        if (this.startDate != null) {
            return (Date) this.startDate.clone();
        } else {
            return null;
        }
    }

    public void setStartDate(Date startDate) {

        if (startDate != null) {
            this.startDate = (Date) startDate.clone();
        } else {
            this.startDate = null;
        }
    }

    public Date getEndDate() {

        if (this.endDate != null) {
            return (Date) this.endDate.clone();
        } else {
            return null;
        }
    }

    public void setEndDate(Date endDate) {

        if (endDate != null) {
            this.endDate = (Date) endDate.clone();
        } else {
            this.endDate = null;
        }
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {

        this.login = login;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Person getManager() {

        return manager;
    }

    public void setManager(Person manager) {

        this.manager = manager;
    }

    public Person getCareerCounselor() {

        return careerCounselor;
    }

    public void setCareerCounselor(Person careerCounselor) {

        this.careerCounselor = careerCounselor;
    }

    public Person getHrRep() {

        return hrRep;
    }

    public void setHrRep(Person hrRep) {

        this.hrRep = hrRep;
    }

}
