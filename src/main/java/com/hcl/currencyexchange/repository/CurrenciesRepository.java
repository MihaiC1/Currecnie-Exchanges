package com.hcl.currencyexchange.repository;

import com.hcl.currencyexchange.entity.CurrenciesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <h1>Currencies Repository interface</h1>
 * This interface is used to make a connection with currency table.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Repository
public interface CurrenciesRepository extends JpaRepository<CurrenciesEntity,Integer> {
    /**
     * This function is used to extract a single object of type CurrenciesEntity. The object will be extracted based on the ISO sent by the user.
     * @param ISO The code of the currency that will be extracted.
     * @return CurrenciesEntity An object of the CurrenciesEntity class.
     */
    @Query("select c from CurrenciesEntity c where curIsoCode = ?1")
    CurrenciesEntity getByISO(String ISO);

}
