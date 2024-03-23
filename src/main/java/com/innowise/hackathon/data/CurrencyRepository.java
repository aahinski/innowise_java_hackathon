package com.innowise.hackathon.data;

import com.innowise.hackathon.entity.CurrencyEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CurrencyRepository {

	@Select("select * from currency")
	List<CurrencyEntity> findAll();

	@Insert("INSERT INTO currency (name, price) " +
			"VALUES (#{name}, #{price}) " +
			"ON CONFLICT (name) " +
			"DO UPDATE SET price = #{price}")
	void save(CurrencyEntity currencyEntity);

}
