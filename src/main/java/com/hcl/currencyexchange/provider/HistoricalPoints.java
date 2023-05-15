package com.hcl.currencyexchange.provider;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HistoricalPoints {
    @JsonProperty("PointInTime")
    private Long pointInTime;
    @JsonProperty("InterbankRate")
    private float interbankRate;
    @JsonProperty("InverseInterbankRate")
    private float inverseInterbankRate;

    public HistoricalPoints(Long PointInTime, float InterbankRate, float InverseInterbankRate) {
        this.pointInTime = PointInTime;
        this.interbankRate = InterbankRate;
        this.inverseInterbankRate = InverseInterbankRate;
    }
    public HistoricalPoints() {
    }

    public Long getPointInTime() {
        return this.pointInTime;
    }

    public void setPointInTime(Long PointInTime) {
        this.pointInTime = PointInTime;
    }

    public float getInterbankRate() {
        return this.interbankRate;
    }

    public void setInterbankRate(float InterbankRate) {
        this.interbankRate = InterbankRate;
    }

    public float getInverseInterbankRate() {
        return this.inverseInterbankRate;
    }

    public void setInverseInterbankRate(float InverseInterbankRate) {
        this.inverseInterbankRate = InverseInterbankRate;
    }
}
