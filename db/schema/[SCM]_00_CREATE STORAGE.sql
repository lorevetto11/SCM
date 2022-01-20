/******************************************************************************************************
 GPI s.p.a. 

 SCRIPT DI INSTALLAZIONE 

 APPLICAZIONE: Privacy Managment
 VERSIONE:     01.00
 CLIENTE:      
 DATA:         2015-09-11

 AUTORE:       Zanella Gianpaolo

 NOTE:      Script da eseguire direttamente da CONSOLE con un utente SYSDBA
            
            ATTENZIONE ALLA POSIZIONE DEI DATAFILES!!!
 
*******************************************************************************************************/

create tablespace privacy_managment 
datafile '/u01/app/oracle/oradata/XE/PRIVACY_MANAGMENT.dbf' 
size 100M 
autoextend on 
next 50M
maxsize 10G;

create tablespace privacy_managment_idx
datafile '/u01/app/oracle/oradata/XE/PRIVACY_MANAGMENT_IDX.dbf' 
size 50M 
autoextend on 
next 50M 
maxsize 10G;

CREATE USER privacymanagment 
    IDENTIFIED BY privacymanagment1 
    DEFAULT TABLESPACE privacy_managment
    TEMPORARY TABLESPACE TEMP;    
    
GRANT CREATE SESSION TO privacymanagment;
GRANT CREATE VIEW TO privacymanagment; 
GRANT RESOURCE TO privacymanagment;
GRANT DEBUG ANY PROCEDURE TO privacymanagment;
grant create synonym to privacymanagment;
