package com.oracle.oBootHello.repository;

import java.util.List;

import com.oracle.oBootHello.domain.Member1;

public interface MemberRepository {
	
	
	//v파라미터가 맴버1을 가져간다... save메서드는  Member1타입의 객체를 저장하는 역할
	//이는 save 메서드가 주어진 member1 객체를 저장하고, 
	//저장된 결과로 새로운 Member1 객체를 반환한다는 것을 의미합니다.
	Member1 save(Member1 member1);
	
	//indAll 메서드는 시스템에 저장된 모든 Member1 객체를 조회하는 역할을 합니다.
	//메서드의 시그니처를 보면 리턴 타입으로 List<Member1>을 사용하고 있습니다. 
	//이는 여러 개의 Member1 객체를 담은 리스트를 반환한다는 것을 의미합니다.
	List<Member1> findAll();
		
		
		
	
}
