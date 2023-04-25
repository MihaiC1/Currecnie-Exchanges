package com.hcl.currencyexchange.repository;

import com.hcl.currencyexchange.entity.Currencies;
//import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies, Integer> {

    @Query("select c from Currencies c")
    List<Currencies> getAll();

    @Query("select c.curID from Currencies c where curIsoCode = ?1")
    int getIDByISO(String ISO);
    @Query("select c from Currencies c where curIsoCode = ?1")
    Currencies getByISO(String ISO);



}
