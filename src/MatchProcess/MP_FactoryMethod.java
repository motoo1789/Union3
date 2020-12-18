package MatchProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

import database.RoleCounter;
import marker.GetResource;

public class MP_FactoryMethod implements MP_Strategy{

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
		boolean flag5 = false;

		Map<String,String> role = new HashMap<>();


		Connection conn = null;

		Permutation p = new Permutation();
		p.permutation("ABCD", "");
		per.addAll(p.getper());

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

//	        Initialization inza = new Initialization();
//	        inza.initaialization();
	        RoleRegistration r2 = new RoleRegistration();
	        FactoryMethod_Role factory = new FactoryMethod_Role();
	        ResultRegistration rr = new ResultRegistration();
	        ErrorSize es = new ErrorSize();
	        SuccessRegistration sr = new SuccessRegistration();

		    String sql ="select * from FeatureConrrespondence" ;
		    PreparedStatement p2 = conn.prepareStatement(sql);
	        for(int i=0; i < per.size();i++) {
	        	rolelist.add("Creator");
	        	rolelist.add(map.get(per.get(i).substring(0, 1)).toString());
	        	rolelist.add("Product");
	        	rolelist.add(map.get(per.get(i).substring(1, 2)).toString());
	        	rolelist.add("ConcreteCreator");
	        	rolelist.add(map.get(per.get(i).substring(2, 3)).toString());
	        	rolelist.add("ConcreteProduct");
	        	rolelist.add(map.get(per.get(i).substring(3, 4)).toString());

	     		role.put("Creator" ,map.get(per.get(i).substring(0, 1)).toString());
	    		role.put("Product" ,map.get(per.get(i).substring(1, 2)).toString());
	    		role.put("ConcreteCreator" ,map.get(per.get(i).substring(2, 3)).toString());
	    		role.put("ConcreteProduct" ,map.get(per.get(i).substring(3, 4)).toString());

	         	try (ResultSet rs = p2.executeQuery()) {
	         		while (rs.next()) {
	         			if(rs.getString("FeatureName").equals("Inheritance") &&
		         			rs.getString("Sender").equals(per.get(i).substring(2, 3) ) &&
		         			rs.getString("Sendee").equals(per.get(i).substring(0, 1))) {
		         				flag = true;
		         				success_f.add(rs.getString("FeatureName"));
		         				success_er.add(rs.getString("sender"));
		         				success_ee.add(rs.getString("sendee"));
		         		}

		         		if(rs.getString("FeatureName").equals("Inheritance") &&
			         		rs.getString("Sender").equals(per.get(i).substring(3, 4)) &&
			         		rs.getString("Sendee").equals(per.get(i).substring(1, 2))) {
			         			flag1 = true;
			         			success_f.add(rs.getString("FeatureName"));
			         			success_er.add(rs.getString("sender"));
			         			success_ee.add(rs.getString("sendee"));
		         		}

	         			if(rs.getString("FeatureName").equals("OverRide") &&
		         				rs.getString("Sender").equals(per.get(i).substring(2, 3)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(0, 1)))  {
		         					flag2 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}

	         			if(rs.getString("FeatureName").equals("OverRide") &&
		         				rs.getString("Sender").equals(per.get(i).substring(3, 4)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(1, 2)))  {
		         					flag3 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}

	         			if(rs.getString("FeatureName").equals("Instantiate") &&
		         				rs.getString("Sender").equals(per.get(i).substring(2, 3)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(3, 4))) {
		         					flag4 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}

	         			if(rs.getString("FeatureName").equals("WrapperObject") &&
		         				rs.getString("Sender").equals(per.get(i).substring(0, 1)) &&
		         				rs.getString("Sendee").equals(per.get(i).substring(1, 2))) {
		         					flag5 = true;
		         					success_f.add(rs.getString("FeatureName"));
		         					success_er.add(rs.getString("sender"));
		         					success_ee.add(rs.getString("sendee"));
	         			}
	         		}

	         		if(flag == false) {
	         			feature.add("Inheritance");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get( per.get(i).substring(0, 1)));
	         			cnt.add(1);
	         		}

	         		if(flag1 == false) {
	         			feature.add("Inheritance");
	         			sender.add(map.get(per.get(i).substring(3, 4)));
	         			sendee.add(map.get(per.get(i).substring(1, 2)));
	         			cnt.add(2);
	         		}

	         		if(flag2 == false) {
	         			feature.add("OverRide");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get(per.get(i).substring(0, 1)));
	         			cnt.add(3);
	         		}

	         		if(flag3 == false) {
	         			feature.add("OverRide");
	         			sender.add(map.get(per.get(i).substring(3, 4)));
	         			sendee.add(map.get(per.get(i).substring(1, 2)));
	         			cnt.add(4);
	         		}

	         		if(flag4 == false) {
	         			feature.add("Instantiate");
	         			sender.add(map.get(per.get(i).substring(2, 3)));
	         			sendee.add(map.get(per.get(i).substring(3, 4)));
	         			cnt.add(5);
	         		}

	         		if(flag5 == false) {
	         			feature.add("WrapObject");
	         			sender.add(map.get(per.get(i).substring(0, 1)));
	         			sendee.add(map.get(per.get(i).substring(1, 2)));
	         			cnt.add(6);
	         		}

         			r2.register(count, rolelist);
         			factory.register(count,role);
         			rr.register(feature, sender, sendee, cnt,count);
         			es.sizeregister(feature.size(), count);
         			if(flag == true && flag1 == true && flag2 == true && flag3 == true && flag4 == true && flag5 == true) {
         			 sr.s_register(success_f, success_er, success_ee, count);
         			}
         			count++;
         			System.out.println("from MP_Composition count->" + count);
	         		flag = false;
	         		flag1 = false;
	         		flag2 = false;
	         		flag3 = false;
	         		flag4 = false;
	         		flag5 = false;

	         		feature.clear();
	         		sender.clear();
	         		sendee.clear();
	         		cnt.clear();

	         		success_f.clear();
	         		success_er.clear();
	         		success_ee.clear();

	         		rolelist.clear();
	         	}

	         	role.remove("Creator");
	         	role.remove("Product");
	         	role.remove("ConcreteCreator");
	         	role.remove("ConcreteProduct");
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
