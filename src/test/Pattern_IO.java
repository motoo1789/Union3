package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class Pattern_IO {

	public String p_read() {
		Connection conn = null;
		String st = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	         String s1 ="select * from SelectPattern " ;
	         PreparedStatement p2 = conn.prepareStatement(s1);
	         	try (ResultSet rs = p2.executeQuery()) {
	         		while (rs.next()) {
	         			st = rs.getString("name");

	        	}
	        	}
	    } catch (SQLException se) {
	        System.out.println(se.getMessage());
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	            }
	        }
	    }

	    return st;
	}

	public void p_write(String st) {
		int count=1;
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	        String s ="DELETE from SelectPattern";
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(s);
	        stmt.close();

	        String sql ="insert into SelectPattern(id,name) VALUES(?,?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
				pstmt.setInt(1, count);
				pstmt.setString(2, st);

				pstmt.executeUpdate();

	        }
	    } catch (SQLException se) {
	        System.out.println(se.getMessage());
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	            }
	        }
	    }
	}

}
