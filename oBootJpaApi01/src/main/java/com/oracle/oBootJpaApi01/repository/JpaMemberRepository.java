package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootJpaApi01.domain.Member;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor //생성자가 필요한 애들(엔티티메니저)만 생성자에 매핑해준다.
//@AllArgsConstructor 모든것들에대해서 생성자를 만들어 준다.

public class JpaMemberRepository implements MemberRepository {
	//final이기때문에 생성자에 반드시 넣자
	//하지만 위에 @RequiredArgsConstructor 이걸 통해서 넣을 수가 있음
	private final EntityManager em;
	
	
	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("select m from Member m", Member.class)
									.getResultList();
		System.out.println("JpaMemberRepository findaAll memberList.szie()->"+memberList.size());
		return memberList;
	}


	@Override
	public Long save(@Valid Member member) {
		System.out.println("JpaMemberRepository save before..");
		em.persist(member);
		return member.getId();
	}


	@Override
	public void updateMember(Member member) {
		int result = 0;
		Member member3 = em.find(Member.class, member.getId());
		//find로 하자 -> find를 하면 영속상태로 들어가게 되고
		//영속성 컨텍스트에 등록되는 숙ㄴ간 => 변경사항은 자동으로 데이터베이스에 반영이 됨
		if(member3 != null) {
			//회원저장 member3이 존재할떄 set하면 업데이트가 된다.
			member3.setName(member.getName());
			member3.setSal(member.getSal());
			result = 1;
			System.out.println("JpaMemberRepository updateByMember update...");
		}	else {
			result = 0;
			System.out.println("JpaMemberRepository updateByMember No Exist");
		}
		//return;
	}


	@Override
	public Member findByMember(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return member;
	}

}
