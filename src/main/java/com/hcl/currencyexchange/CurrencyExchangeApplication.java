package com.hcl.currencyexchange;

import com.hcl.currencyexchange.restController.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.hcl")
public class CurrencyExchangeApplication {

	private static final Logger LOG = LogManager.getLogger(Controller.class);
	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
		LOG.info("Currency exchange service started");
	}

}
