package com.hcl.currencyexchange.repository;

import com.hcl.currencyexchange.entity.Currencies;
import com.hcl.currencyexchange.entity.Exchanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface CurrenciesRepository extends JpaRepository<Currencies,Integer> {
    @Query("select c from Currencies c where curIsoCode = ?1")
    Currencies getByISO(String ISO);
}
