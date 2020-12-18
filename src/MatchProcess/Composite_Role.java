package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class Composite_Role {
	public void register(int count,Map role) {
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	        String sw ="DELETE from Composite_Role";
	        Statement sst = conn.createStatement();
	        sst.executeUpdate(sw);
	        sst.close();



	    	String sql ="insert into Composite_Role(id,Component,Leaf,Composite)VALUES(?,?,?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {

					pstmt.setInt(1, count);
					pstmt.setString(2, role.get("Component").toString());
					pstmt.setString(3, role.get("Leaf").toString());
					pstmt.setString(4, role.get("Composite").toString());


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
