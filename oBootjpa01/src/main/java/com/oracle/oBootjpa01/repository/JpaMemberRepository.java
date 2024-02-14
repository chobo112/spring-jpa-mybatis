package com.oracle.oBootjpa01.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootjpa01.domain.Member;

import jakarta.persistence.EntityManager;

@Repository
public class JpaMemberRepository implements MemberRepository {
	
	//JPA에서 DML작업을 할떄는 !!! 반드시 ENtityManager객체 필수!!
	private final EntityManager em;
	@Autowired //생성자 DI에 의해서 의존성을 주입하자.
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Member memberSave(Member member) {
		//JPA는 persist로 저장을 해주는 메서드임
		//JDBC방식은 insert into Member ~뭐시기를 자동으로 해주는게 persist
		em.persist(member); 
		System.out.println("JpaMemberRepository memberSave member after..");
		return member;
	}

	@Override
	public List<Member> finaAllMember() {
		//Member.class 에서 Member는 객체 Member를 말함 => domain에 나오는 @Entity객체인 Member를 말한다.
		//JPA의 JPQL(쿼리)문법임 from절에 나오는건 객체를 적어줌 = Member
		//. 돌려주는 select m =>으로 return되는게 Member.class를 말한다.
		//"select m from Member m"여기서 객체타입으로 돌려줌 => Member.class로 만들어준다.
		//createQUery()의 결과를 앞에 List<Member>와 맞춰줘야함 => 그걸 .getResultList()로 바꿔주는거임
		//데이터타입은 List형 이여야 한다. 그래서 마지막에.geResultList();로 해줌.
		List<Member> memberList = em.createQuery("select m from Member m",Member.class)
									.getResultList()
									;
		System.out.println("JpaMemberRepository findAllMember memberList.size()=>"+memberList.size());
		return memberList;
	}

	@Override
	public List<Member> findByNames(String searchName) {
		String pname = searchName + "%";
		System.out.println("JpaMemberRepository findByNames pname->"+pname);
		
		//쿼리문 : SELECT * FROM members WHERE name LIKE 'John%'; 이거 하는거임
		//Member는 개체임. name은 ENtity에 선언된 필드 name이다.
		List<Member> memberList = em.createQuery("select m from Member m where name Like :name", Member.class)
									.setParameter("name", pname)
									.getResultList();
		//:name에 pname을 넣기위해 setParameter를 사용한거고. 쿼리문을 사용할때 Like : %가 원래붙음
		//그래서 serachName+"%"를 사용해준거임
		
		return memberList;
	}

}
