package com.hcl.currencyexchange.repository;

import com.hcl.currencyexchange.entity.Currencies;
//import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies, Integer> {

    @Query("select c from Currencies c")
    List<Currencies> getAll();

    //Return a list of the available records based on the abbreviation
    @Query("select c from Currencies c where CUR_ID = ?1")
    Currencies getById(int id);

    @Query("select c.CUR_ISO_CODE from Currencies c where CUR_ID = ?1")
    String getISOById(int id);

    @Query("select c.CUR_ID from Currencies c where CUR_ISO_CODE = ?1")
    int getIDByISO(String ISO);



}
