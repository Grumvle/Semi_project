package com.starReview.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class starDAO {

	Connection con = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;

	String sql = null;

	// starDAO 클래스를 싱글턴 방식을로 만들어 보자.
	// 1단계 : 싱글턴 방식을 객체를 만들기 위해서는 우선적으로 기본생성자의 접근 제어자를 public이 아닌
	// private 으로 바꾸어 주어야 한다. 즉 외부에서는 직접적으로 기본생성자를 호출하지 못하게 하는 방식이다.

	// 2단계 : starDAO 클래스를 정적(static) 멤버로 선언 해 주어야 한다.

	private static starDAO instance;

	private starDAO() {
	} // 기본 생성자

	// 3단계 : 기본생성자 대신에 싱글턴 객체를 return 해 주는 getInstance() 메서드를 만들어서
	// 해당 getInstance() 메서드를 외부에서 접근할 수 있도록 해주면 됨.
	public static starDAO getInstance() {

		if (instance == null) {
			instance = new starDAO();
		}

		return instance;
	} // getInstance() 메서드 end

	// DB를 연동하는 작업을 진행하는 메서드.
	public void openConn() {
		String driver = "com.mysql.cj.jdbc.Driver";

		String user = "web";

		String password = "tpalvmfhwprxm1010";

		String url = "jdbc:mysql://semi-project1.crerb4qztgxj.ap-northeast-2.rds.amazonaws.com:3306/semi";

		try {
			// 1단계 : 오라클 드라이버를 메모리로 로딩 작업 진행.
			Class.forName(driver);

			// 2단계 : 오라클 데이터베이스와 연결 작업 진행.
			con = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // openConn() 메서드 end

	// DB에 연결되어 있던 자원 종료하는 메서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	} // closeConn() 메서드 end
	
	public int insertStar(starDTO dto) {
	    int result = 0, count = 0;
	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs2 = null;
	    
	    try {
	        openConn();
	        String sql = "select max(star_review_idx) from star_review";
	        pstmt1 = con.prepareStatement(sql);
	        rs1 = pstmt1.executeQuery();
	        
	        if (rs1.next()) {
	            count = rs1.getInt(1) + 1;
	        }
	        
	        sql = "insert into star_review values(?,?,?,?,?)";
	        pstmt2 = con.prepareStatement(sql);
	        pstmt2.setInt(1, count);
	        pstmt2.setInt(2, dto.getStore_num());
	        pstmt2.setString(3, dto.getMember_id());
	        pstmt2.setString(4, dto.getReview());
	        pstmt2.setString(5, dto.getStar_count());
	        result = pstmt2.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs1 != null) rs1.close();
	            if (rs2 != null) rs2.close();
	            if (pstmt1 != null) pstmt1.close();
	            if (pstmt2 != null) pstmt2.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        closeConn(rs2, pstmt2, con);
	    }
	    return result;
	}


	
	
	public JSONArray starList(int idx) {
	    JSONArray result = new JSONArray();
	    try {
	        openConn();

	        sql = "select * from star_review where store_num = ? order by store_num";
	        
	        pstmt = con.prepareStatement(sql);
	        
	        pstmt.setInt(1, idx);
	        System.out.println(idx);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            JSONObject obj = new JSONObject();
	            obj.put("member_id", rs.getString("member_id"));
	            obj.put("review", rs.getString("review"));
	            obj.put("star_count", rs.getString("star_count"));
	            System.out.println(rs.getString("star_count"));
	            obj.put("store_num", rs.getString("store_num"));
	            result.put(obj);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }
	    return result;
	}
	
	
	
	
	
	
}