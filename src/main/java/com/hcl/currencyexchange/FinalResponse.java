package com.hcl.currencyexchange;

public class FinalResponse {
    private Object exchange;
    private String response;

    public FinalResponse(String message) {
        this.response = message;
    }
    public FinalResponse(String message, Object exchange) {
        this.response = message;
        this.exchange = exchange;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getExchange() {
        return exchange;
    }
}
