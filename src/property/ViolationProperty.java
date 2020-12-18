package property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class ViolationProperty {

	protected InputStream inputStream;
	protected final String START_FILE_PATH = "properties/";
	protected final String extension = ".properties";
	protected final Properties properties = new Properties();
	protected String fileName = new String();
	private String filePath = new String();


	public final String getViolation(ViolationPropertyContext context,String patternName, String key) {

		String violation;

		openProperty(patternName);
		violation = getProperty(key);
		next(context);

		return violation;
	}

	abstract void next(ViolationPropertyContext context);

	private final void openProperty(String patternName) {
		try {

			String path = new File(".").getAbsoluteFile().getParent();
	        filePath = START_FILE_PATH + patternName + "/" + fileName + extension;
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
