package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import java.io.Serializable;
import java.util.Comparator;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 * This class is used to compare two person objects by start date.
 *
 * @author michael.humphrey
 *
 */
public class DateComparator
implements Comparator<Person>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Compare two person objects
     *
     * @param p1 the first person in the comparison
     * @param p1 the second person in the comparison
     * @return negative if p1 comes before p2 based on natural ordering, positive if p1 comes after
     *         p2 and zero if they are equal
     */
    @Override
    public int compare(Person p1, Person p2) {

        return p1.getStartDate().compareTo(p2.getStartDate());
    }

}
