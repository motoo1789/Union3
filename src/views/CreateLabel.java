package views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CreateLabel {

	Label label;

	public CreateLabel(Composite container) {
		label = new Label(container,SWT.NULL);
		label.setText("検出中です何も操作はしないでください");
		label.setEnabled(false);
	}

	public void setLabel(String message) {
		label.setText(message);
	}

	public void setEnabled(boolean enabled) {
		label.setEnabled(enabled);
	}
}
