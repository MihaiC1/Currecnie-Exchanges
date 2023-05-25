package com.hcl.currencyexchange.manager;


import com.hcl.currencyexchange.bean.ApiResponseBean;
import com.hcl.currencyexchange.entity.CurrenciesEntity;
import com.hcl.currencyexchange.entity.ExchangesEntity;
import com.hcl.currencyexchange.bean.HistoricalPointsBean;
import com.hcl.currencyexchange.exception.SendRequestToProviderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * <h1>Api Manager class</h1>
 * This class is used to store all the methods used to manipulate the informations came from the API.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */

public class ApiManager {
    private static final Logger LOG = LogManager.getLogger(DatabaseManager.class);
    /**
     * Method used to send a POST request to the API for extracting an exchange from a specific date.
     * @param date The date from which the transaction will be retrieved. Date format: YYYY-MM-DD.
     * @param toCurr The currency ISO to convert into (ex. USD, EUR, etc).
     * @param fromCurr The currency ISO to be converted (ex. USD, EUR, etc).
     * @return HistoricalPointsBean An object of HistoricalPointsBean class.
     * @throws SendRequestToProviderException if the API does not send the proper response.
     */

    public HistoricalPointsBean sendRequestToProvider(String date, String toCurr, String fromCurr) throws SendRequestToProviderException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"method\": \"spotRateHistory\", \"data\": {\"base\": \"USD\", \"term\": \"EUR\", \"period\": \"year\"}}";
        requestBody = requestBody.replace("USD", fromCurr);
        requestBody = requestBody.replace("EUR", toCurr);
        HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);
        String url = "https://api.rates-history-service.prd.aws.ofx.com/rate-history/api/1";
        ApiResponseBean response = restTemplate.postForObject(url,entity, ApiResponseBean.class);

        if (response == null || response.getDataBean() == null){

            LOG.error("Something went wrong when sending a request to the API");
            throw new SendRequestToProviderException("Something went wrong when sending a request to the API");
        }

        HistoricalPointsBean[] historicalPointBeans = response.getDataBean().getHistoricalPointBeans();
        HistoricalPointsBean result = null;
        for (HistoricalPointsBean historical: historicalPointBeans) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            Long timestamp =  historical.getPointInTime();

            if (timestamp == null){
                LOG.error("This date could not be found in the APIs database!");
                throw new SendRequestToProviderException("This date could not be found in the APIs database!");
            }


            LocalDate dateTimeFromTimeStamp = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDate.equals(dateTimeFromTimeStamp)){
                result = historical;
            }
        }
        return result;
    }

    /**
     * This method is used to cast the object returned by the api into an ExchangesEntity object.
     * @param date The date saved into the currency_conversion table. Date format: YYYY-MM-DD.
     * @param curFrom The object is used to get the specific ID for the currency that will be exchanged.
     * @param curTo The object is used to get the specific ID for the currency that the user will exchange into.
     * @return ExchangesEntity An object of ExchangesEntity class.
     * @throws SendRequestToProviderException if the sendRequestToProvider method returns null.
     */
    public ExchangesEntity extractExchangeFromProvider(String date, CurrenciesEntity curFrom, CurrenciesEntity curTo) throws SendRequestToProviderException {

        HistoricalPointsBean hp = sendRequestToProvider(date, curFrom.getCurIsoCode(), curTo.getCurIsoCode());
        if (hp == null) {
            return null;
        }

        int idForCurFrom = curFrom.getCurID();
        int idForCurTo = curTo.getCurID();
        Long timestamp = hp.getPointInTime();
        if (timestamp == null){
            throw new SendRequestToProviderException("This date could not be found in the API's database!");
        }
        Timestamp ts = new Timestamp(timestamp);
        LocalDateTime dateTime = ts.toLocalDateTime();
        LocalDate dateTimeFromTimeStamp = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        float exchangeRate = hp.getInterbankRate();
        return new ExchangesEntity(dateTimeFromTimeStamp,idForCurFrom, idForCurTo,exchangeRate,dateTime);
    }
}
