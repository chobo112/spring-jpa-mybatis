package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepository {

	private final EntityManager em;
	
	@Override
	public Member save(Member member) {
		System.out.println("맴버에지피에이레파지토리이impl 저장이 시작되었습니다.");
		em.persist(member); //통째로 이렇게 되면 그냥 insert쿼리가 나간다.
		return member;
	}

	@Override
	public List<Member> findAll() {
		System.out.println("MemberJpaRepositoryImpl findAll start");
		List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
		return memberList;
	}

	public Optional<Member> findById(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return Optional.ofNullable(member);
	}

	public void updateByMember(Member member) {
		System.out.println("MemberJpaRepositoryImpl updateByMember meber"+member);
		
		//merge -> 현재 setting된 것만 수정, 나머지는 null로 되어버림
		//em.merge(member);
		
		//따라서 불러온다음에 setter를 가지고 내가 원하는 값들만 수정을 해주자..
		Member member3 = em.find(Member.class, member.getId());
		member3.setId(member.getId());
		member3.setName(member.getName());
		
		return;
		
	}

}
