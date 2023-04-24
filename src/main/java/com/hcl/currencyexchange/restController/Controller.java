package com.hcl.currencyexchange.restController;

import com.hcl.currencyexchange.FinalResponse;
import com.hcl.currencyexchange.entity.Exchanges;
import com.hcl.currencyexchange.entity.JoinTable;
import com.hcl.currencyexchange.repository.CurrenciesRepository;
import com.hcl.currencyexchange.repository.ExchangeRepository;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@EnableJpaRepositories(basePackages = "com.hcl.currencyexchange.repository")
@RequestMapping(value = "/")
public class Controller {
    @Autowired
    ExchangeRepository exchangeRepositoryObj;
    @Autowired
    CurrenciesRepository currenciesRepositoryObj;

    @RequestMapping(value = "/exchange/v1/greeting", method = RequestMethod.GET)
    public ResponseEntity<Object> greeting()
    {
        try {
            return new ResponseEntity<>("Welcome to the Currency exchange!!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Save exchanges to the database - returns the result of the saving process
    @RequestMapping(value = "/exchange/v1/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addExchanges(@RequestParam("DATE") String date,
                                               @RequestParam("CUR_FROM") String CUR_FROM,
                                               @RequestParam("CUR_TO") String CUR_TO,
                                               @RequestParam("RATE") Float CCN_RATE)
    {
        try {
            if ((GenericValidator.isDate(date,"yyyy-MM-dd",true)) && (currenciesRepositoryObj.getIDByISO(CUR_FROM) > 0) &&
                    (currenciesRepositoryObj.getIDByISO(CUR_TO) > 0) && CCN_RATE > 0) {
                SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                Date localDate = dateFormat.parse(date);
                exchangeRepositoryObj.insertExchanges(localDate, currenciesRepositoryObj.getIDByISO(CUR_FROM), currenciesRepositoryObj.getIDByISO(CUR_TO),CCN_RATE);
                return new ResponseEntity<>(new FinalResponse("Data added succesfully!"),HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(new FinalResponse("No data added!"),HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
    // Update a specific transaction - returns the result of the saving (Succesfully saved / failed to save)
    //id = identifier to help finding the record
    //TODO Update query in REPO
    @RequestMapping(value = "/exchange/v1/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateById(@RequestParam("RATE") Float rate,
                                             @RequestParam ("DATE") String date,
                                             @RequestParam("CUR_FROM") String cur_FROM,
                                             @RequestParam("CUR_TO") String cur_TO
                                            )
    {

        try {
            if ((GenericValidator.isDate(date,"yyyy-MM-dd",true))
                    && (currenciesRepositoryObj.getIDByISO(cur_FROM) > 0)
                    && (currenciesRepositoryObj.getIDByISO(cur_TO) > 0)
                    && (rate > 0)) {
                SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                Date localDate = dateFormat.parse(date);
                exchangeRepositoryObj.updateRecord(rate,localDate, cur_FROM,cur_TO);
                return new ResponseEntity<Object>(new FinalResponse("OK"), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<Object>(new FinalResponse("Invalid input. " + rate + " cannot be less then or equal to 0."), HttpStatus.BAD_REQUEST);//alt httpstatus
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(new FinalResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the records available - returns a list with all the elements
    @RequestMapping(value = "/exchange/v1/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchanges()
    {


        try {
            List<JoinTable> result = exchangeRepositoryObj.getAll();
            if (result.size() > 0) {
                return new ResponseEntity<Object>(result, HttpStatus.OK);

            } else {
                return new ResponseEntity<Object>(result, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the records from the specified date - returns a list with all the elements
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDate(@PathVariable("date") String date)
    {

        try {

            List<JoinTable> exchangesFromDate;
            if (GenericValidator.isDate(date,"yyyy-MM-dd",true)){
                SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                Date localDate = dateFormat.parse(date);

                exchangesFromDate = exchangeRepositoryObj.findByDate(localDate);
            }
            else {
                return new ResponseEntity<Object>("Invalid date format! The accepted format is: YYYY-MM-DD", HttpStatus.BAD_REQUEST);

            }
            if (exchangesFromDate.size() > 0) {
                return new ResponseEntity<Object>(exchangesFromDate, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(exchangesFromDate, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //Get all the exchanges from the specified date with the specified initial currency - return a list with specific elements
    // fromCurr = currency to be exchanged
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateWithCurr(@PathVariable("date") String date, @PathVariable("fromCurr") String fromCurr)
    {

        try {

            List<JoinTable> result;

            if (GenericValidator.isDate(date,"yyyy-MM-dd",true) && fromCurr != null) {

                SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                Date localDate = dateFormat.parse(date);
                result = exchangeRepositoryObj.getFromDateAndCurr(localDate, fromCurr);
            }
            else {
                return new ResponseEntity<>("Invalid date format! The accepted format is: YYYY-MM-DD", HttpStatus.BAD_REQUEST);
            }
            if (result.size() > 0) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Get all the exchanges from the specified date with the specified initial currency and end currency
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}/{toCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr(@PathVariable("date") String date,
                                                                    @PathVariable("fromCurr") String fromCurr,
                                                                    @PathVariable("toCurr") String toCurr)
    {

        try {
            String responseNotFound = "No exchanges found on " + date + " from " + fromCurr + " to " + toCurr;
            String responseInvalidDate = "Invalid date format! The accepted format is: YYYY-MM-DD";
            List<JoinTable> result = new ArrayList<>();
            if (GenericValidator.isDate(date,"yyyy-MM-dd",true) && fromCurr != null && toCurr != null) {
                SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                Date localDate = dateFormat.parse(date);
                result = exchangeRepositoryObj.getFromDateAndCurr(localDate, fromCurr, toCurr);
            }
            else {
                return new ResponseEntity<Object>("Invalid date format! The accepted format is: YYYY-MM-DD", HttpStatus.BAD_REQUEST);
            }


            if (result.size() > 0) {
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(result, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
