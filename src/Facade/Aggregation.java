package Facade;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import test.FeatureEvaluation;
import test.SaveData;

public class Aggregation {
	private  ArrayList<SaveData> sd;
	public Aggregation(ArrayList<SaveData> sd2) {
		this.sd = sd2;

	}
	public  void AggregationEvaluation() {
		String[] data = {"boolean","byte","shot","int","long","float","double","String","char"};

		ArrayList methoddata = new ArrayList();
		ArrayList methodname = new ArrayList();
		ArrayList block = new ArrayList();
		ArrayList AVN = new ArrayList();
		int count = 2;
		Connection conn = null;
		ArrayList MExS = new ArrayList();
		ArrayList MExE = new ArrayList();

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

	    					pstmt.setInt(1, count);
	    					pstmt.setString(2, "Aggregation");
	    					pstmt.setString(3, sd.get(i).getAbstractName().toString());
	    					pstmt.setString(4, sd.get(j).getAbstractName().toString());
	    					pstmt.executeUpdate();

	    				}
	    			}
	    		}

	    			for(int j = 0;j<sd.size();j++) {
	    				for(int k=0;k<sd.get(i).getAggregationMethodName().size();k +=4) {



	    				if(sd.get(i).getAggregationMethodName().get(k).equals(sd.get(j).getClassName().toString()) ||
	    						sd.get(i).getAggregationMethodName().get(k+1).toString().contains(sd.get(j).getClassName().toString()))  {

	    					methodname.add(sd.get(i).getAggregationMethodName().get(k+2));
	    					String name = sd.get(i).getAggregationMethodName().get(k+1).toString();
	    					if(name.contains(sd.get(j).getClassName().toString())) {
	    						int index2 = sd.get(j).getClassName().length();
	    						int end = name.length();
	    						String sts = name.substring(index2+2,end-1);
	    						methoddata.add(sts);

	    					}
	    					else {
	    						for(int d=0;d<data.length;d++) {
	    							if(name.contains(data[d])) {
	    								int index = data[d].length();
	    								int end = name.length();
	    	    						String sts = name.substring(index+2,end-1);
	    	    						methoddata.add(sts);
	    							}
	    						}
	    					}

	    					for(int z = 0;z<sd.get(i).getAggregationVariableName().size();z+=2) {
	    						String s = sd.get(i).getAggregationVariableName().get(z+1).toString();
	    						if(s.contains("=")) {
	    							int index = s.indexOf("=");
	    							String sts = s.substring(0,index);
	    							AVN.add(sts);
	    						}
	    						else
	    							AVN.add(s);
	    					}

	    					for(int w=0;w<sd.get(i).getBlock().size();w++) {
	    						String st = sd.get(i).getBlock().get(w).toString();
	    						if(st.contains("(")) {
	    							int index = st.indexOf("(");
	    							String sts = st.substring(0,index);
	    							if(sts.contains(sd.get(i).getAggregationMethodName().get(k+2).toString())) {

	    								for(int n=0;n<sd.get(i).getMethodExpression().size();n++) {
	    									if(sd.get(i).getBlock().get(w).toString().contains(sd.get(i).getMethodExpression().get(n).toString())) {
	    										block.add(sd.get(i).getMethodExpression().get(n).toString());
	    				    					for(int u=0;u<block.size();u++) {
	    				    						for(int z = 0;z<AVN.size();z++) {
	    				    							for(int d=0;d<methoddata.size();d++) {

	    				    					if((block.get(u).toString().contains("=")||block.get(u).toString().contains("add"))&&
	    				    							block.get(u).toString().contains(AVN.get(z).toString())&&
	    				    							block.get(u).toString().contains(methoddata.get(d).toString())) {

	    					    					pstmt.setInt(1, count);
	    					    					pstmt.setString(2, "Aggregation");
	    					    					pstmt.setString(3, sd.get(i).getClassName().toString());
	    					    					pstmt.setString(4, sd.get(j).getClassName().toString());
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
