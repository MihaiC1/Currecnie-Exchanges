package com.hcl.currencyexchange.provider;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    @JsonProperty ("CurrentInterbankRate")
    private float currentInterbankRate;
    @JsonProperty("CurrentInverseInterbankRate")
    private float currentInverseInterbankRate;
    @JsonProperty("Average")
    private float average;
    @JsonProperty("HistoricalPoints")
    private HistoricalPoints[] historicalPoints;

    private boolean supportedByOfx;

    private Long fetchTime;

    public Data(float currentInterbankRate, float currentInverseInterbankRate, float average, HistoricalPoints[] historicalPoints, boolean supportedByOfx, Long fetchTime) {
        this.currentInterbankRate = currentInterbankRate;
        this.currentInverseInterbankRate = currentInverseInterbankRate;
        this.average = average;
        this.historicalPoints = historicalPoints;
        this.supportedByOfx = supportedByOfx;
        this.fetchTime = fetchTime;
    }
    public Data() {

    }

    public float getCurrentInterbankRate() {
        return this.currentInterbankRate;
    }

    public float setCurrentInterbankRate(float currentInterbankRate) {
        return this.currentInterbankRate = currentInterbankRate;
    }

    public float getCurrentInverseInterbankRate() {
        return this.currentInverseInterbankRate;
    }

    public void setCurrentInverseInterbankRate(float currentInverseInterbankRate) {
        this.currentInverseInterbankRate = currentInverseInterbankRate;
    }

    public float getAverage() {
        return this.average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public HistoricalPoints[] getHistoricalPoints() {
        return this.historicalPoints;
    }

    public void setHistoricalPoints(HistoricalPoints[] historicalPoints) {
        this.historicalPoints = historicalPoints;
    }

    public boolean isSupportedByOfx() {
        return this.supportedByOfx;
    }

    public void setSupportedByOfx(boolean supportedByOfx) {
        this.supportedByOfx = supportedByOfx;
    }

    public Long getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Long fetchTime) {
        this.fetchTime = fetchTime;
    }
}
