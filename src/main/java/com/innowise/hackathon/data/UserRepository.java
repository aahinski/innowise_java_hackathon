package com.innowise.hackathon.data;

import com.innowise.hackathon.entity.CurrencyEntity;
import com.innowise.hackathon.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UserRepository {

	@Select("SELECT ui.id, ui.chat_id, ui.change_rate_trigger " +
			"FROM user_info ui " +
			"JOIN user_currency uc ON ui.id = uc.user_id " +
			"WHERE uc.currency_name = #{currencyName} " +
			"AND ui.change_rate_trigger = #{changeRateName}")
	List<UserEntity> findAllByCurrencyAndChangeRate(String currencyName, String changeRateName);

	@Select("SELECT ui.id, ui.chat_id, ui.change_rate_trigger " +
			"FROM user_info ui " +
			"JOIN user_currency uc ON ui.id = uc.user_id")
	List<UserEntity> findAll();

	@Insert("INSERT INTO user_info (id, chat_id, change_rate_trigger) " +
			"VALUES (#{id}, #{chatId}, #{changeRateTrigger})")
	void saveUser(UserEntity user);

	@Insert("<script>" +
			"INSERT INTO user_currency (user_id, currency_name) " +
			"VALUES " +
			"<foreach item='currency' collection='currencies' separator=','>" +
			"(#{id}, #{currency.name})" +
			"</foreach>" +
			"</script>")
	void saveUserCurrencies(@Param("id") UUID userId, @Param("currencies") List<CurrencyEntity> currencies);

}
