package sherpath.edu.app.testng;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.collections.Maps;
import org.testng.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Logger
{


	private static final String TRACE = "TRACE";
	private static final String INFO = "INFO";
	private static final String DEBUG = "DEBUG";
	private static final String WARN = "WARN";
	private static final String ERROR = "ERROR";

	private static List<LogMessage> m_output = new Vector<LogMessage>();
	private static Map<ITestResult, List<Integer>> m_methodOutputMap = Maps
			.newHashMap();

	private static boolean m_escapeHtml = false;

	public static List<LogMessage> getLogMessages() {
		return m_output;
	}
	
	/**
	 * Erase the content of all the output generated so far.
	 */
	public static void clear() {
		m_methodOutputMap.clear();
		m_output.clear();
	}

	public static void setEscapeHtml(boolean escapeHtml) {
		m_escapeHtml = escapeHtml;
	}

	private static synchronized void log(LogMessage logMessage, ITestResult m) {
		// Escape for the HTML reports
		if (m_escapeHtml) {
			logMessage.setMessage(Strings.escapeHtml(logMessage.getMessage()));
		}

		int n = getLogMessages().size();
		List<Integer> lines = m_methodOutputMap.get(m);
		if (lines == null) {
			lines = Lists.newArrayList();
			m_methodOutputMap.put(m, lines);
		}
		lines.add(n);
		getLogMessages().add(logMessage);

	}

	public static void debug(String message) {
		LogMessage lm = new LogMessage(message, DEBUG);
		log(lm, getCurrentTestResult());
	}

	public static void warn(String message) {
		LogMessage lm = new LogMessage(message, WARN);
		log(lm, getCurrentTestResult());
	}

	public static void trace(String message) {
		LogMessage lm = new LogMessage(message, TRACE);
		log(lm, getCurrentTestResult());
	}

	public static void info(String message) {
		LogMessage lm = new LogMessage(message, INFO);
		log(lm, getCurrentTestResult());
	}

	public static void error(String message) {
		LogMessage lm = new LogMessage(message, ERROR);
		log(lm, getCurrentTestResult());
	}

	public static ITestResult getCurrentTestResult() {
		return Reporter.getCurrentTestResult();
	}

	public static synchronized List<LogMessage> getLogMessages(ITestResult tr) {
		List<LogMessage> result = new ArrayList<LogMessage>();
		List<Integer> lines = m_methodOutputMap.get(tr);
		if (lines != null) {
			for (Integer n : lines) {
				result.add(getLogMessages().get(n));
			}
		}

		return result;
	}

}
