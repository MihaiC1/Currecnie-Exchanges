package com.hcl.currencyexchange.entity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "currency_conversion")
public class Exchanges {
    @Id
    private int CCN_ID; // Unique identifier for every record
    @Column
    private Date CCN_DATE = Date.from(Instant.now()); // The date when the transaction was made
    @Column
    private int CCN_CUR_ID_FROM; //The ID for Currency to be converted
    @Column
    private int CCN_CUR_ID_TO; //The ID for Currency to convert to

    @Column
    private float CCN_RATE; // Exchanging rate

    public Exchanges() {
    }

    public Exchanges(int CCN_ID, Date CCN_DATE, int CCN_CUR_ID_FROM, int CCN_CUR_ID_TO, float CCN_RATE) {
        this.CCN_ID = CCN_ID;
        this.CCN_DATE = CCN_DATE;
        this.CCN_CUR_ID_FROM = CCN_CUR_ID_FROM;
        this.CCN_CUR_ID_TO = CCN_CUR_ID_TO;
        this.CCN_RATE = CCN_RATE;
    }

    public Exchanges(int CCN_CUR_ID_FROM, int CCN_CUR_ID_TO) {

        this.CCN_CUR_ID_FROM = CCN_CUR_ID_FROM;
        this.CCN_CUR_ID_TO = CCN_CUR_ID_TO;

    }

    public int getCCN_ID() {
        return CCN_ID;
    }

    public void setCCN_ID(int CCN_ID) {
        this.CCN_ID = CCN_ID;
    }

    public Date getCCN_DATE() {
        return CCN_DATE;
    }

    public void setCCN_DATE(Date CCN_DATE) {
        this.CCN_DATE = CCN_DATE;
    }

    public int getCCN_CUR_ID_FROM() {
        return CCN_CUR_ID_FROM;
    }

    public void setCCN_CUR_ID_FROM(int CCN_CUR_ID_FROM) {
        this.CCN_CUR_ID_FROM = CCN_CUR_ID_FROM;
    }

    public int getCCN_CUR_ID_TO() {
        return CCN_CUR_ID_TO;
    }

    public void setCCN_CUR_ID_TO(int CCN_CUR_ID_TO) {
        this.CCN_CUR_ID_TO = CCN_CUR_ID_TO;
    }

    public float getCCN_RATE() {
        return CCN_RATE;
    }

    public void setCCN_RATE(float CCN_RATE) {
        this.CCN_RATE = CCN_RATE;
    }

    @Override
    public String toString() {
        return "Exchanges{" +
                "CCN_ID=" + CCN_ID +
                ", CCN_DATE=" + CCN_DATE +
                ", CCN_CUR_ID_FROM=" + CCN_CUR_ID_FROM +
                ", CCN_CUR_ID_TO=" + CCN_CUR_ID_TO +
                ", CCN_RATE=" + CCN_RATE +
                '}';
    }
}
