package com.oracle.oBootBoard.command;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;
import com.oracle.oBootBoard.dto.BDto;

//command가 service임 서비스에서는 DAO와 연결됨
public class BListCommand implements BCommand {
	
	DataSource dataSource;
	
	@Override
	public void execute(Model model) {
		//Dao연결 서비스는 다오와 연결되니까 dao와 연결
		//BDao는 인터페이스라서 구현체인 JdbcDao를 상속받자.
		//dataSource가 들어가는 이유는 아직 모르겠음.
		//JdbcDao에는 데이터리소스가 있어서 여기는 선언만 해주고 넣어준거임
		BDao dao = new JdbcDao(dataSource);
		ArrayList<BDto> boardDtoList = dao.boardList();//여기 부분 완성하기
		System.out.println("BListCommand boardDtoList.size();"+boardDtoList.size());
		model.addAttribute("boardList",boardDtoList);
		//내장객체로 model로 boardList라는 변수에 ArrayList를 넣어줌
	}

}
