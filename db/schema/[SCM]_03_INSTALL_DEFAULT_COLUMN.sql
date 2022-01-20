/******************************************************************************************************
 GPI s.p.a.

 SCRIPT DI INSTALLAZIONE 

 APPLICAZIONE SCM
 VERSIONE     ??.??
 CLIENTE      
 DATA         

 AUTORE     

*******************************************************************************************************/



declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      Where not exists 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='ROW_VERSION')
		   AND  TB.TEMPORARY<>'Y';

begin

  for r in c1 loop
	execute immediate 'ALTER TABLE '  ||  r.table_name || ' ADD (ROW_VERSION NUMERIC(10) DEFAULT 1 NOT NULL)';
	execute immediate 'COMMENT ON COLUMN ' || r.table_name || '.ROW_VERSION IS ''Numero di versione del record''';
    null;
  end loop;

end;  
/

declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      Where not exists 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='DELETED')
		   AND  TB.TEMPORARY<>'Y';

begin

  for r in c1 loop
	execute immediate 'ALTER TABLE '  ||  r.table_name || ' ADD (DELETED NUMERIC(1) DEFAULT 0 NOT NULL)';
	execute immediate 'COMMENT ON COLUMN ' || r.table_name || '.DELETED IS ''Indica se il record e'''' cancellato''';
    null;
  end loop;

end;  
/

declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      WHERE NOT EXISTS 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='INSERT_DATETIME')
		   AND  TB.TEMPORARY<>'Y';

begin

  for r in c1 loop
	execute immediate 'ALTER TABLE '  ||  r.table_name || ' ADD (INSERT_DATETIME DATE)';
    null;
  end loop;

end;  
/


declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      WHERE NOT EXISTS 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='INSERT_USER')
		   AND  TB.TEMPORARY<>'Y';

begin

  FOR R IN C1 LOOP
  
  IF(LENGTH(R.TABLE_NAME) <=24)THEN
      
      EXECUTE IMMEDIATE 'ALTER TABLE '  ||  r.table_name || ' ADD CONSTRAINT FK_IU_' || r.table_name || ' FOREIGN KEY (INSERT_USER) REFERENCES USERS (ID)';
      null;
  ELSE
      
      EXECUTE IMMEDIATE 'ALTER TABLE '  ||  r.table_name || ' ADD CONSTRAINT FK_IU_' || SUBSTR(r.table_name,1,24) || ' FOREIGN KEY (INSERT_USER) REFERENCES USERS (ID)';
      null;
  END IF;
   
  end loop;

END;  
/


declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      WHERE NOT EXISTS 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='LASTUPDATE_DATETIME')
		   AND  TB.TEMPORARY<>'Y';

begin

  for r in c1 loop
	execute immediate 'ALTER TABLE '  ||  r.table_name || ' ADD (LASTUPDATE_DATETIME DATE)';
    null;
  end loop;

END;  
/

declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      WHERE NOT EXISTS 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='LASTUPDATE_USER')
		   AND  TB.TEMPORARY<>'Y';

begin

  FOR R IN C1 LOOP
  
  
  IF(LENGTH(R.TABLE_NAME) <=24)THEN
  
	  
	  EXECUTE IMMEDIATE 'ALTER TABLE '  ||  r.table_name || ' ADD CONSTRAINT FK_UU_' || r.table_name || ' FOREIGN KEY (LASTUPDATE_USER) REFERENCES USERS (ID)';
	  
  ELSE
  
	  
	  EXECUTE IMMEDIATE 'ALTER TABLE '  ||  r.table_name || ' ADD CONSTRAINT FK_UU_' || SUBSTR(r.table_name,1,24) || ' FOREIGN KEY (LASTUPDATE_USER) REFERENCES USERS (ID)';
	  
  END IF;
  
	
    null;
  end loop;

END;  
/


declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      WHERE NOT EXISTS 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='CANCEL_DATETIME')
		   AND  TB.TEMPORARY<>'Y';

begin

  for r in c1 loop
	execute immediate 'ALTER TABLE '  ||  r.table_name || ' ADD (CANCEL_DATETIME DATE)';
    null;
  end loop;

END;  
/

declare
  
  cursor c1 is
    Select Table_Name From user_Tables TB
      WHERE NOT EXISTS 
          ( select 1 from user_tab_columns TC where TC.TABLE_NAME=TB.TABLE_NAME and TC.COLUMN_NAME='CANCEL_USER')
		   AND  TB.TEMPORARY<>'Y';

begin

  FOR R IN C1 LOOP
  
  
  IF(LENGTH(R.TABLE_NAME) <=24)THEN
  	
	 EXECUTE IMMEDIATE 'ALTER TABLE '  ||  r.table_name || ' ADD CONSTRAINT FK_DU_' || r.table_name || ' FOREIGN KEY (CANCEL_USER) REFERENCES USERS (ID)';
	  
  ELSE
  	
	EXECUTE IMMEDIATE 'ALTER TABLE '  ||  r.table_name || ' ADD CONSTRAINT FK_DU_' || SUBSTR(r.table_name,1,24) || ' FOREIGN KEY (CANCEL_USER) REFERENCES USERS (ID)';
	  
  END IF;
  
	
    null;
  end loop;

END;  
/
