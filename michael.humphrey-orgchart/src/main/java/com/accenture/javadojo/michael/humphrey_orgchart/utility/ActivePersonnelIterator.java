package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.collections.iterators.FilterIterator;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 * This iterator implementation acts the same as the iterator parameter, however it skips any person
 * objects with a end date before tday's date.
 *
 * @author michael.humphrey
 */
public class ActivePersonnelIterator
implements Iterator<Person> {

    private boolean nextCalled = false;
    private FilterIterator fit;

    public ActivePersonnelIterator(Iterator<Person> itr) {

        fit = new FilterIterator(itr, new EndDatePredicateImpl());
    }

    /**
     * Check to see if collection has additional elements
     *
     * @return true if the collection has additional elements
     */
    @Override
    public boolean hasNext() {

        return fit.hasNext();

    }

    /**
     * Returns the next element in the collection, skips any person objects that have an end date
     * before today.
     *
     * @return the next person in the collection
     */
    @Override
    public Person next() throws NoSuchElementException {

        if (fit.hasNext() == false) {
            // add a log message
            throw new NoSuchElementException();
        }

        this.nextCalled = true;
        return (Person) fit.next();
    }

    /**
     * Removes the item that the iterator is currently pointed to from the collection
     */
    @Override
    public void remove() throws IllegalStateException {

        if (this.nextCalled == false) {
            // add a log message
            throw new IllegalStateException("Called remove before calling next");
        }

        fit.remove();
        this.nextCalled = false;
    }

}
