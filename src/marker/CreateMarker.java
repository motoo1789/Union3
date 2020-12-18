package marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import conversion.Conversion;
import database.SQLiteSample;
import property.MapProperty;
import views.View;

public class CreateMarker {

	//private ArrayList<String[]> tableData = new ArrayList<String[]>();
	private static SQLiteSample sql = SQLiteSample.getInstance();
	private static MapProperty propertyObject = MapProperty.getInstance();
	private static long markerID = 0;

	public static void createMarker() throws PartInitException {
		ArrayList<Map<String,String>> tableData = new ArrayList<Map<String,String>>();


		//SQLiteから検出結果をとってくる
		tableData = sql.getWTF_tableTable();

		//Map<String,String> sendData = splitRoleViolation(tableData);

		//プロジェクトの名前をとってくる
		String projectname = GetResource.getProjectname();
		IResource resource = GetResource.getResource(projectname);

		assignment(resource,tableData);

	}



	private static void assignment(IResource resource,ArrayList<Map<String,String>> tableData) throws PartInitException {

		ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();

		if(tableData.size() != 0)
		{
			System.out.println("assigment");
			Iterator it = tableData.iterator();
			while(it.hasNext())
			{
				HashMap<String,String> sqldata = (HashMap<String, String>) it.next();
				String role_id = getRole_id(sqldata);
				String fix_id = getFix_id(sqldata);
				String error_id = getError_id(sqldata);


				String errorRoleName = propertyObject.crossperty("ErrorMessage_readRoleName", fix_id);
				String errorClassName = getErrorClassName(errorRoleName,role_id);

				//違反理由とってくる
				String result = sql.getErrorTableResult(error_id ,fix_id);
				String solution = propertyObject.crossperty("Fix", fix_id);
				String replacementSolution = Conversion.replacementSolution(solution, fix_id, role_id);
				String message = result + "\n" + replacementSolution + "\n\n";

				try {
					resource.accept(new IResourceVisitor() {
						public boolean visit(IResource resource) throws CoreException {
							if(resource.getType() == IResource.FILE)
							{
								if(resource.getName().equals(errorClassName + ".java"))
								{
									//マーカーが既にあるかを調べる
									IMarker marker = resource.findMarker(markerID);

									if(marker == null)
									{
										//マーカーがない
										//ResourceからIDocumentを取得
										IPath path = resource.getFullPath();
										try {
											manager.connect(path, LocationKind.IFILE,null);
										} catch (CoreException e) {
											// TODO �����������ꂽ catch �u���b�N
											e.printStackTrace();
										}
										ITextFileBuffer buffer = manager.getTextFileBuffer(path,LocationKind.IFILE);
										IDocument document = buffer.getDocument();

										//IDocumentからクラス名のオフセットを求める
										FindReplaceDocumentAdapter finder = new FindReplaceDocumentAdapter(document);
										IRegion region = null;

										try {
											region = finder.find(0, errorClassName, true, true, false, false);
											int start = region.getOffset();
											int end = start + errorClassName.length();
											markerID = ErrorMarker.addMarker(resource, start, end, message);

										} catch (BadLocationException e) {
											// TODO 自動生成された catch ブロック
											e.printStackTrace();
										}
									}
									else {
										//マーカーはある
										ErrorMarker.addMessage(marker, message);
									}


								}
							}
							return true;
						}
					});
				} catch (CoreException e2) {
					// TODO �����������ꂽ catch �u���b�N
					e2.printStackTrace();
				}
			}
		}
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

	private static String getRole_id(HashMap<String,String> sqldata) {

		return sqldata.get("Role_id");

	}

	private static String getFix_id(HashMap<String,String> sqldata) {

		return sqldata.get("Fix_id");

	}

	private static String getError_id(HashMap<String,String> sqldata) {

		return sqldata.get("Error_id");

	}
}

