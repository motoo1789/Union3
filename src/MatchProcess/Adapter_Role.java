package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class Adapter_Role {
	public void register(int count,Map role) {
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	        String sw ="DELETE from Adapter_Role";
	        Statement sst = conn.createStatement();
	        sst.executeUpdate(sw);
	        sst.close();



	    	String sql ="insert into Adapter_Role(id,Target,Adapter,Adaptee)VALUES(?,?,?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {

					pstmt.setInt(1, count);
					pstmt.setString(2, role.get("Target").toString());
					pstmt.setString(3, role.get("Adapter").toString());
					pstmt.setString(4, role.get("Adaptee").toString());


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
