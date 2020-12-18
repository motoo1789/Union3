package Facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;

import marker.GetResource;
import test.SaveData;

public class WrapperObject {
	private ArrayList<SaveData> sd;
	public WrapperObject(ArrayList<SaveData> sd2) {
		this.sd = sd2;

	}
	public void WrapperObjectEvaluation() {
		int count = 6;
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
	    				for(int z = 0;z<sd.get(i).getAggregationMethodName().size();z+=4) {

	    				if( (sd.get(i).getAggregationMethodName().get(z).equals(sd.get(j).getClassName().toString()) )
	    						 ) {

	    					pstmt.setInt(1, count);
	    					pstmt.setString(2, "WrapperObject");
	    					pstmt.setString(3, sd.get(i).getAbstractName().toString());
	    					pstmt.setString(4, sd.get(j).getAbstractName().toString());

	    					pstmt.executeUpdate();
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
