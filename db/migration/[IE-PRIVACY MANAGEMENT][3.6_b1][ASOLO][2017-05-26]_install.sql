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

DEFINE _usrAnagrafe = "[[ANAGRAFE_USR]]"        -- Utente Oracle anagrafe
DEFINE _passwdAnagrafe = "[[ANAGRAFE_PWD]]"     -- Password Oracle anagrafe

CONNECT &&_usrAnagrafe/&&_passwdAnagrafe@&&_connectId;

grant select on comuni to _usrPrivacyManagement;


CONNECT &&_usrPrivacyManagement/&&_passwdPrivacyManagement@&&_connectId;


CREATE OR REPLACE FORCE VIEW "PRIVACYMANAGMENT"."PATIENTS" ("ID", "NAME", "MIDDLE_NAME", "SURNAME", "FISCAL_CODE", "SEX", "BIRTHDAY", "ID_BIRTH_PLACE", "TEAM_CARD_NUMBER", "ROW_VERSION", "DELETED", "INSERT_DATETIME", "INSERT_USER", "LASTUPDATE_DATETIME", "LASTUPDATE_USER", "CANCEL_DATETIME", "CANCEL_USER", "FL_BPPC", "BIRTH_PLACE", "ID_RESIDENCE_PLACE", "RESIDENCE_PLACE", "RESIDENCE_ADDRESS", "RESIDENCE_POSTALCODE", "RESIDENCE_COUNTRY", "TEL") AS 
SELECT trim(CAST(ass.codice AS VARCHAR2(16)))  AS ID,
  SUBSTR (ass.nome, INSTR (ass.nome, ',') + 1) AS NAME,
  NULL MIDDLE_NAME,
  SUBSTR (ass.nome, 0, INSTR (ass.nome, ',') - 1) AS surname,
  ass.codfis                                      AS FISCAL_CODE,
  ass.sesso                                       AS sex,
  ass.datanas                                     AS BIRTHDAY,
  lpad(ass.comnas,6,0) ID_BIRTH_PLACE,
  '12345' TEAM_CARD_NUMBER,
  0 ROW_VERSION,
  0 DELETED,
  DATAINS INSERT_DATETIME,
  NULL INSERT_USER,
  DATAMOD LASTUPDATE_DATETIME,
  NULL LASTUPDATE_USER,
  NULL CANCEL_DATETIME,
  NULL CANCEL_USER,
  NULL FL_BPPC,
  (SELECT descr
  FROM ANAGRAFE.comuni com
  WHERE com.cod_prov
    ||com.cod_istat = ass.comnas
  AND prefisso      =8
  )                    AS BIRTH_PLACE,
  lpad(ass.comres,6,0) AS ID_RESIDENCE_PLACE,
  (SELECT descr
  FROM ANAGRAFE.comuni com
  WHERE com.cod_prov
    ||com.cod_istat = ass.comres
  AND prefisso      =8
  ) RESIDENCE_PLACE,
  ass.indres AS RESIDENCE_ADDRESS,
  ass.capres AS RESIDENCE_POSTALCODE,
  NULL RESIDENCE_COUNTRY,
  telefono AS TEL
FROM anagrafe.assistiti ass
WHERE ass.codanarif IS NULL
AND ass.cancellato  <> 1;

