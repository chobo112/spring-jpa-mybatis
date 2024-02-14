package com.oracle.oBootDBConnect.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConnect.domain.Member1;

//@Repository
public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member1> store = new HashMap<>();
	private static long sequence = 0L;
	
	@Override
	public Member1 save(Member1 member1) {
		System.out.println("메모리멤버레파지토리 save가 시작되었습니다.");
		member1.setId(++sequence);
		System.out.println("메모리멤버레파지토리 member1.getId()->"+member1.getId());
		store.put(member1.getId(), member1);
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("메모리맴버레파티뢰 findAll메서드가 시작되었씁니다.");
		List<Member1> listMember = new ArrayList<>(store.values());
		//store.values(); 이거는 map에서 values를 꺼내는 방법
		System.out.println("MemoryMemberRepository findAll slistMember.size()->"+listMember.size());
		return listMember;
		//return new ArrayList<>(store.values());
	}

}
