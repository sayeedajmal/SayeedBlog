package com.strong.blogGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BlogGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogGatewayApplication.class, args);
	}

}
