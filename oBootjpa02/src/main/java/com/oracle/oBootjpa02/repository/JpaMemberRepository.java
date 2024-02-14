package com.oracle.oBootjpa02.repository;

import java.util.List;
import java.util.Optional;

import com.oracle.oBootjpa02.domain.Member;
import com.oracle.oBootjpa02.domain.Team;

import jakarta.persistence.EntityManager;


public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	//조인테이블이라 DB에다가 Team이랑 Member에 다 저장하려고 하는중..
	//회원이름 : <input type = "text" name="name" placeholder = "회원이름을 입력하세요"><p>
	//팀 이름 : <input type = "text" name="teamname" placeholder = "팀이름을 입력하세요">
	@Override
	public Member save(Member member) {
		//팀저장
		Team team = new Team();
		
		//team네임이 관련된 부분은 => Member임
		team.setName(member.getTeamname());
		
		em.persist(team);//fk를 맞춰준뒤에 하겠다..?
		
		//회원 저장
		//team만 넣어주면 fk가 알아서 설정됨(fk는 teamname이였음)
		member.setTeam(team); //단방향 연관관계 설정, 참조저장
		em.persist(member);
		Member member3 = em.find(Member.class, member.getId());

		
		System.out.println("JpaMemberRepository save member->"+member);
		
		return member3;
	}

	@Override
	public List<Member> findAll() {
		//Member.class 에서 Member는 객체 Member를 말함 
		//=> domain에 나오는 @Entity객체인 Member를 말한다.
		
		//JPA의 JPQL(쿼리)문법임 from절에 나오는건 객체를 적어줌 = Member
		//. 돌려주는 select m =>으로 return되는게 Member.class를 말한다.
		//"select m from Member m"여기서 객체타입으로 돌려줌 => Member.class로 만들어준다.
		//createQUery()의 결과를 앞에 List<Member>와 맞춰줘야함 => 그걸 .getResultList()로 바꿔주는거임
		//데이터타입은 List형 이여야 한다. 그래서 마지막에.geResultList();로 해줌.
		List<Member> memberList = em.createQuery(
										"select m from Member m",Member.class)
									.getResultList()
									;
		System.out.println("JpaMemberRepository findAllMember memberList.size()=>"+memberList.size());
		return memberList;
	}

	@Override
	public List<Member> findByNames(String searchName) {
		List<Member> memberList = em.createQuery(
									"select m from Member m where m.name= :name",Member.class)
									.setParameter("name", searchName)
									.getResultList()
									;
		System.out.println("JpaMemberRepository memberList.size()=>"+memberList.size());
		return memberList;
	}

	// Optional 객체를 사용하면 예상치 못한 NullPointerException 
	//예외를 제공되는 메소드로 간단히 회피.
	// 즉, 복잡한 조건문 없이도 널(null) 값으로 인해 발생하는 예외를 처리
	@Override
	public Optional<Member> findByMember(Long id) {
		Member member = em.find(Member.class, id); 
		//jpa는 PK만 주면.. 해당하나는 entity돌려줌?
		return Optional.ofNullable(member);
	}

	@Override
	public void updateByMember(Member member) {
		//em.persist를 하면 DB에 update를 해준다?
		int result = 0;
		Member member3 = em.find(Member.class, member.getId());
		if(member3 != null) {
			//팀 저장
			Team team = em.find(Team.class, member.getTeamid());
			if(team != null) {
				team.setName(member.getTeamname());
				em.persist(team);
			}
			//회원저장
			member3.setTeam(team); //단방향 연관관계 설정, 참조저장
			member3.setName(member.getName()); //단방향 연관관계 설정, 참조저장
			//member3.setTeam(member.getTeam());
			em.persist(member3);
			System.out.println("JpaMemberRepository updateByMember mebmer->"+member);
			result = 1;
		}else {
			result = 0;
			System.out.println("JpaMemberRepository updateByMember가 존재하지 않습니다.");
		}
		return ;
	}

}
