package com.hcl.currencyexchange.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>Historical Points Bean class</h1>
 * This class is used to store all the numerical data for the exchanges retrieved from the API.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Getter
@Setter
public class HistoricalPointsBean extends DataBean {
    @JsonProperty("PointInTime")
    private Long pointInTime;
    @JsonProperty("InterbankRate")
    private float interbankRate;
    @JsonProperty("InverseInterbankRate")
    private float inverseInterbankRate;

    /**
     * This is the parametrized constructor of the class.
     * @param PointInTime The timestamp variable which represents the exact time when the record was inserted into the APIs database.
     * @param interbankRate The exchange rate between the specified currencies.
     * @param inverseInterbankRate The inverse exchange rate between the specified currencies.
     */
    public HistoricalPointsBean(Long PointInTime, float interbankRate, float inverseInterbankRate) {
        this.pointInTime = PointInTime;
        this.interbankRate = interbankRate;
        this.inverseInterbankRate = inverseInterbankRate;
    }

    /**
     * This is the empty constructor.
     */
    public HistoricalPointsBean() {
    }

//    /**
//     * This is the get method for the pointInTime variable.
//     * @return Long the pointInTime variable.
//     */
//    public Long getPointInTime() {
//        return this.pointInTime;
//    }
//
//    /**
//     * This is the set method for the pointInTime variable.
//     * @param pointInTime The value that will be saved into the pointInTime variable.
//     */
//    public void setPointInTime(Long pointInTime) {
//        this.pointInTime = pointInTime;
//    }
//
//    /**
//     * This is the get method for the interbankRate variable.
//     * @return float the interbankRate variable.
//     */
//    public float getInterbankRate() {
//        return this.interbankRate;
//    }
//
//    /**
//     * This is the set method for the interbankRate variable.
//     * @param interbankRate The value that will be saved into the interbankRate variable.
//     */
//    public void setInterbankRate(float interbankRate) {
//        this.interbankRate = interbankRate;
//    }
//
//    /**
//     * This is the get method for the inverseInterbankRate variable.
//     * @return float the inverseInterbankRate variable.
//     */
//    public float getInverseInterbankRate() {
//        return this.inverseInterbankRate;
//    }
//
//    /**
//     * This is the set method for the inverseInterbankRate variable.
//     * @param inverseInterbankRate The value that will be saved into the inverseInterbankRate variable.
//     */
//    public void setInverseInterbankRate(float inverseInterbankRate) {
//        this.inverseInterbankRate = inverseInterbankRate;
//    }
}
