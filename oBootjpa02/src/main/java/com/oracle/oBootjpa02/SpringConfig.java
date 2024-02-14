package com.oracle.oBootjpa02;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootjpa02.repository.JpaMemberRepository;
import com.oracle.oBootjpa02.repository.MemberRepository;
import com.oracle.oBootjpa02.service.MemberService;

import jakarta.persistence.EntityManager;

@Configuration
public class SpringConfig {
	
	private final DataSource dataSource;
	private final EntityManager em;
	
	public SpringConfig(DataSource dataSource, EntityManager em) {
		this.dataSource = dataSource;
		this.em = em;
	}
	
	@Bean //레파지토리 부분 해준것
	public MemberRepository memberRepository() {
		return new JpaMemberRepository(em);
	}
	
	@Bean //서비스부분 @Service대신에 하는거
	public MemberService memberService() {
		return new MemberService(memberRepository());
	}
	
	
}
