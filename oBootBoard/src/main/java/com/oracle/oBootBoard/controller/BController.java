package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scripting.bsh.BshScriptUtils.BshExecutionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oracle.oBootBoard.command.BCommand;
import com.oracle.oBootBoard.command.BExcuteCommand;
import com.oracle.oBootBoard.command.BListCommand;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BController {
	
	private static final Logger logger = LoggerFactory.getLogger(BController.class);
	
	//Command = Service임. 컨트롤러에서 서비스를 부르기위해서 사용함
	//BCommand command = null;
	
	private final BExcuteCommand bExcuteService;
	@Autowired
	public BController(BExcuteCommand bExcuteService) {
		this.bExcuteService = bExcuteService;
	}
	
	@RequestMapping("list")
	public String list(Model model) {
		logger.info("list가 시작되었습니다");
		
		//컨트롤러 -> 서비스가기. jsp스럽게 하는중
		//command = new BListCommand();
		//bcommand인터페이스를 BListCommand가 구현한거라서 .execute사용 가능 
		//command.execute(model);
		
		//전통적인거에서 다시 부트 스럽게 해보자.
		bExcuteService.bListCmd(model);
		/*
		 * 위에서 의존성주입을 하고 컨테이너에 들어가고 list메서드 안에서는 그 컨테이너 안에 있던 bExecuteService를 꺼내서 사용한다는거지?

		ChatGPT
		맞습니다. 의존성 주입을 통해 BExcuteCommand 타입의 빈 객체(bExcuteService)를 스프링 컨테이너에 등록하고, 
		BController가 생성될 때 스프링 컨테이너가 해당 빈을 주입해줍니다. 
		그 후 list 메서드 내에서는 이 주입된 bExcuteService를 사용할 수 있습니다.
		 * */
		
		
		return "list"; //뷰로 보내줌 뷰 리절브의 역할(핸들러맵핑을 view단에서 하는 애) => WEB-INT/view/ list .html을 붙여줌
		//model안했는데 가는 이유?? => Command(service)에서 객체인 model
		
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		logger.info("write_view가 시작되었습니다.");
		
		return "write_view";
	}
	
	@PostMapping(value="/write")
	//write_view.jsp에서 요청들이 request에서 받아서 이리로 오게됨
	public String write(HttpServletRequest request, Model model) {
		logger.info("write메서드가 시작되었습니다");
		
		//"request"는 텍스트로 문자열을 받아오고, reqiest객체를 통으로 넣음
		//model은 k-v 쌍으로 저장함
		model.addAttribute("request", request);
		bExcuteService.bWriteCmd(model);//파라미터를 통합하는 부분
		//계속 모델로 받는이유 => 나중에 인터페이스로 뽑을 수가 있따.??
		
		
		return "redirect:list";
	}
	
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
		bExcuteService.bContentCmd(model);
		//bContentCmd여기 빨간불 메서드 만들어야겠다
		//메서드 => Dao로 만들겠지만
		//BDao인터페이스를 상속받은 JdbcDao에 만들겟구나 
				
		return "content_view";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		logger.info("모디파이가 시작되었습니다");
		model.addAttribute("request",request);
		bExcuteService.bModifyCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply view가 실행되었습니다.");
		
		model.addAttribute("request", request);
		bExcuteService.bReplyViewCmd(model);
		
		return "reply_view";
	}
	//얘는 새로운 페이지를 띄워주는 애들임. 답글쓰기를 하면..reply_view가 나오는 애들임
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(HttpServletRequest request, Model model) {
		logger.info("리플라이가 시작되었습니다");
		
		model.addAttribute("request",request);
		//bExcuteService.bReplyCmd(model);
		bExcuteService.bReplyCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest request, Model model) {
		logger.info("리플라이가 시작되었습니다");
		
		model.addAttribute("request",request);
		//bExcuteService.bReplyCmd(model);
		bExcuteService.bDeleteCmd(model);
		
		return "redirect:list";
	}
	
}
