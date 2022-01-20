package com.gpi.scm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateSequenceScript {

	private static String SQL_COMMENT = "--";
	private static String NEW_LINE = System.getProperty("line.separator");

	public static void main(String[] args) {
		new CreateSequenceScript(args[0], args[1], args[2]);
	} 

	public CreateSequenceScript(String baseDir, String inputFileName, String modelFileName) {
		String modelPath = baseDir + "/db/model/" + modelFileName+".sql";
		// recupero tabelle di cui non va creata la sequence
		String fileName = baseDir + "/db/model/" + inputFileName+".txt";
		HashSet<String> tablesNoSeq = new HashSet<String>();
		
		try {
			File file = new File(fileName);

			byte[] fileByte = FileUtils.getBytesFromFile(file);

			String fileString = new String(fileByte);

			String[] scripts = fileString.split(";");
			
			for (int i = 0; i < scripts.length; i++) {
				if (!scripts[i].startsWith(SQL_COMMENT)){
					if (!scripts[i].trim().equals(""))
						tablesNoSeq.add( scripts[i].trim() );
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// creazione script di drop/create sequence
		@SuppressWarnings("unused")
		Connection connection = null;
		Statement stmt = null;
		ResultSet rst = null;
		BufferedWriter fw;
		StringBuffer sb = new StringBuffer();
		@SuppressWarnings("unused")
		String sqlScript;
		
	    try {
	    	

	        sb.append("/******************************************************************************************************");
	        sb.append(NEW_LINE);
	        sb.append("GPI s.p.a."); 
	        sb.append(NEW_LINE);
	        sb.append(NEW_LINE);
	        sb.append("SCRIPT DI CREAZIONE SEQUENCE (autogenerato)");
	        sb.append(NEW_LINE);
	        sb.append(NEW_LINE);
	        sb.append("APPLICAZIONE: PrivacyManagement");
	        sb.append(NEW_LINE);
	        //sb.append(NEW_LINE);
	        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //sb.append("DATA: "+ sdf.format(new Date()));
	        //sb.append(NEW_LINE);
	        sb.append(NEW_LINE);
	        sb.append("NOTE: Genera gli script per cancellare e ricreare tutte le sequence");
	        sb.append(NEW_LINE);
	        sb.append(NEW_LINE);
	        sb.append("*******************************************************************************************************/");
	        sb.append(NEW_LINE);
	        sb.append(NEW_LINE);
	        	        
	        //leggo il modello
	        File fmodel = new File(modelPath);
			byte[] fileByteModel = FileUtils.getBytesFromFile(fmodel);
			String model = new String(fileByteModel);

	        //serve uno stringbuffer per ogni file di output (oracle,postgres.. ma fin qui e` uguale)
	        StringBuffer sbPostgres = new StringBuffer(sb.toString());
	        
	        List<StringBuffer> sblist = new ArrayList<StringBuffer>();
	        sblist.add(sb);
	        sblist.add(sbPostgres);
			//crea la stringa di output per le sequence
			sblist = fillSequence(sblist, tablesNoSeq, model);
	        
			sb = sblist.get(0);
	        fw = new BufferedWriter(new FileWriter("db/schema/[PrivacyManagement]_02_INSTALL_SEQUENCE.sql"));
	    	fw.write(sb.toString());
	        fw.close();
	        
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	    	try {
	    		if (rst != null) rst.close();
	    		if (stmt != null) stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }

	}
	
	private List<StringBuffer> fillSequence(List<StringBuffer> sblist, HashSet<String> tablesNoSeq, String outputFile) {
		Pattern pattern = Pattern.compile("(?m)^CREATE\\s+TABLE\\s+(\\w+)");
		Matcher matcher = pattern.matcher(outputFile);
		StringBuffer sb, sbPostgres;
		//aggiungere un get se si aggiunge supporto a altri engine
		sb = sblist.get(0);
		sbPostgres = sblist.get(1);
		
		String tableName;
		while (matcher.find()) {
			tableName = matcher.group(1).toUpperCase();
			if( !tablesNoSeq.contains(tableName) && !tableName.endsWith("_REL") ){
				sb.append(sequenceOracle(tableName));
				sbPostgres.append(sequencePostgres(tableName));
			}
		}
		
		return sblist;
	}

	private String sequencePostgres(String tableName) {
		return  "DROP SEQUENCE SEQ_"+tableName+";"+
				NEW_LINE+
				"CREATE SEQUENCE SEQ_"+tableName+" INCREMENT BY 1 START WITH 1;"+NEW_LINE;
	}

	private String sequenceOracle(String tableName) {
		return  "DROP SEQUENCE SEQ_"+tableName+";"+
				NEW_LINE+
				"CREATE SEQUENCE SEQ_"+tableName+" START WITH 1 "+
				        "MAXVALUE 999999999999999999999999999 "+
				        "MINVALUE 1 "+
				        "NOCYCLE "+
				        "NOCACHE "+
				        "NOORDER; "+NEW_LINE;
	}

	
}
