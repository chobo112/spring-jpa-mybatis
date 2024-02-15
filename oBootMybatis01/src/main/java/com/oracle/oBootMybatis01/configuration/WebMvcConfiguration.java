package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.oBootMybatis01.service.SampleInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//누군가 URL /interCeptor를 입력하면 -> SampleIntercetpor()처리 해줌
		registry.addInterceptor(new SampleInterceptor()).addPathPatterns("/interCeptor");
														//.addPathPatterns("추가할거있으면 추가해줘")
	}
	
}
