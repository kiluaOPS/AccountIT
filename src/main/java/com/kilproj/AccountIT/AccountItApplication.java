package com.kilproj.AccountIT;

import com.kilproj.AccountIT.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class AccountItApplication {

	public static void main(String[] args) {

		SpringApplication.run(AccountItApplication.class, args);
	}

}
