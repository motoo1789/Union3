package MatchProcess;

import java.util.ArrayList;

public class Permutation {
	private ArrayList<String> per = new ArrayList();
	public  void permutation(String list,String ans) {
		if(list.length() <=1) {
			per.add(ans + list);
		}
		else {
			for(int i=0;i<list.length();i++) {
				permutation(list.substring(0,i)+ list.substring(i+1),ans+list.charAt(i));
			}
		}
	}
	
	public ArrayList<String> getper() {
		return per;
	}
}
