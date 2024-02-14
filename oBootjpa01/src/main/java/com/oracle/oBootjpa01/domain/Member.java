package com.oracle.oBootjpa01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity //=>논리적객체 DB에 만들어놓은거 
//Entity는 JPA가 DB관리하기 위해 필요함(MyBatis에서는 DTO)
@Table(name = "member1") //member1이 물리적인 객체임
//어노테이션을 주면 DDL까지 해주면서 db에 테이블을 만들어줌
//ORM(Object Relational Mapping)
//O(Object) = 논리적 객체 Member
//R(Relational) = member1=물리적
//물리객체는 Member1, 논리객체는 member로 된다.
@Getter
@Setter

@ToString
public class Member {
	
	@Id
	private Long id;
	
	
	private String name;
	
	
	//관계설정
	
	
//	@Override
//	public String toString() {
//		String returnStr = "";
//		returnStr = "[id:"+this.id+", name:"+this.name+"]";
//		return returnStr;
//	}
//	
	
//	@Getter @Setter로 대신해줌
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}

}
