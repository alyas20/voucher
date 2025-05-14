package com.test.voucher.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.voucher.entity")
public class VoucherApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
	}

}
