package marker;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;


public class DeleteMarker {

	public static void deleteMarker(){

		String projectName = GetResource.getProjectname();
		IResource resource = GetResource.getResource(projectName);

		try {
			resource.accept(new IResourceVisitor() {
				public boolean visit(IResource resource) throws CoreException {
					if(resource.getType() == IResource.FILE)
					{
						String ext = resource.getFileExtension();
				    	if(ext != null)
				    	{
					    	if(ext.equals("java"))	//���΂��Ή߂�
					    		ErrorMarker.deleteMarker(resource);
				    	}
					}
					return true;
				}
			});
		} catch (CoreException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

	}

}
