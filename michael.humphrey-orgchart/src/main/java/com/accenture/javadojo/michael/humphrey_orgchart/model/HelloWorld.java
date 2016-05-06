package com.accenture.javadojo.michael.humphrey_orgchart.model;

/**
 * This class was created for practice in Java Dojo. It generates a string that includes
 * "hello {name}", where name is supplied in the method.
 *
 * @author michael.humphrey
 *
 */

public class HelloWorld {

    /**
     *
     * @param name The name that will be returned with the string
     * @return string that includes Hello + parameter name
     */
    public String greet(String name) {

        if (name == null) {
            throw new IllegalArgumentException();
        }
        return "Hello " + name;
    }
}
