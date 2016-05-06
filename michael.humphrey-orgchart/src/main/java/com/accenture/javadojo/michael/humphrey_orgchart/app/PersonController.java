package com.accenture.javadojo.michael.humphrey_orgchart.app;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.javadojo.michael.humphrey_orgchart.config.DatabaseConfig;
import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.services.PersonService;
import com.accenture.javadojo.michael.humphrey_orgchart.services.PersonServiceImpl;

/**
 * This class handles http request and calls the appropriate service to handle these requests.
 *
 * @author michael.humphrey
 *
 */

@RestController
@ComponentScan(basePackages = "com.accenture.javadojo.michael.humphrey_orgchart")
public class PersonController {

    private static final Logger log = LoggerFactory
        .getLogger(PersonController.class);

    @Inject
    @Qualifier(value = "personServiceImpl")
    private PersonService personServiceImpl;

    @SuppressWarnings("unused")
    @Inject
    private DatabaseConfig dbc;

    /**
     * Maps /getPerson with id so that the record can be retrieved from storage
     *
     * @param id the id of the target record
     * @return a person object representing the record in storage
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("id") String id) {

        log.info("Call to getPerson(" + id + ")");
        return personServiceImpl.findById(id);

    }

    /**
     * Maps /deletePerson so that a request can be made to delete target from storage
     *
     * @param id the id of the target record to be deleted
     * @return true if the deletion was successful
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.DELETE)
    public void deletePerson(@PathVariable("id") String id) {

        log.info("Call to deletePerson()");
        personServiceImpl.deletePerson(id);

    }

    /**
     * Maps /getAllpersons which retrieves all records from storage
     *
     * @return a list of all person objects in storage
     */
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public List<Person> getAllPersons() {

        log.info("Call to getAllPersons()!");
        return personServiceImpl.findAll();
    }

    /**
     * Update a record currently in storage
     *
     */
    @RequestMapping(value = "/persons/{id}", method = RequestMethod.POST)
    public ResponseEntity<Person> updatePerson(@PathVariable String id,
        @RequestBody Person person) {

        log.info("The object params being update are " + person
            + " with a date of " + person.getStartDate());
        if (personServiceImpl.updatePerson(person)) {
            log.info("Call to updatePerson(), successful!");
            return new ResponseEntity<Person>(HttpStatus.OK);
        }
        log.info("Call to updatePerson(), failure!");
        return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Maps /addPerson so that a record can be added to storage
     *
     */
    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public ResponseEntity<Person> insertPerson(@RequestBody Person person) {

        log.info("Call to insertPerson() ");
        if (personServiceImpl.insertPerson(person)) {
            log.info("Call to insertPerson(), successful!");
            return new ResponseEntity<Person>(HttpStatus.OK);
        } else {
            log.info("Call to insertPerson(), failure!");
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }
    }

    // getters/setters

    public PersonService getPersonServiceImpl() {

        return personServiceImpl;
    }

    public void setPersonServiceImpl(PersonServiceImpl personServiceImpl) {

        this.personServiceImpl = personServiceImpl;
    }
}
