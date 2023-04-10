package com.hcl.currencyexchange.entity;

import javax.persistence.*;

@Entity
@Table(name = "currencies")
public class Currencies {
    @Id
    @Column
    @GeneratedValue
    private int id;
    @Column
    private String abbreviation; //short name of a currency; ISO currencie codes

    public Currencies() {
    }

    public Currencies(int id, String abbreviation) {
        this.id = id;
        this.abbreviation = abbreviation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "Currencies{" +
                "id=" + id +
                ", abbreviation='" + abbreviation + '\'' +
                "}\n";
    }
}
