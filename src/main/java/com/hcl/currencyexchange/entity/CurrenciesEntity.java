package com.hcl.currencyexchange.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * <h1>Currencies Entity class</h1>
 * This class represents an entity for the currency table from the database.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Entity
@Table(name = "currency")
@Data
public class CurrenciesEntity {
    @Id
    @Column(name = "CUR_ID")
    @GeneratedValue
    private int curID;
    @Column(name = "CUR_NAME")
    private String curName;
    @Column(name = "CUR_ISO_CODE")
    private String curIsoCode;
    @Column(name = "CUR_CODE_FOR_WEB")
    private String curCodeForWeb;

    /**
     * This is the empty constructor of the class.
     */
    public CurrenciesEntity() {

    }

    /**
     * This is the parametrized constructor of the class.
     * @param curID The variable that corresponds to the CUR_ID column from the currency table.
     * @param curName The variable that corresponds to the CUR_NAME column from the currency table.
     * @param curIsoCode The variable that corresponds to the CUR_ISO_CODE column from the currency table.
     * @param curCodeForWeb The variable that corresponds to the CUR_CODE_FOR_WEB column from the currency table.
     */
    public CurrenciesEntity(int curID, String curName, String curIsoCode, String curCodeForWeb) {
        this.curID = curID;
        this.curName = curName;
        this.curIsoCode = curIsoCode;
        this.curCodeForWeb = curCodeForWeb;
    }

//    /**
//     * This is the get method for curID variable.
//     * @return int The curID variable.
//     */
//    public int getCurID() {
//        return curID;
//    }
//
//    /**
//     * This is the set method for the curID variable.
//     * @param curID The value that will be saved into the curID variable.
//     */
//    public void setCurID(int curID) {
//        this.curID = curID;
//    }
//
//    /**
//     * This is the get method for curName variable.
//     * @return String The curName variable.
//     */
//    public String getCurName() {
//        return curName;
//    }
//
//    /**
//     * This is the set method for the curName variable.
//     * @param curName The value that will be saved into the curName variable.
//     */
//    public void setCurName(String curName) {
//        this.curName = curName;
//    }
//
//    /**
//     * This is the get method for the curIsoCode variable.
//     * @return String The curIsoCode variable.
//     */
//    public String getCurIsoCode() {
//        return curIsoCode;
//    }
//
//    /**
//     * This is the set method for the curIsoCode variable.
//     * @param curIsoCode The value that will be saved into the curIsoCode variable.
//     */
//    public void setCurIsoCode(String curIsoCode) {
//        this.curIsoCode = curIsoCode;
//    }
//
//    /**
//     * This is the get method for curCodeForWeb variable.
//     * @return String The curCodeForWeb variable.
//     */
//    public String getCurCodeForWeb() {
//        return curCodeForWeb;
//    }
//
//    /**
//     * This is the set method for the curCodeForWeb variable.
//     * @param curCodeForWeb The value that will be saved into the curCodeForWeb variable.
//     */
//    public void setCurCodeForWeb(String curCodeForWeb) {
//        this.curCodeForWeb = curCodeForWeb;
//    }

}
