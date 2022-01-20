******************************************************************************************************
 GPI s.p.a.

 SCRIPT DI MIGRATION 

 APPLICAZIONE: PrivacyManagement
 VERSIONE     
 CLIENTE      
 DATA         
 AUTORE       
 NOTE         

*******************************************************************************************************/

DEFINE _connectId = "[[CONNECT_IDENTIFIER]]"          		-- Oracle Net database alias
DEFINE _usrPrivacyManagement = "[[PRIVACYMGER_USR]]"        -- Utente Oracle PrivacyManagement
DEFINE _passwdPrivacyManagement = "[[PRIVACYMGER_PWD]]"     -- Password Oracle PrivacyManagement


CONNECT &&_usrPrivacyManagement/&&_passwdPrivacyManagement@&&_connectId;

--todo
--ADD TEL ON USERS (evaluate)
ALTER TABLE PATIENTS_PM ADD RESIDENCE_POSTALCODE VARCHAR2(200);
ALTER TABLE PATIENTS_PM ADD RESIDENCE_COUNTRY VARCHAR2(200);
ALTER TABLE PATIENTS_PM ADD TEL VARCHAR2(200);

--ADD TEL on organization
--ADD COUNTRY to Organization
ALTER TABLE ORGANIZATIONS ADD ROOT_OID VARCHAR2 (400);
ALTER TABLE ORGANIZATIONS ADD TEL VARCHAR2 (200);
insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 15,'Aggiunta colonna ROOT_OID, TEL alla tabella ORGANIZATION, postalcode, country, tel on PATIENTS_PM', 'wip.sql', sysdate);


--TODO: da lanciare CAT!!!! dictionary values per component combo
--add to model

CREATE TABLE DICTIONARY_VALUES
  (
    ID                  NUMBER (10) NOT NULL ,
    CODE                VARCHAR2 (128) ,
    NAME                VARCHAR2 (400) ,
    DESCRIPTION         VARCHAR2 (400) ,
    TYPE                VARCHAR2 (128) ,
    REF_PARENT          NUMBER (10) ,
    DELETED             NUMBER (1) DEFAULT 0 ,
    INSERT_DATETIME     DATE ,
    INSERT_USER         VARCHAR2 (255 CHAR) ,
    LASTUPDATE_DATETIME DATE ,
    LASTUPDATE_USER     VARCHAR2 (255 CHAR) ,
    CANCEL_DATETIME     DATE ,
    CANCEL_USER        VARCHAR2(255 CHAR),
    ROW_VERSION         NUMBER DEFAULT 0
  ) ;
  
ALTER TABLE DICTIONARY_VALUES ADD CHECK ( DELETED IN (0, 1));
ALTER TABLE DICTIONARY_VALUES ADD CONSTRAINT PK_DICTIONARY_VALUES PRIMARY KEY(ID);
ALTER TABLE DICTIONARY_VALUES ADD CONSTRAINT FK_DICTION_VAL_DICTION_VAL_1 FOREIGN KEY ( REF_PARENT ) REFERENCES DICTIONARY_VALUES ( ID ) ;

CREATE SEQUENCE SEQ_DICTIONARY_VALUES START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE NOCACHE NOORDER;

insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 16,'Aggiunta tabella dictionary_values, per componente combo', 'wip.sql', sysdate);


--confidentiality codes		
insert into DICTIONARY_VALUES(id, code, name,description,type) values (SEQ_DICTIONARY_VALUES.nextval, 'CONFIDENTIALITY_CODE','CONFIDENTIALITY CODES', 'CONFIDENTIALITY CODES', 'C');
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'N','Normal','Normal', 'V', (select d.id from dictionary_values d where d.code='CONFIDENTIALITY_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'R','Restricted','Restricted', 'V', (select d.id from dictionary_values d where d.code='CONFIDENTIALITY_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'V','Very Restricted','Very Restricted', 'V', (select d.id from dictionary_values d where d.code='CONFIDENTIALITY_CODE'));



--Language codes		
insert into DICTIONARY_VALUES(id, code, name,description,type) values (SEQ_DICTIONARY_VALUES.nextval, 'LANGUAGE_CODE','LANGUAGE CODES', 'LANGUAGE CODES', 'C');
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'it-IT','Italian','Italian', 'V', (select d.id from dictionary_values d where d.code='LANGUAGE_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'en-US','English - US','Englis - US', 'V', (select d.id from dictionary_values d where d.code='LANGUAGE_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'en-UK','English - UK','Englis - UK', 'V', (select d.id from dictionary_values d where d.code='LANGUAGE_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'fr-FR','French','French', 'V', (select d.id from dictionary_values d where d.code='LANGUAGE_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'nl-NL','Dutch - NETHERLANDS','Dutch - NETHERLANDS', 'V', (select d.id from dictionary_values d where d.code='LANGUAGE_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'de-DE','German','German', 'V', (select d.id from dictionary_values d where d.code='LANGUAGE_CODE'));
insert into DICTIONARY_VALUES(id, code, name,description,type,ref_parent)values (SEQ_DICTIONARY_VALUES.nextval, 'es-ES','German','Spanish', 'V', (select d.id from dictionary_values d where d.code='LANGUAGE_CODE'));


insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 17,'Aggiunta confidentiality codes and languages', 'wip.sql', sysdate);



--add options column to document

ALTER TABLE PRIVACY_DOCUMENTS ADD FILE_NAME VARCHAR2(200);
ALTER TABLE PRIVACY_DOCUMENTS ADD CONFIDENTIALITY_CODE VARCHAR2(5 CHAR);
ALTER TABLE PRIVACY_DOCUMENTS ADD LANGUAGE_CODE VARCHAR2(10 CHAR);


insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 18,'Aggiunta options columm (fileName, confidentialityCode, languageCode)', 'wip.sql', sysdate);

--
CREATE TABLE PARAMETERS
  (
    ID              NUMBER (10) NOT NULL ,
    KEY             VARCHAR2 (128) ,
    VALUE           VARCHAR2 (200) ,
    ID_ORGANIZATION NUMBER (10) NOT NULL,
    DELETED             NUMBER (1) DEFAULT 0 ,
    INSERT_DATETIME     DATE ,
    INSERT_USER         VARCHAR2 (255 CHAR) ,
    LASTUPDATE_DATETIME DATE ,
    LASTUPDATE_USER     VARCHAR2 (255 CHAR) ,
    CANCEL_DATETIME     DATE ,
    CANCEL_USER        VARCHAR2(255 CHAR),
    ROW_VERSION         NUMBER DEFAULT 0
  ) ;
ALTER TABLE PARAMETERS ADD CONSTRAINT PARAMETERS_PK PRIMARY KEY ( ID ) ;
ALTER TABLE PARAMETERS ADD CONSTRAINT FK_PARAMETER_ORGANIZATION FOREIGN KEY ( ID_ORGANIZATION ) REFERENCES ORGANIZATIONS ( ID ) ;

CREATE SEQUENCE SEQ_PARAMETERS INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCYCLE NOCACHE NOORDER;

insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 19,'Aggiunta tabella Parameters', 'wip.sql', sysdate);


--
ALTER TABLE ORGANIZATIONS ADD  CODE                VARCHAR2 (128) ;
insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 20,'Aggiunta column code sulla tabella Organization', 'wip.sql', sysdate);


--FN_CONVERT_PATIENT_CODE
create or replace 
FUNCTION FN_CONVERT_PATIENT_CODE(orgCode IN VARCHAR2, patientId IN VARCHAR2) 
RETURN NUMBER AS
  PATIENT_ID   NUMBER(10);
  COL_NAME     VARCHAR2(100);  
BEGIN

  -- default read patient    
   SELECT ID INTO PATIENT_ID 
   FROM PATIENTS 
   WHERE ID = TO_NUMBER(patientId);
     
   dbms_output.put_line('Result Patient_id:'|| PATIENT_ID);  
   
   RETURN PATIENT_ID;

END;
/


insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 21,'Aggiunta Funzione di default per la conversione degli id paziente', 'wip.sql', sysdate);






