package com.oracle.oBootjpa01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootjpa01.domain.Member;
import com.oracle.oBootjpa01.repository.MemberRepository;


@Service //서비스는 비즈니스 로직을 짜는곳 => Dao에 넘겨주기 위한로직들
@Transactional
public class MemberService {
	
	private final MemberRepository memberRepository;
	@Autowired//final이기 때문에 생성자로 연결을 해줘야지 에러가 사라짐
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	
	//회원가입
	public Long memberSave (Member member) {
		System.out.println("MemberService memberSave member=>"+member);
		memberRepository.memberSave(member);
		System.out.println("MemberService memberSave After..");
		return member.getId();
	}


	public List<Member> getListAllMember() {
		List<Member> listmember = memberRepository.finaAllMember();
		System.out.println("MemberService getListAllMember.size()->"+listmember.size());
		return listmember;
	}


	public List<Member> getListSearchMember(String searchName) {
		System.out.println("MemberService getListSearchMember가 시작되어씁니다.");
		System.out.println("MemberService getListSearchMember searchName->"+searchName);
		List<Member> listMember = memberRepository.findByNames(searchName);
		System.out.println("MemberService getListSearchMember listMember.size()->"+listMember.size());
		
		return listMember;
	}



	
	
	
}
