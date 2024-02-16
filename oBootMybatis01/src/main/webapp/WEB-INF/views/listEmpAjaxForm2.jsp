<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

<script type="text/javascript">
	function getListDept() {
	    console.log("getListDept Run"); // 콘솔에 메시지를 출력합니다.
	
	    var str = ""; // 빈 문자열 변수를 선언합니다.
	    var str2 = ""; // 빈 문자열 변수를 선언합니다.
	
	    // 서버에 Ajax 요청을 보냅니다.
	    $.ajax({
	        url: "/sendVO3", // 요청할 URL
	        dataType: 'json', // 받을 데이터의 형식은 JSON
	        success: function(dept) { // 요청이 성공했을 때의 콜백 함수
	            var jsonStr = JSON.stringify(dept); // JSON 객체를 문자열로 변환합니다.
	            alert("jsonStr->" + jsonStr); // 변환된 문자열을 알림으로 표시합니다.
	            $('#dept_list_str').append(jsonStr); // 변환된 문자열을 해당 요소에 추가합니다.
	
	            str += "<select name='dept'>"; // 선택 목록의 시작을 나타내는 HTML 문자열을 생성합니다.
	            $(dept).each(function() { // JSON 객체의 각 요소에 대해 반복합니다.
	                str2 = "<option value='" + this.deptno + "'>" + this.dname + "</option>"; // 선택 목록 옵션 문자열을 생성합니다.
	                str += str2; // 생성된 문자열을 추가합니다.
	            });
	            str += "</select><p>"; // 선택 목록의 끝과 단락 구분 문자열을 추가합니다.
	            alert("combobox str->" + str); // 생성된 선택 목록 문자열을 알림으로 표시합니다.
	            $('#dept_list_combobox').append(str); // 선택 목록 문자열을 해당 요소에 추가합니다.
	        }
	    });
	}
    
    function getDeptDelete(pIndex) {
    	var selEmpno = $("#empno"+pIndex).val();
    	var selEname = $("#ename"+pIndex).val();
    	//alert("getDeptDelete selEmpno->"+selEmpno);
    	$.ajax(
    		{
    			url:"/empnoDelete",
    			/* 파라미터는 empno ename2개임
    				empno(key) - selEmpno(value)
    			*/
    			data:{ empno: selEmpno,
    					ename:selEname
    					},
    			dataType:'text',
    			success:function(data) {
    				alert(".ajax getDeptdelete data->"+data);
    				if(data == '1') {
    					//성공하면 아래라인 수행
    					$('#emp'+pIndex).remove();	/*Delete Tag*/
    				}
    			}
    		}		
    	);
    }
    
    
    






</script>
</head>
<body>
	<h2>회원 정보</h2>
	<table>
		<tr><th>번호</th><th>사번</th><th>이름</th><th>업무</th><th>부서</th></tr>
	 	<c:forEach var="emp" items="${listEmp}" varStatus="status">
			<tr id="emp${status.index}"><td>emp${status.index}</td>
		     	
			    <td>
			        <input type="hidden" id="deptno${status.index}" value="${emp.deptno }">
			        <input type="text" id="empno${status.index}" value="${emp.empno }">${emp.empno }</td>
			    <td><input type="text" id="ename${status.index}" value="${emp.ename }">${emp.ename }</td>
				<td>${emp.job }</td><td>${emp.deptno } 
				    <input type="button" id="btn_idCheck2" value="사원 Row Delete" onclick="getDeptDelete(${status.index})">
				</td>
			</tr>    
	     
	     </c:forEach>
	
	
	</table>

    RestController LISTVO3: <input type="button" id="btn_Dept3"
                                   value="부서명 LIST"  
                                   onclick="getListDept()"><p>
                                   
	dept_list_str:	<div id="dept_list_str"></div>

	dept_list_combobox:
	<div id="dept_list_combobox"></div>
	
	<h1>The End </h1>
</body>
</html>