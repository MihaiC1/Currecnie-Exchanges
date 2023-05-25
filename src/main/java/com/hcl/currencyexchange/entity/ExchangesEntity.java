package com.hcl.currencyexchange.entity;

import lombok.Data;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <h1>Exchanges Entity class</h1>
 * This class represents an entity for the currency_conversion table from the database.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */

@Entity
@Table(name = "currency_conversion")
@Data
public class ExchangesEntity {
    @Id
    @Column(name = "CCN_ID")
    private int id; // Unique identifier for every record
    @Column(name = "CCN_DATE")
    private LocalDate date; // The date when the transaction was made
    @Column(name = "CCN_CUR_ID_FROM")
    private int curIdFrom; //The ID for Currency to be converted
    @Column(name = "CCN_CUR_ID_TO")
    private int curIdTo; //The ID for Currency to convert to

    @Column(name = "CCN_RATE")
    private float rate; // Exchanging rate

    @Column(name = "CCN_INSERT_TIME")
    private LocalDateTime insertTime;

    /**
     * This is the empty constructor of the class.
     */
    public ExchangesEntity() {
    }

    /**
     * This is the parametrized constructor of the class.
     * @param date The variable that corresponds to the CCN_DATE column from the currency_conversion table.
     * @param curIdFrom The variable that corresponds to the CCN_CUR_ID_FROM column from the currency_conversion table.
     * @param curIdTo The variable that corresponds to the CCN_CUR_ID_TO column from the currency_conversion table.
     * @param rate The variable that corresponds to the CCN_RATE column from the currency_conversion table.
     * @param insertTime The variable that corresponds to the CCN_INSERT_TIME column from the currency_conversion table.
     */
    public ExchangesEntity(LocalDate date, int curIdFrom, int curIdTo, float rate, LocalDateTime insertTime) {

        this.date = date;
        this.curIdFrom = curIdFrom;
        this.curIdTo = curIdTo;
        this.rate = rate;
        this.insertTime = insertTime;
    }

    /**
     * This is the get method for the insertTime variable.
     * @return LocalDateTime The insertTime variable.
     */
//    public LocalDateTime getInsertTime() {
//        return insertTime;
//    }
//
//    /**
//     * This is the set method for the insertTime variable.
//     * @param insertTime The value that will be saved into the insertTime variable.
//     */
//    public void setInsertTime(LocalDateTime insertTime) {
//        this.insertTime = insertTime;
//    }
//
//    /**
//     * This is the get method for the id variable.
//     * @return int The id variable.
//     */
//    public int getId() {
//        return id;
//    }
//
//    /**
//     * This is the set method for the id variable.
//     * @param id The value that will be saved into the id variable.
//     */
//    public void setId(int id) {
//        this.id = id;
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
//     * This is the get method for the curIdFrom variable.
//     * @return int The curIdFrom variable.
//     */
//    public int getCurIdFrom() {
//        return curIdFrom;
//    }
//
//    /**
//     * This is the set method for the curIdFrom variable.
//     * @param curIdFrom The value that will be saved into the curIdFrom variable.
//     */
//    public void setCurIdFrom(int curIdFrom) {
//        this.curIdFrom = curIdFrom;
//    }
//
//    /**
//     * This is the get method for the curIdTo variable.
//     * @return int The curIdTo variable.
//     */
//    public int getCurIdTo() {
//        return curIdTo;
//    }
//
//    /**
//     * This is the set method for the curIdTo variable.
//     * @param curIdTo The value that will be saved into the curIdTo variable.
//     */
//    public void setCurIdTo(int curIdTo) {
//        this.curIdTo = curIdTo;
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
