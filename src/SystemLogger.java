import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * SystemLogger is used to log error messages and other messages which may
 * be useful into a log file.
 *
 * @author Ben Lawton
 */
public class SystemLogger {

	private static FileHandler fh;
	private static SimpleFormatter formatter;
	private static Logger logger;
	private static boolean initialised;

	/**
	 * Initialise the system logger
	 */
	public static void init() {
		if (!initialised) {
			logger = Logger.getLogger("SystemLogs");
			try {
				fh = new FileHandler("./system.log", true);
				logger.addHandler(fh);
				formatter = new SimpleFormatter();
				fh.setFormatter(formatter);
				logger.setUseParentHandlers(false);
				initialised = true;
			}
			catch (SecurityException e) {}
			catch (IOException e) {}
		}
	}

	/**
	 * Log an error to the system log file
	 * @param errorMessage The error message to log
     */
	public static void logError(String errorMessage) {
		if (initialised) {
			logger.warning(errorMessage);
		}
	}

	/**
	 * Log a message which may be useful to the log file
	 * @param infoMessage The message to log
     */
	public static void logInfo(String infoMessage) {
		if (initialised) {
			logger.info(infoMessage);
		}
	}
}