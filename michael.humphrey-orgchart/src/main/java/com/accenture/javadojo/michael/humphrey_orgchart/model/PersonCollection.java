package com.accenture.javadojo.michael.humphrey_orgchart.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This is a collection class built to manage a collection of person objects. It uses a HashMap to
 * store the ID as a key and the Person object as the value.
 *
 * @author michael.humphrey
 *
 */
public class PersonCollection {

    private Set<Person> persons;

    /**
     * Constructs and empty PersonCollection.
     */
    public PersonCollection() {

        persons = new LinkedHashSet<Person>();
    }

    /**
     * Adds a person to the collection.
     *
     * @param p the person object to be added to the collection
     * @return true if the person object was successfully added
     */
    public boolean addPerson(Person p) {

        if (p == null) {
            return false;
        } else {
            return this.persons.add(p);
        }

    }

    /**
     * Removes a person from the collection
     *
     * @param p the person to be added to the collection
     * @return true if the person object was successfully removed
     */
    public boolean removePerson(Person p) {

        if (p == null) {
            return false;
        }
        return persons.remove(p);

    }

    /**
     * Check with ID to see if a person is in the collection
     *
     * @return true if the person object was found in the collection
     */
    public boolean isPersonInCollection(Long id) {

        Person p = getPersonWithID(id);

        if (p == null) {
            return false;
        }
        return true;

    }

    /**
     * Get a person object from the collection with their ID
     *
     * @param id ID of the person being searched for
     * @return the person object if found, if person not found returns null
     */
    public Person getPersonWithID(Long id) {

        for (Person currentPerson : persons) {
            if (currentPerson.getPersonnelId().equals(id)) {
                return currentPerson;
            }
        }

        return null;
    }

    /**
     * Utility method to find Person object in collection with first and last name
     *
     * @param firstName
     * @param lastName
     * @return the Person object with given first and last name, null if Person not in collection
     */
    public Person getPersonWithFullName(String firstName, String lastName) {

        for (Person currentPerson : persons) {
            if (currentPerson.getFirstName().equalsIgnoreCase(firstName)
                && currentPerson.getLastName().equalsIgnoreCase(lastName)) {
                return currentPerson;
            }

        }
        return null;
    }

    /**
     * Build a string representation of the persons collection {PersonnelId}:
     * {LastName},{FirstName};
     *
     * @return the toString
     */
    @Override
    public String toString() {

        StringBuilder personInfo = new StringBuilder();

        List<Person> personList = new ArrayList<Person>(this.persons);

        int listSize = personList.size();
        for (int i = 0; i < listSize; i++) {
            personInfo.append(personList.get(i));
            if (i != (listSize - 1)) {
                personInfo.append(";");
            }

        }

        return personInfo.toString();
    }

    // Getters/Setters

    public Set<Person> getPersons() {

        return persons;
    }

}
