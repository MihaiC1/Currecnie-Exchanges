package com.hcl.currencyexchange.entity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "currency_conversion")
public class Exchanges {
    @Id
    @Column(name = "CCN_ID")
    private int ID; // Unique identifier for every record
    @Column(name = "CCN_DATE")
    private LocalDate date; // The date when the transaction was made
    @Column(name = "CCN_CUR_ID_FROM")
    private int curIdFrom; //The ID for Currency to be converted
    @Column(name = "CCN_CUR_ID_TO")
    private int curIdTo; //The ID for Currency to convert to

    @Column(name = "CCN_RATE")
    private float rate; // Exchanging rate

    public Exchanges() {
    }

    public Exchanges(int ID, LocalDate date, int curIdFrom, int curIdTo, float rate) {
        this.ID = ID;
        this.date = date;
        this.curIdFrom = curIdFrom;
        this.curIdTo = curIdTo;
        this.rate = rate;
    }
    public Exchanges( LocalDate date, int curIdFrom, int curIdTo, float rate) {

        this.date = date;
        this.curIdFrom = curIdFrom;
        this.curIdTo = curIdTo;
        this.rate = rate;
    }

    public Exchanges(int curIdFrom, int curIdTo) {

        this.curIdFrom = curIdFrom;
        this.curIdTo = curIdTo;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCurIdFrom() {
        return curIdFrom;
    }

    public void setCurIdFrom(int curIdFrom) {
        this.curIdFrom = curIdFrom;
    }

    public int getCurIdTo() {
        return curIdTo;
    }

    public void setCurIdTo(int curIdTo) {
        this.curIdTo = curIdTo;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Exchanges{" +
                "CCN_ID=" + ID +
                ", CCN_DATE=" + date +
                ", CCN_CUR_ID_FROM=" + curIdFrom +
                ", CCN_CUR_ID_TO=" + curIdTo +
                ", CCN_RATE=" + rate +
                '}';
    }
}
