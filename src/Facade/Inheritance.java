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

import test.SaveData;

public class Inheritance {
	private ArrayList<SaveData> sd;
	public Inheritance(ArrayList<SaveData> sd) {
		this.sd = sd;
	}

	@SuppressWarnings("unlikely-arg-type")
	public void InheritanceEvaluation() {
		int count = 1;
		Connection conn = null;
		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	        String s ="DELETE from Inheritance";
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(s);
	        stmt.close();

	        String sql ="insert into FeatureExraction(id,FeatureName,Sender,Sendee) VALUES(?,?,?,?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {

	    		for(int i = 0;i<sd.size();i++) {
	    			for(int j=0;j<sd.size();j++) {
	    				if(sd.get(i).getClassName().equals(sd.get(j).getExtendName()) && sd.get(j).getExtendName() != null ) {

	    					pstmt.setInt(1, count);
	    					pstmt.setString(2, "Inheritance");
	    					pstmt.setString(3, sd.get(j).getClassName().toString());
	    					pstmt.setString(4, sd.get(i).getClassName().toString());
	    					pstmt.executeUpdate();
	    				}

	    			}

	    			for(int j=0;j<sd.size();j++) {
	    				if(sd.get(j).getImplementName().toString().contains(sd.get(i).getClassName()) && sd.get(j).getImplementName() !=null) {

	    					pstmt.setInt(1, count);
	    					pstmt.setString(2, "Inheritance");
	    					pstmt.setString(3, sd.get(j).getClassName().toString());
	    					pstmt.setString(4, sd.get(i).getClassName().toString());
	    					pstmt.executeUpdate();
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
