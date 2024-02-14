package com.oracle.oBootMybatis01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.repository.MemberJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional//jpa는 트렌지셔널을 해주자
public class MemberJpaService {
	
	private final MemberJpaRepository memberJpaRepository;
	
	//회원가입
	public Long join(Member member) {
		System.out.println("MemberJpaService join member.getId->"+member.getId());
		memberJpaRepository.save(member);
		return member.getId();
	}
	
	//전체회원조회
	public List<Member> getListAllMember() {
		List<Member> listMember = memberJpaRepository.findAll();
		System.out.println("MemberService getListAllMember listMember.size()->"+listMember.size());
		return listMember;
	}

	public Optional<Member> findById(Long memberId) {
		System.out.println("MemberJpaService finById start");
		Optional<Member> member = memberJpaRepository.findById(memberId);
		return member;
	
	}

	public void memberUpdate(Member member) {
		System.out.println("MemberJpaService memberUpdate member.getName()->"+member.getName());
		System.out.println("MemberJpaService memberUpdate member.getId()->"+member.getId());
		memberJpaRepository.updateByMember(member);
		return;
		
	}
	

}
