/******************************************************************************************************
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


ALTER TABLE PATIENT_PRIVACY_CONSENTS MODIFY UID_CONSENT VARCHAR2(128);

insert into dba_revisions(id, revision,description,script_name,insert_datetime) values (SEQ_DBA_REVISIONS.nextval, 11,'Modifica tipo colonna uid_consent', 'wip.sql', sysdate);

--nb: PATIENTS_PM may need to rename table if integration with IE-Anagrafe is enabled
ALTER TABLE PATIENTS_PM ADD  BIRTH_PLACE VARCHAR2 (400);
ALTER TABLE PATIENTS_PM ADD  ID_RESIDENCE_PLACE NUMBER (10);
ALTER TABLE PATIENTS_PM ADD  RESIDENCE_PLACE    VARCHAR2 (400);
ALTER TABLE PATIENTS_PM ADD  RESIDENCE_ADDRESS  VARCHAR2 (400);
    
    
insert into dba_revisions(id, revision,description,script_name,insert_datetime) values (SEQ_DBA_REVISIONS.nextval, 12,'Aggiunta colonne: BIRTH_PLACE,ID_RESIDENCE_PLACE,RESIDENCE_PLACE,RESIDENCE_ADDRESS sulla tabella PATIENTS', 'wip.sql', sysdate);

CREATE SEQUENCE SEQ_PRIVACYMANAGER_UNIQUE_KEY START WITH 1 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE NOCACHE NOORDER; 

insert into dba_revisions(id, revision,description,script_name,insert_datetime) values (SEQ_DBA_REVISIONS.nextval, 13,'Aggiunta sequence per la creazione OID per BPPC', 'wip.sql', sysdate);


CREATE INDEX "IX_PAT_PRIV_CONS_SPECIFIC_ID" ON "PATIENT_PRIV_CONS_SPECIFIC" ("ID");
CREATE INDEX "IX_PAT_PRIV_CONS_EVENT" ON "PATIENT_PRIV_CONS_SPECIFIC" ("EVENT_TYPE", "EVENT_CODE");
CREATE INDEX "IX_PAT_PRIVACY_CONSENTS_IDPAT" ON "PATIENT_PRIVACY_CONSENTS" ("ID_PATIENT");
CREATE INDEX "IX_PAT_PRIVACY_CONSENTS_IDPOL" ON "PATIENT_PRIVACY_CONSENTS" ("ID_POLICY");


insert into dba_revisions(id, revision,description,script_name,insert_datetime) values (SEQ_DBA_REVISIONS.nextval, 14,'Aggiunta indici sulle tabelle:PATIENT_PRIV_CONS_SPECIFIC,PATIENT_PRIVACY_CONSENTS', 'wip.sql', sysdate);