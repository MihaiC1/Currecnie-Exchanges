package com.hcl.currencyexchange.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.currencyexchange.provider.Data;
import com.hcl.currencyexchange.provider.HistoricalPoints;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class ApiResponse {
    private Data data;

    public ApiResponse(Data data) {
        this.data = data;
    }
    public ApiResponse() {

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
