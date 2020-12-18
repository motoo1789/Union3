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

public class Instantiate {
	private ArrayList<SaveData> sd;


	public Instantiate(ArrayList<SaveData> sd) {
		this.sd = sd;
	}
	public  void InstantiateEvaluation() {
		int count = 5;
		Connection conn = null;
		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	        String sql ="insert into FeatureExraction(id,FeatureName,Sender,Sendee) VALUES(?,?,?,?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {

	    		for(int i = 0;i<sd.size();i++) {
	    			for(int j=0;j<sd.size();j++) {
	    				for(int d=0;d<sd.get(i).getAggregationVariableName().size();d+=2) {
		    				if(sd.get(i).getAggregationVariableName().get(d).toString().equals(sd.get(j).getClassName().toString())&&
		    						sd.get(i).getAggregationVariableName().get(d+1).toString().contains("new")) {

		    					pstmt.setInt(1, count);
		    					pstmt.setString(2, "Instantiate");
		    					pstmt.setString(3, sd.get(i).getAbstractName().toString());
		    					pstmt.setString(4, sd.get(j).getAbstractName().toString());
		    					pstmt.executeUpdate();

	    				}
	    				}


	    				for(int e=0;e<sd.get(i).getAggregationMethodName().size();e+=4) {
	    					for(int f=0;f<sd.get(i).getInBlock().size();f++) {

	    					if(sd.get(i).getInBlock().get(f).toString().contains("new")&&
	    							sd.get(i).getInBlock().get(f).toString().contains(sd.get(j).getClassName().toString())&&
	    							!sd.get(i).getAggregationMethodName().get(e).toString().contains("void")&&
	    							!sd.get(i).getAggregationMethodName().get(e+1).toString().contains("String[] args") &&
	    							!sd.get(i).getAggregationMethodName().get(e+2).toString().contains("main")&&
	    							!sd.get(i).getAggregationMethodName().get(e+3).toString().equals("public")&&
	    							!sd.get(i).getAggregationMethodName().get(e+3).toString().equals("static")) {

		    					pstmt.setInt(1, count);
		    					pstmt.setString(2, "Instantiate");
		    					pstmt.setString(3, sd.get(i).getClassName().toString());
		    					pstmt.setString(4, sd.get(j).getClassName().toString());
		    					pstmt.executeUpdate();
	    					}
	    	    				}

	    					for(int g=0;g<sd.get(i).getReturnDate().size();g++) {
	    						if(sd.get(i).getReturnDate().get(g).toString().contains("new")&&
	    								sd.get(i).getReturnDate().get(g).toString().contains(sd.get(j).getClassName().toString())&&
	    								!sd.get(i).getAggregationMethodName().get(e).toString().contains("void")&&
		    							!sd.get(i).getAggregationMethodName().get(e+1).toString().contains("String[] args") &&
		    							!sd.get(i).getAggregationMethodName().get(e+2).toString().contains("main")&&
		    							!sd.get(i).getAggregationMethodName().get(e+3).toString().equals("public")&&
		    							!sd.get(i).getAggregationMethodName().get(e+3).toString().equals("static")) {
			    					pstmt.setInt(1, count);
			    					pstmt.setString(2, "Instantiate");
			    					pstmt.setString(3, sd.get(i).getClassName().toString());
			    					pstmt.setString(4, sd.get(j).getClassName().toString());
			    					pstmt.executeUpdate();
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
