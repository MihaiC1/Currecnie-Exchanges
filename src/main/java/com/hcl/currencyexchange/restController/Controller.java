package com.hcl.currencyexchange.restController;

import com.hcl.currencyexchange.FinalResponse;
import com.hcl.currencyexchange.entity.Currencies;
import com.hcl.currencyexchange.entity.Exchanges;
import com.hcl.currencyexchange.entity.JoinTable;
import com.hcl.currencyexchange.repository.CurrenciesRepository;
import com.hcl.currencyexchange.repository.ExchangeRepository;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@EnableJpaRepositories(basePackages = "com.hcl.currencyexchange.repository")
@RequestMapping(value = "/")
public class Controller {
    @Autowired
    ExchangeRepository exchangeRepositoryObj;
    @Autowired
    CurrenciesRepository currenciesRepositoryObj;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    @RequestMapping(value = "/exchange/v1/greeting", method = RequestMethod.GET)
    public ResponseEntity<Object> greeting() {
        try {

            return new ResponseEntity<>("Welcome to the Currency exchange!!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Save exchanges to the database - returns the result of the saving process
    @RequestMapping(value = "/exchange/v1/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addExchanges(@RequestParam("Date") String date,
                                               @RequestParam("CurFrom") String curFrom,
                                               @RequestParam("CurTo") String curTo,
                                               @RequestParam("Rate") Float rate) {
        try {
            LOG.info("\nInput:\n Date: " + date + "\nCurrencyFrom: " + curFrom + "\nCurrencyTo: " + curTo + "\nExchange rate: " + rate);
            int idForCurFrom = 0;
            int idForCurTo = 0;
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            idForCurFrom = currenciesRepositoryObj.getIDByISO(curFrom);
            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);
            idForCurTo = currenciesRepositoryObj.getIDByISO(curTo);

            if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true))) {
                LOG.error("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data added!");
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data added!"), HttpStatus.BAD_REQUEST);
            }
            if (curFromObj == null) {
                LOG.error(curFrom + " is not available for exchanging! No data added!");
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging! No data added!"), HttpStatus.BAD_REQUEST);
            }
            if (curToObj == null) {
                LOG.error(curTo + " is not available for exchanging! No data added!");
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging! No data added!"), HttpStatus.BAD_REQUEST);
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
    // Update a specific transaction - returns the result of the saving (Succesfully saved / failed to save)
    //id = identifier to help finding the record

    @RequestMapping(value = "/exchange/v1/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateById(@RequestParam("Rate") Float rate,
                                             @RequestParam("Date") String date,
                                             @RequestParam("CurFrom") String curFrom,
                                             @RequestParam("CurTo") String curTo) {

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

    //Get all the records available - returns a list with all the elements
    @RequestMapping(value = "/exchange/v1/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchanges() {
        try {
            List<JoinTable> result = exchangeRepositoryObj.getAll();
            LOG.info("DATA RETRIEVED");
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the records from the specified date - returns a list with all the elements
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDate(@PathVariable("date") String date) {

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
            if (exchangesFromDate.size() == 0) {
                LOG.info("Response: OK. No exchanges found on " + date);
                response.put("Response", "No exchanges found on" );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            LOG.info("Response: OK");
            return new ResponseEntity<Object>(exchangesFromDate, HttpStatus.OK);


        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //Get all the exchanges from the specified date with the specified initial currency - return a list with specific elements
    // fromCurr = currency to be exchanged
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateWithCurr(@PathVariable("date") String date,
                                                              @PathVariable("fromCurr") String curFrom) {

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
            if (result.size() == 0){
                LOG.info("Response: OK" + "\n Nothing was displayed!");
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            }
            else {
                LOG.info("Response: OK");
                return new ResponseEntity<>(new FinalResponse("OK", result),HttpStatus.OK);
            }


        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Get all the exchanges from the specified date with the specified initial currency and end currency
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}/{toCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr(@PathVariable("date") String date,
                                                                    @PathVariable("fromCurr") String curFrom,
                                                                    @PathVariable("toCurr") String curTo) {

        try {
            List<JoinTable> result;
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);
            LOG.info("\nInput: \n" + "date: " + date + "\n" + "fromCurrency: " + curFrom + "\n" + "toCurrency: " + curTo);
            if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true))) {
                LOG.error("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data displayed!");
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data displayed!"), HttpStatus.BAD_REQUEST);
            }
            if (curFromObj == null) {
                LOG.error(curFrom + " is not available for exchanging! No data displayed!");
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging! No data displayed!"), HttpStatus.BAD_REQUEST);
            }
            if (curToObj == null) {
                LOG.error(curTo + " is not available for exchanging! No data displayed!");
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging! No data displayed!"), HttpStatus.BAD_REQUEST);
            }


            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            result = exchangeRepositoryObj.getFromDateAndCurr(localDate, curFrom, curTo, PageRequest.of(0,1));

            Map<String, Object> response = new HashMap<>();
            response.put("Response:", "Nothing do display!");
            if (result.size() == 0){
                LOG.info("Response: OK");
                return new ResponseEntity<Object>(response, HttpStatus.OK);
            }
            else {
                LOG.info("Response: OK");
                return new ResponseEntity<>(new FinalResponse("OK", result),HttpStatus.OK);
            }



        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
