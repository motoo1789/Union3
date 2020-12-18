package FeatureCorrespondencePattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import MatchProcess.MatchProcess;
import marker.GetResource;

public class Conrrespondence {
	public void Conrrespondence(ArrayList list,ArrayList list2,ArrayList list3,ArrayList list4,Map map){

		ArrayList<Integer> id = new ArrayList<Integer>();
		ArrayList<String> feature = new ArrayList<String>();
		ArrayList<String> sender = new ArrayList();
		ArrayList<String> sendee = new ArrayList();

		id.addAll(list);
		feature.addAll(list2);
		sender.addAll(list3);
		sendee.addAll(list4);

		int count = 1;
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	        String sql ="DELETE from FeatureConrrespondence";
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(sql);
	        stmt.close();

	    	int size = id.size();


	    	sql ="insert into FeatureConrrespondence(id,FeatureName,Sender,Sendee)VALUES(?,?,?,?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
	        	for(int i=0;i<size;i++) {
					pstmt.setInt(1, i+1);
					pstmt.setString(2, feature.get(i));
					pstmt.setString(3, sender.get(i));
					pstmt.setString(4, sendee.get(i));
					pstmt.executeUpdate();
	        	}
	        }
	        try {
	        	  File file = new File("C:\\pleiades10月13日\\pleiades\\workspace\\Union3\\Result\\result.txt");
	        	  FileWriter fw = new FileWriter(file, true);

	        	  fw.write("途中結果   class: " + map + "\n");





	         String s1 ="select * from FeatureConrrespondence " ;
	         PreparedStatement p2 = conn.prepareStatement(s1);

	         System.out.println("途中結果   class: " + map);
	         	try (ResultSet rs = p2.executeQuery()) {
	         		while (rs.next()) {
	         			fw.write("id："+ rs.getInt("id"));
	         			fw.write("  FeatureName " + rs.getString("FeatureName"));
	         			fw.write("  Sender：" + rs.getString("Sender"));
	         			fw.write("  Sendee：" + rs.getString("Sendee"));
	         			fw.write("\n");
		                System.out.format("id：%s", rs.getInt("id") + " ");
		                System.out.format("FeatureName：%s", rs.getString("FeatureName") + " ");
		                System.out.format("Sender：%s", rs.getString("Sender") + " ");
		                System.out.format("Sendee：%s", rs.getString("Sendee") + " ");

		                System.out.println();

	        	}
	         		 fw.close();
	        	}

        	} catch(IOException e3) {
	        	  System.out.println(e3);
	        	}
//	         	file_w(map);
		    	MatchProcess mp = new MatchProcess();
		    	mp.process(map);



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

	public void file_w(Map map) {

	}
}
