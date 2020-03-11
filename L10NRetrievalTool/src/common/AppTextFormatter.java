// PACKAGE DECLARATIONS
package common;

// JAVA LIBRARIES
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import code.Constants;

/**
 * Custom Text Formatter for Log Records
 * @author ploh
 *
 */
class AppTextFormatter extends Formatter {
	
	// This method is called for every log record
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(1000);
		buf.append (calcDate(rec.getMillis()));
		
		if (rec.getLevel().intValue() < Level.WARNING.intValue()) {
			buf.append(rec.getLevel());
			buf.append ("\t\t" + formatMessage(rec) + Constants.NEWLINE);
		} else {
			buf.append(rec.getLevel().toString().toUpperCase());
			buf.append("\t" + formatMessage(rec).toUpperCase() + Constants.NEWLINE);
		}
		
		return buf.toString();
	}
	
	/**
	 * Date formatter for log record
	 * @param millisecs - The time to log the record
	 * @return the formatted date time format for the log 
	 */
	private String calcDate(long millisecs) {
		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss ");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}
} 