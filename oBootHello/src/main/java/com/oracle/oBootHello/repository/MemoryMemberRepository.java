package com.oracle.oBootHello.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootHello.domain.Member1;

@Repository
public class MemoryMemberRepository implements MemberRepository {
	
	//key는 long // value = DTO로 하자
	private static Map<Long, Member1> store = new HashMap<>();
	
	
	private static Long sequence = 0L;
	
	@Override
	public Member1 save(Member1 member1) {
		//DB에서의 sequnce역할을 해줌. 1씩증가하는 pk대신 쓰는애들
		member1.setId(++sequence);
		//시퀸스를 Map인 store에 넣자. key(id인 pk넣고) value인 member1을 넣음) PK넣는작업
		
		
		store.put(member1.getId(), member1); //DTO를 전체 통으로 맵에 저장
		//레파지토리는 그냥 DB처럼 사용중이고 member1 DTO
		
		
		System.out.println("MemoryMemberRepository sequence ->"+sequence);
		System.out.println("MemoryMemberRepository member1.getName() ->"+member1.getName());
		
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepository findAll start");
		
		//store의 value(Member1)
		//Map에서 Member1의 value만 가져옴. => List로 가져옴 (객체니까 List로가져옴)
		List<Member1> listMember = new ArrayList<>(store.values());
		System.out.println("MemoryMemberRepository findAll listMember.size() ->"+listMember.size());
		
		return listMember;
	}

}
