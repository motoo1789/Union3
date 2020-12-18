package SelectPattern;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import PatternType.FeatureConrrespondence;


public class SelectPattern_2 implements Strategy{
	private ArrayList<String> CombinationList = new ArrayList();
	private ArrayList List = new ArrayList();

	public ArrayList<String> FileNameProcessing(File[] filelist) {

		ArrayList<String> file = new ArrayList();
		for(int i=0;i<filelist.length;i++) {
		String filename = filelist[i].getName();
		int index = filename.indexOf(".");
		String st = filename.substring(0,index );

		file.add(st);
		}
		return file;
	}


	public void ProvisionalNumber() {
		key.clear();
		key.add("A");
		key.add("B");

		Map<String,String> map = new HashMap<>();
		for(int i = 0;i<CombinationList.size();i +=2) {
			map.put("A", CombinationList.get(i));
			map.put("B", CombinationList.get(i + 1));

			//�@�N���X�̑g�ݍ��킹���̓��������o��
			FeatureConrrespondence fc = new FeatureConrrespondence();
			fc.Conrrespondence(map,key);

			map.remove("A");
			map.remove("B");
		}
	}

	public void nCombination(ArrayList<String> file) {
		int count = file.size();
		for(int i = 0; i< count - 1;i++) {
			for(int j = i + 1; j < count ;j++) {
						CombinationList.add(file.get(i));
						CombinationList.add(file.get(j));
			}
		}
	}

}
