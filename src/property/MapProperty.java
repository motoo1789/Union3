package property;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MapProperty {

	private static MapProperty mapproperty = new MapProperty();
	private InputStream inputStream;
	private final String START_FILE_PATH = "properties/";
	private final String extension = ".properties";
	private final Properties properties = new Properties();

	private String filePath = new String();


	private MapProperty() {

	}

	public static MapProperty getInstance() {
		return mapproperty;
	}

	public final String crossperty(String fileName, String key) {

		String crossposition;

		openProperty(fileName);
		crossposition = getProperty(key);

		return crossposition;
	}

	private final void openProperty(String fileName) {
		try {

	        filePath = START_FILE_PATH + fileName + extension;
	        inputStream = ViolationProperty.class.getClassLoader().getResourceAsStream(filePath);
            properties.load(inputStream);

		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
	}


	private String getProperty (final String key) {
		return properties.getProperty(key);
	}
}
