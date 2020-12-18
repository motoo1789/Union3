package makererrormap;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;


public class ErrorMapMaker {

	private ErrorMapMaker() {

	}

	public static void makeErrorMap(Composite container,TabFolder tabFolder) {

		//ErrorMap�ւ̃f�[�^�ǉ�
		CreateTab tab = new CreateTab(container,tabFolder);
		tab.createTab();

	}
}
