package com.hcl.currencyexchange;

import com.hcl.currencyexchange.repository.CurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CurrencyValidation {
//    @Autowired
//    static CurrenciesRepository currencyRepositoryObj;
//    public static ResponseEntity<Object> isCurrencyAvailable(String currency){
//        Map<String, Object> response = new HashMap<>();
//        if (currency == null){
//            response.put("Response: ", "No currency inserted.");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//        if (currencyRepositoryObj.getByISO(currency) == null ){
//            response.put("Response: ",currency + " is not available for exchanging! No data displayed!");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//    }

}
