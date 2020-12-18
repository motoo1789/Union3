package makererrormap;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


import marker.JumpMarker;
import swt_resultsave.SWTResultsave;

public class CreateErrorMessage {

	Table table;
	TableItem item;
	GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	private String[] data = {"クラス名","原因","解決策"};
	private int[] tableWidth = {120,250,250};

	SWTResultsave saveObject = SWTResultsave.getInstance();

	CreateErrorMessage(Composite container, TabFolder tabFolder) {

		gd.heightHint = 95;
//		gd.widthHint = 600;

		TableViewer viewer = new TableViewer(container,SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		table = viewer.getTable();
		table.setLayoutData(gd);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		for(int i = 0; i < data.length; i++)
		{
			TableColumn col = new TableColumn(table,SWT.LEFT);
			col.setText(data[i]);
			col.setWidth(tableWidth[i]);
		}

		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int select = table.getSelectionIndex();
				TableItem[] item = table.getSelection();
				System.out.println(item[0].getText(0) + " " + item[0].getText(1) + " " + item[0].getText(2));

				JumpMarker jump = new JumpMarker();
				jump.getMarker(item[0].getText(0));
			}
		});

		table.addMouseTrackListener(new MouseTrackListener() {

			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				String date = saveObject.getDate();
				String moveMouse = "ErrorMessageにのった:";

				saveObject.writeresult(moveMouse + date);
			}

			@Override
			public void mouseExit(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ

				String date = saveObject.getDate();
				String moveMouse = "ErrorMessageから離れた:";

				saveObject.writeresult(moveMouse + date);
			}

			@Override
			public void mouseHover(MouseEvent e) {
				// TODO 自動生成されたメソッド・スタブ

			}

		});
	}

	Table getTable() {
		return table;
	}

}