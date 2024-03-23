package com.innowise.hackathon;

import com.innowise.hackathon.entity.CurrencyEntity;
import com.innowise.hackathon.entity.UserEntity;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.innowise.hackathon.data")
@MappedTypes({UserEntity.class, CurrencyEntity.class})
public class HackathonApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackathonApplication.class, args);
	}

}
