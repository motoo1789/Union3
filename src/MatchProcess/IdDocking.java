package MatchProcess;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class IdDocking {

	private static IdDocking singleton = new IdDocking();
	private ArrayList<Integer> idlist = new ArrayList<Integer>();
	private ArrayList<Integer> list = new ArrayList();
	private int r;

	private IdDocking() {

	}

	public static IdDocking getInstance() {
		return singleton;
	}


	public void docking() {

		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);



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

	public void insertWTF_table() {
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			for(int i=0;i<list.size();i++) {
	 	        String s ="select distinct Error.id,Role.id,Fix.id,Error.fix  from Error,Role,Fix where "
	 	        		+ "Error.id == Role.id And Error.fix == Fix.id AND Error.id =" + list.get(i);
	 	         PreparedStatement p1 = conn.prepareStatement(s);
	             try (ResultSet rs = p1.executeQuery()) {
	 	        	while (rs.next()) {
	 	        		idlist.add(rs.getInt("id"));
	 	        		idlist.add(rs.getInt("id"));
	 	        		idlist.add(rs.getInt("fix"));
	 	        	}
	 	        }
	  	    }



	    	String sql = "insert into WTF_table(id,Error_id,Role_id,Fix_id)VALUES(?,?,?,?)";
			int cnt = 1;
	        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
	        	for(int i=0;i<idlist.size();i+=3) {
					pstmt.setInt(1, cnt);
					pstmt.setInt(2, idlist.get(i));
					pstmt.setInt(3, idlist.get(i+1));
					pstmt.setInt(4, idlist.get(i+2));
					pstmt.executeUpdate();
	        	}
	        	cnt++;
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

	public void clearList() {
		idlist.clear();
		list.clear();
	}
}
