package Facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;

import marker.GetResource;
import test.SaveData;

public class Forwarding {
	private  ArrayList<SaveData> sd;

	public Forwarding(ArrayList<SaveData> sd2) {
		this.sd = sd2;

	}
	public  void ForwardingEvaluation() {
		String[] data = {"boolean","byte","shot","int","long","float","double","String","char"};

		ArrayList methoddata = new ArrayList();
		ArrayList methodname = new ArrayList();
		ArrayList block = new ArrayList();
		ArrayList AVN = new ArrayList();
		int count = 3;
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


	    try {
	        conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	        String sql ="insert into FeatureExraction(id,FeatureName,Sender,Sendee) VALUES(?,?,?,?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {




	    		//AggregationVariableName
	    		for(int i = 0;i<sd.size();i++) {
	    			for(int j = 0;j<sd.size();j++) {
	    				for(int z = 0;z<sd.get(i).getAggregationVariableName().size();z+=2) {

	    				if( (sd.get(i).getAggregationVariableName().get(z).equals(sd.get(j).getClassName().toString())&&
	    						!sd.get(i).getAggregationVariableName().get(z+1).toString().contains("new") )
	    						 ) {
	    					for(int w=0;w<sd.get(i).getInBlock().size();w++) {
	    						if(sd.get(i).getInBlock().get(w).toString().contains(sd.get(i).getAggregationVariableName().get(z+1).toString())) {
		    					for(int k=0;k<sd.get(i).getAggregationMethodName().size();k +=4) {
		    						if(sd.get(i).getAggregationVariableName().get(z).equals(sd.get(j).getClassName().toString())&&
		    								sd.get(i).getInBlock().get(w).toString().contains(sd.get(j).getAggregationMethodName().get(k+2).toString())) {
		    	    					pstmt.setInt(1, count);
		    	    					pstmt.setString(2, "Forwarding");
		    	    					pstmt.setString(3, sd.get(i).getAbstractName().toString());
		    	    					pstmt.setString(4, sd.get(j).getAbstractName().toString());
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