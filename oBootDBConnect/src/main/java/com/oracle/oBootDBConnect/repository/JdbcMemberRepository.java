package com.oracle.oBootDBConnect.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConnect.domain.Member1;

//@Repository
public class JdbcMemberRepository implements MemberRepository {
	
	//dataSource는 application.properties에 넣어둔 dataSource를 가져오고
	//private Connection getConn메서드로 자동으로 커넥션연결을 내부적으로 하는 메서드를 만들어줌
	private final DataSource dataSource;
	@Autowired //데이터 주입
	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
	
	
	@Override
	public Member1 save(Member1 member1) {
		String sql = "insert into member7(id,name) values(member7_seq.nextval,?)";
		System.out.println("JDBC멤버레파지토리 SQL->"+sql);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,member1.getName());
			pstmt.executeUpdate();
			System.out.println("JDBC레파지토리 pstmt.executeUpdate After");
			return member1;
		}catch (Exception e) {
			throw new IllegalStateException(e);
		}finally {
			//여기서 try catch하면 귀찮으니까 메서드를 만들어버림
			close(conn, pstmt, rs);
		}
	}

	//위에 부분 close부분 하는 로직
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//자동완성이 아니라 이건 스스로 만든거 // 연결만 끊고 싶을때 dataSource랑-conn연결을 끊는거인듯?
	private void close(Connection conn) throws SQLException {
		DataSourceUtils.releaseConnection(conn, dataSource);
	}
	
	
	@Override
	public List<Member1> findAll() {
		String sql = "select * from member7";
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("JDBC맴버레파지토리 findaALl sql->"+sql);
		
		ResultSet rs = null;
		try {
			conn=getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//여기까지가 DB에서 쿼리문 실행결과
			
			List<Member1> members = new ArrayList<>();
			//조회결과가 많으니까 List에다가 members만들어서 담아줄 그릇 만들어줌
			
			while(rs.next()) {
				Member1 member = new Member1();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				//DB쿼리결과 => DTO(서버단에서 활용하려고)
				
				members.add(member);
				//만들어둔 그릇에 DTO에 세팅한 값을 넣어주자 
			}
			return members;
		}catch (Exception e) {
			throw new IllegalStateException(e);
		}finally {
			close(conn,pstmt,rs);
		}
		
	}

}
