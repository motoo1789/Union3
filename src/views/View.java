package views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import makererrormap.ErrorMapMaker;


public class View extends ViewPart {
	Table ErrorMessage;
	Button button;
	Combo combo;
	Text text;
	Text detectingmessage;
	TabFolder tabFolder;
	String path;
	CreateDirectoryButton cButton;
	CreateComboBox cCombo;
	CreateLabel cLabel;
	CreateTabFolder cTabFolder;
	Composite container;
	MessageDialog dialog;

	public View() {
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		System.out.println("Viewクラスコンストラクタ");
	}

	@Override
	public void createPartControl(Composite parent) {

		ScrolledComposite scroll = new ScrolledComposite(parent,SWT.H_SCROLL | SWT.V_SCROLL);
		scroll.setLayout(new FillLayout());
		scroll.setMinSize(750, 600);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		container = new Composite(scroll,SWT.NONE);
		container.setLayout(new GridLayout(2,false));

		//検出中のメッセージを表示
		cLabel = new CreateLabel(container);

		//タブフォルダー
		cTabFolder = new CreateTabFolder(container);
		tabFolder = cTabFolder.gteTabFolder();

		//ユーザーが選択するSWT
		cButton = new CreateDirectoryButton(container);
		cCombo = new CreateComboBox(container);

		combo = cCombo.getCombo();
		button = cButton.getDirectoryButton();
		text = cButton.getText();

		scroll.setContent(container);
	}

	public void updateTable() {
		ErrorMapUpdate();
	}

	private void ErrorMapUpdate() {
		ErrorMapMaker.makeErrorMap(container, tabFolder);
	}

	//Table��Colum�����ׂď���
	public void columsDispone() {
		ErrorMapDispone();
	}

	private void ErrorMapDispone() {

		//Mapの初期化
		TabItem[] ts = tabFolder.getItems();

		for(int i = 0; i < ts.length; i++)
		{
			ts[i].dispose();
		}

	}

	public void detectingMessage() {
		cLabel.setEnabled(true);

	}
	public void notDetecingMessage() {
		cLabel.setEnabled(false);
	}

	//combo����I������Ă�����̂��擾
	public String getSelectPattern() {
		return combo.getText();
	}

	public String getDirectory() {
		return text.getText();
	}

	@Override
	public void setFocus() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}