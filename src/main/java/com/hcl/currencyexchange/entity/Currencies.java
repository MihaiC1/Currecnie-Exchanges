package com.hcl.currencyexchange.entity;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currencies {
    @Id
    @Column(name = "CUR_ID")
    @GeneratedValue
    private int curID;
    @Column(name = "CUR_NAME")
    private String curName; //name of a currency;
    @Column(name = "CUR_ISO_CODE")
    private String curIsoCode;
    @Column(name = "CUR_CODE_FOR_WEB")
    private String curCodeForWeb;

    public Currencies() {
    }

    public Currencies(int curID, String curName, String curIsoCode, String curCodeForWeb) {
        this.curID = curID;
        this.curName = curName;
        this.curIsoCode = curIsoCode;
        this.curCodeForWeb = curCodeForWeb;
    }

    public int getCurID() {
        return curID;
    }

    public void setCurID(int curID) {
        this.curID = curID;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurIsoCode() {
        return curIsoCode;
    }

    public void setCurIsoCode(String curIsoCode) {
        this.curIsoCode = curIsoCode;
    }

    public String getCurCodeForWeb() {
        return curCodeForWeb;
    }

    public void setCurCodeForWeb(String curCodeForWeb) {
        this.curCodeForWeb = curCodeForWeb;
    }

    @Override
    public String toString() {
        return "Currencies{" +
                "CUR_ID=" + curID +
                ", CUR_NAME='" + curName + '\'' +
                ", CUR_ISO_CODE='" + curIsoCode + '\'' +
                ", CUR_CODE_FOR_WEB='" + curCodeForWeb + '\'' +
                '}';
    }
}
