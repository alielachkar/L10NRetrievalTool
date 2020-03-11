// PACKAGE DECLARATION
package common;

// JAVA LIBRARIES
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Makes sure the application settings are loaded and written 
 * to allow settings to be saved by the user.
 * @author ploh
 * @version 1.0
 */
public class Props {
	private static Properties defaultProps, applicationProps;
	private static boolean propsLoaded = false;
	private static File propertiesFile = null;
	private static InputStream inputStream;

	/* 
	 * Loads and updates the Properties Hash Map 
	 * The order of how the properties key and values are updated must be maintained!
	 * NOTE: The application must always load and save properties to the same location so that it can find them the next time it's launched
	 */
	private static void load() {
		defaultProps = new Properties();

		try {
			// Loads the Default Properties from the Resource File
			inputStream = Props.class.getClassLoader().getResourceAsStream("tool_en.properties");
			defaultProps.load(inputStream);

			// Loads the default properties into the Main Application Properties Hash Map
			applicationProps = new Properties(defaultProps);

			// Specifies the file containing the User Settings 
			propertiesFile = new File("userSettings.properties");

			// Checks if the User Settings File exists
			if (propertiesFile.exists()) {
				inputStream = new FileInputStream(propertiesFile);
				applicationProps.load(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Closes the input stream
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			propsLoaded = true;
		}
	}

	/**
	 * This method is called to retrieve the value of the requested key
	 * @param prop - the key
	 * @return The value of the requested key
	 */
	public static String prop(String prop) {	
		// Checks if the properties file has been loaded
		if (!propsLoaded) {
			load();
		}
		// Returns the value of the requested key
		return applicationProps.getProperty(prop);
	}

	/**
	 * This method is called to update the User Settings of the Application.
	 * It takes in the key and its corresponding value and writes it to the userSettings.properties file. 
	 * @param key - The name of the key
	 * @param value - The value of the key
	 */
	public static void propChange(String key, String value) {

		// Sets the properties loaded flag to false
		propsLoaded = false;

		// Writes the updated settings to the properties file 
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream("userSettings.properties");		
			applicationProps.setProperty(key, value);
			applicationProps.store(outputStream, null);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// Calls the loaded method to update the HashMap values
			load();
		}
	}
}