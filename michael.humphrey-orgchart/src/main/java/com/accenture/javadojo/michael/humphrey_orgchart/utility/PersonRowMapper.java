package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;

/**
 * This classes assists JDBC template in mapping rows from table into a person object
 *
 * @author michael.humphrey
 *
 */
public class PersonRowMapper
implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

        Person p = new Person();
        p.setId(rs.getLong("id"));
        p.setPersonnelId(rs.getLong("personnelId"));
        p.setLastName(rs.getString("lastName"));
        p.setFirstName(rs.getString("firstName"));
        p.setPhoneNumber(rs.getString("phoneNumber"));
        p.setEmail(rs.getString("email"));

        // checks to avoid null pointer exceptions
        if (p.getStartDate() != null) {
            p.setStartDate(new Date(rs.getDate("startDate").getTime()));
        }
        if (p.getEndDate() != null) {
            p.setEndDate(new Date(rs.getDate("endDate").getTime()));
        }

        p.setLogin(rs.getString("login"));
        p.setPassword(rs.getString("password"));

        return p;
    }

}
