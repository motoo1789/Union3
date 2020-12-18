package makererrormap;

import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import database.SQLiteSample;
import property.MapProperty;
import swt_resultsave.SWTResultsave;

public class CreateAdapterTable  implements ProductTable{

	private SQLiteSample sql = SQLiteSample.getInstance();
	private Composite container;
	private TabFolder tabfolder;
	private GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	private Table table;

	private SWTResultsave saveObject = SWTResultsave.getInstance();


	final String KEY_ERROR_ID = "Error_id";
	final String KEY_FIX_ID = "Fix_id";
	final String Role[] = {"Micro-Structure＼Class","Target","Adaptee","Adapter"};
	final String MicroStructure[] = {"Inheritance", "Forwarding", "OverRide","Classname"};

	String[][] TableData = {
			{" "," ","○"},
			{" "," ","○"},
			{" "," ","○"},
			{" "," "," "}
	};

	private MapProperty pampropertyObjct = MapProperty.getInstance();

	public CreateAdapterTable(Composite container,TabFolder tabfolder) {

		this.tabfolder = tabfolder;
		this.container = container;
		gd.heightHint = 150;
//		gd.widthHint = 600;

		TableViewer viewer = new TableViewer(container,SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		table = viewer.getTable();
		table.setLayoutData(gd);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.addMouseTrackListener(new MouseTrackListener() {

			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				String date = saveObject.getDate();
				String moveMouse = "AdapterのMapにのった:";

				saveObject.writeresult(moveMouse + date);
			}

			@Override
			public void mouseExit(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ

				String date = saveObject.getDate();
				String moveMouse = "AdapterのMapから離れた:";

				saveObject.writeresult(moveMouse + date);

			}

			@Override
			public void mouseHover(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ

			}

		});

	}

	@Override
	public void makeTableData(Map<String, String> tabledata) {
		// TODO 自動生成されたメソッド・スタブ

		if(tabledata != null)
		{
			String Fix_id = tabledata.get(KEY_FIX_ID);		//プロパティファイルのキー取得
			String splitdata = pampropertyObjct.crossperty("Cross", Fix_id);	//プロパティファイルの読み込み
			String[] cross_position = splitdata.split(":",0);	//分割
			TableData[ Integer.parseInt(cross_position[0]) ][ Integer.parseInt(cross_position[1]) ] = "×";

		}
		else
		{
			System.out.println("Adapter:makeTableDataより引数がnull" );
		}
	}

	public void setClassname(String roleID) {

		if(TableData[MicroStructure.length - 1][0].equals(" "))
		{
			for(int i = 0; i < 3; i++)
			{
				TableData[MicroStructure.length - 1][i] = sql.getRoleTableName(roleID,Role[i + 1]);
			}
		}

	}

	public void setWidget() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u



		for(int i = 0; i < Role.length; i++)
		{
			TableColumn col = new TableColumn(table,SWT.LEFT);
			col.setText(Role[i]);
			col.setWidth(125);
		}

		for(int i = 1; i <= MicroStructure.length; i++)
		{
			TableItem item = new TableItem(table,SWT.NONE);
			item.setText(0, MicroStructure[i-1]);
			for(int j = 1; j <= 3; j++)
			{
				item.setText(j,TableData[i - 1][j - 1]);
			}
		}

	}

}
