package com.example.A2MavenTry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class A2MavenTryApplication {

	public static void main(String[] args) {
		SpringApplication.run(A2MavenTryApplication.class, args);
	}

	/*@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.A2MavenTry"))
				.paths(PathSelectors.ant("/api/*"))
				.build();
	}*/


}
