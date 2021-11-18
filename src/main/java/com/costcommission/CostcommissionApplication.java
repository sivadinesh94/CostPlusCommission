package com.costcommission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2

public class CostcommissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CostcommissionApplication.class, args);
	}

}
