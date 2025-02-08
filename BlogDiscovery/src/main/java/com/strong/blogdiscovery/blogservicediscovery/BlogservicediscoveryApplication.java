package com.strong.blogdiscovery.blogservicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BlogservicediscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogservicediscoveryApplication.class, args);
    }

}
