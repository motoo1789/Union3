package views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class CreateComboBox {

	Combo combobox;
	GridData gd;

	public CreateComboBox(Composite container) {
		gd = new GridData();
		gd.horizontalSpan = 1;
		combobox = new Combo(container,SWT.READ_ONLY);
		combobox.add("Composite");
		combobox.add("Factory Method");
		combobox.add("Adapter");
		combobox.setLayoutData(gd);
	}

	Combo getCombo() {
		return combobox;
	}
}
