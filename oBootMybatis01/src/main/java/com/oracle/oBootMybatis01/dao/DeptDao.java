package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVo;

public interface DeptDao {
	List<Dept> deptSelect();

	void insertDept(DeptVo deptVO);

	void selListDept(HashMap<String, Object> map);
	

}
