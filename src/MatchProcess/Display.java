package MatchProcess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class Display {
	public void show() {


		Connection conn = null;
		ArrayList<Integer> list = new ArrayList();
		ArrayList<Integer> list2 = new ArrayList();
		ArrayList<Integer> list3 = new ArrayList();
		int cnt = 0;
		int tmp;
		int r;
		String success = null;

//	    	try {
//          		 File file = new File("C:\\pleiades10月13日\\pleiades\\workspace\\Union3\\Result\\result.txt");
//          		 FileWriter fw = new FileWriter(file,true);
//          		 fw.write(role + "\n");
//          		 fw.close();
//		  	     }catch(IOException e2) {
//		  	    	 System.out.println(e2);
//		  	     }

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

	    	String succ ="select *  from Success ";
	  	      try (PreparedStatement tmt = conn.prepareStatement(succ);) {

		  	      PreparedStatement p = conn.prepareStatement(succ);
		  	      ResultSet re5 = p.executeQuery();
		  	      while (re5.next()) {
		  	       success = re5.getString("FeatureName");
		  	      }
	  	        }



	    	String sql ="select *  from ErrorSize order by id";
	  	      try (PreparedStatement pstmt = conn.prepareStatement(sql);) {

		  	      PreparedStatement p = conn.prepareStatement(sql);
		  	      ResultSet re = p.executeQuery();
		  	       r = re.getInt("size");
		  	       while(re.next()) {
		  	    	   if(r > re.getInt("size")) {
		  	    		   r = re.getInt("size");
		  	    		   list.clear();
		  	    		   list.add(re.getInt("id"));
		  	    	   }
		  	    	   else if(r == re.getInt("size")) {
		  	    		   list.add(re.getInt("id"));
		  	    	   }

		  	    	 }
	  	        }
	  	     if(success != null) {
	  	    	try {
	  	    	File file = new File("C:\\pleiades10月13日\\pleiades\\workspace\\Union3\\Result\\result.txt");
           		 FileWriter fw = new FileWriter(file,true);
           		 fw.write("no PatternError \n");
           		 fw.close();
	  	    	 System.out.println("no PatternError");
	  	    	 //System.exit(0);
		  	     }catch(IOException e2) {
		  	    	 System.out.println(e2);
		  	     }
	  	     }
	  	     else {
		  	     System.out.println("結果");
		  	     int c = 1;
		  	     for(int i=0;i<list.size();i++) {
		 	        String s ="select distinct Error.id,Error.result,Fix.message from Error,Fix where Error.fix == Fix.id AND Error.id =" + list.get(i);
		 	         PreparedStatement p1 = conn.prepareStatement(s);
		             try (ResultSet rs = p1.executeQuery()) {
		            	 try {
		            		 File file = new File("C:\\pleiades10月13日\\pleiades\\workspace\\Union3\\Result\\result.txt");
		            		 FileWriter fw = new FileWriter(file,true);
		            		 fw.write("error number: " + c +"\n");
//		            	 System.out.println("error number: " + c);
		 	        	while (rs.next()) {
		 	        		fw.write("id�F " + rs.getInt("id"));
//		 	        		fw.write("   " + rs.getString("result"));
		 	        		fw.write("   " + rs.getString("message"));
		 	        		fw.write("\n");
//		 	                System.out.format("id�F%s", rs.getInt("id") + " ");
//		 	                System.out.format(" %s", rs.getString("result") + " ");
//		 	               System.out.format("   %s", rs.getString("message") + " ");
//		 	                System.out.println();
		 	        	}

		             c++;
		             fw.write("------------------------------------------------------------------");
		             fw.write("\n");
		             fw.close();
//		             System.out.println("------------------------------------------------------------------");

//		  	   System.out.println();
		  	     }catch(IOException e2) {
		  	    	 System.out.println(e2);
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
