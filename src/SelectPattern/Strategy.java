package SelectPattern;

import java.io.File;
import java.util.ArrayList;

public interface Strategy {
	ArrayList key = new ArrayList();
	public abstract ArrayList<String> FileNameProcessing(File[] filelist);
	public abstract void ProvisionalNumber();
	public abstract void nCombination(ArrayList<String> file);

}
