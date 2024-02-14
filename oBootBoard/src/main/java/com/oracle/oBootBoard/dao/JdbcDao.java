package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.oracle.oBootBoard.dto.BDto;


public class JdbcDao implements BDao {

	//JDBC사용
	private final DataSource dataSource;
	
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
	
	/*
	 * <td>번호</td><td>이름</td><td>제목</td><td>날짜</td><td>히트</td> <!-- 수정: td 태그 추가 -->
	 * </tr> <c:forEach items="${boardList}" var="mvc_board"> <!-- 수정: 띄어쓰기 제거 -->
	 * <tr> <td>${mvc_board.bId}</td> <td>${mvc_board.bName}</td> <td> <c:forEach
	 * begin="1" end="${mvc_board.bIndent}">-</c:forEach> <a
	 * href="content_view?bId=${mvc_board.bId}">${mvc_board.bTitle}</a></td>
	 * <td>${mvc_board.bDate}</td> <td>${mvc_board.bHit}</td> </tr>
	 */
	@Override
	public ArrayList<BDto> boardList() /*throws SQLException*/{
		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//String sql = "select * from mvc_board where BId = ?";
		String sql = "select * from mvc_board order by bGroup desc, bStep asc";
		//이렇게 적으면 댓글이 모두 해결이 되는 sql문??
		
		//BDto bto = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
				while(rs.next()) {
					//이거는 그냥 DTO를 활영한거. bto생성자를 활용하지 않은버전
//					bto = new BDto();
//					bto.setbId(rs.getInt("bId"));
//					bto.setbName(rs.getString("bName"));
//					bto.setbTitle(rs.getString("bTitle"));
//					bto.setbContent(rs.getString("bContent"));
//					bto.setbDate(rs.getTimestamp("bdate"));
//					bto.setbHit(rs.getInt("bHit"));
//					bto.setbGroup(rs.getInt("bGroup"));
//					bto.setbStep(rs.getInt("bStep"));
//					bto.setbIndent(rs.getInt("bIndent"));
//					bList.add(bto);
					
					//여기는 생성자안쪽에 코드가 조금 더럽기 하지만 이건단계를 덜 거친버전
					/*bto = new BDto(
				    rs.getInt("bId"),
				    rs.getString("bName"),
				    rs.getString("bTitle"),
				    rs.getString("bContent"),
				    rs.getTimestamp("bdate"),
				    rs.getInt("bHit"),
				    rs.getInt("bGroup"),
				    rs.getInt("bStep"),
				    rs.getInt("bIndent")
					);
					bList.add(bto);*/
					//DTO에 넣을거는 일단 다 넣어주자. 뽑는걸 뭘 뽑을지만 중요한거임
					
					int bId = rs.getInt("bId");
					String bName = rs.getString("bName");
					String bTitle = rs.getString("bTitle");
					String bContent = rs.getString("bContent");
					Timestamp bDate = rs.getTimestamp("bDate");
					int bHit = rs.getInt("bHit");
					int bGroup = rs.getInt("bGroup");
					int bStep = rs.getInt("bStep");
					int bIndent = rs.getInt("bIndent");
					BDto dto = new BDto(bId, bName, bTitle, bContent, 
							bDate, bHit, bGroup, bStep, bIndent);
					bList.add(dto);
					//생성자를 이용한 DB데이터 처리 
					
				
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bList;
		}

	@Override
	public void write(String bName, String bTitle, String bContent) {
		//1. insert Into mvc_board
		//2. prepareStatement
		//3. MVC_BOARD_SEQ를 사용하자??
		
		// 1. Insert Into mvc_board
		// 2 prepareStatement
		// 3. mvc_board_seq
		// 4. bId , bGroup 같게 => 왜 같아야 하는지 의문이고..
		// 5.  bStep, bIndent, bDate --> 0, 0 , sysdate
		Connection conn = null;
		PreparedStatement pstmt = null;
		//String sql1 = "select MVC_board_seq from MVC_board";
		//String sql2 = "insert into MVC_board values(?,?,?,?,sysdate,?,?,?,?)";
		
		//String sql = "insert into MVC_board values"
		//		+ "(MVC_BOARD_SEQ.nextVal,?,?,?,sysdate,0,MVC_BOARD_SEQ.nextVal,0,0)";
		//nextval을 2번하면 41 / 42가 되어서 번호가 흐트러짐. 그래서 안됨. 
		
		//String sql = "insert into MVC_board values"
		//		+ "(MVC_BOARD_SEQ.nextVal,?,?,?,sysdate,0,MVC_BOARD_SEQ.curval,0,0)";
		
		//여기서 @@@@@@@@@@@@@@@@@@@@@@@@bid와 group을 시퀀스로 맞춰줘서 같은 그룹으로 삽입되게함@@@@@@@@@@@@@@@@@@@@@@@@@@@
		String sql = "insert into MVC_board values(MVC_BOARD_SEQ.nextVal,?,?,?,sysdate,0,MVC_BOARD_SEQ.nextVal,0,0)";
		//String sql2 = "insert into MVC_board values(MVC_BOARD_SEQ.nextVal,?,?,?,sysdate,0,MVC_BOARD_SEQ.currval,0,0)";
		//얘는 시발 안된다. cur_val은 작동이 안됨... => currval로 해야지 작동이 된다..
		
		//BHIT / BSETP / BINDENT(=댓글, 대댓글) 여기부분은 처음만들어질떄 0
		//인기조회수 처음만들어지면 0 = BHIT / BSTEP = 들여쓰기 역시 처음만들어지면 아무것도 없으니까 0
		// BINDENT = 처음만들어지면 댓글없으니까 0
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
			if(pstmt != null) pstmt.close();
			if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	
	/*
	 * <input type="hidden" name="bId" value="${mvc_board.bId }"> <table border="1">
	 * <tr> <td>번호</td><td>${mvc_board.bId }</td> </tr> <tr>
	 * <td>히트</td><td>${mvc_board.bHit }</td> </tr> <tr> <td>이름</td><td><input
	 * type="text" name="bName" value="${mvc_board.bName }"></td> </tr> <tr>
	 * <td>제목</td><td><input type="text" name="bTitle"
	 * value="${mvc_board.bTitle }"></td> </tr> <tr> <td>내용</td> <td><textarea
	 * rows="10" name="bContent">${mvc_board.bContent }</textarea></td> </tr> <tr>
	 */
	@Override
	public BDto contentView(String bId) {
		upHit(bId);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		//PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		int ibId = Integer.parseInt(bId);
		
		BDto bdto = new BDto();
		
		String sql = "select * from mvc_board where bId=?";
		//String sql = "select bId, bHit, bName, bTitle, bContent from mvc_board where bId=?";
		
		//String sqlupdate = "update mvc_board set bHit=bHit+1 where bId=?";
		try {
			conn = getConnection();
			//pstmt2 = conn.prepareStatement(sqlupdate);
		//	pstmt2.setInt(1, ibId);
			//pstmt2.executeUpdate(); //실행을 하자.
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ibId);
			//pstmt는 DB로 가서 실행할 쿼리문을 타입만 맞춰준거. 결과와는 상관없음
			
			//rs는 전부 디비쿼리를 실행하고 나서 결과의 집합
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//DTO에 설정하는거는 결과를 설정하는거니까 DB에 있는 그대로 가져와야함
				//bdto.setbId(ibId);
				bdto.setbId(rs.getInt("BID"));
				bdto.setbName(rs.getString("BNAME"));
				bdto.setbTitle(rs.getString("BTITLE"));
				bdto.setbContent(rs.getString("BCONTENT"));
				bdto.setbDate(rs.getTimestamp("BDATE"));
				bdto.setbHit(rs.getInt("BHIT"));
				bdto.setbGroup(rs.getInt("BGROUP"));
				bdto.setbStep(rs.getInt("BSTEP"));
				bdto.setbIndent(rs.getInt("BINDENT"));
				//위에는 DTO세터로 한거임
				
				//아래는 생성자로 해보자
				//int bid = rs.getInt("bId");
				//Timestamp bDate = rs.getTimestamp("bDate");
				//dto = new BDto(bId, bName, .bIdent);
				
			}
			//if(pstmt!=null) pstmt2.close();
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bdto;
	}
	
	

	private void upHit(String bId) {
		Connection conn = null;
		PreparedStatement pstmt2 = null;
		//int ibId = Integer.parseInt(bId);
		//이게 없어도 실행이 된다.
		
		String sqlupdate = "update mvc_board set bHit=bHit+1 where bId=?";
		try {
			conn = getConnection();
			conn = getConnection();
			pstmt2 = conn.prepareStatement(sqlupdate);
			//pstmt2.setInt(1, ibId);
			pstmt2.setString(1, bId);
			pstmt2.executeUpdate(); //실행을 하자.
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
			if(pstmt2!=null) pstmt2.close();
			if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void modify(String bId, String bName, String bTitle, String bContent) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update MVC_board "
				+ "set bName=?, bTitle=?, bContent=? "
				+ "where bid=?  ";
		
		//String sql = "update MVC_board set bName=?, bTitle=?, bContent=? where bid=? ";
		try {
			conn = getConnection();
			
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public BDto reply_view(String bId) {
		//이번에는 생성자로 한번 만들어볼까
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BDto bdto = new BDto();
		String sql = "select * from MVC_board where bId = ?";
		
		try {
			conn = getConnection();
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, bId);
			rs= pstmt.executeQuery();
			
			//pstmt.setInt(1, Integer.parseInt(strId));
			
			while(rs.next()) {
				/*int bId1 = rs.getInt("bID");
				String bName = rs.getString("BNAME");
				String bTitle =rs.getString("BTITLE");
				String bCONTENT=rs.getString("BCONTENT");
				Timestamp bDate =rs.getTimestamp("BDATE");
				int bHit = rs.getInt("BHIT");
				int bGROUP = rs.getInt("BGROUP");
				int bSTEP = rs.getInt("BSTEP");
				int bINDENT = rs.getInt("BINDENT");
				dto = new BDto(bId, bName, bTitle,bCONTENT, bDate, bHit
				, bGROUP, bSTEP, bINDENT);*/
				
				bdto.setbId(rs.getInt("BID"));
				bdto.setbName(rs.getString("BNAME"));
				bdto.setbTitle(rs.getString("BTITLE"));
				bdto.setbContent(rs.getString("BCONTENT"));
				bdto.setbDate(rs.getTimestamp("BDATE"));
				bdto.setbHit(rs.getInt("BHIT"));
				bdto.setbGroup(rs.getInt("BGROUP"));
				bdto.setbStep(rs.getInt("BSTEP"));
				bdto.setbIndent(rs.getInt("BINDENT"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bdto;
	}

	@Override
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
//				[1] bId SEQUENCE = bGroup 
//			    [2] bName, bTitle, bContent -> request Value
//			    [3] 홍해 기적
//			    [4] bStep / bIndent   + 1
		
		//홍해의 기적@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 최신댓글을 위로넣어주기..
		replyshape(bGroup, bStep);
		//홍해의 기적@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		
		try {
			//댓글 그룹은 같아야하니까 bGroup은 같으면됨
			//댓글을 다려면 bStep+1 ,  bIndent+1이 됨
			conn = getConnection();
			String query = "insert into MVC_board(bId, bName, bTitle, bContent, bGroup, bStep, bIndent) "
					+ "values (MVC_BOARD_SEQ.nextVal,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			
			//댓글 그룹은 같아야하니까 bGroup은 같으면됨
			//댓글을 다려면 bStep+1 ,  bIndent+1이 됨
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep)+1);
			pstmt.setInt(6, Integer.parseInt(bIndent)+1);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}

	//홍해의 기적...
	//bStep과 BIndent는 +1이됨
	//여기서는 내가 선택한 댓글그룹에 대해서 indent를 level을 1씩 올려서 하는거임

	private void replyshape(String strGroup, String strSteo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			//여기서는 DB에서 내가 선택한 조건에 대해서 1씩 올리는거.
			//bStep (순서) , bGroup, 
			String query = "update mvc_board "
					+ "set bStep = bStep+1 "
					+ "where bGroup=? and bStep >?";  
			//where bGroup=? and bStep >? : 내가 댓글을 작성하기위해서
			//클릭한것에 대한 조건을 준거임. 즉, 50(원글)을 찾은다음에 
			// 
			
			//String query = "update mvc_board set bStep = bStep+1 where bGroup=? and bStep >?";
			//글의 순서니까 내가 쓰면 1씩 글들을 올려줌
			//
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strSteo));
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void delete(String bId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "DELETE FROM MVC_board WHERE bId = ?";
	        pstmt = conn.prepareStatement(sql);
	        //pstmt.setInt(1, Integer.parseInt(bId));
	        pstmt.setString(1, bId);
	        //값 세팅
	        
	        pstmt.executeUpdate();
	        //쿼리문실행한뒤 결과 가지고 있음
	        
	        String my = pstmt.toString();
	        System.out.print(my);
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	}

}
