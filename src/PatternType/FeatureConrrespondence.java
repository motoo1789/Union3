package PatternType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import FeatureCorrespondencePattern.*;
import SelectPattern.Strategy;
import marker.GetResource;


public class FeatureConrrespondence {
	private String pattern;

	public void Conrrespondence(Map<String,String> map,ArrayList key) {

		ArrayList<Integer> id = new ArrayList<Integer>();
		ArrayList<String> feature = new ArrayList<String>();
		ArrayList<String> sender = new ArrayList();
		ArrayList<String> sendee = new ArrayList();

		int count = 1;
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	        		String s = "select distinct * from FeatureExraction ";

	    	        PreparedStatement p = conn.prepareStatement(s);
	    	        ResultSet re = p.executeQuery();
	    	        while(re.next()) {

	    	        	for(int i=0;i<key.size();i++) {
	    	        		for(int j=0;j<key.size();j++) {
	    	        			if(map.get(key.get(i)).equals(re.getString("Sender")) && map.get(key.get(j)).equals(re.getString("Sendee"))) {
	    	        				id.add(re.getInt("id"));
    				    	        feature.add(re.getString("FeatureName"));
    				    	        sender.add(key.get(i).toString());
    				    	        sendee.add(key.get(j).toString());
	    	        			}
	    	        		}
	    	        	}
	    	        }

	    	// 取り出した特徴を別の場所に保存する
	    	Conrrespondence c = new Conrrespondence();
	    	c.Conrrespondence(id,feature,sender,sendee,map);

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
