package com.oracle.oBootjpa02;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.oracle.oBootjpa02.domain.Member;
import com.oracle.oBootjpa02.repository.MemberRepository;
import com.oracle.oBootjpa02.service.MemberService;

import jakarta.transaction.Transactional;

//@SpringBootTest : 스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
//반복 가능한 테스트 지원, 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고
//테스트가 끝나면 트랜잭션을 강제로 롤백
@SpringBootTest
@Transactional
public class MemberServiceTest {
	//@SpringBootTest를 써주고 @AutoWired를 쓰자..?
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	
	
	@BeforeEach
	public void before() {
		System.out.println("test @BeforeEach...");
	}
	
	@Test
	@Rollback(value=false) //커밋하겠다. 롤백이 되더라도 시퀸스는 작동이 된다,,?
	public void memberSave() {
		//1.조건  -> 2.행위  -> 3.결과
		
		// 1.조건
		Member member = new Member();
		//member.setTeam("고구려");
		member.setTeamname("고구려");
		member.setName("개소문");
		
		// 2.행위  -> 컨트롤 시점..
		Member member3 = memberService.join(member);
		//join을 하게되면 persist로 update가 하게 되어있음
		
		// 3.결과
		 System.out.println("MemberServiceTest memberSave member.getId()->"+member.getId());
		 System.out.println("MemberServiceTest memberSave member3.getId()->"+member3.getId());
		 //member.getId와 member3.getId가 같으면 로직이 정상이다..
	}
	
	/*public MemberServiceTest(MemberService memberService) {
		this.memberService = memberService;
	}*/
	
	@Test
	public void memberFindAll() {
		//1.조건
		//회원조회 --> 강감찬
		Long findId = 1L;
		
		//2.행위
		List<Member> memberList = memberService.getListAllMember();
		
		//3.결과
		System.out.println("memberList.size()->"+memberList.size());
	}
	
	@Test
	public void memberFind() {
		//1.조건
		//회원조회 --> 강감찬
		Long findId = 1L;
		
		//2.행위
		Optional<Member> member= memberService.findByMember(findId);
		
		//3.결과
		System.out.println("memberServiceTest()->"+member);
	}
	
}
