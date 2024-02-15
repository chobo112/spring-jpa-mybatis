package com.oracle.oBootMybatis01.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.dao.Member1Dao;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVo;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //pirvate final EmpDao니까..
public class EmpServiceImpl implements EmpService {
	
	private final Member1Dao md;
	
	private final DeptDao dd;
	
	private final EmpDao ed;
	/*public EmpServiceImpl() {
		this.ed = ed;
	}*/

	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl start total...");
		int totEmpCnt = ed.totalEmp();
		System.out.println("EmpServiceImpl totalEmp totTmpCnt->"+totEmpCnt);
		return totEmpCnt;
		
	}

	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpServicImpl start listEmp");
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size()->"+empList.size());
		
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpserviceImp start detailEmp...");
		
//		Emp emp = new Emp();
		Emp emp = null;
		
		emp = ed.detailEmp(empno);
		
		
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpServiceImpl update ...");
		int updateCount = 0;
		updateCount = ed.updateEmp(emp);
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		//pk를 준게 아니고 해당하는 열(결과값)이 여러개니까 List를 적어준거임
		List<Emp> empList = null;
		System.out.println("EmpServicImpl start listEmp");
		empList = ed.listManager();
		System.out.println("EmpServiceImpl listEmp empList.size()->"+empList.size());
		
		return empList;
	}

	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		System.out.println("EmpServiceImpl deptselect start");
		deptList = dd.deptSelect();
		System.out.println("EmpServiceImpl deptSelect deptList.size()->"+deptList.size());
		return deptList;
	}

	@Override
	public int insertEmp(Emp emp) {
		int insertEmp = 0;
		System.out.println("서비스단에서 insertEmp메서드가 시작되었습니다.");
		insertEmp = ed.insertEmp(emp);
		
		return insertEmp;
	}

	@Override
	public List<Emp> listSearchEmp(Emp emp) {
		List<Emp> empSearchList = null;
		System.out.println("EmpServiceImpl listEmp start...");
		empSearchList = ed.empSearchList3(emp);
		System.out.println("EmpServiceImpl listSearchEmp empSearchList.size()=>"+
		empSearchList.size());
		
		return empSearchList;
	}

	   @Override
	   public int condTotalEmp(Emp emp) {
	      System.out.println("EmpServiceImpl condTotalEmp start...");
	      // 조건에 맞는 전체 직원 수를 가져오는 메서드 호출
	      int totEmpCnt = ed.condTotalEmp(emp);
	      System.out.println("EmpServiceImpl condTotalEmp totTmpCnt->"+totEmpCnt);
	      
	      return totEmpCnt;
	   }

	@Override
	public List<EmpDept> listEmpDept() {
		List<EmpDept> empDeptList = null;
		empDeptList = ed.listEmpDept();
		return empDeptList;
	}

	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		result = ed.deleteEmp(empno);
		return result;
	}

	@Override
	public void insertEmp(DeptVo deptVO) {
		System.out.println("EmpServiceImpl insertDept start...");
		dd.insertDept(deptVO);
	}

	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("EmpServiceImpl selListDept 시작됨");
		dd.selListDept(map);
	}

	@Override
	public int memCount(String id) {
		System.out.println("EmpSericeImpl memCount id->"+id);
		return md.memCount(id);
	}

	@Override
	public List<Member1> listMem(Member1 member1) {
		System.out.println("EmpserviceImpl listMem start...");
		return md.listMem(member1);
	}
	
	

}
