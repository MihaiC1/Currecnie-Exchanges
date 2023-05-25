package com.hcl.currencyexchange.utility;

import com.hcl.currencyexchange.entity.CurrenciesEntity;
import com.hcl.currencyexchange.exception.ParameterCheckException;
import com.hcl.currencyexchange.repository.CurrenciesRepository;
import com.hcl.currencyexchange.repository.ExchangeRepository;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h1>Parameter validation class</h1>
 * This class is used to check if the parameters passed by the user are valid.
 * @author Dumitrascu Mihai - Cosmin
 * @version 1.0
 * @since 2023-05-18
 */
@Service
public class ParameterValidation {

    @Autowired
    CurrenciesRepository currenciesRepositoryObj;

    /**
     * This method is used to check the validity of currencies ISO.
     * @param currency The currency ISO that will be checked.
     * @return CurrenciesEntity A CurrenciesEntity object with ISO code that was checked.
     * @throws ParameterCheckException if the ISO code is not valid or is not found in the database.
     */
    public CurrenciesEntity currencyValidation(String currency) throws ParameterCheckException {

        CurrenciesEntity cur = currenciesRepositoryObj.getByISO(currency);
        if (cur == null){
            throw new ParameterCheckException(currency + " is not available for exchanging! No data displayed!");
        }
        return cur;
    }

    /**
     * This method is used to check if the date is in the correct format (YYYY-MM-DD).
     * @param date The date to be checked.
     * @throws ParameterCheckException if the date is not in the correct format (YYYY-MM-DD).
     */
    public void dateValidation(String date) throws ParameterCheckException{
        if (!(GenericValidator.isDate(date, "yyyy-MM-dd", true)) || date == null){
            throw new ParameterCheckException("The data is not in the correct format. The accepted format is: YYYY-MM-DD! No data updated!");
        }
    }

    /**
     * This method is used to check if the conversion rate is grater than 0.
     * @param rate The value to be checked.
     * @throws ParameterCheckException if the conversion rate is less than 0.
     */
    public void rateValidation(float rate) throws ParameterCheckException{
        if (rate <= 0){
            throw new ParameterCheckException("The rate cannot be less then, or equal to 0!");
        }
    }

}
