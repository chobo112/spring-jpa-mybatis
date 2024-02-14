package com.oracle.oBootMybatis01.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member3") //member2는 이미 사용 했음
@Getter
@Setter
@ToString
public class Member {
	@Id
	private Long id;
	private String name;
	private String password;
	
	
	@Column(nullable = false, columnDefinition = "date default sysdate")
	//column에 들어가는 date의 기본값이 sysdate임.
	private Date reg_date = new Date();

}
