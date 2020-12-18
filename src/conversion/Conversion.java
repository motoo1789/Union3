package conversion;

import java.util.ArrayList;
import java.util.Map;

import database.SQLiteSample;
import property.MapProperty;

public class Conversion {

	private static Conversion singleton = new Conversion();
	private static SQLiteSample sql = SQLiteSample.getInstance();
	private static MapProperty mapObject = MapProperty.getInstance();

	public static Conversion getInstance() {
		return singleton;
	}

	//解決策の置換処理
	public static String replacementSolution(final String solution, final String fixID, final String roleID) {

		String result = null;
		if(solution != null)
		{
			//propertyファイルから置換の場所を特定する役割名の取得
			String propertyString = mapObject.crossperty("Replacement", fixID);
			String[] splitData = propertyString.split(":",0);

			//Roletableから置換後のクラス名を取得
			String[] sqlResult = new String[2];
			sqlResult[0] = sql.getRoleTableName(roleID, splitData[0]);
			sqlResult[1] = sql.getRoleTableName(roleID, splitData[1]);

			result = solution.replace(splitData[0], sqlResult[0]);
			result = result.replace(splitData[1], sqlResult[1]);
		}
		else
		{
			System.out.println("置換したい文字がnull");
		}
		return result;
	}

	public String getRoleName(ArrayList<Map<String,String>> roleData) {

		String result = "";
		for(int i = 0; i < roleData.size(); i++)
		{
			Map rolemap = roleData.get(i);
			result += rolemap.get("role") + ":" + rolemap.get("name") + " ";
		}

		return result;

	}
}
