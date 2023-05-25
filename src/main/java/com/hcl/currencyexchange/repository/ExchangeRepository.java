package com.hcl.currencyexchange.repository;

import com.hcl.currencyexchange.entity.ExchangesEntity;
import com.hcl.currencyexchange.bean.JoinTableBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <h1>Exchange Repository interface</h1>
 * This interface is used to make a connection with currency_conversion table.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Transactional
@Repository
public interface ExchangeRepository extends JpaRepository<ExchangesEntity,Integer> {
    /**
     * This method is used to extract a list of JoinTableBean objects, using MySQL query, from the table resulting from joining the currency and currency_conversion tables.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @return List<JoinTableBean> a list with all the records found in the table.
     */
    @Query(value = "SELECT new com.hcl.currencyexchange.bean.JoinTableBean ( c.date, c.rate,cur2.curIsoCode, cur.curIsoCode, c.insertTime) " +
                   "FROM CurrenciesEntity cur " +
                   "JOIN ExchangesEntity c ON cur.curID = c.curIdFrom " +
                   "JOIN CurrenciesEntity cur2 ON cur2.curID = c.curIdTo " +
                   "WHERE c.date = ?1 "+
                   "ORDER BY c.id ASC")
    List<JoinTableBean> findByDate(LocalDate date);

    /**
     * This method is used to extract a list of JoinTableBean objects, using MySQL query, from the table resulting from joining the currency and currency_conversion tables.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curr The currency ISO used to filter all the records  (ex. USD, EUR, etc).
     * @return List<JoinTableBean> a list with all the records found in the table after filtering.
     */
    @Query(value = "SELECT new com.hcl.currencyexchange.bean.JoinTableBean (c.date, c.rate,cur2.curIsoCode, cur.curIsoCode, c.insertTime) " +
                   "FROM CurrenciesEntity cur " +
                   "JOIN ExchangesEntity c ON cur.curID = c.curIdFrom " +
                   "JOIN CurrenciesEntity cur2 ON cur2.curID = c.curIdTo " +
                   "WHERE c.date = ?1 AND cur.curIsoCode = ?2 "+
                   "ORDER BY c.id ASC")
    List<JoinTableBean> getFromDateAndCurr(LocalDate date, String curr);

    /**
     * This method is used to extract a list of JoinTableBean objects, using MySQL query, from the table resulting from joining the currency and currency_conversion tables.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param curr The currency ISO used to filter all the records  (ex. USD, EUR, etc).
     * @param toCurr The currency ISO used to filter all the records  (ex. USD, EUR, etc).
     * @param page Used to display only the first record found.
     * @return List<JoinTableBean> a list with all the records found in the table after filtering.
     */
    @Query(value = "SELECT new com.hcl.currencyexchange.bean.JoinTableBean ( c.date, c.rate,cur2.curIsoCode, cur.curIsoCode, c.insertTime) " +
            "FROM CurrenciesEntity cur " +
            "JOIN ExchangesEntity c ON cur.curID = c.curIdFrom " +
            "JOIN CurrenciesEntity cur2 ON cur2.curID = c.curIdTo " +
            "WHERE c.date = ?1 AND cur.curIsoCode = ?2 AND cur2.curIsoCode = ?3 " +
            "ORDER BY c.id DESC ")
    List<JoinTableBean> getFromDateAndCurr(LocalDate date, String curr, String toCurr, Pageable page);

    /**
     * This method is used to extract a list of JoinTableBean objects, using MySQL query, from the table resulting from joining the currency and currency_conversion tables.
     * @return List<JoinTableBean> a list with all the records found.
     */
    @Query(value = "SELECT new com.hcl.currencyexchange.bean.JoinTableBean ( c.date, c.rate, cur2.curIsoCode, cur.curIsoCode, c.insertTime)" +
                   "FROM CurrenciesEntity cur " +
                   "JOIN ExchangesEntity c ON cur.curID = c.curIdFrom " +
                   "JOIN CurrenciesEntity cur2 ON cur2.curID = c.curIdTo " +
                   "ORDER BY c.id ASC")
    List<JoinTableBean> getAll();

    /**
     * This method is used to insert records in the currency_conversion table.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param CCN_CUR_ID_FROM The ID of the currency that will be converted.
     * @param CCN_CUR_ID_TO The ID of the currency in which the CCN_CUR_ID_FROM will be converted.
     * @param CCN_RATE The conversion rate between specified currencies.
     * @param insertTime The date and time when the record was inserted.
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO currency_conversion (CCN_DATE, CCN_CUR_ID_FROM, CCN_CUR_ID_TO, CCN_RATE, CCN_INSERT_TIME) " +
            "VALUES (:CCN_DATE, :CCN_CUR_FROM, :CCN_CUR_TO, :CCN_RATE, :CCN_INSERT_TIME) ", nativeQuery = true)
    void insertExchanges(@Param("CCN_DATE") LocalDate date,
                         @Param("CCN_CUR_FROM") int CCN_CUR_ID_FROM,
                         @Param("CCN_CUR_TO") int CCN_CUR_ID_TO,
                         @Param("CCN_RATE") float CCN_RATE,
                         @Param("CCN_INSERT_TIME")LocalDateTime insertTime);

    /**
     * This method is used to update the rate of a specific record from the currency_conversion table.
     * @param rate The updated conversion rate between specified currencies.
     * @param date The date of the transaction. Date format: YYYY-MM-DD.
     * @param isoCodeFrom The currency ISO to be converted (ex. USD, EUR, etc).
     * @param isoCodeTo The currency ISO to convert into (ex. USD, EUR, etc).
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE currency_conversion cur " +
            "JOIN currency c ON c.CUR_ID = cur.CCN_CUR_ID_FROM " +
            "JOIN currency c2 ON c2.CUR_ID = cur.CCN_CUR_ID_TO " +
            "SET cur.CCN_RATE = ?1 " +
            "WHERE c.CUR_ISO_CODE = ?3 AND c2.CUR_ISO_CODE = ?4 AND cur.CCN_DATE = ?2", nativeQuery = true)
    void updateRecord(float rate, LocalDate date, String isoCodeFrom, String isoCodeTo);



}
