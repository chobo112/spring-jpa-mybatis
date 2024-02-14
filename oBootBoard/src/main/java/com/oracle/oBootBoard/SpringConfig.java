package com.oracle.oBootBoard;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;

@Configuration // 환경파일 => 모든 컴포넌트중에 제일 먼저 인식하게해줌
public class SpringConfig {
	
	private final DataSource dataSource;
	//@Autowired //안 적어줘도 자동으로 AutoWired해줌
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	public BDao jdbcDao() {
		return new JdbcDao(dataSource);
		//return new MemoryMemberRepository();
	}
	
	

}
