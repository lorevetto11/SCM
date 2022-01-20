/******************************************************************************************************
GPI s.p.a.

SCRIPT DI RESET DELLE SEQUENCE

APPLICAZIONE: PRIVACYMANAGMENT

DATA: 2015-09-16

NOTE: Genera gli script per ricreare tutte le sequence con initial_value corretto delle sole tabelle che sono state
movimentate negli script:
- [PrivacyManagment]_08_INSERT_DEFAULT_VALUE.sql 


*******************************************************************************************************/

/* drop sequences */
DROP SEQUENCE SEQ_APPLICATIONS;
DROP SEQUENCE SEQ_CUSTODIANS;
DROP SEQUENCE SEQ_DBA_REVISIONS;
DROP SEQUENCE SEQ_ORGANIZATIONS;
DROP SEQUENCE SEQ_PATIENT_CODES;
DROP SEQUENCE SEQ_PATIENT_DATA_SNAPSHOTS;
DROP SEQUENCE SEQ_PATIENT_PRIV_CONS_SPECIFIC;
DROP SEQUENCE SEQ_PATIENT_PRIVACY_CONSENTS;
DROP SEQUENCE SEQ_PATIENT_SESSIONS;
DROP SEQUENCE SEQ_PATIENTS;
DROP SEQUENCE SEQ_POLICY_CODING_SYSTEMS;
DROP SEQUENCE SEQ_POLICY_CONTEXTS;
DROP SEQUENCE SEQ_POLICY_SET_LIST;
DROP SEQUENCE SEQ_USERS;
DROP SEQUENCE SEQ_PRIVACY_DOCUMENTS;
DROP SEQUENCE SEQ_DICTIONARY_VALUES;





  
/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from APPLICATIONS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_APPLICATIONS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from CUSTODIANS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_CUSTODIANS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from DBA_REVISIONS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_DBA_REVISIONS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from ORGANIZATIONS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_ORGANIZATIONS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PATIENT_CODES;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PATIENT_CODES INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PATIENT_DATA_SNAPSHOTS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PATIENT_DATA_SNAPSHOTS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PATIENT_PRIV_CONS_SPECIFIC;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PATIENT_PRIV_CONS_SPECIFIC INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PATIENT_PRIVACY_CONSENTS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PATIENT_PRIVACY_CONSENTS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PATIENT_SESSIONS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PATIENT_SESSIONS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PATIENTS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PATIENTS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from POLICY_CODING_SYSTEMS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_POLICY_CODING_SYSTEMS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from POLICY_CONTEXTS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_POLICY_CONTEXTS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from POLICY_SET_LIST;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_POLICY_SET_LIST INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from USERS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_USERS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PRIVACY_DOCUMENTS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PRIVACY_DOCUMENTS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/

/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from DICTIONARY_VALUES;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_DICTIONARY_VALUES INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


/* sequence da rigenerare delle sole tabelle popolate dagli script precedenti */
declare
 ex number;
begin
  select nvl(MAX(ID)+1,1) into ex from PARAMETERS;
  If ex>0 then
    execute immediate 'CREATE SEQUENCE SEQ_PARAMETERS INCREMENT BY 1 START WITH ' || ex || ' MINVALUE 1 NOCYCLE NOCACHE NOORDER';
  end if; 

END;
/


