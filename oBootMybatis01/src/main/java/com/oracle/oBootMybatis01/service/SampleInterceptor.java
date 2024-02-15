package com.oracle.oBootMybatis01.service;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.lang.reflect.Method;
import org.springframework.web.method.HandlerMethod;

public class SampleInterceptor implements HandlerInterceptor {

	public SampleInterceptor() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("post handle...start");
		String ID = (String)modelAndView.getModel().get("id");
		int memCnt = (Integer) modelAndView.getModel().get("memCnt");
		System.out.println("SampleInterceptor post handle memCnt: "+memCnt);
		if(memCnt < 1) {
			System.out.println("memCnt Not exists");
			request.getSession().setAttribute("ID", ID);
			//user가 존재하지 않으면 User interCeptor Page(회원등록) 이동
			
			response.sendRedirect("doMemberWrite");
		}else {		//		정상 	Login User
			System.out.println("memCnt exists");
			request.getSession().setAttribute("ID", ID);
			//user가 존재하면 user interCeptor Page(회원 List)이동
			response.sendRedirect("doMemberList");
		}
		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandler...start...");
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		System.out.println("Bean: "+method.getBean());
		System.out.println("Method: "+methodObj);
		
		return true;
		
	}
}
