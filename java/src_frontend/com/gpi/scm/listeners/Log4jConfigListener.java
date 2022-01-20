package com.gpi.scm.listeners;


import java.io.File;
import java.text.MessageFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.gpi.scm.generic.utils.ScmUtil;
import com.gpi.scm.generic.utils.StringUtils;


public class Log4jConfigListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(Log4jConfigListener.class);
	
	public static final String LOG4J_CONFIG_FILE = "logging.LOG4J_CONFIG_FILE";
	public static final String LOG4J_CONFIG_REFRESH_INTERVAL = "logging.LOG4J_REFRESH_INTERVAL";
	public static final String LOG4J_CONFIG_DEBUG_IP = "logging.LOG4J_DEBUG_IP";
	private static long DEFAULT_INTERVAL = 300000;

	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("Log4jConfigListener contextDestroyed...");

		// Turn off logging when the Servlet Context is destroyed
		LogManager.shutdown();
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.debug(StringUtils.concat(Log4jConfigListener.class.getName(),StringUtils.STR_PACKAGE_SEPARATOR, "contextInitialized..."));
		
		// Read configuration properties from web.xml
		ServletContext servletContext = event.getServletContext();

		String file = servletContext.getInitParameter(LOG4J_CONFIG_FILE);
		String interval = servletContext.getInitParameter(LOG4J_CONFIG_REFRESH_INTERVAL);

		long delay = DEFAULT_INTERVAL; // Default delay is 5 minutes

		// Check for interval parameter
		if (!StringUtils.isEmpty(interval)) {
			try {
				delay = Long.parseLong(interval);
			} catch (NumberFormatException e) {
				// Can't really log the error since we haven't initialized Log4J
				// Will use the default value
				delay = DEFAULT_INTERVAL;
			}
		}

		// Check for file parameter
		if (!StringUtils.isEmpty(file)) {
			String confDir = ScmUtil.getBaseConfigDir();
			if (!confDir.endsWith(File.separator))
				confDir += File.separator;
			file = StringUtils.concat(confDir, file);
			
			if (!(new File(file).exists())) {
				throw new IllegalArgumentException(MessageFormat.format("Invalid value for LOG4J_CONFIG_LOCATION parameter: {0}.",file));
			}
			if (file.toLowerCase().endsWith(".xml")) {
				DOMConfigurator.configureAndWatch(file, delay);
			} else {
				PropertyConfigurator.configureAndWatch(file, delay);
			}
			logger.info("LOG4J configuration loaded from file " + file);
		}
		
	}

}