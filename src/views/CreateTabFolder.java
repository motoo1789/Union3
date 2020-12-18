package views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;

public class CreateTabFolder {

	private Composite container;
	TabFolder tabFolder;

	GridData gd = new GridData(GridData.FILL_HORIZONTAL);

	public CreateTabFolder(Composite container) {

		this.container = container;

		gd.heightHint = 350;
//		gd.widthHint = 600;
		gd.horizontalSpan = 2;

		tabFolder = new TabFolder(container,SWT.NONE);
		tabFolder.setLayoutData(gd);

	}

	public TabFolder gteTabFolder() {
		return tabFolder;
	}
}
