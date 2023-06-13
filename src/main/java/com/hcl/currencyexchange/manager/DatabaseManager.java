package com.hcl.currencyexchange.manager;

import com.hcl.currencyexchange.bean.FinalResponseBean;
import com.hcl.currencyexchange.entity.CurrenciesEntity;
import com.hcl.currencyexchange.entity.ExchangesEntity;
import com.hcl.currencyexchange.bean.JoinTableBean;
import com.hcl.currencyexchange.repository.CurrenciesRepository;
import com.hcl.currencyexchange.repository.ExchangeRepository;
import com.hcl.currencyexchange.exception.ParameterCheckException;
import com.hcl.currencyexchange.utility.ParameterValidation;
import com.hcl.currencyexchange.exception.SendRequestToProviderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Database manager</h1>
 * The class DatabaseManager is used to handle inserting, updating and extracting data to/from the database.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Service
public class DatabaseManager extends ApiManager {
    @Autowired
    ExchangeRepository exchangeRepositoryObj;
    @Autowired
    ParameterValidation parameterValidation;
    private static final Logger LOG = LogManager.getLogger(DatabaseManager.class);
    Map<String, Object> response = new HashMap<>();

    /**
     * This is the empty constructor of the class.
     */
    public DatabaseManager() {

    }

    /**
     * This method is used to insert records in the currency_conversion table using information extracted from the API.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The object is used to get the specific ID for the currency that will be exchanged.
     * @param curTo The object is used to get the specific ID for the currency that the user will exchange into.
     * @return Map<String, Object> A HashMap object with a String - String key - value pair if inserting failed. A HashMap object with a String-JoinTableBean key-value pair if inserting was successful.
     */
    public Map<String, Object> insertExchangesFromAPI(String date, CurrenciesEntity curFrom, CurrenciesEntity curTo) {

        try {

            ExchangesEntity exchangeToBeInserted = extractExchangeFromProvider(date, curFrom, curTo);
            if (exchangeToBeInserted == null) {
                response.put("Response: ", "The API does not have informations you are looking for!");
                LOG.error("Response: The API does not have informations you are looking for!");
                return response;
            }
            JoinTableBean result = new JoinTableBean(exchangeToBeInserted.getDate(), exchangeToBeInserted.getRate(), curTo.getCurIsoCode(), curFrom.getCurIsoCode(), exchangeToBeInserted.getInsertTime());
            exchangeRepositoryObj.insertExchanges(exchangeToBeInserted.getDate(), exchangeToBeInserted.getCurIdFrom(), exchangeToBeInserted.getCurIdTo(), exchangeToBeInserted.getRate(), exchangeToBeInserted.getInsertTime());
            LOG.info("Response: OK");
            response.put("Response: ", result);
            return response;
        }
        catch (Exception e) {
            response.put("Response: ", e.getMessage());
            LOG.error(e);
            return response;
        }
    }

    /**
     * This method is used to manually update the rate of a record from the currency_conversion table.
     * @param rate The updated rate for the transaction.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param curTo The currency ISO to convert into (ex. USD, EUR, etc).
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    public ResponseEntity<Object> updateById(Float rate, String date, String curFrom, String curTo) {

        try {
            LOG.info("\nInput:\n Date: " + date + "\nCurrencyFrom: " + curFrom + "\nCurrencyTo: " + curTo + "\nExchange rate: " + rate);

            parameterValidation.dateValidation(date);
            parameterValidation.currencyValidation(curFrom);
            parameterValidation.currencyValidation(curTo);
            parameterValidation.rateValidation(rate);


            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            exchangeRepositoryObj.updateRecord(rate, localDate, curFrom, curTo);
            response.put("response", "OK");
            LOG.info("Response: OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ParameterCheckException e) {
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This  method is used to manually add records into the currency_conversion table.
     * @param rate The updated rate for the transaction.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param curTo The currency ISO to convert into (ex. USD, EUR, etc).
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    public ResponseEntity<Object> addExchanges(String date, String curFrom, String curTo, Float rate) {

        try {
            LOG.info("\nInput:\n Date: " + date + "\nCurrencyFrom: " + curFrom + "\nCurrencyTo: " + curTo + "\nExchange rate: " + rate);

            CurrenciesEntity curFromObj = parameterValidation.currencyValidation(curFrom);
            int idForCurFrom = curFromObj.getCurID();

            CurrenciesEntity curToObj = parameterValidation.currencyValidation(curTo);
            int idForCurTo = curToObj.getCurID();

            parameterValidation.dateValidation(date);
            parameterValidation.rateValidation(rate);

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            exchangeRepositoryObj.insertExchanges(localDate, idForCurFrom, idForCurTo, rate, LocalDateTime.now());

            response.put("response", "OK");
            LOG.info("Response: OK");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (ParameterCheckException e) {
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            LOG.error("Error stack trace: ", e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * This method is used to retrieve a record from the currency_conversion table, based on the parameters. If the transaction is not available, the transaction will be retrieved from the API and inserted into the table.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param curTo The currency ISO to convert into (ex. USD, EUR, etc).
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */

    public ResponseEntity<Object> getExchangeFromDateFromCurrAtCurr(String date, String curFrom, String curTo) {

        try {

            LOG.info("\nInput: \n" + "date: " + date + "\n" + "fromCurrency: " + curFrom + "\n" + "toCurrency: " + curTo);

            parameterValidation.dateValidation(date);

            CurrenciesEntity curFromObj = parameterValidation.currencyValidation(curFrom);

            CurrenciesEntity curToObj = parameterValidation.currencyValidation(curTo);

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);


            List<JoinTableBean> result = exchangeRepositoryObj.getFromDateAndCurr(localDate, curFrom, curTo, PageRequest.of(0, 1));


            if (result.isEmpty()) {
                Map<String, Object> insertingResponse = insertExchangesFromAPI(date, curFromObj, curToObj);
                if (!insertingResponse.get("Response: ").getClass().equals(String.class)){
                    return new ResponseEntity<>(new FinalResponseBean("OK", insertingResponse.get("Response: ")), HttpStatus.OK);
                }
                Map<String, Object> failureResponse = new HashMap<String, Object>();
                failureResponse.put("Response: ", "Invalid date passed!");
                return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);

            }

            LOG.info("Response: OK");
            return new ResponseEntity<>(new FinalResponseBean("OK", result), HttpStatus.OK);

        } catch (ParameterCheckException e) {
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        catch(SendRequestToProviderException e){
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.BAD_GATEWAY);
        }
        catch (Exception e){

            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * This method is used to retrieve a record from the currency_conversion table, based on the parameters.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    public ResponseEntity<Object> getExchangeFromDateWithCurr(String date, String curFrom) {

        try {

            List<JoinTableBean> result;

            LOG.info("\nInput: \n" + "date: " + date + "\n" + "fromCurrency: " + curFrom + "\n");
            parameterValidation.dateValidation(date);
            parameterValidation.currencyValidation(curFrom);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, dateFormat);
            result = exchangeRepositoryObj.getFromDateAndCurr(localDate, curFrom);
            if (result.size() == 0) {
                LOG.info("Response: OK" + "\n Nothing was displayed!");
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                LOG.info("Response: OK");
                return new ResponseEntity<>(new FinalResponseBean("OK", result), HttpStatus.OK);
            }
        }catch(ParameterCheckException e){
            LOG.error("Error stack trace: " + e);

            response.put("Response: ", e.getMessage());
            LOG.info(response.hashCode());
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    /**
     * This method is used to retrieve a record from the currency_conversion table, based on the parameters.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    public ResponseEntity<Object> getExchangeFromDate(String date) {

        try {


            LOG.info("\nInput: \ndate: " + date);
            List<JoinTableBean> exchangesFromDate;
            parameterValidation.dateValidation(date);
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


        }catch (ParameterCheckException e){
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method return all the records from the currency_conversion table.
     * @return ResponseEntity<Object> used to display the output in JSON format.
     */
    public ResponseEntity<Object> getExchanges() {

        try {
            List<JoinTableBean> result = exchangeRepositoryObj.getAll();
            LOG.info("DATA RETRIEVED");
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error stack trace: " + e);
            response.put("Response: ", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
