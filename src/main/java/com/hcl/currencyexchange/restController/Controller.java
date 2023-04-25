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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public ResponseEntity<Object> addExchanges(@RequestParam("Date") String date,
                                               @RequestParam("CurFrom") String curFrom,
                                               @RequestParam("CurTo") String curTo,
                                               @RequestParam("Rate") Float rate)
    {
        try {
            boolean isValidDate = GenericValidator.isDate(date,"yyyy-MM-dd",true);
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);
            int idForCurFrom = 0;
            int idForCurTo = 0;
            if (curFromObj != null && curToObj != null){
                idForCurFrom = currenciesRepositoryObj.getIDByISO(curFrom);
                idForCurTo = currenciesRepositoryObj.getIDByISO(curTo);
            }

            if ( isValidDate && idForCurFrom > 0 && idForCurTo > 0 && rate > 0)
            {
                DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date, dateFormat);
                exchangeRepositoryObj.insertExchanges(localDate, idForCurFrom, idForCurTo, rate);
                Exchanges addedObj = new Exchanges(localDate, idForCurFrom, idForCurTo, rate);
                Map<String, Object> response = new HashMap<>();

                response.put("response","OK");

                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            else if (!isValidDate)
            {
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else if (idForCurFrom <= 0)
            {
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else if (idForCurTo <= 0)
            {
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else if (rate < 0){
                return new ResponseEntity<>(new FinalResponse("The rate cannot be less then, or equal to 0! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<>(new FinalResponse("No data added!"),HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
    // Update a specific transaction - returns the result of the saving (Succesfully saved / failed to save)
    //id = identifier to help finding the record

    @RequestMapping(value = "/exchange/v1/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateById(@RequestParam("Rate") Float rate,
                                             @RequestParam ("Date") String date,
                                             @RequestParam("CurFrom") String curFrom,
                                             @RequestParam("CurTo") String curTo
                                            )
    {

        try {
            boolean isValidDate = GenericValidator.isDate(date,"yyyy-MM-dd",true);
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);
            int idForCurFrom = 0;
            int idForCurTo = 0;
            if (curFromObj != null && curToObj != null){
                idForCurFrom = currenciesRepositoryObj.getIDByISO(curFrom);
                idForCurTo = currenciesRepositoryObj.getIDByISO(curTo);
            }
            if (isValidDate && idForCurFrom > 0 && idForCurTo > 0 && rate > 0)
            {
                DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date, dateFormat);
                exchangeRepositoryObj.updateRecord(rate,localDate, curFrom,curTo);
                Exchanges updatedObj = new Exchanges(localDate,idForCurFrom, idForCurTo,rate);
                return new ResponseEntity<>(new FinalResponse("OK", updatedObj), HttpStatus.OK);
            }
            else if (!isValidDate)
            {
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else if (idForCurFrom <= 0)
            {
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else if (idForCurTo <= 0)
            {
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else if (rate < 0)
            {
                return new ResponseEntity<>(new FinalResponse("The rate cannot be less then, or equal to 0! No data added!"),HttpStatus.BAD_REQUEST);
            }
            else
            {
                return new ResponseEntity<>(new FinalResponse("Invalid input. Nothing updated! "), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new FinalResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get all the records available - returns a list with all the elements
    @RequestMapping(value = "/exchange/v1/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchanges()
    {
        try {
            List<JoinTable> result = exchangeRepositoryObj.getAll();
            LOG.info("DATA RETRIEVED");
            return new ResponseEntity<Object>(result, HttpStatus.OK);
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
            boolean isValidDate = GenericValidator.isDate(date,"yyyy-MM-dd",true);

            List<JoinTable> exchangesFromDate;
            if (isValidDate){
                DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date, dateFormat);
                exchangesFromDate = exchangeRepositoryObj.findByDate(localDate);
                return new ResponseEntity<Object>(exchangesFromDate, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<Object>("Invalid date format! The accepted format is: YYYY-MM-DD", HttpStatus.BAD_REQUEST);

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //Get all the exchanges from the specified date with the specified initial currency - return a list with specific elements
    // fromCurr = currency to be exchanged
    // date = transaction date
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateWithCurr(@PathVariable("date") String date,
                                                              @PathVariable("fromCurr") String curFrom)
    {

        try {
            boolean isValidDate = GenericValidator.isDate(date,"yyyy-MM-dd",true);
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);

            List<JoinTable> result = new ArrayList<>();

            if (isValidDate && curFromObj != null) {

                DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date, dateFormat);
                result = exchangeRepositoryObj.getFromDateAndCurr(localDate, curFrom);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else if (!isValidDate){
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data displayed!"),HttpStatus.BAD_REQUEST);
            }
            else if (curFromObj == null){
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging! No data displayed!"),HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Get all the exchanges from the specified date with the specified initial currency and end currency
    @RequestMapping(value = "/exchange/v1/get/{date}/{fromCurr}/{toCurr}", method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr(@PathVariable("date") String date,
                                                                    @PathVariable("fromCurr") String curFrom,
                                                                    @PathVariable("toCurr") String curTo)
    {

        try {
            boolean isValidDate = GenericValidator.isDate(date,"yyyy-MM-dd",true);
            Currencies curFromObj = currenciesRepositoryObj.getByISO(curFrom);
            Currencies curToObj = currenciesRepositoryObj.getByISO(curTo);
            JoinTable result;
            if ( isValidDate && curFromObj != null && curToObj != null) {
                DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(date, dateFormat);
                result = exchangeRepositoryObj.getFromDateAndCurr(localDate, curFrom, curTo);

                return new ResponseEntity<Object>(new FinalResponse("OK", result), HttpStatus.OK);
            }
            else if (!isValidDate){
                return new ResponseEntity<>(new FinalResponse("The data is not in the correct format. The accepted format is: YYYY-MM-DD! \n No data displayed!"),HttpStatus.BAD_REQUEST);
            }
            else if (curFromObj == null){
                return new ResponseEntity<>(new FinalResponse(curFrom + " is not available for exchanging!\n No data displayed!"),HttpStatus.BAD_REQUEST);
            }
            else if (curToObj == null){
                return new ResponseEntity<>(new FinalResponse(curTo + " is not available for exchanging!\n No data displayed!"),HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<Object>(new FinalResponse("Nothing to display!"), HttpStatus.OK);
            }


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
