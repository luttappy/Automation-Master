package sherpath.edu.app.testng;

import java.io.IOException;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import sherpath.edu.app.logging.model.*;
import sherpath.edu.app.util.*;

public class TestLogListener implements  ITestListener, IInvokedMethodListener
{
	
	private Logger getLogger(String className){
		Logger logger = LoggerFactory.getLogger(className);
		return logger;
		
	}

	public void onFinish(ITestContext context) {
		Logger logger = getLogger(context.getSuite().getName());
		logger.info("Suite Completed");
	}
	
	/**
	 * This method copies provided driver to temp folder, loads framework and application configuration.
	 * It also logs to DB based on useReportDB value. By default it logs to report database.
	 */

	public void onStart(ITestContext context) {
		String browserName = System.getProperty("browserName");
		Logger logger = getLogger(context.getSuite().getName());
		logger.info("Suite Started");
		if ((browserName != null) && !(browserName.toLowerCase().contains("firefox"))){
			try {
				Util.copyDriverToTemp();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		Configurator.loadCongifuration();
		TestRun testRun = new TestRun();
		testRun.setConfigFile(Util.getConfigFile());
		testRun.setEnvironment(Util.getEnv());
		testRun.setHostName(Util.getHostname());
		testRun.setPackageId(Util.getPackageId());
		testRun.setRunId(Util.getRunID());
	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}
	

	
	public void onTestSkipped(ITestResult result) {
		Logger logger = getLogger(result.getTestClass().getName() + "." + result.getMethod().getMethodName());
		flushLogMessages(result,logger);
		logger.info("Skipped");

	}
	
	public void onTestStart(ITestResult result) {
		Logger logger = getLogger(result.getTestClass().getName() + "." + result.getMethod().getMethodName());
		Test test = new Test();
		test.setName(result.getTestClass().getName() + "." + result.getMethod().getMethodName());
		test.setDescription(result.getMethod().getDescription());
		test.setPackageId(Util.getPackageId());
		logger.info("Started");
	}
	
	public void onTestSuccess(ITestResult result) {
		updateTestResult(result,"Passed");
		
	}
	
	public void onTestFailure(ITestResult result) {
		updateTestResult(result,"Failed");

	}
	
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
            WebDriverManager.quitDriver();
       }
    }
	
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	
	private void updateTestResult(ITestResult result, String message) {
		String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
		Logger logger = getLogger(testName);
		logger.info("Inside updateTestResult");
		flushLogMessages(result,logger);
		TestLog testLog = new TestLog();
		testLog.setLogName("test");
		testLog.setMessage("Test " + message);
		if(message.equalsIgnoreCase("Passed")){
			testLog.setLogLevel("INFO");
		}else{
			testLog.setLogLevel("ERROR");
		}
		
		testLog.setTimestamp(new Timestamp((new Date()).getTime()));

		logger.info(message);
		logger.info("test time: " + (result.getEndMillis() - result.getStartMillis()));
	}
	
	/**
	 * This method writes messages to console, log file and database.
	 * @param result ITestResult object for the current test.
	 * @param logger logback Logger to log messages to console and log file.
	 */
	private synchronized void flushLogMessages(ITestResult result, Logger logger) {	
		String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
		List<LogMessage> logMessages = sherpath.edu.app.testng.Logger.getLogMessages(result);
		for (LogMessage logMessage : logMessages) {
			TestLog testLog = new TestLog();
			testLog.setLogName(testName);
			testLog.setMessage(logMessage.getMessage());
			testLog.setLogLevel(logMessage.getLevel());
			testLog.setTimestamp(logMessage.getTimestamp());
			
			switch (logMessage.getLevel()){
			case "DEBUG":
				logger.debug(logMessage.getMessage());
				break;
			case "WARN":
				logger.warn(logMessage.getMessage());
				break;
			case "INFO":
				logger.info(logMessage.getMessage());
				break;
			case "ERROR":
				logger.error(logMessage.getMessage());
				break;
			case "TRACE":
				logger.trace(logMessage.getMessage());
				break;
			}
          }
	}}