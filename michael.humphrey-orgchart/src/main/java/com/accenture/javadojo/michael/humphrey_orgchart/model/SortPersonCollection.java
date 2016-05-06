package com.accenture.javadojo.michael.humphrey_orgchart.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.accenture.javadojo.michael.humphrey_orgchart.utility.DateComparator;

/**
 *
 * @author michael.humphrey
 *
 */
public final class SortPersonCollection {

    /**
     * Sort the list of person objects by natural ordering
     *
     * @param persons the collection of persons to sort
     * @return sorted list of person objects sorted by name
     */
    public static List<Person> sortCollectionByName(PersonCollection persons) {

        List<Person> sortedList = new ArrayList<Person>(persons.getPersons());
        Collections.sort(sortedList);
        return sortedList;

    }

    /**
     * Sort the list of person objects by start date
     *
     * @param persons the collection of persons to sort
     * @return sorted list of person objects sorted by start date in ascending order
     */
    public static List<Person> sortCollectionByDateAscending(PersonCollection persons) {

        List<Person> sortedList = new ArrayList<Person>(persons.getPersons());
        Collections.sort(sortedList, new DateComparator());
        return sortedList;

    }

    /**
     * Sort the list of person objects by end date
     *
     * @param persons the collection of persons to sort
     * @return sorted list of person objects sorted by start date in Descending order
     */
    public static List<Person> sortCollectionByDateDescending(PersonCollection persons) {

        List<Person> sortedList = new ArrayList<Person>(persons.getPersons());
        Collections.sort(sortedList,
            Collections.reverseOrder(new DateComparator()));
        return sortedList;

    }
}
