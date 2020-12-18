package SelectPattern;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import PatternType.FeatureConrrespondence;


public class SelectPattern_3 implements Strategy{
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
		key.add("C");
		Map<String,String> map = new HashMap<>();
		System.out.println("from ProvisionallNumber size->" + CombinationList.size());
		for(int i = 0;i<CombinationList.size();i +=3) {
			map.put("A", CombinationList.get(i));
			map.put("B", CombinationList.get(i + 1));
			map.put("C", CombinationList.get(i + 2));


			//�@�N���X�̑g�ݍ��킹���̓��������o��
			FeatureConrrespondence fc = new FeatureConrrespondence();
			fc.Conrrespondence(map,key);

			map.remove("A");
			map.remove("B");
			map.remove("C");
		}
	}

	public void nCombination(ArrayList<String> file) {

		int count = file.size();
		for(int i = 0; i< count - 2;i++) {
			for(int j = i + 1; j < count - 1;j++) {
				for(int k = j + 1; k < count; k++) {

						CombinationList.add(file.get(i));
						CombinationList.add(file.get(j));
						CombinationList.add(file.get(k));
				}
			}
		}
	}
}
