// PACKAGE DECLARATIONS
package common;

// JAVA LIBRARIES
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger Class for Application
 * @author ploh
 *
 */
public class AppLogger {

	static private FileHandler fileTxt;
	static private AppTextFormatter formatterTxt;

	static public void setup() throws IOException {
		fileTxt = new FileHandler("Logging.txt", true);
		Logger l = Logger.getLogger("");

		// create a TXT formatter
		formatterTxt = new AppTextFormatter();
		fileTxt.setFormatter(formatterTxt);
		l.addHandler(fileTxt);
		l.setLevel(Level.INFO);

		// Removes logging at console
		l.setUseParentHandlers(false);
	}
}
