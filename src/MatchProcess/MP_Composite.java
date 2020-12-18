package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import database.RoleCounter;
import marker.GetResource;

public class MP_Composite implements MP_Strategy{

	RoleCounter counterObject = RoleCounter.getInstance();
	IdDocking id = IdDocking.getInstance();
	private int count = 1;

	public void match(Map map) {

		count = counterObject.getCount();

		ArrayList<String> per = new ArrayList();
		ArrayList<Integer> cnt = new ArrayList();

		ArrayList feature = new ArrayList();
		ArrayList sender = new ArrayList();
		ArrayList sendee = new ArrayList();

		ArrayList<String> success_f = new ArrayList<String>();
		ArrayList<String> success_er = new ArrayList<String>();
		ArrayList<String> success_ee = new ArrayList<String>();

		ArrayList<String> rolelist = new ArrayList<String>();

		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;

		Connection conn = null;
		Map<String,String> role = new HashMap<>();

		Permutation p = new Permutation();
		p.permutation("ABC", "");
		per.addAll(p.getper());
		System.out.print("from MP_Composite per length->" + per);
		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

//	         Initialization inza = new Initialization();
//	         inza.initaialization();
	         RoleRegistration r2 = new RoleRegistration();
	         Composite_Role composite = new Composite_Role();
	         ResultRegistration rr = new ResultRegistration();
	         ErrorSize es = new ErrorSize();
	         SuccessRegistration sr = new SuccessRegistration();

		     String sql ="select * from FeatureConrrespondence" ;
		     PreparedStatement p2 = conn.prepareStatement(sql);
	         for(int i=0; i<per.size();i++) {
	        	 rolelist.add("Component");
	        	 rolelist.add(map.get(per.get(i).substring(0, 1)).toString());
	        	 rolelist.add("Leaf");
	        	 rolelist.add(map.get(per.get(i).substring(1, 2)).toString());
	        	 rolelist.add("Composite");
	        	 rolelist.add(map.get(per.get(i).substring(2, 3)).toString());



	     		role.put("Component" ,(String) map.get(per.get(i).substring(0, 1)));
	    		role.put("Leaf" ,(String) map.get(per.get(i).substring(1, 2)));
	    		role.put("Composite" ,(String) map.get(per.get(i).substring(2, 3)));

	         	try (ResultSet rs = p2.executeQuery()) {
	         		while (rs.next()) {
	         			if(rs.getString("FeatureName").equals("Inheritance") &&
	         				rs.getString("Sender").equals(per.get(i).substring(1, 2) ) &&
	         				rs.getString("Sendee").equals(per.get(i).substring(0, 1)))  {
	         					flag = true;
	         					success_f.add(rs.getString("FeatureName"));
	         					success_er.add(rs.getString("sender"));
	         					success_ee.add(rs.getString("sendee"));
	         			}
	         			//

	         			if(rs.getString("FeatureName").equals("Inheritance") &&
		         				rs.getString("Sender").equals(per.get(i).substring(2, 3)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(0, 1)))  {
		         					flag1 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}
	         			//

	         			if(rs.getString("FeatureName").equals("Aggregation") &&
		         				rs.getString("Sender").equals(per.get(i).substring(2, 3)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(0, 1)))  {
		         					flag2 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}

	         			if(rs.getString("FeatureName").equals("OverRide") &&
		         				rs.getString("Sender").equals(per.get(i).substring(1, 2)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(0, 1))) {
		         					flag3 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}

	         			if(rs.getString("FeatureName").equals("OverRide") &&
		         				rs.getString("Sender").equals(per.get(i).substring(2, 3)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(0, 1))) {
		         					flag4 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}

	         		}

	         		if(flag == false) {
	         			feature.add("Inheritance");
	         			sender.add(map.get(per.get(i).substring(1, 2)));
	         			sendee.add(map.get( per.get(i).substring(0, 1)));
	         			cnt.add(7);
	         		}

	         		if(flag1 == false) {
	         			feature.add("Inheritance");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get(per.get(i).substring(0, 1)));
	         			cnt.add(8);
	         		}

	         		if(flag2 == false) {
	         			feature.add("Aggregation");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get(per.get(i).substring(0, 1)));
	         			cnt.add(9);
	         		}

	         		if(flag3 == false) {
	         			feature.add("OverRide");
	         			sender.add(map.get(per.get(i).substring(1, 2)));
	         			sendee.add(map.get(per.get(i).substring(0, 1)));
	         			cnt.add(10);
	         		}

	         		if(flag4 == false) {
	         			feature.add("OverRide");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get(per.get(i).substring(0, 1)));
	         			cnt.add(11);
	         		}

	     			r2.register(count, rolelist);

	     			composite.register(count,role);
	     			rr.register(feature, sender, sendee, cnt,count);
	     			es.sizeregister(feature.size(), count);
	     			if(flag == true && flag1 == true && flag2 == true && flag3 == true && flag4 == true ) {
	         			 sr.s_register(success_f, success_er, success_ee, count);
	         		}
	     			count++;
	     			System.out.println("from MP_Composition count->" + count);
		         	flag = false;
		         	flag1 = false;
		         	flag2 = false;
		         	flag3 = false;
		         	flag4 = false;

	         		feature.clear();
	         		sender.clear();
	         		sendee.clear();
	         		cnt.clear();

	         		success_f.clear();
	         		success_er.clear();
	         		success_ee.clear();

	         		rolelist.clear();

		         }
		         role.remove("Component");
		         role.remove("Leaf");
		         role.remove("Composite");
	        }
	        counterObject.setCount(count);


	         id.docking();
	         // �Ō�̏����@�p�^�[���G���[�̌����ƏC����\������
	         Display dis = new Display();
	         dis.show();

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



