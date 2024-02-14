package com.oracle.oBootDBConnect.comtroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootDBConnect.domain.Member1;
import com.oracle.oBootDBConnect.service.MemberService;

import oracle.net.aso.m;
//controller딴은 view로 다시 보내줌 => 즉 return은 뷰 = html과 연결됨
@Controller
public class MemberController {
//생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌
//객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 함
//이전 에서는 개발자가 직접 주입했고, 여기서는 @Autowired에 의해 스프링이 주입
//  @Component 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.
//    @Controller
//    @Service
//    @Repository
	private final MemberService memberService;
	
	@Autowired//생성자
	public MemberController(MemberService memberService) {
		//컨테이너에 있는 memberService를 꺼내와서
		this.memberService = memberService; //여기다가 주입을 함. 
	}
	
	//디스패쳐(D/S)역할을 Get/Post/RequestMapping이 함 => templet/memberFrom.html
	@GetMapping(value="/members/memberForm")
	public String createMemberForm() {
		System.out.println("맴버컨트롤러 /members/memberForm이 시작되었씁니다.");
		return "members/createMemberForm";
	}
	
	//저장할떄는 post
	@PostMapping("/members/newSave")
	public String memberSave(Member1 member) {
		System.out.println("맴버컨트롤러의 memberSave메서드가 시작되었습니다.");
		System.out.println("member.getName()->"+member.getName());
	    memberService.memberSave(member);
	    
		return "redirect:/";
	}
	
	@GetMapping("/members/memberList")
	public String memberLists(Model model) {
		List<Member1> memberList = memberService.findMembers();
		System.out.println("memberList.size()->"+memberList.size());
		model.addAttribute("members", memberList);
		return "members/memberList";
		
	}
	
}
