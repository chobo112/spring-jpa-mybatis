package com.oracle.oBootDBConnect;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootDBConnect.repository.JdbcMemberRepository;
import com.oracle.oBootDBConnect.repository.MemberRepository;
import com.oracle.oBootDBConnect.repository.MemoryMemberRepository;

@Configuration // 환경파일 => 모든 컴포넌트중에 제일 먼저 인식하게해줌
public class SpringConfig {
	
	private final DataSource dataSource;
	//@Autowired //안 적어줘도 자동으로 AutoWired해줌
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean //어노테이션으로 @Repository를 맨위에 선언을 하지않았음
	//그래서 bean으로 어노테이션을 해줘야지 레파지토리로 인식을 해줌
	public MemberRepository memberRepository() {
		return new JdbcMemberRepository(dataSource);
		//return new MemoryMemberRepository();//메모리DB라서 DATAsource필요가 없음
	}

}
