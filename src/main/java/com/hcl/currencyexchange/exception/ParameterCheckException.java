package com.hcl.currencyexchange.exception;

/**
 * <h1>Parameter Check Exception class</h1>
 * This class represents a custom exception for handling a wrong input of a parameter.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
public class ParameterCheckException extends RuntimeException{
    /**
     * This is empty constructor of the class.
     */
    public ParameterCheckException() {
    }

    /**
     * This is parametrized constructor of the class.
     * @param message The custom message displayed when the exception is thrown.
     */
    public ParameterCheckException(String message) {
        super(message);
    }
}
