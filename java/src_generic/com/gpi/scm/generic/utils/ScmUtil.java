package com.gpi.scm.generic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ScmUtil {

	private static Class<?> thisClass = java.lang.invoke.MethodHandles.lookup().lookupClass();
	private static final Logger logger = Logger.getLogger(thisClass);

	private static final String JBOSS_CONF_DIR_PROPERTY = "jboss.server.config.dir";
	private static final String FILE_PROTOCOL = "file:///";
	private static final String PRIVACY_CONF_DIR = File.separator + "gpi" + File.separator + "scm" + File.separator;
	private static final String CONFIG_FINE_NAME = "scm.properties";

	private static Properties configurationProperties = null;

	public static String Loadproperties() {
		String value = "";
		String propertiesURLspec = "";

		try {
			String confDir = System.getProperty(JBOSS_CONF_DIR_PROPERTY);
			if (confDir == null)
				throw new Exception();
			else {

				confDir += PRIVACY_CONF_DIR;
				propertiesURLspec = confDir + CONFIG_FINE_NAME;

				try {
					propertiesURLspec = FILE_PROTOCOL + propertiesURLspec;
					URL url = new URL(propertiesURLspec);
					value = url.getFile();
				} catch (MalformedURLException e1) {

				}

				if (value != null) {
					File propertiesFile = new File(value);
					if (propertiesFile.exists()) {

						FileInputStream in;
						try {
							in = new FileInputStream(propertiesFile);
							configurationProperties.load(in);
							in.close();
						} catch (IOException e) {
							throw new RuntimeException(StringUtils.concat("Error loading property file [", value, "]"), e);
						}
					} else {
						logger.error(StringUtils.concat("Missing configuration file: ", value));
					}
				}
			}
		} catch (Exception e) {

		}
		return value;
	}

	public static Properties getConfigurationProperties() throws Exception {
		return getConfigurationProperties(false);
	}

	public static Properties getConfigurationProperties(boolean forceReload) throws Exception {
		if (configurationProperties == null || forceReload) {
			configurationProperties = new Properties();
			Loadproperties();
		}
		return configurationProperties;
	}

	public static String resolveParamName(String parName) {
		try {
			return (String) getConfigurationProperties().get(parName);
		} catch (Exception e) {
			logger.error(MessageFormat.format("error finding value of parameter {0}", parName));
			return null;
		}
	}

	public static boolean resolveBooleanParamName(String parName) {
		try {
			String param = (String) resolveParamName(parName);
			if (param != null && param.trim().length() > 0) {
				return Boolean.parseBoolean(param);
			}

		} catch (Exception e) {
			logger.error(MessageFormat.format("error finding value of parameter {0}", parName));
		}

		return false;
	}

	public static float resolveFloatParam(String parName) {
		String param = resolveParamName(parName);
		return Float.parseFloat(param);
	}

	public static Properties getPropertiesByStartKey(String startKey) {

		Properties result = new Properties();
		try {
			if (startKey != null && startKey.trim().length() > 0) {
				Properties configProp = getConfigurationProperties();
				if (configProp != null && !configProp.isEmpty()) {
					for (Enumeration<?> e = configProp.propertyNames(); e.hasMoreElements();) {
						String name = (String) e.nextElement();
						String value = configProp.getProperty(name);

						if (name.startsWith(startKey)) {
							result.put(name, value);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public static String getBaseConfigDir() {
		String confDir = null;
		try {

			confDir = System.getProperty(JBOSS_CONF_DIR_PROPERTY);
			if (confDir == null)
				throw new Exception();
			else {

				confDir += PRIVACY_CONF_DIR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return confDir;
	}

}
