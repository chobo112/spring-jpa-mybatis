package com.oracle.oBootHello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootHello.domain.Member1;
import com.oracle.oBootHello.repository.MemberRepository;
import com.oracle.oBootHello.repository.MemoryMemberRepository;

@Service
public class MemberService {

		//전통적 MemberRepository 인터페이스를 구현한게 메모리멤머레파지토리
	//MemberRepository memberRepository = new MemoryMemberRepository();
	
	private final MemberRepository memberRepository; //new를 안했으니까 메모리를 차지하지는 않는 상태
	@Autowired //빈으로 설정된애를 맴버변수로 넣어주는 애가 Autowired임
	//memberRepository가 빈으로 연동 된 이유는 MemoryMemberRepository에서 @Respository가 선언되었기 떄문임
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;//위에 선언한 것에 대한 생명주기 설정
		
	}
	//빈 : Spring에서 "빈(Bean)"은 스프링 컨테이너에 의해 관리되는 객체를 의미합니다. 
	//빈은 일반적으로 Spring 애플리케이션의 핵심 구성 요소이며, 
	//스프링 IoC (Inversion of Control) 컨테이너에 의해 생성, 관리, 주입됩니다.
	//여기서 "빈으로 설정된 애"는 MemberRepository 인터페이스를 구현한 구현체의 인스턴스를 말합니다. 
	//pring은 이러한 구현체를 빈으로 관리하고, 필요한 곳에서 주입합니다.
	
	
	//회원가입
	public Long memberSave(Member1 member1) {
		System.out.println("MemberService memberSave 시작되었씁니다.");
		
		//save 메서드가 주어진 member1 객체를 저장하고
		memberRepository.save(member1);
		
		return member1.getId();
	}
	/*
memberSave 메서드는 Member1 객체를 저장하는 역할을 합니다.
메서드의 매개변수로 Member1 객체(member1)를 받아서, 이를 memberRepository.save(member1)를 통해 저장합니다.
memberRepository는 어떤 데이터 저장소(예: 데이터베이스)에 접근하는데 사용되는 리포지토리(Repository) 객체로 가정됩니다.
저장된 member1 객체의 ID를 반환합니다. 이 ID는 일반적으로 데이터베이스에서 자동으로 생성된 고유 식별자입니다.
*/
	
	public List<Member1> allMembers() {
				System.out.println("MemberService allMembers 시작되었습니다.");
				
		List<Member1> memList = null;
		
		memList = memberRepository.findAll();
		
		System.out.println("memList.size()->"+memList.size());
		
		return memList;
	}
/*
allMembers 메서드는 시스템에 저장된 모든 Member1 객체를 조회하는 역할을 합니다.
memberRepository.findAll()을 통해 저장소에서 모든 회원 정보를 조회하여 
리스트(List<Member1>)로 받아옵니다.
조회된 회원 리스트(memList)의 크기를 출력하고, 
최종적으로 조회된 회원 리스트를 반환합니다.
 */
	
	

}
