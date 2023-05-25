package com.hcl.currencyexchange.bean;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <h1>Join Table Bean class</h1>
 * This class is used to facilitate the join between the currency_conversion and currency tables.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Data
public class JoinTableBean {
    String currencyTo;
    String currencyFrom;
    LocalDate date;
    float rate;
    LocalDateTime insertTime;

    /**
     * This is the parametrized constructor of the class.
     * @param date The date when the transaction was made. Date format: YYYY-MM-DD.
     * @param rate The exchange rate between the specified currencies.
     * @param currencyFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param currencyTo The currency ISO to convert into (ex. USD, EUR, etc).
     * @param insertTime The exact date and time when the transaction was made.
     */
    public JoinTableBean(LocalDate date, float rate, String currencyTo, String currencyFrom, LocalDateTime insertTime) {

        this.currencyTo = currencyTo;
        this.currencyFrom = currencyFrom;
        this.date = date;
        this.rate = rate;
        this.insertTime = insertTime;
    }

//    /**
//     * This is the get method for the insertTime variable.
//     * @return LocalDateTime The insertTime variable.
//     */
//    public LocalDateTime getInsertTime() {
//        return insertTime;
//    }
//
//    /**
//     * This is the set method for the insertTime variable.
//     * @param insertTime The value that will be saved into insertTime variable.
//     */
//    public void setInsertTime(LocalDateTime insertTime) {
//        this.insertTime = insertTime;
//    }
//
//    /**
//     * This is the get method for the currencyTo variable.
//     * @return String The currencyTo variable.
//     */
//    public String getCurrencyTo() {
//        return currencyTo;
//    }
//
//    /**
//     * This is the set method for the currencyTo variable.
//     * @param currencyTo The value that will be saved into the currencyTo variable.
//     */
//    public void setCurrencyTo(String currencyTo) {
//        this.currencyTo = currencyTo;
//    }
//
//    /**
//     * This is the get method for currencyFrom variable.
//     * @return String The currencyFrom variable.
//     */
//    public String getCurrencyFrom() {
//        return currencyFrom;
//    }
//
//    /**
//     * This is the set method for the currencyFrom variable.
//     * @param currencyFrom The value that will be saved into the currencyFrom variable.
//     */
//    public void setCurrencyFrom(String currencyFrom) {
//        this.currencyFrom = currencyFrom;
//    }
//
//    /**
//     * This is the get method for the date variable.
//     * @return LocalDate The date variable.
//     */
//    public LocalDate getDate() {
//        return date;
//    }
//
//    /**
//     * This is the set method for the date variable.
//     * @param date The value that will be saved into the date variable.
//     */
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    /**
//     * This is the get method for the rate variable.
//     * @return float The rate variable.
//     */
//    public float getRate() {
//        return rate;
//    }
//
//    /**
//     * This is the set method for the rate variable.
//     * @param rate The value that will be saved into the rate variable.
//     */
//    public void setRate(float rate) {
//        this.rate = rate;
//    }
}
