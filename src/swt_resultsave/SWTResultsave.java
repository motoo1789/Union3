package swt_resultsave;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class SWTResultsave {

	private String path = "";
	private static SWTResultsave singleton = new SWTResultsave();



	private SWTResultsave() {

	}

	public static SWTResultsave getInstance() {
		return singleton;
	}

	public void writeresult(String message) {

		String projectname = GetResource.getProjectname();
		IResource resource = GetResource.getResource(projectname);
		this.path = GetResource.getProjectPath(resource);

		File file = new File(path + "/" + "swtresult.txt");
		try {

			FileWriter filewriter = new FileWriter(file,true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(filewriter));
			pw.println(message);
			pw.close();
			filewriter.close();

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			System.out.println("ファイルに正しく書き残せませんでした");
			e.printStackTrace();
		}

	}

	public String getDate() {

		Calendar carendar = Calendar.getInstance();
		int year = carendar.get(Calendar.YEAR);
		int month = carendar.get(Calendar.MONTH) + 1;
		int day = carendar.get(Calendar.DATE);
		int hour = carendar.get(Calendar.HOUR);
		int minute = carendar.get(Calendar.MINUTE);
		int second = carendar.get(Calendar.SECOND);

		String date = String.valueOf(year) + "年/" + String.valueOf(month) + "月/" + String.valueOf(day) + "日/";
		date += String.valueOf(hour) + "時/" + String.valueOf(minute) + "分/" + String.valueOf(second) + "秒";
		return date;

	}
}
