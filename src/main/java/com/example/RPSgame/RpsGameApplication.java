package com.example.RPSgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RpsGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpsGameApplication.class, args);
	}

}
