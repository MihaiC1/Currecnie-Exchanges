package com.hcl.currencyexchange.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.currencyexchange.provider.ApiResponse;
import com.hcl.currencyexchange.entity.Currencies;
import com.hcl.currencyexchange.entity.Exchanges;
import com.hcl.currencyexchange.provider.HistoricalPoints;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ApiManager {
    public HistoricalPoints getFromYahooByDateToCurrFromCurr(String date, String toCurr, String fromCurr) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"method\": \"spotRateHistory\", \"data\": {\"base\": \"USD\", \"term\": \"EUR\", \"period\": \"year\"}}";
        requestBody = requestBody.replace("USD", fromCurr);
        requestBody = requestBody.replace("EUR", toCurr);
        HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);
        String url = "https://api.rates-history-service.prd.aws.ofx.com/rate-history/api/1";
        ApiResponse response = restTemplate.postForObject(url,entity, ApiResponse.class);
        if (response == null){
            return null;
        }
        HistoricalPoints[] historicalPoints = response.getData().getHistoricalPoints();
        for (HistoricalPoints historical:historicalPoints) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            Long timestamp =  historical.getPointInTime();
            LocalDate dateTimeFromTimeStamp = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDate.equals(dateTimeFromTimeStamp)){
                return historical;
            }
        }
        return  null;
    }
    public Exchanges modifyDataFromAPI(String date, Currencies curFrom, Currencies curTo) throws JsonProcessingException {

        HistoricalPoints hp = getFromYahooByDateToCurrFromCurr(date, curFrom.getCurIsoCode(), curTo.getCurIsoCode());
        if (hp == null) {
            return null;
        }

        int idForCurFrom = curFrom.getCurID();
        int idForCurTo = curTo.getCurID();
        Long timestamp = hp.getPointInTime();
        Timestamp ts = new Timestamp(timestamp);
        LocalDateTime dateTime = ts.toLocalDateTime();
        LocalDate dateTimeFromTimeStamp = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        float exchangeRate = hp.getInterbankRate();
        return new Exchanges(dateTimeFromTimeStamp,idForCurFrom, idForCurTo,exchangeRate,dateTime);
    }
}
