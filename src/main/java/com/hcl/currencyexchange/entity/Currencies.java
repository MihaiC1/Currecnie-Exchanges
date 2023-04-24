package com.hcl.currencyexchange.entity;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currencies {
    @Id
    @Column
    @GeneratedValue
    private int CUR_ID;
    @Column
    private String CUR_NAME; //name of a currency;
    @Column
    private String CUR_ISO_CODE;
    @Column
    private String CUR_CODE_FOR_WEB;

    public Currencies() {
    }

    public Currencies(int CUR_ID, String CUR_NAME, String CUR_ISO_CODE, String CUR_CODE_FOR_WEB) {
        this.CUR_ID = CUR_ID;
        this.CUR_NAME = CUR_NAME;
        this.CUR_ISO_CODE = CUR_ISO_CODE;
        this.CUR_CODE_FOR_WEB = CUR_CODE_FOR_WEB;
    }

    public int getCUR_ID() {
        return CUR_ID;
    }

    public void setCUR_ID(int CUR_ID) {
        this.CUR_ID = CUR_ID;
    }

    public String getCUR_NAME() {
        return CUR_NAME;
    }

    public void setCUR_NAME(String CUR_NAME) {
        this.CUR_NAME = CUR_NAME;
    }

    public String getCUR_ISO_CODE() {
        return CUR_ISO_CODE;
    }

    public void setCUR_ISO_CODE(String CUR_ISO_CODE) {
        this.CUR_ISO_CODE = CUR_ISO_CODE;
    }

    public String getCUR_CODE_FOR_WEB() {
        return CUR_CODE_FOR_WEB;
    }

    public void setCUR_CODE_FOR_WEB(String CUR_CODE_FOR_WEB) {
        this.CUR_CODE_FOR_WEB = CUR_CODE_FOR_WEB;
    }

    @Override
    public String toString() {
        return "Currencies{" +
                "CUR_ID=" + CUR_ID +
                ", CUR_NAME='" + CUR_NAME + '\'' +
                ", CUR_ISO_CODE='" + CUR_ISO_CODE + '\'' +
                ", CUR_CODE_FOR_WEB='" + CUR_CODE_FOR_WEB + '\'' +
                '}';
    }
}
