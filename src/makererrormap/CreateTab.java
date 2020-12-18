package makererrormap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import conversion.Conversion;
import database.SQLiteSample;
import property.MapProperty;

public class CreateTab {

	Composite container;
	Creator tablecreator;
	TabFolder tabFolder;
	GridData gd = new GridData(GridData.FILL_HORIZONTAL);

	private static MapProperty propertyObject = MapProperty.getInstance();
	private static SQLiteSample sql = SQLiteSample.getInstance();


	public CreateTab(Composite container,TabFolder tabFolder) {

		this.container = container;
		this.tabFolder = tabFolder;

		gd.heightHint = 300;
	}


	public void createTab() {

		ArrayList<Map<String,String>> tableData = new ArrayList<Map<String,String>>();
		TabItem tabitem = new TabItem(tabFolder,SWT.NONE);

		//composite と　map、 message の初期化
		Composite composite = new Composite(tabFolder,SWT.NULL);
		composite.setLayout(new GridLayout(1,false));

		//WTF_tableの中身を取得
		tableData = sql.getWTF_tableTable();
		if(tableData.size() == 0)
		{
			String ErrorSize_id = sql.getErrorSize_id();
			//tableを作成するオブジェクトの作成
			tablecreator = new CreateTable(composite,tabFolder);
			ProductTable tablemap = tablecreator.createProduct();
			tablemap.setClassname(ErrorSize_id);
			tablemap.setWidget();//tableDataを追加
			tabitem.setText("タブ1");
			tabitem.setControl(composite);
			return ;
		}

		Map<String,String> fast = tableData.get(0);
		String role_id = fast.get("Role_id");

		//tableを作成するオブジェクトの作成
		tablecreator = new CreateTable(composite,tabFolder);
		ProductTable tablemap = tablecreator.createProduct();

		//ErrorMessageを作成するオブジェクトの作成
		CreateErrorMessage cMessage;
		cMessage = new CreateErrorMessage(composite,tabFolder);


		//TableItem itemMessage = cMessage.getItem();
		Iterator it = tableData.iterator();
		int count = 1;

		while(it.hasNext())
		{
			Map<String,String> errordata = (Map<String,String>)it.next();
			String nextroleID = errordata.get("Role_id");

			if(role_id.equals(nextroleID) == true)
			{
				System.out.println("前と同じ");
				tablemap.setClassname(role_id);
				tablemap.makeTableData(errordata); //〇×の更新
				sendErrorMessage(errordata,cMessage.getTable()); //Messageの追加
				if(it.hasNext() == false)
				{
					System.out.println("iteratorの終わりになので最後のデータを入れる");
					tablemap.setWidget();//tableDataを追加
					tabitem.setText("タブ" + count++);
					tabitem.setControl(composite);
				}
			}
			else
			{
				System.out.println("前のやつと違う");
				tablemap.setWidget(); //tableData(〇×とクラス名が入ってるやつ)を追加
				tabitem.setText("タブ" + count++);
				tabitem.setControl(composite);

				composite = new Composite(tabFolder,SWT.NULL);
				composite.setLayout(new GridLayout(1,false));

				tablecreator = new CreateTable(composite,tabFolder);
				tablemap = tablecreator.createProduct();
				tabitem = new TabItem(tabFolder,SWT.NONE);

				//ErrorMessageを作成するオブジェクトの作成
				cMessage = new CreateErrorMessage(composite,tabFolder);

				role_id = nextroleID;
				tablemap.setClassname(role_id);
				tablemap.makeTableData(errordata); //〇×の更新
				sendErrorMessage(errordata,cMessage.getTable()); //Messageの追加

				if(it.hasNext() == false)
				{
					System.out.println("iteratorの終わりになので最後のデータを入れる");
					tablemap.setWidget();//tableDataを追加
					tabitem.setText("タブ" + count++);
					tabitem.setControl(composite);
				}
			}

		}
	}

	//Message
	private void sendErrorMessage(Map<String,String> errordata, Table table) {

		TableItem item = new TableItem(table,SWT.NULL); //入れるやつ
		final String role_id = errordata.get("Role_id");
		final String error_id = errordata.get("Error_id");
		final String fix_id = errordata.get("Fix_id");
		String classname = "";
		String result = "";
		String solution = "";

		String errorRoleName = propertyObject.crossperty("ErrorMessage_readRoleName", fix_id);
		String errorClassName = getErrorClassName(errorRoleName,role_id);

		//Errorテーブル result
		String errortable_result = sql.getErrorTableResult(error_id,fix_id);

		//違反理由とってくる
		solution = propertyObject.crossperty("Fix", fix_id);
		String replacementSolution = Conversion.replacementSolution(solution,fix_id,role_id);

		//Tableへ追加
		item.setText(0,errorClassName);
		item.setText(1,errortable_result);
		item.setText(2,replacementSolution);

	}

	//Roleのテーブルからクラス名を取得
	private static String getErrorClassName(final String rolename, final String role_id) {

		ArrayList<Map<String,String>> roleData = new ArrayList<Map<String,String>>();
		roleData = sql.getRoleTable(role_id);

		Iterator it = roleData.iterator();
		while(it.hasNext())
		{
			Map<String,String> role = (Map<String,String>) it.next();
			if(role.get("role").equals(rolename))
				return role.get("name");
		}

		return "";

	}
}
