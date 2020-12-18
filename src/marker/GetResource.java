package marker;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import views.View;

public class GetResource {
	private static String dbPath = new String();
	//実験用
	private static String swtresultPath = new String();

	public static IWorkspaceRoot getWorcspaceRoot() {

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot();

	}

	public static IProject getProject(String projectName) {

		IWorkspaceRoot root = getWorcspaceRoot();
		return root.getProject(projectName);

	}

	public static IResource getResource(String projectName) {

		IProject project = getProject(projectName);
		return (IResource)project.getAdapter(IResource.class);

	}



	//パスからプロジェクトの名前をとる
	public static String getProjectname() {

		String projectname = new String();

		try {

			View view = (View) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("Union3.ErrorView");
			String projectpath = view.getDirectory();
			String[] pathlist = projectpath.split("\\\\",0);

			//srcの1個前のディレクトリがプロジェクト直下なのでそれを探す
			for(int index = 0; index < pathlist.length; index++)
			{
				if(pathlist[index].equals("src"))
				{
					projectname = pathlist[index - 1];
					//System.out.println("getProjectname" + projectname);
					break;
				}
			}

		} catch (PartInitException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

		return projectname;
	}

	public static String getDatabase(IResource resource) {

		try {
			resource.accept(new IResourceVisitor() {
				public boolean visit(IResource resource) throws CoreException {
					if(resource.getType() == IResource.FILE)
					{
						if(resource.getName().equals("test.sqlite3"))
						{
							dbPath = getProjectPath(resource);
						}
					}
					return true;
				}
			});
		} catch (CoreException e2) {
			// TODO �����������ꂽ catch �u���b�N
			e2.printStackTrace();
		}

		return dbPath;
	}

	//実験用
	public static String getSWTResult(IResource resource) {

		try {
			resource.accept(new IResourceVisitor() {
				public boolean visit(IResource resource) throws CoreException {
					if(resource.getType() == IResource.FILE)
					{
						if(resource.getName().equals("swtresult.txt"))
						{
							swtresultPath = getProjectPath(resource);
						}
					}
					return true;
				}
			});
		} catch (CoreException e2) {
			// TODO �����������ꂽ catch �u���b�N
			e2.printStackTrace();
		}

		return swtresultPath;
	}
	public static String getProjectPath(IResource resouece) {

		return resouece.getLocation().toString();

	}
}
