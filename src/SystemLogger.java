import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SystemLogger {

	private static FileHandler fh;
	private static SimpleFormatter formatter;
	private static Logger logger;
	private static boolean initialised;

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

	public static void logError(String errorMessage) {
		if (initialised) {
			logger.warning(errorMessage);
		}
	}

	public static void logInfo(String infoMessage) {
		if (initialised) {
			logger.info(infoMessage);
		}
	}
}