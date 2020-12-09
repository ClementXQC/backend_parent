package com.meetingfilm.cinema;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.meetingfilm.cinema.dao"})
@ComponentScan(basePackages = {"com.meetingfilm"})
public class BackendCinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCinemaApplication.class, args);
    }

}