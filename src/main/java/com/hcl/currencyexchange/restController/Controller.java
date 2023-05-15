package com.hcl.currencyexchange.restController;

import com.hcl.currencyexchange.manager.DatabaseManager;
import com.hcl.currencyexchange.repository.CurrenciesRepository;
import com.hcl.currencyexchange.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;



@RestController
@EnableJpaRepositories(basePackages = "com.hcl.currencyexchange.repository")
@RequestMapping(value = "/")
public class Controller {
    @Autowired
    ExchangeRepository exchangeRepositoryObj;
    @Autowired
    CurrenciesRepository currenciesRepositoryObj;
    DatabaseManager databaseManager = new DatabaseManager();


    //Save exchanges to the database - returns the result of the saving process
    @RequestMapping(value = "/exchange/v1/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addExchanges(@RequestParam("Date") String date,
                                               @RequestParam("CurFrom") String curFrom,
                                               @RequestParam("CurTo") String curTo,
                                               @RequestParam("Rate") Float rate) {
        return databaseManager.addExchanges(date, curFrom, curTo, rate, currenciesRepositoryObj, exchangeRepositoryObj);

    }
    // Update a specific transaction - returns the result of the saving (Succesfully saved / failed to save)
    //id = identifier to help finding the record

    @RequestMapping(value = "/exchange/v1/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateById(@RequestParam("Rate") Float rate,
                                             @RequestParam("Date") String date,
                                             @RequestParam("CurFrom") String curFrom,
                                             @RequestParam("CurTo") String curTo) {

        return databaseManager.updateById(rate, date, curFrom, curTo, currenciesRepositoryObj, exchangeRepositoryObj);
    }

    //Get all the records available - returns a list with all the elements
    @RequestMapping(value = "/exchange/v1/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchanges() {
        return databaseManager.getExchanges(exchangeRepositoryObj);
    }

    //Get all the records from the specified date - returns a list with all the elements
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDate(@PathVariable("date") String date) {
        return databaseManager.getExchangeFromDate(date, exchangeRepositoryObj);
    }


    //Get all the exchanges from the specified date with the specified initial currency - return a list with specific elements
    // fromCurr = currency to be exchanged
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateWithCurr(@PathVariable("date") String date,
                                                              @PathVariable("fromCurr") String curFrom) {
        return databaseManager.getExchangeFromDateWithCurr(date, curFrom, currenciesRepositoryObj, exchangeRepositoryObj);
    }

    //Get all the exchanges from the specified date with the specified initial currency and end currency
    //If nothing was found in the database, send a request to yahoo API and save the response in the database;
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}/{toCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr(@PathVariable("date") String date,
                                                                    @PathVariable("fromCurr") String curFrom,
                                                                    @PathVariable("toCurr") String curTo) {
        return databaseManager.getExchangeFromDateFromCurrAtCurr(date, curFrom, curTo, currenciesRepositoryObj, exchangeRepositoryObj);
    }
}
