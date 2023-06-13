package com.hcl.currencyexchange;

import com.hcl.currencyexchange.bean.HistoricalPointsBean;
import com.hcl.currencyexchange.exception.SendRequestToProviderException;
import com.hcl.currencyexchange.manager.ApiManager;

import com.hcl.currencyexchange.manager.DatabaseManager;
import com.hcl.currencyexchange.utility.ParameterValidation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class CurrencyExchangeApplicationTests {

	@Autowired
	ParameterValidation parameterValidation;

	@Autowired
	DeleteFromDatabase deleteFromDataBase;
	@InjectMocks
	ApiManager apiManager;
//	@InjectMocks
//	DatabaseManager databaseManager;


    @Test
    void sendRequestToProviderTestNull() {

		HistoricalPointsBean expected = apiManager.sendRequestToProvider("2026-04-13","USD","JPY");
		assertNull(expected);
		assertThrows(SendRequestToProviderException.class, () ->apiManager.sendRequestToProvider("2023-04-13","USD","JP"));
		assertThrows(SendRequestToProviderException.class, () ->apiManager.sendRequestToProvider("2023-04-13","D","JPY"));

    }
	@Test
	void sendRequestToProviderTestNotNull() {
		HistoricalPointsBean expected = apiManager.sendRequestToProvider("2023-04-13","USD","GBP");
		assertNotNull(expected);
		assertTrue(expected.getInterbankRate() > 0);
	}

	@Test
	void getFromApiTest(){
		DatabaseManager databaseManager1 = new DatabaseManager();
		deleteFromDataBase.deleteRow("2023-05-13","GBP", "USD");
		ResponseEntity<Object> expected = databaseManager1.getExchangeFromDateFromCurrAtCurr("2023-05-13","GBP", "USD");
		System.out.println("body: " + expected);
	}
}

@Service
class DeleteFromDatabase {
	private final JdbcTemplate jdbcTemplate;

	public DeleteFromDatabase(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public void deleteRow(String date, String curFrom, String curTo){

		int rowsAffected = 0;
		String query = "DELETE cur FROM currency_conversion cur " +
				"JOIN currency c ON c.CUR_ID = cur.CCN_CUR_ID_FROM " +
				"JOIN currency c2 ON c2.CUR_ID = cur.CCN_CUR_ID_TO " +
				"WHERE c.CUR_ISO_CODE = ? AND c2.CUR_ISO_CODE = ? AND cur.CCN_DATE = ?";

		rowsAffected = jdbcTemplate.update(query, curFrom, curTo,date);

		System.out.println("Rows affected: " + rowsAffected);

	}
}
