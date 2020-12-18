package database;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import org.eclipse.core.resources.IFile;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;


import marker.GetResource;
import property.ViolationProperty;


public class SQLiteSample extends ClassLoader{

	private static SQLiteSample singleton = new SQLiteSample();
	private static InputStream inputStream = null;
	private static String tableName = "WTF_table";
	private static String dbPath;
    private static Connection conn = null;
    private static Statement statement = null;

    private SQLiteSample() {

    	//データベース接続のためのディレクトリを取得
    	String projectName = GetResource.getProjectname();
    	IResource resouece = GetResource.getResource(projectName);
    	dbPath = GetResource.getDatabase(resouece);

    }

    public static SQLiteSample getInstance() {
    	return singleton;
    }

	//接続をしてWTR_tableテーブルの中身を取得
	public static ArrayList<Map<String,String>> getWTF_tableTable() {
		final String TABLENAME = "WTF_table";
		final String[] KOLUM = {"id","Error_id","Role_id","Fix_id"}; //�@union_test
		ArrayList<Map<String,String>> tableData = new ArrayList<Map<String,String>>();	//送信するデータ格納用

        try {
        	Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = conn.createStatement();

            String sql = "select distinct * from " + TABLENAME + " order by Role_id;";
            ResultSet rs = statement.executeQuery(sql);

            //Mapにテーブルデータ保存
            int columCount = rs.getMetaData().getColumnCount();	//columの数
            while(rs.next()) {

            	Map<String,String> data = new HashMap<String,String>();
            	for(int num = 0; num < columCount; num++)
            	{
            		data.put(KOLUM[num],rs.getString(KOLUM[num]));
            	}

            	tableData.add(data);

            }

        } catch (SQLException se) {
            System.out.println(se.getMessage());
            System.out.println("SQLException");

        } catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                	System.out.println("SQLException");
                }
            }
        }

        return tableData;
	}

	//ErrorMesageを作るとに必要だと思う
	public static  ArrayList<Map<String,String>> getRoleTable(final String roleNumber) {
		final String TABLENAME = "Role";
		final String[] KOLUM = {"id","role","name"}; //�@union_test
		ArrayList<Map<String,String>> tableData = new ArrayList<Map<String,String>>();	//送信するデータ格納用

		if(roleNumber != null)
		{
			try {
	        	Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	            statement = conn.createStatement();


	            String sql = "select * from " + TABLENAME + " where id = " + roleNumber + ";";
	            ResultSet rs = statement.executeQuery(sql);

	            //Mapにテーブルデータ保存
	            int columCount = rs.getMetaData().getColumnCount();	//columの数
	            while(rs.next()) {

	            	Map<String,String> data = new HashMap<String,String>();
	            	for(int num = 0; num < columCount; num++)
	            	{
	            		data.put(KOLUM[num],rs.getString(KOLUM[num]));
	            	}

	            	tableData.add(data);

	            }

	        } catch (SQLException se) {
	            System.out.println(se.getMessage());
	            System.out.println("SQLException");

	        } catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                	System.out.println("SQLException");
	                }
	            }
	        }
		}
		else
		{
			System.out.println("getRoleTableから：引数がnull");
		}

        return tableData;
	}

	//ErrorMapを作るとに必要だと思う
	public static  ArrayList<Map<String,String>> getErrorTable(final String errorID) {
		final String TABLENAME = "Error";
		final String[] KOLUM = {"id","result","fix"}; //�@union_test
		ArrayList<Map<String,String>> tableData = new ArrayList<Map<String,String>>();	//送信するデータ格納用

		if(errorID != null)
		{
			try {
	        	Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	            statement = conn.createStatement();


	            String sql = "select * from " + TABLENAME + " where id = " + errorID + ";";
	            ResultSet rs = statement.executeQuery(sql);

	            //Mapにテーブルデータ保存
	            int columCount = rs.getMetaData().getColumnCount();	//columの数
	            while(rs.next()) {

	            	Map<String,String> data = new HashMap<String,String>();
	            	for(int num = 0; num < columCount; num++)
	            	{
	            		data.put(KOLUM[num],rs.getString(KOLUM[num]));
	            	}

	            	tableData.add(data);

	            }

	        } catch (SQLException se) {
	            System.out.println(se.getMessage());
	            System.out.println("SQLException");

	        } catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                	System.out.println("SQLException");
	                }
	            }
	        }
		}
		else
		{
			System.out.println("getErrorTableから：引数がnull");
		}

        return tableData;
	}

	//Errorテーブルのresultだけ取得
	public static  String getErrorTableResult(final String errorID ,final String fixID) {
		final String TABLENAME = "Error";
		final String RESILT = "result";
		String result = "";

		if(errorID != null)
		{
			try {
	        	Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	            statement = conn.createStatement();


	            String sql = "select * from " + TABLENAME + " where id = " + errorID + " and fix = " + fixID + ";";
	            ResultSet rs = statement.executeQuery(sql);

	            //Mapにテーブルデータ保存
           		result = rs.getString(RESILT);


	        } catch (SQLException se) {
	            System.out.println(se.getMessage());
	            System.out.println("SQLException");

	        } catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                	System.out.println("SQLException");
	                }
	            }
	        }
		}
		else
		{
			System.out.println("getErrorTableから：引数がnull");
		}

        return result;
	}

	public static  String getRoleTableName(final String roleID ,final String roleName) {
		final String TABLENAME = "Role";
		final String NAME = "name";
		String result = "";

		if(roleID != null && roleName != null)
		{
			try {
	        	Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	            statement = conn.createStatement();

	            String sql = "select * from " + TABLENAME + " where id = " + roleID + " and role = \"" + roleName + "\";";
	            ResultSet rs = statement.executeQuery(sql);

	            //Mapにテーブルデータ保存
           		result = rs.getString(NAME);


	        } catch (SQLException se) {
	            System.out.println(se.getMessage());
	            System.out.println("SQLException");

	        } catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} finally {
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                	System.out.println("SQLException");
	                }
	            }
	        }
		}
		else
		{
			System.out.println("getRoleTableから：引数がnull");
		}

        return result;
	}

	public static String getErrorSize_id() {
		final String TABLENAME = "ErrorSize";
		final String ID = "id";
		String result = "";


		try {
        	Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = conn.createStatement();

            String sql = "select * from " + TABLENAME + " where size = 0;";
            ResultSet rs = statement.executeQuery(sql);

            //Mapにテーブルデータ保存
       		result = rs.getString(ID);


        } catch (SQLException se) {
            System.out.println(se.getMessage());
            System.out.println("SQLException");

        } catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                	System.out.println("SQLException");
                }
            }
        }

        return result;
	}
}

