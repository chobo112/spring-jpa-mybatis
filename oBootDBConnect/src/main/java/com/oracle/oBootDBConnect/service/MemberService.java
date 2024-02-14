package com.oracle.oBootDBConnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootDBConnect.domain.Member1;
import com.oracle.oBootDBConnect.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	//회원가입
	public Long memberSave(Member1 member) {
		memberRepository.save(member);
		return member.getId();
	}
	
	
	//전체회원조회
	public List<Member1> findMembers() {
		System.out.println("맴버서비스 findMembers메서드가 시작되었습니다.");
		List<Member1> listMembers = memberRepository.findAll();
		System.out.println("listMembers.size()->"+listMembers.size());
		return listMembers;
	}
	
}
