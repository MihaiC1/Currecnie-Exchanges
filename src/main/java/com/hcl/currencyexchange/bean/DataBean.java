package com.hcl.currencyexchange.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <h1>Data bean class</h1>
 * This class is used to manage the output of the API.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Data
public class DataBean {
    @JsonProperty ("CurrentInterbankRate")
    private float currentInterbankRate;
    @JsonProperty("CurrentInverseInterbankRate")
    private float currentInverseInterbankRate;
    @JsonProperty("Average")
    private float average;
    @JsonProperty("HistoricalPoints")
    private HistoricalPointsBean[] historicalPointBeans;

    private boolean supportedByOfx;

    private Long fetchTime;

    /**
     * This is the parametrized constructor of the class.
     * @param currentInterbankRate The exchange rate between the specified currencies.
     * @param currentInverseInterbankRate The inverse exchange rate between the specified currencies.
     * @param average The average of the exchange rates in the specified period.
     * @param historicalPointBeans The list of HistoricalPointsBean objects
     * @param supportedByOfx The boolean variable.
     * @param fetchTime The Long variable.
     */
    public DataBean(float currentInterbankRate, float currentInverseInterbankRate, float average, HistoricalPointsBean[] historicalPointBeans, boolean supportedByOfx, Long fetchTime) {
        this.currentInterbankRate = currentInterbankRate;
        this.currentInverseInterbankRate = currentInverseInterbankRate;
        this.average = average;
        this.historicalPointBeans = historicalPointBeans;
        this.supportedByOfx = supportedByOfx;
        this.fetchTime = fetchTime;
    }

    /**
     * This is the empty constructor for the class
     */
    public DataBean() {

    }

    /**
     * This is the get method for currentInterbankRate variable.
     * @return float The currentInterbankRate variable.
     */
//    public float getCurrentInterbankRate() {
//        return this.currentInterbankRate;
//    }
//
//    /**
//     * This is the set method for currentInterbankRate variable.
//     * @param currentInterbankRate The value that will be set for the currentInterbankRate variable.
//     */
//    public void setCurrentInterbankRate(float currentInterbankRate) {
//        this.currentInterbankRate = currentInterbankRate;
//    }
//
//    /**
//     * This is the get method for currentInverseInterbankRate variable.
//     * @return float The currentInverseInterbankRate variable.
//     */
//    public float getCurrentInverseInterbankRate() {
//        return this.currentInverseInterbankRate;
//    }
//    /**
//     * This is the set method for currentInverseInterbankRate variable.
//     * @param currentInverseInterbankRate The value that will be set for the currentInverseInterbankRate variable.
//     */
//    public void setCurrentInverseInterbankRate(float currentInverseInterbankRate) {
//        this.currentInverseInterbankRate = currentInverseInterbankRate;
//    }
//
//    /**
//     * This is the get method for average variable.
//     * @return float The average variable.
//     */
//    public float getAverage() {
//        return this.average;
//    }
//
//    /**
//     * This is the set method for average variable.
//     * @param average The value that will be set for the average variable.
//     */
//    public void setAverage(float average) {
//        this.average = average;
//    }
//
//    /**
//     * This is the get method for historicalPointBeans variable.
//     * @return HistoricalPointsBean[] The historicalPointBeans variable.
//     */
//    public HistoricalPointsBean[] getHistoricalPoints() {
//        return this.historicalPointBeans;
//    }
//
//    /**
//     * This is the set method for historicalPointBeans variable.
//     * @param historicalPointBeans The value that will be set for the historicalPointBeans variable.
//     */
//    public void setHistoricalPoints(HistoricalPointsBean[] historicalPointBeans) {
//        this.historicalPointBeans = historicalPointBeans;
//    }
//
//    /**
//     * This is the get method for supportedByOfx variable.
//     * @return boolean The supportedByOfx variable.
//     */
//    public boolean isSupportedByOfx() {
//        return this.supportedByOfx;
//    }
//
//    /**
//     * This is the set method for supportedByOfx variable.
//     * @param supportedByOfx The value that will be set for the supportedByOfx variable.
//     */
//    public void setSupportedByOfx(boolean supportedByOfx) {
//        this.supportedByOfx = supportedByOfx;
//    }
//
//    /**
//     * This is the get method for fetchTime variable.
//     * @return Long The fetchTime variable.
//     */
//    public Long getFetchTime() {
//        return fetchTime;
//    }
//
//    /**
//     * This is the set method for fetchTime variable.
//     * @param fetchTime The value that will be set for the fetchTime variable.
//     */
//    public void setFetchTime(Long fetchTime) {
//        this.fetchTime = fetchTime;
//    }
}
