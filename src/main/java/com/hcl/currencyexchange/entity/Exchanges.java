package com.hcl.currencyexchange.entity;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "Exchanges")
public class Exchanges {
    @Id
    private int id; // Unique identifier for every record
    @Column
    private LocalDate date = LocalDate.now(); // The date when the transaction was made
    @Column
    private String fromCurrency; //Currency to be converted
    @Column
    private String toCurrency; //Currency to convert to

    @Column
    private float initialValue; // Amount to be exchanged
    @Column
    private float finalValue; // Calculated amount after the exchange

    public Exchanges() {
    }

    public Exchanges(int id, String fromCurrency, String toCurrency, float initialValue) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.initialValue = initialValue;

    }
    public Exchanges(int id, String fromCurrency, String toCurrency) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public float getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(float initialValue) {
        this.initialValue = initialValue;
    }

    public float getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(float finalValue) {
        this.finalValue = finalValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Exchanges{" +
                "id=" + id +
                ", date=" + date +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", initialValue=" + initialValue +
                ", finalValue=" + finalValue +
                "}\n";
    }
}
