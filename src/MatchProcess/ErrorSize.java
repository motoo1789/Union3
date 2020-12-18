package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class ErrorSize {

	public void sizeregister(int size,int count){

		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	    	String sql ="insert into ErrorSize(id,size)VALUES(?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
					pstmt.setInt(1, count);
					pstmt.setInt(2, size);
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
