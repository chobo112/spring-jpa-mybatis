package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVo;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.net.aso.m;

@Controller
@RequiredArgsConstructor //DI주입인데 안에 생성자 자동으로해주기때문에 
//생성자해서 this.~~ = ~~ ; 이거 안해줘도됨
@Slf4j //로그 찍는 어노테이션
public class EmpController {
	
	private final JavaMailSender mailSender;
	
	private final EmpService es;
	
	@RequestMapping(value="listEmp")
	public String empList(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp...");
		
		/*STring이니까 페이지 없으면 null로 주고 없으면 "1"을 넣자
		if(emp.getCurrentPage() == null) {
			emp.setCurrentPage("1");
		}
		Paging에서 작업을 했기떄문에 위에거는 안 넣어줘도 된다.
		*/
		
		//Emp전부 count 14
		int totalEmp = es.totalEmp();
		System.out.println("EmpController Start totalEmp->"+totalEmp);
		
		
		//pageing작엄. paging.java로 따로 분리해서 해야함
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		//Parameter emp --> Page만 추가 Setting
		emp.setStart(page.getStart());  //시작시 1
		emp.setEnd(page.getEnd());		//시작시 10
		
		//Emp => DTO Emp.java를 list로 담아서 모델로 넘겨줌.. 스프링은 model을 사용함
		//Dao도 만드는데 ..?
		//private final EmpService es; es가 EmpService로 보내고(interface -> 구현은 EmpServiceImpl에서 함)
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController list listEmp.size()=>"+listEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);
		
		return "list";
	}
	
	
	@GetMapping(value = "detailEmp")
	public String detailEmp(Emp emp1, Model model) {
		System.out.println("EmpController start detailEmp....");
//		1. EmpService안에 detailEmp method 선언
//		   1) parameter : empno
//		   2) Return      Emp
//		
//		2. EmpDao   detailEmp method 선언 
////		                    mapper ID   ,    Parameter
//		emp = session.selectOne("tkEmpSelOne",    empno);
//		System.out.println("emp->"+emp1);
		
		
		Emp emp = es.detailEmp(emp1.getEmpno());
		model.addAttribute("emp",emp);
		
		return "detailEmp";
	}
	
	@GetMapping(value="updateFormEmp") 
	//조회는 전부 get, update하기 전에 조회한 화면이니까 
	//이름은 updateFormEmp이지만 실제는 select로 조회하는 곳이라서 @GetMapping을 한다.
	//input태그를 열어주기 위해서.. select로 폼을 열어주는거임
	public String updateFormEmp(Emp emp1, Model model) {
		System.out.println("EmpController start updateForm...");
	
		Emp emp = es.detailEmp(emp1.getEmpno());
		
		System.out.println("emp.getEname(0->"+emp.getEname());
		System.out.println("emp.getHiredate()->"+emp.getHiredate());
		//System.out.println("hiredate->"+hiredate);
		// 문제 
				// 1. DTO  String hiredate
				// 2.View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
				// 3.해결책  : 년월일만 짤라 넣어 주어야 함
		String hiredate = "";
		if(emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0, 10);
			emp.setHiredate(hiredate);
		}
		System.out.println("hiredate->"+hiredate);
		
		model.addAttribute("emp",emp);
		return "updateFormEmp";
	}
	
	@PostMapping(value="updateEmp")
	public String updateEmp(Emp emp, Model model) {
        log.info("updateEmp Start...");
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//   2. EmpDao updateEmp method 선언
////                              mapper ID   ,    Parameter
		int updateCount = es.updateEmp(emp);
		System.out.println("empController es.updateEmp updateCount-->"+updateCount);
		model.addAttribute("uptCnt",updateCount);    // Test Controller간 Data 전달
		model.addAttribute("kk3","Message Test");    // Test Controller간 Data 전달
   		return "forward:listEmp";   
   		//return "redirect:listEmp";   //model한테 넣어준거를 가져가지 않음//
		 //즉 페이지만 이동할거야 = redirect를 사용하고
   		//model의 값도 가져가려면 forward를 사용하자. 
	}
	
	
	@RequestMapping(value="writeFormEmp")
	public String writeFormEmp(Model model) {
		System.out.println("empController writeFormEmp가 시작되었습니다.");
		
		//pk를 준게 아니고 해당하는 열(결과값)이 여러개니까 List를 적어준거임
		//관리자 사번만 get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeForm empList.size->"+empList.size());
		model.addAttribute("empMngList",empList);// emp Manager List
		// 1. Service , DAO --> listManager
		// 2. Mapper -> tkSelectManager
		//    1) Emp Table --> MGR 등록된 정보 Get
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//2번. 부서코드 가져오기
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList",deptList); //dept
		System.out.println("EmpController writeForm deptList.size->"+deptList.size());
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		return "writeFormEmp";
	}
	
	@PostMapping(value="writeEmp")
	public String writeEmp(Emp emp, Model model) {
		System.out.println("EmpController Start wrtieEmp..");
		
		//service, Dao, Mapper명[insertEmp]까지 -> insert
		int insertResult = es.insertEmp(emp);
		if(insertResult > 0) return "redirect:listEmp";
		else {
			model.addAttribute("msg", "입력실패 확인해 보세요");
			
			//forward와 redirect의 공통점 viewResolver를 타지 않고, 컨트롤러의 메시지를 타고 이동함
			return "forward:writeFormEmp"; 
			//return "redirect:listEmp";
		}
		
	}
	
	@RequestMapping(value="writeFormEmp3")
	public String writeFormEmp3(Model model) {
		System.out.println("empController writeFormEmp3가 시작되었습니다.");
		
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeForm3 empList.size->"+empList.size());
		model.addAttribute("empMngList",empList);// emp Manager List
		
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList",deptList); //dept
		System.out.println("EmpController writeForm3 deptList.size->"+deptList.size());
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		return "writeFormEmp3";
	}
	
	//Validaion참조 할 경우
	@PostMapping(value="writeEmp3")
	//<form:form action = "writeEmp3" method="post" name="frm" 
	//form쪽 : modelAttribute="emp" = @Valid Emp emp의 이유>
	//form쪽 : modelAttribute="emp"  emp를 form에서 사용하겠다??
	//BindingResult -> @valid가 문제가 생기면 BindingResult에 던져줌
	//@valid 할떄는 데리고 다니는 애다 라고 생각..?
	
	public String writeEmp3(@ModelAttribute("emp") @Valid Emp emp
							, BindingResult result
							, Model model
							) {
		System.out.println("EmpController Start writeEmp3...");
		
		//Validation 오류시 Result
		if(result.hasErrors()) {
			System.out.println("EmpController writeEmp3 hasErrors...");
			model.addAttribute("msg", "BindingResult 입력 실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}
		
		//Service, Dao, Mapper명[insertEmp]까지 -> insert
		int insertResult = es.insertEmp(emp);
		if(insertResult>0) return "redirect:listEmp";
		else {
			model.addAttribute("msg", "입력실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}
	}
	
	   @GetMapping(value = "confirm")
	   public String confirm(Emp emp1, Model model) {
	      Emp emp = es.detailEmp(emp1.getEmpno());
	      model.addAttribute("empno", emp1.getEmpno());
	      
	      if (emp != null) {
	         System.out.println("EmpController confirm 중복된 사번... ");
	         model.addAttribute("msg", "중복된 사번입니다.");
	         return "forward:writeFormEmp";
	         
	      }else {
	         System.out.println("EmpController confirm 사용가능한 사번... ");
	         model.addAttribute("msg", "사용가능한 사번입니다.");
	         return "forward:writeFormEmp";
	      }
	   }
	   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@RequestMapping(value="deleteEmp")
	public String deleteEmp(Emp emp, Model model) {
		System.out.println("Empcontroller start delete..");
		//이름 : 서비스, 다오, 매퍼
		int result = es.deleteEmp(emp.getEmpno());
		return "redirect:listEmp";
	}
	   
	   
	   @RequestMapping(value="listSearch3")
	   public String listSearch3(Emp emp, Model model) {
		   //Emp 전체 Count 25
		   int totalEmp = es.condTotalEmp(emp);
		   System.out.println("EmpController listSearch3 totalEmp=>"+totalEmp);
		   
		   //Paging 작업
		   Paging page = new Paging(totalEmp, emp.getCurrentPage());
		   
		   //파라미터 emp => Page만 추가 Setting
		   emp.setStart(page.getStart());  //시작시 1
		   emp.setEnd(page.getEnd());		//시작시 10
	   
		   
		   List<Emp> listSearchEmp = es.listSearchEmp(emp);
		   System.out.println("EmpController listSearch3 listSearchEmp.size()=>"+listSearchEmp.size());
		   
		   model.addAttribute("totalEmp", totalEmp);
		   model.addAttribute("listEmp", listSearchEmp);
		   model.addAttribute("page", page);
		   
		   return  "list";
	   }
	   
	   @GetMapping(value="listEmpDept")
	   public String listEmpDept(Model model) {
		   System.out.println("EmpController listEmpDept start,,,");
		   //service, DAO -> listEmpDept
		   //Mapper만 -> tkListEmpDept
		   List<EmpDept> listEmpDept = es.listEmpDept();
		   model.addAttribute("listEmpDept", listEmpDept);
		   
		   return "listEmpDept";
	   }
	   
	   @RequestMapping(value="mailTransport")
	   public String mailTransport(HttpServletRequest request, Model model) {
		   System.out.println("메일보내는중");
		   
		   String tomail = "ttekwand3@naver.com"; // 받는사람 이메일
		   System.out.println(tomail);
		   
		   String setfrom = "ttaekwang3@gmail.com";
		   String title = "mailTransport입니다.";
		   
		   try {
			   //Mine 전자우편 Internet 표존 Format
			   MimeMessage message = mailSender.createMimeMessage();
			   MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			   messageHelper.setFrom(setfrom); //보내는사람 생략하거나 하면 정상작동을 안함
			   messageHelper.setTo(tomail); //보내는사람 이메일
			   messageHelper.setSubject(title);
			   
			   //6자리를 하려고 *999999 6자리 곱한거
			   String tempPassword = (int) (Math.random()*999999)+1 + "";
			   
			   messageHelper.setText("임시비밀번호입니다 : " + tempPassword);   //메일 내용
			   mailSender.send(message);
			   model.addAttribute("check", 1); //정상전달
			   //DB Logic 구성
	//passwordUpdate라는 메서드를 만들어서 서비스와 연결을 해볼거..
	// 임시패스워드를 여기다가 써준다??	   
			   
			   
			   
		   } catch (Exception e) {
			   System.out.println("mailTransport e.getMessage()=>"+e.getMessage());
			   model.addAttribute("check",2); //메일 전달 실패
		   }
		   
		   
		   return "mailResult";
	   }
	   
	//프로시저 test 입력화면
	   @RequestMapping(value="writeDeptIn")
	   public String writeDeptIn(Model model) {
		   System.out.println("writeDeptIn 시작");
		   return "writeDept3";
	   }
	   
	   //프로시저를 통한 Dept 입력후 VO 전달
	   @PostMapping(value="writeDept")
	   public String writeDept(DeptVo deptVO, Model model) {
		   es.insertEmp(deptVO);
		   if(deptVO == null) {
			   System.out.println("deptVO NUll");
		   } else {
			   System.out.println("deptVO.getOdeptno()->"+deptVO.getOdeptno());
			   System.out.println("deptVO.getOdname()->"+deptVO.getOdname());
			   System.out.println("deptVO.getOloc()->"+deptVO.getOloc());
			   model.addAttribute("msg", "정상입력 되었습니다.");
			   model.addAttribute("dept", deptVO);
		   }
		   
		   return "writeDept3";
	   }
	   
	   //Map적용
	   @GetMapping(value="writeDeptCursor")
	   public String writeDeptCursor(Model model) {
		   System.out.println("EmpController writeDeptCursor start...");
		   
		   //부서범위 조회
		   		HashMap<String, Object> map = new HashMap<String, Object>();
		   		map.put("sDeptno", 10);
		   		map.put("eDeptno", 55); //eDeptno가 key, 55가 value
		   		//map으로 넘기는 방식이 있고 DTO로 넘기는 방식이 있음	
		   		//map대신에 vo로 넘기는 방식
		   		
		   		/*<select id="proc_DeptInsert" parameterType="deptVO" statementType="CALLABLE">
		   		{	
		   			call Dept_Insert3(
		   				 #{deptno, mode=IN, jdbcType=INTEGER}
		   		따라서 deptno에는 value인 10, 55이런애들이 담겨있다.
		   		*
		   		*/
		   		//	1. map으로 보내는 이유 : sdeptno-10번으로, edeptno-55번으로 
		   		//	내가 마음대로 그냥 넘겨줘도 됨. 팀원과 상의가 필요가 없음
		   		//	유지보수가 어렵다. 하지만 유연하다
		   		
		   		//	2.DTO, VO는 명확하게 알 수가 있음..
		   		//	
		   		//	
		   		
		   	es.selListDept(map); //map이 in도 되고 out도 된다..?
		   	List<Dept> deptLists = (List<Dept>) map.get("dept");
		   	//map.get("dept"); -> 을 하면 dept에 해당하는 값들이 여러개 나옴 => 리스트타입처럼
		   	//=> 그래서 List로 형변환을 해준거임
		   	
		   		for(Dept dept: deptLists) {
		   			System.out.println("dept.getDname->"+dept.getDname());
		   			System.out.println("dept.getLoc->"+dept.getLoc());
		   		}
		   		System.out.println("deptList Size->"+deptLists.size());
		   		model.addAttribute("deptList", deptLists);
		   		
		   		return "writeDeptCursor";
	   }
	   
	   //interCeptor 시작화면
	   @RequestMapping(value="interCeptorForm")
	   public String interCeptorForm(Model model) {
		   System.out.println("interCeptorForm Start");
		   return "interCeptorForm";
	   }
	   
	   //interCeptor의 Number2임
	   @RequestMapping(value="interCeptor")
	   public String interCeptor(Member1 member1, Model model) {
		   System.out.println("EmpController interCeptor Test start");
		   System.out.println("EmpController interCeptor id->"+member1.getId());
		   
		   //존재 : 1, 비존재 : 0
		   int memCnt = es.memCount(member1.getId());
		   
		   System.out.println("EmpController interCeptor memCnt->"+memCnt);
		   
		   model.addAttribute("id", member1.getId());
		   model.addAttribute("memCnt", memCnt);
		   System.out.println("interCeptor Test End");
		   
		   return "interCeptor"; //user가 존재하면 User 이용 조회 Page
	   }
	   
	   //SampleInterceptor 내용을 받아 쳐라		get은 메서드 안 적어줘도 됨
	   @RequestMapping(value="doMemberWrite", method= RequestMethod.GET)
	   public String doMemberWrite(Model model, HttpServletRequest request) {
		   String ID = (String) request.getSession().getAttribute("ID");
		   System.out.println("doMemberWrite 부터 하세요");
		   model.addAttribute("id",ID);
		   return "doMemberWrite";
	   }
	   
	   //interCeptor 진행 Test
	   @RequestMapping(value="doMemberList")
	   public String doMemberList(Model model, HttpServletRequest request) {
		   String ID = (String) request.getSession().getAttribute("ID");
		   System.out.println("doMemberList Test Start Id->"+ID);
		   Member1 member1 = null;
		   
		   //Mmeber1 List Get Service
		   List<Member1> listMem = es.listMem(member1);
		   model.addAttribute("ID", ID);
		   model.addAttribute("listMem", listMem);
		   return "doMemberList"; 	//user존재하면 user 이용조회 page
		   
	   }
	   
	   
	   
}
