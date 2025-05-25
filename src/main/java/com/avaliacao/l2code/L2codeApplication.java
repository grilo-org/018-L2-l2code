package com.avaliacao.l2code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class L2codeApplication {

	public static void main(String[] args) {
		SpringApplication.run(L2codeApplication.class, args);
	}

}
