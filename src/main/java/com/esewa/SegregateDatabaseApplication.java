package com.esewa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.esewa.repository")

public class SegregateDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegregateDatabaseApplication.class, args);
	}

}
