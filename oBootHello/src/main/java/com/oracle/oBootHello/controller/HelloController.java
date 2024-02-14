package com.oracle.oBootHello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootHello.domain.Emp;

@Controller
public class HelloController {
	
	//로그를 걸때 사용 하는게 로거..
	//로거는 pm.xml에서 다운받고 web
	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	//prefix => templates/
	//suffix -> .html   이 2가지가 RequestMapping("hello")앞뒤로붙음
	//RequestMapping("hello") = templates/hello.html => 이 실행되게 함
	@RequestMapping("hello") // => RequestMapping은 뷰단(html로 보내준다고 생각하자)
	//즉, mvc2에서 => list.do=service.listForm 의 역할을 어노테이션이 하는것
	
	//public String hello(Model model) { 
	//메서드 이름X // RequestMapping("hello")에 맞춰서 들어오는거임
	//model은 뷰로 보내는역할 
	public String hellowowowowow(Model model) {
		System.out.println("hello start");
		logger.info("start"); //로거는 실행하면 밑에 콘솔창이 알록달록 나오는거
		//시스아웃 vs log의 차이점을 한번 봐보자.
		
		
		//Model이 이제 파라미터를 데리고 다니게 된다!! 
		//request써도 되지만 스프링은 model
		//@@@@  D/S가 하는일 : templlates/+ hello + .html@@@
		model.addAttribute("parameter", "스프링부트 시작");
		return "hello";//Mapping을 hello로 했으니까 hello를 리턴하자.
	}
	
	
	@ResponseBody //<--ajax를 하기 위한 어노테이션
	//ResponseBody는 뷰를 거치지 않고 그냥 출력해줌
	//@ResponseBody: 이 어노테이션은 해당 메소드가 HTTP 응답 바디에 직접 데이터를 작성하도록 
	//지정하는 데 사용됩니다. 
	//즉, 이 메소드는 뷰를 거치지 않고 직접 데이터를 클라이언트에게 반환합니다.
	
	@GetMapping("ajaxString")
	//@GetMapping("ajaxString"): 이 어노테이션은 HTTP GET 요청이 
	//"/ajaxString" 경로로 들어왔을 때 해당 메소드가 실행되도록 매핑합니다.
	
	//브라우저에서 ajaxName을 넣으면 받아와서 reuqestParam에 들어가고 String aName이 된다.
	//                      파라미터 필수=ajaxName  
	public String ajaxString (@RequestParam("ajaxName") String aName) {
		System.out.println("HelloController ajaxString aName->"+aName);
		return aName;
	}
	
	@ResponseBody
	@GetMapping("ajaxEmp") //브라우저에서 ajaEmp로 Get요청이 들어오면 실행
	//GET파라미터니까 url에 /ajaxEmp?empno=4&ename=채 파라미터를 넘긴다.
	public Emp ajaxEmp(@RequestParam("empno") String empno,
			@RequestParam("ename") String ename) {
		
		System.out.println("HelloController ajaxEmp empno-> " + empno);
		logger.info("ename -> {}", ename);
		
		//DTO를 활용해서 DTO에 값을 설정해주고
		Emp emp = new Emp();
		emp.setEmpno(empno);
		emp.setEname(ename);
		
		//DTO를 반환해줌
		return emp;
		//반환은 URL로 가줌
		
		//
	}
	

}
