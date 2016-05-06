package com.accenture.javadojo.michael.humphrey_orgchart.utility;

/**
 * Class that contains common sql queries
 *
 * @author michael.humphrey
 *
 */
public final class PersonQueries {

    public static final String GET_ALL = "SELECT * FROM person";
    public static final String GET_BY_ID = "SELECT * FROM person "
        + "WHERE id = ?";
    public static final String INSERT =
        "INSERT into person "
            + "(id, personnelId, lastName, firstName, phoneNumber, email, startDate, endDate, login, password) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE =
 "DELETE FROM person WHERE id = ?";
    public static final String UPDATE =
        "UPDATE person SET "
            + "id = ?, personnelId = ?, lastName = ?, firstName = ?, phoneNumber = ?, email = ?, startDate = ?, endDate = ?, login = ?, password = ? "
            + "WHERE id = ?";
    public static final String GET_ALL_JPA = "SELECT c FROM person c";
    public static final String GET_BY_ID_JPA =
        "SELECT c FROM person c where c.id = :id";

}
