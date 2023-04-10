package com.hcl.currencyexchange.repository;
import ch.qos.logback.core.encoder.EchoEncoder;
import com.hcl.currencyexchange.entity.Exchanges;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchanges,Integer> {
    // Method to extract a record by date
    @Query(value = "select e from Exchanges e where date=?1")
    List<Exchanges> findByDate(LocalDate date);

    // Methods to extract a record by date and currency
    @Query(value = "select e from Exchanges e where date =?1 and from_Currency = ?2")
    List<Exchanges> getFromDateAndCurr(LocalDate date, String curr);

    @Query(value = "select e from Exchanges e where date =?1 and from_Currency = ?2 and to_Currency =?3")
    List<Exchanges> getFromDateAndCurr(LocalDate date, String curr, String toCurr);

    //Method to extract all the records
    @Query(value = "select e from Exchanges e")
    List<Exchanges> getAll();



}
