package com.paymybuddy.fund_transfer;

import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
//@ComponentScan(basePackages = {"com.paymybuddy.fund_transfer.Services"})
public class FundTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundTransferApplication.class, args);
	}
}
