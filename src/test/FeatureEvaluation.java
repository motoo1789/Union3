package test;

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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Type;


import Facade.*;
import marker.GetResource;

public class FeatureEvaluation {
	private ArrayList<SaveData> sd = new ArrayList<SaveData>();
	private boolean MainFlag;

	public FeatureEvaluation() {
	}
	public void setSaveInstance(SaveData SD) {
		sd.add(SD);
	}
	public void register() {

		Connection conn = null;
		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	        String sql ="DELETE from FeatureExraction";
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(sql);
	        stmt.close();

			Inheritance IE = new Inheritance(sd);
			IE.InheritanceEvaluation();
			Aggregation AE = new Aggregation(sd);
			AE.AggregationEvaluation();

//			SuperInstance SI = new SuperInstance(sd);
//			SI.SubInstanceEvaluation();

	        Forwarding FD = new Forwarding(sd);
	        FD.ForwardingEvaluation();
	        OverRide OR = new OverRide(sd);
	        OR.OverRideEvaluation();
	        Instantiate IS = new  Instantiate(sd);
	        IS. InstantiateEvaluation();
	        WrapperObject wo = new WrapperObject(sd);
	        wo.WrapperObjectEvaluation();



	        try {
	        	File file = new File("C:\\pleiades10月13日\\pleiades\\workspace\\Union3\\Result\\result.txt");

        		 FileWriter fw = new FileWriter(file,true);
	         sql = "select distinct * from FeatureExraction";
	         PreparedStatement p = conn.prepareStatement(sql);
	            try (ResultSet rs = p.executeQuery()) {

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
	                fw.write("\n");
	                fw.close();
	            }
	  	     }catch(IOException e2) {
	  	    	 System.out.println(e2);
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
