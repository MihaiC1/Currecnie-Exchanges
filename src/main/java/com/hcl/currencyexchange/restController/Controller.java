package com.hcl.currencyexchange.restController;

import com.hcl.currencyexchange.manager.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>Controller class</h1>
 * This class is used to handle all the requests sent by the user.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */

@RestController
@EnableJpaRepositories(basePackages = "com.hcl.currencyexchange.repository")
@RequestMapping(value = "/")
@Scope("request")
@CrossOrigin
public class Controller {

    @Autowired
    DatabaseManager databaseManager;

    /**
     * This method is used to handle the POST request to the localhost:8080/exchange/v1/add URL by calling the addExchanges method from the DatabaseManager class.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param curTo The currency ISO to convert into (ex. USD, EUR, etc).
     * @param rate The conversion rate between specified currencies.
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    @RequestMapping(value = "/exchange/v1/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addExchanges(@RequestParam("Date") String date,
                                               @RequestParam("CurFrom") String curFrom,
                                               @RequestParam("CurTo") String curTo,
                                               @RequestParam("Rate") Float rate) {
        return databaseManager.addExchanges(date, curFrom, curTo, rate);

    }

    /**
     * This method is used to handle the PUT request to the localhost:8080/exchange/v1/update URL by calling the updateById method from the DatabaseManager class.
     * @param rate The updated conversion rate between specified currencies.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param curTo The currency ISO to convert into (ex. USD, EUR, etc).
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */

    @RequestMapping(value = "/exchange/v1/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateById(@RequestParam("Rate") Float rate,
                                             @RequestParam("Date") String date,
                                             @RequestParam("CurFrom") String curFrom,
                                             @RequestParam("CurTo") String curTo) {

        return databaseManager.updateById(rate, date, curFrom, curTo);
    }

    /**
     * This method is used to handle the GET request to the localhost:8080/exchange/v1/get URL by calling the getExchanges method from the DatabaseManager class.
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    @RequestMapping(value = "/exchange/v1/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchanges() {
        return databaseManager.getExchanges();
    }

    /**
     * This method is used to handle the GET request to the localhost:8080/exchange/v1/get/{date} URL by calling the getExchangeFromDate method from the DatabaseManager class.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    @RequestMapping(value = "/exchange/v1/get/{date}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDate(@PathVariable("date") String date) {
        return databaseManager.getExchangeFromDate(date);
    }


    /**
     * This method is used to handle the GET request to the localhost:8080/exchange/v1/get/{date}/{fromCurr} URL by calling the getExchangeFromDateWithCurr method from the DatabaseManager class.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateWithCurr(@PathVariable("date") String date,
                                                              @PathVariable("fromCurr") String curFrom) {
        return databaseManager.getExchangeFromDateWithCurr(date, curFrom);
    }

    /**
     * This method is used to handle the GET request to the localhost:8080/exchange/v1/get/{date}/{fromCurr} URL by calling the getExchangeFromDateFromCurrAtCurr method from the DatabaseManager class.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param curTo The currency ISO to convert into (ex. USD, EUR, etc).
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}/{toCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr(@PathVariable("date") String date,
                                                                    @PathVariable("fromCurr") String curFrom,
                                                                    @PathVariable("toCurr") String curTo) {
        return databaseManager.getExchangeFromDateFromCurrAtCurr(date, curFrom, curTo);
    }
}
