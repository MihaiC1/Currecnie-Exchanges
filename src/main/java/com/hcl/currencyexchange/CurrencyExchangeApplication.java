package com.hcl.currencyexchange;

//import com.hcl.currencyexchange.restController.Controller;
import com.hcl.currencyexchange.manager.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.hcl.currencyexchange")
public class CurrencyExchangeApplication {

	private static final Logger LOG = LogManager.getLogger(DatabaseManager.class);
	public static void main(String[] args) {

		SpringApplication.run(CurrencyExchangeApplication.class, args);
		LOG.info("Currency exchange service started");

	}

}
