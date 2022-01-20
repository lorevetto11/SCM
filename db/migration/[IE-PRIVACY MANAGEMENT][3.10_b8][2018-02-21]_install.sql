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

DEFINE _usrIERepo = "[[IEREPO_USR]]"        -- Utente Oracle IE-REPOSITORY
DEFINE _passwdIERepo = "[[IEREPO_PWD]]"     -- Password Oracle IE-REPOSITORY


CONNECT &&_usrPrivacyManagement/&&_passwdPrivacyManagement@&&_connectId;


ALTER TABLE PRIVACY_DOCUMENTS ADD FL_SIGNED NUMBER (1) DEFAULT 0;
ALTER TABLE PRIVACY_DOCUMENTS ADD CHECK ( FL_SIGNED IN (0, 1));


 
insert into dba_revisions(id, revision,description,script_name,INSERT_DATETIME) values (SEQ_DBA_REVISIONS.nextval, 26,'Aggiunta colonna FL_SIGNED sulla tabella PRIVACY_DOCUMENTS', 'wip.sql', sysdate);






