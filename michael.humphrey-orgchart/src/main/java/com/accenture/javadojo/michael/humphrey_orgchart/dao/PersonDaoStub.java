package com.accenture.javadojo.michael.humphrey_orgchart.dao;

import java.util.ArrayList;
import java.util.List;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 * This is a stub class used to test PersonDAO without accessing database or storage
 *
 * @author michael.humphrey
 *
 */
public class PersonDaoStub
implements PersonDao<Person, Long> {

    private List<Person> persons = new ArrayList<Person>();
    {
        persons.add(new Person(1L, 1L, "Mike", "Humphrey"));
        persons.add(new Person(2L, 2L, "Bob", "Marley"));
        persons.add(new Person(3L, 3L, "Old", "Yeller"));
    };

    public PersonDaoStub() {

    }

    @Override
    public List<Person> findAll() {

        return persons;
    }

    @Override
    public Person findById(Long id) {

        for (Person p : persons) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean insertPerson(Person p) {

        if (persons.contains(p)) {
            return false;
        } else {
            persons.add(p);
            return true;
        }
    }

    @Override
    public boolean deletePerson(Long id) {

        for (Person p : persons) {
            if (p.getId().equals(id)) {
                persons.remove(p);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePerson(Person updatedPerson) {

        for (Person p : persons) {
            if (p.getId().equals(updatedPerson.getId())) {
                persons.remove(p);
                persons.add(updatedPerson);
                return true;
            }
        }
        return false;

    }

    // getters and setters

    public void setPersons(List<Person> persons) {

        this.persons = persons;
    }

    public List<Person> getPersons() {

        return this.persons;
    }

}
