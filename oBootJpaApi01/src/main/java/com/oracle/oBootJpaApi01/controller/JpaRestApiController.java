package com.oracle.oBootJpaApi01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController //이거를 사용하자.
//@Controller
/*RestController로 오면 ResponseBody로 들어옴
즉, RestController = @controller + @ResponseBody를 합친것 : 사용목적2가지
Ajax를 사용하거나 외부에 API를 제공하려고 할떄 사용한다.
  */
@Slf4j //logger를 대신함 -> 롬복이 해준거
@RequiredArgsConstructor //->롬복이 해준거
public class JpaRestApiController {
	
	private final MemberService memberService;
	//선언만 한거 -> fianl이라서 객체를 생성을 못함. 그래서 생성자에 넣어줬어야함

	
	// postman ---> Body --> raw---> JSON	 
    //  예시    {	    "name" : "kkk222"	}
	
	//내장클래스? JpaRestApiController안에서만 사용됨 이때 내장클래스를 만든다.
	@PostMapping("/restApi/v1/membersSave") //v1은 Bad Api
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
									// @RequestBody : Json(member)으로 온것을  --> Member member Setting
									//@Valid를 거쳐서 검증을 한다..?
		System.out.println("JpaRestApiController /api/v1/memberSave member.getId()->"+member.getId());
		log.info("member.getName()->"+member.getName());
		log.info("member.getSal()->"+member.getSal());
		
		Long id = memberService.saveMember(member);
		return new CreateMemberResponse(id); 
		//이거안하고 String으로 하면 불편함 객체로 받는거 => 그래야 Json으로 한다.
		
		
		//new하고 id를 담고있는 생성자가 있겠구나 생각하고 밑에 내장클래스에 적어주자
		//new -> 객체를 만든다 -> 그러면 설계도인 class가 있어야 한다. 그래서 static class를 만듦
	}
	
	
	// 목적  : Entity Member member --> 직접 화면이나 API위한 Setting 금지
	// 예시  : @NotEmpty  -->	@Column(name = "userName")
	//v1처럼 하지말라고 해서 v2를 함
	@PostMapping("/restApi/v2/membersSave")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest cMember) {
		System.out.println("JPARestApiController /api/v2/memberSave cmember.getName()->"+cMember.getName());
		log.info("cMember.getName()->{}", cMember.getName());
		log.info("cMember.getSal()->{}", cMember.getSal());
		Member member = new Member();
		member.setName(cMember.getName());
		member.setSal(cMember.getSal());
		
		Long id = memberService.saveMember(member);
		return new CreateMemberResponse(id);
		//객체로 돌려줘야 key-value로 가기때문에ㅣ..? 좋다
	}
	
	@Data
	static class CreateMemberRequest {
		@NotEmpty
		private String name;
		private Long sal;
		
	}
	
	@Data //안에 @RequiredArgsConstructor이 있어서 final이 문제가 없어짐
	static class CreateMemberResponse {
		/*return new CreateMemberResponse(id);
		new하고 id를 담고있는 생성자가 있겠구나 생각하고 
		밑에 내장클래스에 적어주자*/
		private final Long id;
		/*public CreateMemberResponse(Long id) {
			this.id = id;
		} = 
	@Data(엄밀히 말하면 @Data안에 @RequiredArgsConstructor*/
		
	}
	
	//Bad API  저장된 모든 회원 정보를 반환하는 API엔드포인트
	@GetMapping("/restApi/v1/members")
	public List<Member> membersVer1() {
		System.out.println("JpaRestApiController /restApi/v1/members start..");
		List<Member> listMember = memberService.getListAllMember();
		return listMember;
	}
	
	//Good API EasyVersion
	//목표 : 이름 & 급여 만 전송
	@GetMapping("/restApi/v2/members")
	public Result membersVer2() {
	//public Object membersVer2() {
	//public List<MemberRtnDto> membersVer2() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController restApi/v21/members findMembers.size()->"
				+findMembers.size());
		
		//위와 같은 구조이지만 하나의 그릇을 더 만들거임 MemberRtnDto부분은 
		//필요한 부분만 MemberRtnDto클래스에서 name과 sal만 꺼내기위해서 만들어준거임
		List<MemberRtnDto> resultList = new ArrayList<MemberRtnDto>();
				//List<Member> findMembers를 --> List<MemberRtnDto> resultList 이전
				//이전 목적 : 반드시 필요한 Data만 보여준다(외부노출 최대한금지)
		
		//findMembers -> mmember를 가져옴
		for(Member member : findMembers) {
			//@AllArgsConstructor안에 생성자를 만드는 애들이 있음. 그래서 만들어지게 됨
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());
			System.out.println("restApi/v21/members getName->"+memberRtnDto.getName());
			System.out.println("restApi/v21/memberRtnDto.getSal->"+memberRtnDto.getSal());
			resultList.add(memberRtnDto);
		}
		System.out.println("restApi/v21/resultList.size()->"+resultList.size());
		//return new Result(resultList.size(), resultList);
		return new Result(resultList.size(), resultList);
		//return resultList;
		//Result(resultList); => class Result<T>여기로 들어가서 data가 된다.
	}
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//Good API 람다 Version !!
			//목표 : 이름 & 급여 만 전송
		@GetMapping("/restApi/v22/members")
		public Result memberVer22() {
			List<Member> findMembers = memberService.getListAllMember();
			System.out.println("JpaRestApiController restApi/v22/members findMembers.size()"+
					findMembers.size());
			//자바8에서 추가한 스트림(Stream)은 람다를 활용 할 수 있는 기술 중 하나
			List<MemberRtnDto> memberCollect = findMembers.stream()
					/*for(Member member : findMembers) {   향상형 포문을 map에서 해주는ㄱ고 Strema을 써줘야지 람다식 표현을 쓸 수가 있음 ->람다식표현방법
					 
			//@AllArgsConstructor안에 생성자를 만드는 애들이 있음. 그래서 만들어지게 됨
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());
			System.out.println("restApi/v21/members getName->"+memberRtnDto.getName());
			System.out.println("restApi/v21/memberRtnDto.getSal->"+memberRtnDto.getSal());
			resultList.add(memberRtnDto);
					 *                                 * 향상된 포문에서 출력되는 하나의 로우 => m     ,  */ 
												.map(m->new MemberRtnDto(m.getName(), m.getSal()))
												.collect(Collectors.toList()); //버퍼에 누적되어있는거를 collect에서 모아주는거임 모아준다음에 toList()
																			  //여기는 Return과 관련된 부분임 = resultList.add(memberRtnDto);
			System.out.println("restApi/v22/members memberCollect.size()->"+memberCollect.size());
			return new Result(memberCollect.size(), memberCollect);
		}
	
	
	
	
	//Result를 T라고 할떄 생기는 장점
	// T는 인스턴스를 생성할 때 구체적인 타입으로 변경  --> 유연성
	@Data //@RequiredArgsConstructor얘가 있기때문에 이걸 이용한 @Data를 사용한거임
	//+final이 붙는 애들(생성자가 필수로 생겨야 하는 애들) => @Data안에 @RequiredArgsConstructor과 관련된 애들임
	
	class Result<T> {
		//result
		private final int totCount;//총 인원수 추가.. = resultList.size()
		
		//
		//List<MemberRtnDto> resultList
				//private String name;
				//private Long sal;
		private final T data;// = resultList
	}
	
	@Data //getter+setter해결됨
	@AllArgsConstructor
	class MemberRtnDto {
		//여긴 내가 보낼것만 넣는거...
		
		//List<MemberRtnDto> resultList
		private String name;
		private Long sal;
		
	}
	
	/*
	 *   수정 API PUT은 !!!!전체수정!!!
	 *   PUT 방식을사용했는데, PUT은 전체 업데이트를 할 때 사용
	 *   URI 상에서 '{ }' 로 감싸여있는 부분과 동일한 변수명을 사용하는 방법 = 보통 pk로 함
	 *   해당 데이터가 있으면 업데이트를 하기에 
	 *   PUT요청이 여러번 실행되어도 해당 데이터는 같은 상태이기에 멱등
	 *   멱등(동시에 실행시켜도 같은 결과를 가져옴)
	 *   일부 수정은 petch를 사용한다.
	 */
	
	      
	@PutMapping("/restApi/v21/members/{id}") // {id} 여기는 보통 pk로 적어준다. 여긴 맴버아이디
	public UpdateMemberResponse updateMemberV21(@PathVariable("id") Long id,
												@RequestBody @Valid UpdateMemberRequest uMember) {
		//@RequestBody는 {name : 이율곡, sal : 7770} 을 제이슨에 묻어온애들이 UpdateMemberRequest
		//id와 uMember.getName과 uMember.getSal을 updateMember로 넘겨주자.
		memberService.updateMember(id, uMember.getName(), uMember.getSal());
		Member findMember = memberService.findByMember(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getSal());
		//new 객체 => json형태로 날라가게 됨.
	}
	
	@Data
	static class UpdateMemberRequest /*()*/ {
		private String name;
		private Long sal;
	}
	
	@Data
	@AllArgsConstructor //return new UpdateMemberResponse 모든 생성자 부분
	static class UpdateMemberResponse {
		private Long id;
		private String name;
		private Long sal;
		
	}
	
	
	
	
	
	
}
