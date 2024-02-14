package com.oracle.oBootHello.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootHello.domain.Member1;
import com.oracle.oBootHello.service.MemberService;



//컨트롤러의 역할 : 컨트롤러는 클라이언트로부터의 요청을 받아서 처리하고, 
//그 결과를 응답으로 반환하는 역할을 합니다. 주로 웹 애플리케이션에서 사용되며,
//HTTP 요청을 처리하여 비즈니스 로직이나 서비스를 호출하고, 
//그 결과를 적절한 응답 형태로 가공하여 반환합니다.

//처음실행하면 index로 실행됨 -> 이벤트하면 컨트롤러로 오게되고
//해당하는 Mapping을 해줌
@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(MemberController.class);
	
	
	//전통적
	//비즈니스로직을 담고있는(service나 도메인을 불러옴)
	//MemberService memberService = new MemberService();
	
	private final MemberService memberService;
	//컴포넌트에 들어가있기때문에 생성자의 파라미터로 사용을 할 수 있음.
	//컴포넌트란 ? 클래스가 인스턴스화 되어서 톰캣컨테이너에 등록되어 있는 상태..
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	//회원가입을 index에서 누르면 이리로 오게 됨
	@GetMapping(value="members/memberForm")
	public String memberForm() {
		System.out.println("MemberController /members/memberForm 시작되었습니다.");
		//D/S  -> V/R --> templates/ + members/memberForm + .html
		return "members/memberForm";
		//members는 뷰단에서는 폴더 // 자바단에서는 패키지임
	}
	
	//save를 하는 순간 => MemoryMember에 저장이 되어있음.
	@PostMapping(value = "/members/save")
	public String save(Member1 member1) { //자바빈처럼 DTO를 여기서 씀
		System.out.println("MemberContorller /members/save가 시작되었습니다.");
		System.out.println("MemberContorller /members/save member1.getName()->"+member1.getName());
		Long id = memberService.memberSave(member1);
		System.out.println("MemberController /members/save id->"+id);
		return "redirect:/";
	}	
	
	//localhost:8381에서 회원목록을 누르면 이리로 오게됨
	@GetMapping(value="/members/memberList")
	public String memberList(Model model) {
		logger.info("memberList가 시작되었습니다.");
		List<Member1> memberLists = memberService.allMembers();
		model.addAttribute("memberLists", memberLists);
		logger.info("memberLists.size() -> {}", memberLists.size());
	
		return "members/memberList"; //뷰단으로 돌아가는거임.
	}
	
	//getMapping은 조회할떄 postMapping은 저장할떄 사용함...!!!
	
	


}
