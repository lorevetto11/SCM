package com.gpi.scm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import org.apache.log4j.Logger;

public class InstallationScriptVerificator {
	
	private static final Logger logger = Logger.getLogger(InstallationScriptVerificator.class);

	private static String CREATE = "CREATE";
	private static String ALTER = "ALTER";
	
	private static String OUTPUT_FILE_NAME = "[SCM]_01_INSTALL.sql";
	
	private static String NEW_LINE = System.getProperty("line.separator");

	
	public static void main(String[] args) {
		new InstallationScriptVerificator(args[0], args[1]);		
	}

	public InstallationScriptVerificator(String baseDir, String inputFileName) {
		String fileName = baseDir + "/db/model/" + inputFileName+".sql";
		
		try {
			File file = new File(fileName);

			byte[] fileByte = FileUtils.getBytesFromFile(file);

			String fileString = new String(fileByte);
			fileString = fileString.replaceAll("ON DELETE SET NULL", "");
			String outputFile = "/******************************************************************************************************"+ NEW_LINE
					+ " GPI s.p.a."+ NEW_LINE + NEW_LINE +" SCRIPT DI INSTALLAZIONE "+ NEW_LINE + NEW_LINE + " APPLICAZIONE PRIVACY MANAGEMENT"+ NEW_LINE
					+ " VERSIONE     ??.??"+ NEW_LINE + " CLIENTE      "+ NEW_LINE + " DATA         "
					//+ new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date()) 
					+ NEW_LINE + NEW_LINE
					+ " AUTORE       com.gpi.privacyManagement.util.InstallationScriptVerificator"+ NEW_LINE + NEW_LINE
					+ " NOTE         Script generato automaticamente da SQLModeler e verificato a buildtime "+ NEW_LINE + NEW_LINE
					+ "*******************************************************************************************************/"+ NEW_LINE + NEW_LINE + NEW_LINE + NEW_LINE;

			String cleanedString = fileString.substring(fileString.indexOf(CREATE));
			String[] scripts = cleanedString.split(";");
			logger.info("Number of installation scripts found " + scripts.length);
			
			String tempString = "";
			
			for (int i = 0; i < scripts.length; i++) {
				tempString = scripts[i].trim();
				if (tempString.startsWith(CREATE) || tempString.startsWith(ALTER)){
					outputFile += tempString + ";"+ NEW_LINE + NEW_LINE;
				}		
			}

			outputFile += NEW_LINE + " -- Script importati da Adjustment.sql, non gestiti da DataModel" + NEW_LINE + NEW_LINE;
			//Carico i file con le configurazioni non generate da DataModel
			fileName = baseDir + "/db/model/" + inputFileName+"Adjustment.sql";
			file = new File(fileName);
			fileByte = FileUtils.getBytesFromFile(file);
			fileString = new String(fileByte);
			
			outputFile += fileString + NEW_LINE + NEW_LINE;

			createOutputFile(baseDir, outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createOutputFile(String baseDir, String content){
		String fileName = baseDir + "/db/schema/" + OUTPUT_FILE_NAME;
		File outputFile = new File(fileName);
		try {
			FileOutputStream outputStream = new FileOutputStream(outputFile, false);
			outputStream.write(content.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
