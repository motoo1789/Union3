package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class FactoryMethod_Role {
	public void register(int count,Map role) {
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	        String sw ="DELETE from FactoryMethod_Role";
	        Statement sst = conn.createStatement();
	        sst.executeUpdate(sw);
	        sst.close();



	    	String sql ="insert into FactoryMethod_Role(id,Creator,Product,ConcreteCreator,ConcreteProduct)VALUES(?,?,?,?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {

					pstmt.setInt(1, count);
					pstmt.setString(2, role.get("Creator").toString());
					pstmt.setString(3, role.get("Product").toString());
					pstmt.setString(4, role.get("ConcreteCreator").toString());
					pstmt.setString(5, role.get("ConcreteProduct").toString());

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

