package com.oracle.oBootBoard.command;

import java.util.ArrayList;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;
import com.oracle.oBootBoard.dto.BDto;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;

@Service  //서비스(command) => DAo가 필요함
//BDao는 인터페이스고 그걸 구현한게 jdbcDao임. 
public class BExcuteCommand {
	//BCommand.java와 BListCommand를 합친거.
	
	//Dao만들기
	private final BDao jdbcDao;
	public BExcuteCommand(BDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public void bListCmd(Model model) {
		//Dao연결
		
		ArrayList<BDto> boardDtoList = jdbcDao.boardList();//여기 부분 완성하기
		System.out.println("BListCommand boardDtoList.size();"+boardDtoList.size());
		model.addAttribute("boardList",boardDtoList);
	}

	public void bWriteCmd(Model model) {
		//1) model 이용, map 선언
		//2) request 이용 -> bName, bTitle, bContent 추출
		//3) dao instance 선언
		//4) write method 이용하여 저장(bName, bTitle, bContent)
		Map<String, Object> map = model.asMap();//asMap()은 맵을 꺼낼수 있음
		HttpServletRequest request = 
				(HttpServletRequest) map.get("request");
		//진짜 request객체를 꺼내옴 key였음 앞에서 "request"는 key였음.
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.write(bName, bTitle, bContent);
		
	}

	public void bContentCmd(Model model) {
		//1. model이를 Map으로 전환
		//2. request -> bId get
		//3
		//BDto board = jdbcDao.contentView(bId);
		//model.addAttribute("mvc_board",board);
		
		//1번 Map으로 전환
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = 
				(HttpServletRequest) map.get("request");
		
		//2번 bId get
		String bId = request.getParameter("bId");
		
		BDto board = jdbcDao.contentView(bId);
		System.out.println("bContentCmd board.getbName()->"+board.getbName());
		model.addAttribute("mvc_board",board);
	}

	public void bModifyCmd(Model model) {
		// 1. model Map선언
		// 2. parameter ->  bId, bName , bTitle , bContent
		//jdbcDao.modify(bId, bName, bTitle, bContent);
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request =
				(HttpServletRequest) map.get("request");
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.modify(bId, bName, bTitle, bContent);
		
	}

	public void bReplyViewCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 ->  bid  추출
//		  3) reply_view method 이용하여 (bid)
//		    - BDto dto = dao.reply_view(bId);
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request =
				(HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		//int ibId = request.getParameter((Integer.parseInt(bId)));
		
		BDto dto = jdbcDao.reply_view(bId);
		
		model.addAttribute("reply_view", dto);
	}

	
	public void bReplyCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 -> bid,         bName ,  bTitle,
//		                    bContent ,  bGroup , bStep ,
//		                    bIndent 추출
//		  3) reply method 이용하여 댓글저장 
//		    - dao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
//		    [1] bId SEQUENCE = bGroup 
//		    [2] bName, bTitle, bContent -> request Value
//		    [3] 홍해 기적
//		    [4] bStep / bIndent   + 1
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request =
				(HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bGroup = request.getParameter("bGroup");
		String bStep = request.getParameter("bStep");
		String bIndent = request.getParameter("bIndent");
		
		int bIntGroup = Integer.parseInt(request.getParameter("bGroup"));
		//여기는 이렇게 int로 파싱 한 이유가 뭘까요?
		
		
		System.out.println("BReplyCommand bIntGroup->"+bIntGroup);
		jdbcDao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
	}

	public void bDeleteCmd(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request =
				(HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		
		jdbcDao.delete(bId);
		
	}
}
