package com.hcl.currencyexchange.restController;

import com.hcl.currencyexchange.entity.Currencies;
import com.hcl.currencyexchange.entity.Exchanges;
import com.hcl.currencyexchange.repository.CurrenciesRepository;
import com.hcl.currencyexchange.repository.ExchangeRepository;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<Object> addExchanges(@RequestBody Exchanges exchangesEntityObj) {
        Exchanges exchangesEntityResult = new Exchanges();

        try {
            //Lists to check if the currencies from the body exist in the DB
            Currencies fromCurr = currenciesRepositoryObj.getByAbb(exchangesEntityObj.getFromCurrency());
            Currencies toCurr = currenciesRepositoryObj.getByAbb(exchangesEntityObj.getToCurrency());
            if (exchangesEntityObj.getInitialValue() == 0){
                exchangesEntityObj.setInitialValue(1);
            }
            if ((fromCurr != null) && (toCurr != null) && (exchangesEntityObj.getInitialValue() > 0)) {

                if (fromCurr.getAbbreviation().equals("EUR") && toCurr.getAbbreviation().equals("USD")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 1.09f);

                    exchangesEntityResult = exchangeRepositoryObj.save(exchangesEntityObj);

                } else if (fromCurr.getAbbreviation().equals("USD") && toCurr.getAbbreviation().equals("EUR")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 0.92f);

                    exchangesEntityResult = exchangeRepositoryObj.save(exchangesEntityObj);
                } else if (fromCurr.getAbbreviation().equals("RON") && toCurr.getAbbreviation().equals("USD")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 0.22f);

                    exchangesEntityResult = exchangeRepositoryObj.save(exchangesEntityObj);
                } else if (fromCurr.getAbbreviation().equals("USD") && toCurr.getAbbreviation().equals("RON")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 4.59f);

                    exchangesEntityResult = exchangeRepositoryObj.save(exchangesEntityObj);
                } else if (fromCurr.getAbbreviation().equals("EUR") && toCurr.getAbbreviation().equals("RON")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 4.92f);

                    exchangesEntityResult = exchangeRepositoryObj.save(exchangesEntityObj);
                } else if (fromCurr.getAbbreviation().equals("RON") && toCurr.getAbbreviation().equals("EUR")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 0.20f);

                    exchangesEntityResult = exchangeRepositoryObj.save(exchangesEntityObj);
                }

            } else {
                if (toCurr == null) {
                    return new ResponseEntity<Object>("Invalid input. " + exchangesEntityObj.getToCurrency() + " is not available for exchange.", HttpStatus.NOT_FOUND);
                }
                if (fromCurr == null) {
                    return new ResponseEntity<Object>("Invalid input. " + exchangesEntityObj.getFromCurrency() + " is not available for exchange.", HttpStatus.NOT_FOUND);
                }

            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (exchangesEntityResult.getFinalValue() != 0){
            return new ResponseEntity<Object>(exchangesEntityResult.toString(), HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<Object>(exchangesEntityResult, HttpStatus.NO_CONTENT);
        }

    }

    //Get all the records available - returns a list with all the elements
    @RequestMapping(value = "/exchange/v1/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchanges() {

        try {
            List<Exchanges> result = exchangeRepositoryObj.getAll();

            if (result.size() > 0) {

                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("The database is empty!", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the records from the specified date - returns a list with all the elements
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDate(@PathVariable("date") String date) {

        try {

            List<Exchanges> exchangesFromDate = new ArrayList<>();
            if (GenericValidator.isDate(date,"yyyy-MM-dd",true)){
                LocalDate localDate = LocalDate.parse(date);
                exchangesFromDate = exchangeRepositoryObj.findByDate(localDate);
            }
            else {
                return new ResponseEntity<>("Invalid date format!", HttpStatus.BAD_REQUEST);
            }
            if (exchangesFromDate.size() > 0) {
                return new ResponseEntity<Object>(exchangesFromDate, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("No exchanges found on " + date + "!", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a specific transaction - returns the result of the saving (Succesfully saved / failed to save)
    //id = identifier to help finding the record
    @RequestMapping(value = "/exchange/v1/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateById(@PathVariable("id") int id,
                                             @RequestBody Exchanges exchangesEntityObj) {

        try {
            Optional<Exchanges> result = exchangeRepositoryObj.findById(id);
            if (result.isPresent()) {
                if (exchangesEntityObj.getFromCurrency().equals("EUR") && exchangesEntityObj.getToCurrency().equals("USD")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 1.08f);
                    exchangesEntityObj.setDate(LocalDate.now());
                } else if (exchangesEntityObj.getFromCurrency().equals("USD") && exchangesEntityObj.getToCurrency().equals("EUR")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 0.93f);
                    exchangesEntityObj.setDate(LocalDate.now());
                } else if (exchangesEntityObj.getFromCurrency().equals("RON") && exchangesEntityObj.getToCurrency().equals("USD")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 0.22f);
                    exchangesEntityObj.setDate(LocalDate.now());
                } else if (exchangesEntityObj.getFromCurrency().equals("USD") && exchangesEntityObj.getToCurrency().equals("RON")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 4.57f);
                    exchangesEntityObj.setDate(LocalDate.now());
                } else if (exchangesEntityObj.getFromCurrency().equals("EUR") && exchangesEntityObj.getToCurrency().equals("RON")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 4.92f);
                    exchangesEntityObj.setDate(LocalDate.now());
                } else if (exchangesEntityObj.getFromCurrency().equals("RON") && exchangesEntityObj.getToCurrency().equals("EUR")) {
                    exchangesEntityObj.setFinalValue(exchangesEntityObj.getInitialValue() * 0.20f);
                    exchangesEntityObj.setDate(LocalDate.now());
                } else {
                    exchangesEntityObj.setFinalValue(0);
                }

                return new ResponseEntity<>(exchangeRepositoryObj.save(exchangesEntityObj), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("The transaction with ID: " + id + " is not available in our records!",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the exchanges from the specified date with the specified initial currency - return a list with specific elements
    // fromCurr = currency to be exchanged
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateWithCurr(@PathVariable("date") String date, @PathVariable("fromCurr") String fromCurr) {

        try {

            List<Exchanges> result;
            if (GenericValidator.isDate(date,"yyyy-MM-dd",true) && fromCurr != null) {
                LocalDate localDate = LocalDate.parse(date);
                result = exchangeRepositoryObj.getFromDateAndCurr(localDate, fromCurr);
            }
            else {
                return new ResponseEntity<>("Invalid date format!", HttpStatus.BAD_REQUEST);
            }
            if (result.size() > 0) {
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("No exchanges found on " + date + " from " + fromCurr,
                        HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the exchanges from the specified date with the specified initial currency and end currency
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}/{toCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr(@PathVariable("date") String date, @PathVariable("fromCurr") String fromCurr, @PathVariable("toCurr") String toCurr) {

        try {

            List<Exchanges> result = new ArrayList<>();
            if (GenericValidator.isDate(date,"yyyy-MM-dd",true) && fromCurr != null && toCurr != null) {
                LocalDate localDate = LocalDate.parse(date);
                result = exchangeRepositoryObj.getFromDateAndCurr(localDate, fromCurr, toCurr);
            }
            else {
                return new ResponseEntity<>("Invalid date format!", HttpStatus.BAD_REQUEST);
            }


            if (result.size() > 0) {
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("No exchanges found on " + date + " from " + fromCurr + " to " + toCurr,
                        HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
