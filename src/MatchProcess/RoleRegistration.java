package MatchProcess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.eclipse.core.resources.IResource;
import marker.GetResource;

public class RoleRegistration {

	Connection conn = null;

	//dbのディレクトリ取得
	String projectName = null;
	IResource resouece = null;
	String dbPath= null;

	public RoleRegistration() {

		projectName = GetResource.getProjectname();
		resouece = GetResource.getResource(projectName);
		dbPath= GetResource.getDatabase(resouece);

		System.out.println("RoleRegistration:register");

	}

	public void register(int count,ArrayList rolelist){

		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	    	String sql ="insert into Role(id,role,name)VALUES(?,?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
	        	for(int i=0; i < rolelist.size();i+=2) {

					pstmt.setInt(1, count);
					pstmt.setString(2, rolelist.get(i).toString());
					pstmt.setString(3, rolelist.get(i+1).toString());
					pstmt.executeUpdate();

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
	}
}

