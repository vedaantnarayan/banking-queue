package com.turvo.bankingqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(value = { "com.turvo.bankingqueue" })
@EnableJpaRepositories("com.turvo.bankingqueue.repository")
@EntityScan("com.turvo.bankingqueue.entity")
public class BankingQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingQueueApplication.class, args);
	}

}
