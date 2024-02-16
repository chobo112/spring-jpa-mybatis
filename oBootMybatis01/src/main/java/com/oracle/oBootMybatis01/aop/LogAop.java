package com.oracle.oBootMybatis01.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect //aspect이 나오면 AOP라고 생각하자..
@Component //컨트롤러, 서비스, 레파지가 아니기때문에 컴포넌트 등록해주자
public class LogAop {
	
/*	
	//oBootMybatis01.dao.EmpDao 패키지 안에 있는 모든 메소드를(모든것들 실행할떄)
	//즉 EmpDao안에 있는 메소드들이 핵심관심사..
	//loggerAop방식으로 처리해주세요..
	//@Pointcut("within(com.oracle.oBootMybatis01.dao.EmpDao*)")
	@Pointcut("within(com.oracle.oBootMybatis01.dao.*)") //핵심관심사 어디에 던질거니??
	private void pointcutMethod() {
	}
	
	//ProceedingJoinPoint를 했다는거는 around방식으로 했다느거?
	@Around("pointcutMethod()")
	//								여기서 던진 예외들은 메소드(핵심관심사)-EmpDao들에서 잡아준다.
	public Object loggerAop(ProceedingJoinPoint joinPoint) throws Throwable {
		
		
		//oBootMybatis01.dao.EmpDao에서 수행되는 핵심관심사(Core Concern) = String signatureStr
		String signatureStr = joinPoint.getSignature().toString();
		System.out.println(signatureStr + " is Start..");
		long st = System.currentTimeMillis();
		
		try {
		//핵심관심사
		Object obj = joinPoint.proceed();
		return obj;
		} finally {
			long et = System.currentTimeMillis();
			System.out.println(signatureStr+ " is finished.");
			System.out.println(signatureStr+ " 경과시간 : "+(et-st));
			
		}
	}
	
	@Before("pointcutMethod()")
	public  void before() {
		System.out.println(" Before.");

	}
*/
}

 


