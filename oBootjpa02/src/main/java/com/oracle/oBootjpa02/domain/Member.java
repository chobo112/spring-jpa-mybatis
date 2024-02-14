package com.oracle.oBootjpa02.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//Entity와 DTO의 차이는?(myBetis는 DTO고, Entity는 JPA)임
//myBetis는 테이블과 객체간에 1:1로 쿼리를 수행
//entity는 JPA를 이용해서 객체

//Entity를 안준 클래스는 JPA와 아무 상관이 없음
@Entity
@Getter
@Setter
@ToString
@SequenceGenerator( name = 		  "member_seq_gen",
					sequenceName = "member_seq_generate",
					initialValue = 1,
					allocationSize = 1)
@Table(name="member2")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "member_seq_gen")
	@Column(name="member_id", precision = 10)
	private Long id;
	
	@Column(name="user_name", length=50)
	private String name;
	
	//@Column을 안붙여도 기본적으로 @Column(value="sal")으로 이름을 맞춰서 지정을 해 놓음
	private Long sal;
	
	//관계설정
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	//team테이블의 team_id컬럼을 member Entity로 가져와서
	//외래키로 사용하겠다.
	
	
	//JPA레파지토리에서 사용하기 위한 teamname;
	//@Transient 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
	//실제 column 아니고 -> Buffer용도로 사용한다.
	@Transient
	private String teamname;
	//여기서 teamname은 Team.java에서 받아온 entity 이름을 이용한거
	
	@Transient
	private Long teamid;

}
