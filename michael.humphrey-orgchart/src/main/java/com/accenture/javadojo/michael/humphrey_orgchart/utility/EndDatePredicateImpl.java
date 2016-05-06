package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import org.apache.commons.collections.Predicate;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

public class EndDatePredicateImpl
implements Predicate {

    /**
     *
     * @param obj the object to evaluate
     * @return true if the person end date is not before today
     */
    @Override
    public boolean evaluate(Object obj) {

        if (obj instanceof Person) {
            if (endDateBeforeToday((Person) obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to see if end date is after today's current date
     *
     * @param p the person object being checked
     * @return true if the person's end date is after today
     */
    private boolean endDateBeforeToday(Person p) {

        if (p.getEndDate() == null) {
            return false;
        }

        DateTime personnelEndDate = new DateTime(p.getEndDate());
        DateTime today;
        LocalTime todayLocal = new LocalTime(0, 0, 0);
        today = todayLocal.toDateTimeToday();
        today.plusDays(1);

        return personnelEndDate.isBefore(today);

    }
}
