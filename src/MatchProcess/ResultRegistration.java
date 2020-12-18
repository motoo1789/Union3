package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class ResultRegistration {

	public void register(ArrayList list,ArrayList list2,ArrayList list3,ArrayList list4,int count){

		ArrayList<Integer> fix = new ArrayList<Integer>();
		ArrayList<String> feature = new ArrayList<String>();
		ArrayList<String> sender = new ArrayList();
		ArrayList<String> sendee = new ArrayList();
		String st;

		feature.addAll(list);
		sender.addAll(list2);
		sendee.addAll(list3);
		fix.addAll(list4);

		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);



	    	String sql ="insert into Error(id,result,fix)VALUES(?,?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
	        	for(int i=0;i < feature.size();i++) {
	        		st = sender.get(i) + "と" + sendee.get(i) + "間の"+ feature.get(i) + "がエラー";
					pstmt.setInt(1, count);
					pstmt.setString(2, st);
					pstmt.setInt(3, fix.get(i));

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
