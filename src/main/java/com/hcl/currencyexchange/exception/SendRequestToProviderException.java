package com.hcl.currencyexchange.exception;

/**
 * <h1>Send Request To Provider Exception class</h1>
 * This class represents a custom exception for handling a wrong output received from the API.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
public class SendRequestToProviderException extends RuntimeException{
    /**
     * This is the empty constructor of the class.
     */
    public SendRequestToProviderException() {
    }

    /**
     * This is parametrized constructor of the class.
     * @param message The custom message displayed when the exception is thrown.
     */
    public SendRequestToProviderException(String message) {
        super(message);
    }
}
