package com.oracle.oBootjpa01.repository;

import java.util.List;

import com.oracle.oBootjpa01.domain.Member;

public interface MemberRepository {
	
		Member memberSave(Member member);
		List<Member> finaAllMember();
		List<Member> findByNames(String searchName);

}
