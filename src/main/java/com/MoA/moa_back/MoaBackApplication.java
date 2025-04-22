package com.MoA.moa_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing 
public class MoaBackApplication {

	public static void main(String[] args) {
		// 깃 테스트 테스트 //테스트 // 테스트
		SpringApplication.run(MoaBackApplication.class, args);
	}

}
