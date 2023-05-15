package com.hcl.currencyexchange.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcl.currencyexchange.provider.FinalResponse;
import com.hcl.currencyexchange.entity.Currencies;
import com.hcl.currencyexchange.entity.Exchanges;
import com.hcl.currencyexchange.entity.JoinTable;
import com.hcl.currencyexchange.repository.CurrenciesRepository;
import com.hcl.currencyexchange.repository.ExchangeRepository;
//import com.hcl.currencyexchange.restController.Controller;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableJpaRepositories(basePackages = "com.hcl.currencyexchange.repository")
public class DatabaseManager extends ApiManager {

    private static final Logger LOG = LogManager.getLogger(DatabaseManager.class);

    public DatabaseManager() {
    }

    public Map<String, Object> insertExchangesFromAPI(String date, Currencies curFrom, Currencies curTo,ExchangeRepository exchangeRepositoryObj){
        Map<String, Object> response = new HashMap<>();
        try{

            Exchanges exchangeToBeInserted = modifyDataFromAPI(date, curFrom, curTo);
            if (exchangeToBeInserted == null){
                response.put("Response: ", "The API does not have informations you are looking for!");
                return response;
            }
            JoinTable result = new JoinTable(exchangeToBeInserted.getDate(),exchangeToBeInserted.getRate(),curTo.getCurIsoCode(),curFrom.getCurIsoCode(),exchangeToBeInserted.getInsertTime());
            exchangeRepositoryObj.insertExchanges(exchangeToBeInserted.getDate(),exchangeToBeInserted.getCurIdFrom(),exchangeToBeInserted.getCurIdTo(),exchangeToBeInserted.getRate(),exchangeToBeInserted.getInsertTime());
            response.put("Response: ", result);
            return response;
        }
        catch (JsonProcessingException e){
            response.put("Response: ", e);
            return response;
        }
    }
    public ResponseEntity<Object> updateById(Float rate, String date, String curFrom, String curTo, CurrenciesRepository currenciesRepositoryObj, ExchangeRepository exchangeRepositoryObj) {

        try {
            LOG.info("\nInput:\n Date: " + date + "\nCurrencyFrom: " + curFrom + "\nCurrencyTo: " + curTo + "\nExchange rate: " + rate);
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);
            if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true))) {
                LOG.error("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data updated!");
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data updated!"), HttpStatus.BAD_REQUEST);
            }
            if (curFromObj == null) {
                LOG.error(curFrom + " is not available for exchanging! No data updated!");
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging! No data updated!"), HttpStatus.BAD_REQUEST);
            }
            if (curToObj == null) {
                LOG.error(curTo + " is not available for exchanging! No data updated!");
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging! No data updated!"), HttpStatus.BAD_REQUEST);
            }
            if (rate <= 0) {
                LOG.error("The rate cannot be less then, or equal to 0! No data updated!");
                return new ResponseEntity<>(new FinalResponse("The rate cannot be less then, or equal to 0! No data updated!"), HttpStatus.BAD_REQUEST);
            }


            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            exchangeRepositoryObj.updateRecord(rate, localDate, curFrom, curTo);
            Map<String, Object> response = new HashMap<>();
            response.put("response", "OK");
            LOG.info("Response: OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return new ResponseEntity<>(new FinalResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Object> addExchanges(String date, String curFrom, String curTo, Float rate, CurrenciesRepository currenciesRepositoryObj, ExchangeRepository exchangeRepositoryObj) {
        try {
            LOG.info("\nInput:\n Date: " + date + "\nCurrencyFrom: " + curFrom + "\nCurrencyTo: " + curTo + "\nExchange rate: " + rate);

            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            if (curFromObj == null) {
                LOG.error(curFrom + " is not available for exchanging! No data added!");
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging! No data added!"), HttpStatus.BAD_REQUEST);
            }
            int idForCurFrom = curFromObj.getCurID();

            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);
            if (curToObj == null) {
                LOG.error(curTo + " is not available for exchanging! No data added!");
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging! No data added!"), HttpStatus.BAD_REQUEST);
            }
            int idForCurTo = curToObj.getCurID();

            if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true))) {
                LOG.error("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data added!");
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data added!"), HttpStatus.BAD_REQUEST);
            }


            if (rate <= 0) {
                LOG.error("The rate cannot be less then, or equal to 0! No data added!");
                return new ResponseEntity<>(new FinalResponse("The rate cannot be less then, or equal to 0! No data added!"), HttpStatus.BAD_REQUEST);
            }

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            exchangeRepositoryObj.insertExchanges(localDate, idForCurFrom, idForCurTo, rate, LocalDateTime.now());
            Map<String, Object> response = new HashMap<>();
            response.put("response", "OK");
            LOG.info("Response: OK");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            LOG.error("Error stack trace: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr (String date, String curFrom, String curTo, CurrenciesRepository currenciesRepositoryObj, ExchangeRepository exchangeRepositoryObj) {

        try {
            Map<String, Object> response = new HashMap<>();
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);

            LOG.info("\nInput: \n" + "date: " + date + "\n" + "fromCurrency: " + curFrom + "\n" + "toCurrency: " + curTo);
            if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true))) {
                LOG.error("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data displayed!");
                response.put("Response: ", "The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data displayed!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (curFromObj == null) {
                LOG.error(curFrom + " is not available for exchanging! No data displayed!");
                response.put("Response: ",curFrom + " is not available for exchanging! No data displayed!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (curToObj == null) {
                LOG.error(curTo + " is not available for exchanging! No data displayed!");
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging! No data displayed!"), HttpStatus.BAD_REQUEST);
            }

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            List<JoinTable> result = exchangeRepositoryObj.getFromDateAndCurr(localDate, curFrom, curTo, PageRequest.of(0, 1));


            if (result.isEmpty()) {
                return new ResponseEntity<>(new FinalResponse("OK", insertExchangesFromAPI(date, curFromObj, curToObj,exchangeRepositoryObj).get("Response: ")), HttpStatus.OK);
            }

            LOG.info("Response: OK");
            return new ResponseEntity<>(new FinalResponse("OK", result), HttpStatus.OK);

        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    public ResponseEntity<Object> getExchangeFromDateWithCurr(String date, String curFrom, CurrenciesRepository currenciesRepositoryObj, ExchangeRepository exchangeRepositoryObj) {

        try {
            List<JoinTable> result;
            Map<String, Object> response = new HashMap<>();
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            LOG.info("\nInput: \n" + "date: " + date + "\n" + "fromCurrency: " + curFrom + "\n");
            if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true))) {
                LOG.error("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data displayed!");
                response.put("Response", "The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data displayed!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (curFromObj == null) {
                LOG.error(curFrom + " is not available for exchanging! No data displayed!");
                response.put("Response", curFrom + " is not available for exchanging! No data displayed!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            result = exchangeRepositoryObj.getFromDateAndCurr(localDate, curFrom);
            if (result.size() == 0) {
                LOG.info("Response: OK" + "\n Nothing was displayed!");
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                LOG.info("Response: OK");
                return new ResponseEntity<>(new FinalResponse("OK", result), HttpStatus.OK);
            }


        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    public ResponseEntity<Object> getExchangeFromDate(String date, ExchangeRepository exchangeRepositoryObj) {

        try {


            LOG.info("\nInput: \ndate: " + date);
            Map<String, Object> response = new HashMap<>();
            List<JoinTable> exchangesFromDate;
            if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true))) {
                LOG.error("Invalid date format! The accepted format is: YYYY-MM-DD");
                response.put("Response", "Invalid date format! The accepted format is: YYYY-MM-DD");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            exchangesFromDate = exchangeRepositoryObj.findByDate(localDate);


            if (exchangesFromDate.isEmpty()) {
                LOG.info("Response: OK. No exchanges found on " + date);
                response.put("Response", "No exchanges found on " + date);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            LOG.info("Response: OK");
            return new ResponseEntity<Object>(exchangesFromDate, HttpStatus.OK);


        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    public ResponseEntity<Object> getExchanges(ExchangeRepository exchangeRepositoryObj) {
        try {
            List<JoinTable> result = exchangeRepositoryObj.getAll();
            LOG.info("DATA RETRIEVED");
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
