package makererrormap;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import views.View;

public class CreateTable extends Creator{

	Composite container;
	TabFolder tabfolder;

	public CreateTable(Composite container,TabFolder tabfolder) {

		this.container = container;
		this.tabfolder = tabfolder;
	}

	@Override
	public ProductTable createProduct() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		String patternname = new String();
		ProductTable tableObject = null;

		try {
			// ConboBox�őI�����ꂽ�p�^�[���𒲂ׂ�
			View view = (View) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("Union3.ErrorView");
			patternname = view.getSelectPattern();

		} catch (PartInitException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

		if		(patternname.equals("Factory Method"))
		{

			tableObject = new CreateFactoryMethodTable(container,tabfolder);
		}
		else if	(patternname.equals("Composite"))
		{
			tableObject = new CreateCompositeTable(container,tabfolder);
		}
		else if	(patternname.equals("Adapter"))
		{
			tableObject = new CreateAdapterTable(container,tabfolder);
		}

		return tableObject;
	}

}
