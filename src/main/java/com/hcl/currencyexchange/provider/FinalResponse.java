package com.hcl.currencyexchange.provider;

public class FinalResponse {
    private Object exchangeInfo;
    private String response;

    public FinalResponse(String message) {
        this.response = message;
    }
    public FinalResponse(String message, Object exchangeInfo) {
        this.response = message;
        this.exchangeInfo = exchangeInfo;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getExchangeInfo() {
        return exchangeInfo;
    }
}
