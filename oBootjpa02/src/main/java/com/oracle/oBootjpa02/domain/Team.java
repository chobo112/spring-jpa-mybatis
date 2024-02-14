package com.oracle.oBootjpa02.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity //이걸 써야지 jpa로 테이블을 만들어줌
//@Table 이거 안써주면 테이블 만들어줄때 Team으로 이름 똑같이 만들어줌
@Data
//name은 객체시퀀스, sequenceName은 DB시퀀스
@SequenceGenerator(name = "team_seq_gen", sequenceName = "team_seq_genterator",
					initialValue = 1, allocationSize = 1)
//name은 객체시퀀스, sequenceName = DB시퀀스 이름
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator="team_seq_gen")//generator는 객체시퀀스를 끌고 오자
	@Column
	private Long team_id;
	//pk인 team_id를 시퀀스방법으로 사용할거고 generator(이름은 = tem_seq_gen)으로 사용하겠따.
	
	@Column(name = "teamname")
	private String name;

}
