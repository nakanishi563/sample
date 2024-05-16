package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

/**
 * Bean定義クラス
 * 
 */
@Configuration
public class BeanDefine {
	
	/**
	 * マッピングフレームワークのBean定義を行います。
	 * 
	 * @return マッピングフレームワーク(Dozer)
	 */
	@Bean
	Mapper mapper() {
		return DozerBeanMapperBuilder.buildDefault();
	}
}
