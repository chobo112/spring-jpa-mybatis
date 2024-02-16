package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

import lombok.RequiredArgsConstructor;

@Repository // DAO나 repository나 @repository로 걸어줌
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
   // Mybatis DB 연동  jpa는 entitymanager를 사용함
   private final SqlSession session;
   
   @Override
   public int totalEmp() {
	  int totEmpCount=0;
	  System.out.println("EmpDaoImpl start total...");
	  
	  try {
		  //"com.oracle.oBootMybatis01여기 부분은 Mapper이름을 줌..?
		  //<mapper namespace="com.oracle.oBootMybatis01.EmpMapper"> xml에서 이렇게 줬었어서 이렇게 쓴거임..
		  totEmpCount = session.selectOne("com.oracle.oBootMybatis01.EmpMapper.empTotal");
		  
		  System.out.println("EmpDaoImpl totalEmp totEmpCount->"+totEmpCount);
		  
		  
	  }catch (Exception e) {
		  System.out.println("EmpDaoImpl totalEmp Exception ->"+e.getMessage());
	  }
	  
	   return totEmpCount;
   }

   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listEmp start...");
		
		try {
			// emp => Mapper로 가서 매핑을 해야 함. 쿼리문을 Emp.xml(Mapper에서 완성함)
			//.selectone이 있고 selectList가 있음... 무슨 차이인줄
			empList = session.selectList("tkEmpListAll", emp);
			System.out.println("EmpDaoImpl listEmp empList.size()->"+empList.size());
		}catch (Exception e) {
			System.out.println("EmpDaoImpl listEmp e.getMessage()->"+e.getMessage());
		}
		
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpDaoImpl detailEmp start...");
		
		//Emp emp = null; 
		//session.selectOne("tkdetailEmp", empno);얘가 인스턴스를 만들어면 null해도됨
		
		Emp emp = new Emp();
		
		try {
			//"tkdetailEmp" 이 부분으로 mapper가서 매핑해야함 뒤에는 파라미터로 넣어주자
			//empno는 pk를 줘서 값을 확인하려고함. 그러니까 empno는 꼭 Pk!!
			emp = session.selectOne("tkdetailEmp", empno);
			System.out.println("EmpDaoImpl detailEmp emp?"+emp);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl detailEmp e.getMessage()"+e.getMessage());
		}
		
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpDaoImpl update start..");
		int updateCount= 0;
		try {
			updateCount = session.update("tkEmpUpdate",emp);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl updateEmp Exception->"+e.getMessage());
		}
		return updateCount;
	}


	@Override
	public List<Emp> listManager() {
		//pk를 준게 아니고 해당하는 열(결과값)이 여러개니까 List를 적어준거임
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl mgrList start...");
		
		try {
			//emp관리자인 select               Naming Rule
			empList = session.selectList("tkSelectManager");
		}catch (Exception e) {
			System.out.println("EmpDaoImpl mgrList e.getMessage()->"+e.getMessage());
		}
		return empList;
	}
	
	@Override
	public int insertEmp(Emp emp) {
		int insertEmp = 0;
		System.out.println("EmpDaoImpl insertEmp가 시작되었음");
	
		try {
			insertEmp = session.insert("tkEmpInsert", emp);
		}catch (Exception e) {
			System.out.println("EmpDaoImpl insertEmp e.getMessage()->"+e.getMessage());
		}
		
		return insertEmp;
	}


	   @Override
	   public List<Emp> empSearchList3(Emp emp) {
	      System.out.println("EmpDaoImpl empSearchList3 Start... ");
	      System.out.println("EmpDaoImpl empSearchList3 emp->... "+emp);
	      List<Emp> empSearchList3=null;
	      
	      try {
	         //keyword 검색
	         //Naming Rule                      MapId      parameter
	         empSearchList3= session.selectList("tkEmpSearchList3", emp);
	         
	      } catch (Exception e) {
	         System.out.println(e.getMessage());
	      }
	      return empSearchList3;
	   }


// empSearchList3와 세트인 메서드
	@Override
	   public int condTotalEmp(Emp emp) {
	      int totEmpCount = 0;
	      System.out.println("EmpDaoImpl Start condtotal...");
	      System.out.println("EmpDaoImpl Start emp->"+emp);
	      try {
	         totEmpCount = session.selectOne("condtotalEmp", emp);
	         System.out.println("EmpDaoImpl totalEmp totEmpCount->"+totEmpCount);
	      } catch(Exception e) {
	         System.out.println("EmpDaoImpl totalEmp Exception->"+e.getMessage());
	      }
	      return totEmpCount;
	   }

	@Override
	public List<EmpDept> listEmpDept() {
		System.out.println("EmpServiceImpl listEmpDept start...");
		List<EmpDept> empDept = null;
		try {
			empDept = session.selectList("tkListEmpDept");
			System.out.println("EmpDaoImpl listEmpDept empDept.size()->"+empDept.size());
		} catch(Exception e) {
			System.out.println("EmpDaoImpl delete Exception->"+e.getMessage());
		}
		
		return empDept;
	}

	@Override
	public int deleteEmp(int empno) {
		System.out.println("EmpDaoImpl delete start..");
		int result = 0;
		System.out.println("EmpDaoImpl delete empno->"+empno);
		try {
			result  = session.delete("deleteEmp",empno);
			System.out.println("EmpDaoImpl delete result->"+result);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl delete Exception->"+e.getMessage());
		}

		return result;
	}

	@Override
	public String deptName(int deptno) {
		System.out.println("EmpDaoImpl deptName Start");
		String resultStr = "";
		
		try {
		System.out.println("EmpDaoImpl deptName deptno->"+deptno);
		resultStr = session.selectOne("AjaxDeptno", deptno);
		System.out.println("EmpDaoImp deptName resultStr->"+resultStr);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl deptName Exception->"+e.getMessage());
		}
		
		return resultStr;
	}

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

   

}