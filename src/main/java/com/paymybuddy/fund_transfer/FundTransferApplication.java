package com.paymybuddy.fund_transfer;

import com.paymybuddy.fund_transfer.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class FundTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundTransferApplication.class, args);
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		System.out.println(bCryptPasswordEncoder.encode("password"));
	}
}
