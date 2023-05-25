package com.hcl.currencyexchange.bean;

import lombok.Data;

/**
 * <h1>Final Response Bean class</h1>
 * This class is used to display the response of the requests made by the user, in JSON format.
 * @version 1.0
 * @author Dumitrascu Mihai - Cosmin
 * @since 2023-05-18
 */
@Data
public class FinalResponseBean {
    private Object exchangeInfo;
    private String response;

    /**
     * This is the parametrized constructor.
     * @param response The String object for a custom message.
     * @param exchangeInfo The object retrieved.
     */
    public FinalResponseBean(String response, Object exchangeInfo) {
        this.response = response;
        this.exchangeInfo = exchangeInfo;
    }

//    /**
//     * This is the get method for the response variable.
//     * @return String The response variable.
//     */
//    public String getResponse() {
//        return response;
//    }
//
//    /**
//     * This is the set method for the response variable.
//     * @param response The value that will be saved into the response variable.
//     */
//    public void setResponse(String response) {
//        this.response = response;
//    }
//
//    /**
//     * This is the get method for the exchangeInfo variable.
//     * @return Object The exchangeInfo variable.
//     */
//    public Object getExchangeInfo() {
//        return exchangeInfo;
//    }
}
