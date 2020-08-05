package com.pnu.spring.smartfactory.DAO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:config/api.properties")
public class AppConfig {
	
	@Value("${kakao.key.rest}")
	String kakao_rest_key;
	
	@Bean
	public String getKakaoRestKey()
	{
		return kakao_rest_key;
	}
}
