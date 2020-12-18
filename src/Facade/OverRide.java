package Facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;

import marker.GetResource;
import test.SaveData;

public class OverRide {
private ArrayList<SaveData> sd;


	public OverRide(ArrayList<SaveData> sd) {
		this.sd = sd;
	}
	public void OverRideEvaluation() {
		ArrayList<String> abst = new ArrayList<String>();
		ArrayList<String> con = new ArrayList<String>();
		int count = 4;
		Connection conn = null;
		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	        String s = "select * from FeatureExraction";
	        PreparedStatement p = conn.prepareStatement(s);
	        ResultSet re = p.executeQuery();
	        while(re.next()) {
	        	if(re.getString("FeatureName").equals("Inheritance")) {
	        	abst.add(re.getString("Sendee"));
	        	con.add(re.getString("Sender"));
	        	}
	        }
	        String sql ="insert into FeatureExraction(id,FeatureName,Sender,Sendee) VALUES(?,?,?,?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {

	    		for(int i = 0;i<sd.size();i++) {
	    			for(int j=0;j<sd.size();j++) {
	    				if(i != j) {
	    			for(int d=0;d<sd.get(i).getAggregationMethodName().size();d+=4) {
	    				for(int e=0;e<sd.get(j).getAggregationMethodName().size();e+=4) {
	    					for(int v=0;v<con.size();v++) {
	    					for(int z=0;z<con.size();z++) {

	    				if(sd.get(i).getClassName().toString().equals(abst.get(v))&&
	    						sd.get(j).getClassName().toString().equals(con.get(z))&&
	    						sd.get(i).getAggregationMethodName().get(d+3).toString().contains("abstract")&&
	    						sd.get(i).getAggregationMethodName().get(d+1).toString().contains(sd.get(j).getAggregationMethodName().get(e+1).toString())&&
	    						sd.get(i).getAggregationMethodName().get(d+2).toString().contains(sd.get(j).getAggregationMethodName().get(e+2).toString())) {
	    					pstmt.setInt(1, count);
	    					pstmt.setString(2, "OverRide");
	    					pstmt.setString(3, sd.get(j).getClassName().toString());
	    					pstmt.setString(4, sd.get(i).getClassName().toString());
	    					pstmt.executeUpdate();
	    				}
	    					}
	    				}
	    				}
	    			}
	    			}
	    			}
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
