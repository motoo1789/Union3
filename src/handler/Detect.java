package handler;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import marker.CreateMarker;
import marker.DeleteMarker;
import swt_resultsave.SWTResultsave;
import test.Main;
import views.View;


public class Detect implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
	}

	@Override
	public void dispose() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		View view = null;
		try {
			view = (View) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("Union3.ErrorView");
		} catch (PartInitException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		System.out.println(view.getDirectory());
		/*
		 * Textフィールドに表示されてるディレクトリがStringで取得できます
		 * View view = (View) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("Union.ErrorView");
		 * String directory = view.getDirectory();
		 *
		 * 選択されたパターンがとりたい時は↓
		 * String patternname = view.getPattern();
		 */
		//検出の開始
		view.detectingMessage();
		try {
			Main mainObj = new Main();
			mainObj.process();
		} catch (IOException | PartInitException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		//可視化
		update(view);

		return null;
	}

	private void update(View view) {
		SWTResultsave saveObject = SWTResultsave.getInstance();
		try {
			String date = saveObject.getDate();
			String moveMouse = "実行ボタンが押された";

			saveObject.writeresult("");
			saveObject.writeresult(moveMouse + date);
			// �m�F�p
			view.columsDispone();			// Table削除
			System.out.println("テーブルの削除");
			view.updateTable();				// Table追加
			System.out.println("テーブルの追加");
			DeleteMarker.deleteMarker();	// マーカー削除
			System.out.println("マーカーの削除");
			CreateMarker.createMarker();	// マーカー追加
			System.out.println("マーカーの追加");

			//検出の終わり
			view.notDetecingMessage();

		} catch (PartInitException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

	}

	@Override
	public boolean isEnabled() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
