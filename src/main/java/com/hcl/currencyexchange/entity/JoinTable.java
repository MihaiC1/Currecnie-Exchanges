package com.hcl.currencyexchange.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class JoinTable {


    String currencyTo;
    String currencyFrom;
    LocalDate date;
    float rate;
    LocalDateTime insertTime;


    public JoinTable(LocalDate date, float rate, String currencyTo, String currencyFrom, LocalDateTime insertTime) {

        this.currencyTo = currencyTo;
        this.currencyFrom = currencyFrom;
        this.date = date;
        this.rate = rate;
        this.insertTime = insertTime;
    }
    public JoinTable( LocalDate date, float rate, String currencyTo, String currencyFrom) {
        //this.ID = ID;
        this.currencyTo = currencyTo;
        this.currencyFrom = currencyFrom;
        this.date = date;
        this.rate = rate;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }

//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
