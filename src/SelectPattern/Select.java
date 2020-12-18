package SelectPattern;

import java.io.File;
import java.util.ArrayList;

public class Select {
	private Strategy strategy;
	private ArrayList list = new ArrayList();
	public Select(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void fileprocess(File[] filelist) {
		this.list = strategy.FileNameProcessing(filelist);
	}
	
	public void provisionalnumber() {
		strategy.ProvisionalNumber();
	}
	
	public void ncombination() {
		strategy.nCombination(list);
	}

}
