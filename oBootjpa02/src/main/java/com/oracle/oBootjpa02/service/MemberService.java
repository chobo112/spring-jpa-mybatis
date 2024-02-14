package com.oracle.oBootjpa02.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootjpa02.domain.Member;
import com.oracle.oBootjpa02.repository.MemberRepository;

//JPA  --> 서비스 계층에 트랜잭션 추가
//스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고,
//메서드가 정상 종료되면 트랜잭션을 커밋. 만약 런타임 예외가 발생하면 롤백.
//JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행
@Transactional
//1개의 DB로 여러명이 접속을 하기때문에 누가 어떤걸 쓰는지를 못하니까
//Transactional어노테이션으로 처리순서를 맞춰준 느낌. -> 이론적 설명 isolation level(격리수준)

public class MemberService {
	
	private final MemberRepository memberRepository;
	//@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	//회원가입
	public Member join(Member member) {
		System.out.println("MemberService join member.getName()->"+member.getName());
		memberRepository.save(member);
		return member;
	}
	
	//전체회원조회
	public List<Member> getListAllMember() {
		List<Member> listMember = memberRepository.findAll();
		System.out.println("맴버서비스 getListAllMember listMember.size()->"+listMember.size());
		return listMember;
	}

	
	public List<Member> getListSearchMember(String searchName) {
		System.out.println("MemberService getListSearchMember start..");
		List<Member> listMember = memberRepository.findByNames(searchName);
		System.out.println("맴버서비스 getListAllMember listMember.size()->"+listMember.size());
		return listMember;
	}

	public Optional<Member> findByMember(Long id) {
		Optional<Member> optionalMember = memberRepository.findByMember(id);
		System.out.println("MemberService findByMember member"+optionalMember);
		
		return optionalMember;
	}

	public void memberUpdate(Member member) {
		System.out.println("맴버서비딴에서 멤버리파지토리에서 맴버를 불러오기 전.. ");
		memberRepository.updateByMember(member);
		System.out.println("memberService memberUpdate member->"+member);
		//updateByMember를 맴버레파지토리로 가서 실행을 함..
		return;
		
		//return을 받는 순간에 @Transactional이 붙어있는 서비스단에서 처리가 된다..
	}

}
