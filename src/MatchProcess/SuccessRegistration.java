package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class SuccessRegistration {

	public void s_register(ArrayList list,ArrayList list2,ArrayList list3,int count){
		ArrayList<String> feature = new ArrayList<String>();
		ArrayList<String> sender = new ArrayList();
		ArrayList<String> sendee = new ArrayList();
		String st;

		feature.addAll(list);
		sender.addAll(list2);
		sendee.addAll(list3);


		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);



	    	String sql ="insert into Success(id,FeatureName,Sender,Sendee)VALUES(?,?,?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
	        	for(int i=0;i < feature.size();i++) {

					pstmt.setInt(1, count);
					pstmt.setString(2, feature.get(i));
					pstmt.setString(3, sender.get(i));
					pstmt.setString(4, sendee.get(i));

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
