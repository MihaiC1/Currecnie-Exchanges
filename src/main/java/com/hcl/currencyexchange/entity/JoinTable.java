package com.hcl.currencyexchange.entity;

import java.util.Date;

public class JoinTable {
    int ID;
    String currency_to;
    String currency_from;
    Date date;
    float rate;

    public JoinTable(int ID, Date date, float rate, String currency_to, String currency_from) {
        this.ID = ID;
        this.currency_to = currency_to;
        this.currency_from = currency_from;
        this.date = date;
        this.rate = rate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCurrency_to() {
        return currency_to;
    }

    public void setCurrency_to(String currency_to) {
        this.currency_to = currency_to;
    }

    public String getCurrency_from() {
        return currency_from;
    }

    public void setCurrency_from(String currency_from) {
        this.currency_from = currency_from;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
