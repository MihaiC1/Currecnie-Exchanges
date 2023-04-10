package com.hcl.currencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.hcl")
public class CurrencyExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
		System.out.println("Currency exchange service started");
	}

}
