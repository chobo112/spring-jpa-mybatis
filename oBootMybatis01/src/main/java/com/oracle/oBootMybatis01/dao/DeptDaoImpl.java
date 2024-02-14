package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor 
//private final SqlSession session이기때문에 사용
//이거를 해줘야지 DI를 할 수가 있음.
public class DeptDaoImpl implements DeptDao {
	private final SqlSession session;
	
	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		System.out.println("DeptDaoImpl deptSelect start...");
		
		try {
			deptList = session.selectList("tkSelectDept");
		} catch (Exception e) {
			System.out.println("DeptDaoImpl deptSelect Exception->"+e.getMessage());
		}
		return deptList;
	}

	@Override
	public void insertDept(DeptVo deptVO) {
		System.out.println("DeptDaoImpl insertDept start...");
		session.selectOne("proc_DeptInsert", deptVO); //procedure를 알리자
	}

	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("DeptDaoImp selListDept start");
		session.selectOne("proc_DeptList", map);
	}

}
