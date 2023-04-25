package com.hcl.currencyexchange.repository;
import ch.qos.logback.core.encoder.EchoEncoder;
import com.hcl.currencyexchange.entity.Exchanges;
import com.hcl.currencyexchange.entity.JoinTable;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface ExchangeRepository extends JpaRepository<Exchanges,Integer> {
    // Method to extract a record by date
    @Query(value = "SELECT new com.hcl.currencyexchange.entity.JoinTable (c.ID, c.date, c.rate,cur2.curIsoCode, cur.curIsoCode) " +
                   "FROM Currencies cur " +
                   "JOIN Exchanges c ON cur.curID = c.curIdFrom " +
                   "JOIN Currencies cur2 ON cur2.curID = c.curIdTo " +
                   "WHERE c.date = ?1 "+
                   "ORDER BY c.ID ASC" )
    List<JoinTable> findByDate(LocalDate date);

    // Methods to extract a record by date and currency
    @Query(value = "SELECT new com.hcl.currencyexchange.entity.JoinTable (c.ID, c.date, c.rate,cur2.curIsoCode, cur.curIsoCode) " +
                   "FROM Currencies cur " +
                   "JOIN Exchanges c ON cur.curID = c.curIdFrom " +
                   "JOIN Currencies cur2 ON cur2.curID = c.curIdTo " +
                   "WHERE c.date = ?1 AND cur.curIsoCode = ?2 "+
                   "ORDER BY c.ID ASC"
                   )
    List<JoinTable> getFromDateAndCurr(LocalDate date, String curr);

    @Query(value = "SELECT new com.hcl.currencyexchange.entity.JoinTable (c.ID, c.date, c.rate,cur2.curIsoCode, cur.curIsoCode) " +
            "FROM Currencies cur " +
            "JOIN Exchanges c ON cur.curID = c.curIdFrom " +
            "JOIN Currencies cur2 ON cur2.curID = c.curIdTo " +
            "WHERE c.date = ?1 AND cur.curIsoCode = ?2 AND cur2.curIsoCode = ?3 " +
            "ORDER BY c.ID ASC")
    JoinTable getFromDateAndCurr(LocalDate date, String curr, String toCurr);

    //Method to extract all the records
    @Query(value = "SELECT new com.hcl.currencyexchange.entity.JoinTable (c.ID, c.date, c.rate, cur2.curIsoCode, cur.curIsoCode)" +
                   "FROM Currencies cur " +
                   "JOIN Exchanges c ON cur.curID = c.curIdFrom " +
                   "JOIN Currencies cur2 ON cur2.curID = c.curIdTo " +
                   "ORDER BY c.ID ASC" )
    List<JoinTable> getAll();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO currency_conversion (CCN_DATE, CCN_CUR_ID_FROM, CCN_CUR_ID_TO, CCN_RATE) " +
            "VALUES (:CCN_DATE, :CCN_CUR_FROM, :CCN_CUR_TO, :CCN_RATE) ", nativeQuery = true)
    void insertExchanges(@Param("CCN_DATE") LocalDate date, @Param("CCN_CUR_FROM") int CCN_CUR_ID_FROM,@Param("CCN_CUR_TO") int CCN_CUR_ID_TO,@Param("CCN_RATE") Float CCN_RATE);

    @Modifying
    @Transactional
    @Query(value = "UPDATE currency_conversion cur " +
            "JOIN currency c ON c.CUR_ID = cur.CCN_CUR_ID_FROM " +
            "JOIN currency c2 ON c2.CUR_ID = cur.CCN_CUR_ID_TO " +
            "SET cur.CCN_RATE = ?1 " +
            "WHERE c.CUR_ISO_CODE = ?3 AND c2.CUR_ISO_CODE = ?4 AND cur.CCN_DATE = ?2", nativeQuery = true)
    void updateRecord(float rate, LocalDate date, String isoCodeFrom, String isoCodeTo);

}
