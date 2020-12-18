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

public class MP_Adapter implements MP_Strategy{

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

		Map<String,String> role = new HashMap<>();
		Connection conn = null;

		Permutation p = new Permutation();
		p.permutation("ABC", "");
		per.addAll(p.getper());

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

//	         Initialization inza = new Initialization();
//	         inza.initaialization();
	         RoleRegistration r2 = new RoleRegistration();
	         Adapter_Role adapter = new Adapter_Role();
	         ResultRegistration rr = new ResultRegistration();
	         ErrorSize es = new ErrorSize();
	         SuccessRegistration sr = new SuccessRegistration();

		     String sql ="select * from FeatureConrrespondence" ;
		     PreparedStatement p2 = conn.prepareStatement(sql);
	         for(int i=0; i<per.size();i++) {
	        	 rolelist.add("Target");
	        	 rolelist.add(map.get(per.get(i).substring(0, 1)).toString());
	        	 rolelist.add("Adapter");
	        	 rolelist.add(map.get(per.get(i).substring(1, 2)).toString());
	        	 rolelist.add("Adaptee");
	        	 rolelist.add(map.get(per.get(i).substring(2, 3)).toString());

		     		role.put("Target" ,map.get(per.get(i).substring(0, 1)).toString());
		    		role.put("Adapter" ,map.get(per.get(i).substring(1, 2)).toString());
		    		role.put("Adaptee" ,map.get(per.get(i).substring(2, 3)).toString());
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

		         			if(rs.getString("FeatureName").equals("Forwarding") &&
			         				rs.getString("Sender").equals(per.get(i).substring(1, 2)) &&
			         				rs.getString("Sendee").equals(per.get(i).substring(2, 3)))  {
			         					flag1 = true;
			         					success_f.add(rs.getString("FeatureName"));
			         					success_er.add(rs.getString("sender"));
			         					success_ee.add(rs.getString("sendee"));
		         			}

		         			if(rs.getString("FeatureName").equals("OverRide") &&
			         				rs.getString("Sender").equals(per.get(i).substring(1, 2)) &&
			         				rs.getString("Sendee").equals(per.get(i).substring(0, 1))) {
			         					flag2 = true;
			         					success_f.add(rs.getString("FeatureName"));
			         					success_er.add(rs.getString("sender"));
			         					success_ee.add(rs.getString("sendee"));
		         			}

	         		}

	         		if(flag == false) {
	         			feature.add("Inheritance");
	         			sender.add(map.get(per.get(i).substring(1, 2)));
	         			sendee.add(map.get( per.get(i).substring(0, 1)));
	         			cnt.add(12);
	         		}

	         		if(flag1 == false) {
	         			feature.add("Forwarding");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get(per.get(i).substring(0, 1)));
	         			cnt.add(13);
	         		}

	         		if(flag2 == false) {
	         			feature.add("OverRide");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get(per.get(i).substring(0, 1)));
	         			cnt.add(14);
	         		}
	         			r2.register(count, rolelist);
	         			adapter.register(count, role);
	         			rr.register(feature, sender, sendee, cnt,count);
	         			es.sizeregister(feature.size(), count);
	         			if(flag == true && flag1 == true && flag2 == true ) {
		         			 sr.s_register(success_f, success_er, success_ee, count);
		         			}
	         			count++;

		         	flag = false;
		         	flag1 = false;
		         	flag2 = false;

	         		feature.clear();
	         		sender.clear();
	         		sendee.clear();
	         		cnt.clear();

	         		success_f.clear();
	         		success_er.clear();
	         		success_ee.clear();

	         		rolelist.clear();
	         		}
	         	role.remove("Target");
	         	role.remove("Adapter");
	         	role.remove("Adaptee");
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
