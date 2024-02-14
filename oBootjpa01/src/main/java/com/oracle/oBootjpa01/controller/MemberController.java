package com.oracle.oBootjpa01.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootjpa01.domain.Member;
import com.oracle.oBootjpa01.service.MemberService;


@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private final MemberService memberService;
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	//매핑은 서비스와 오토와이어드로 연결되어있고, 거기서 비즈니스로직을 처리한다음에
	//view딴인 html로 viewResolve를 통해서 html로 return을 해줌
	@GetMapping(value="/members/new")
	public String createForm() {
		System.out.println("MemberController /members/new start..");
		return "members/createMemberForm";
		//default면 templet으로 가게됨. 즉,member폴더를 만들겠고, 그안에 폼이 들어가는구나
	}
	
	
	@PostMapping(value="members/save")
	public String memberSave(Member member) {
		System.out.println("MemberController memberSave start..");
		System.out.println("member.getId()->"+member.getId());
		System.out.println("MemberController memberSave start..");
		memberService.memberSave(member);
		System.out.println("MemberController memberSave After..");
		
		return "redirect:/";
	}
	
	@GetMapping(value="/members")
	public String listMember(Model model) {
		List<Member> memberList = memberService.getListAllMember();
		logger.info("memberList.size->{}", memberList.size());
		model.addAttribute("members", memberList);
		return "members/memberList";
	}
	
	@PostMapping(value="members/search")
	public String memberSave(Member member, Model model) {
		System.out.println("members/search member.getName()->"+member.getName());
		List<Member> memberList = memberService.getListSearchMember(member.getName());
		System.out.println("/members/search memberList.size()->"+memberList.size());
		model.addAttribute("members", memberList);
		
		return "/members/memberList";
	}
	
}
