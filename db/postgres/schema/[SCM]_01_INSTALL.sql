/******************************************************************************************************
 GPI s.p.a.

 SCRIPT DI INSTALLAZIONE 

 APPLICAZIONE: SCM
 VERSIONE     ??.??
 CLIENTE      
 DATA         

 AUTORE       

 NOTE         Script generato automaticamente da SQLModeler e verificato a buildtime 

*******************************************************************************************************/

CREATE TABLE ROL_USERS 
  ( 
     id                   NUMERIC (10) NOT NULL, 
     firstname            VARCHAR (80) NOT NULL, 
     lastname             VARCHAR (80) NOT NULL, 
     username             VARCHAR (80) NOT NULL, 
     password             VARCHAR (80) NOT NULL, 
     password_expire_date TIMESTAMP, 
     email                VARCHAR (80), 
     phone                VARCHAR (30), 
     status               VARCHAR (50) DEFAULT 'VALID', 
     language             VARCHAR (20), 
     ref_organization     NUMERIC (10) NOT NULL, 
     ref_role             NUMERIC (10) NOT NULL,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 
  
ALTER TABLE ROL_USERS 
  ADD CONSTRAINT rol_users_pk PRIMARY KEY ( id ); 

ALTER TABLE ROL_USERS 
  ADD CONSTRAINT user_un UNIQUE ( username ); 
  
CREATE TABLE ORG_ORGANIZATIONS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (80) NOT NULL, 
	 description 	  VARCHAR(1000),
     vat_number       VARCHAR (50) NOT NULL, 
     legal_residence  VARCHAR (80) NOT NULL, 
     email            VARCHAR (80) NOT NULL, 
     phone            VARCHAR (30) NOT NULL, 
     status           VARCHAR (50), 
     ref_organization NUMERIC (10),
	 ref_context        NUMERIC(10) NOT NULL,
	 version          NUMERIC (10) NOT NULL, 
	 deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user      VARCHAR (80) NOT NULL, 
     update_time      TIMESTAMP, 
	 update_user      VARCHAR (80), 
     delete_time      TIMESTAMP, 
	 delete_user      VARCHAR (80) 
  ); 
  
  
CREATE TABLE org_certifications (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    description        VARCHAR(1000),
    autority           VARCHAR(100),
    "DATE"             TIMESTAMP,
    expiry_date        TIMESTAMP,
    ref_organization   NUMERIC(10) NOT NULL,
    ref_context        NUMERIC(10) NOT NULL,
	version       NUMERIC(10) NOT NULL,
  	deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	insert_user    VARCHAR (80) NOT NULL, 
    update_time    TIMESTAMP, 
  	update_user    VARCHAR (80), 
    delete_time    TIMESTAMP, 
  	delete_user    VARCHAR (80) 
	
);


CREATE UNIQUE INDEX org_certifications__idx ON
    org_certifications ( ref_context ASC );

ALTER TABLE org_certifications ADD CONSTRAINT org_certifications_pk PRIMARY KEY ( id );


CREATE TABLE org_places (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    address            VARCHAR(100),
    description        VARCHAR(4000),
    ref_organization   NUMERIC(10) NOT NULL,
	version       NUMERIC(10) NOT NULL,
  	deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	insert_user    VARCHAR (80) NOT NULL, 
    update_time    TIMESTAMP, 
  	update_user    VARCHAR (80), 
    delete_time    TIMESTAMP, 
  	delete_user    VARCHAR (80) 
	
);



ALTER TABLE org_places ADD CONSTRAINT org_places_pk PRIMARY KEY ( id );


ALTER TABLE org_certifications
    ADD CONSTRAINT relation_78 FOREIGN KEY ( ref_organization )
        REFERENCES org_organizations ( id );


ALTER TABLE org_places
    ADD CONSTRAINT relation_82 FOREIGN KEY ( ref_organization )
        REFERENCES org_organizations ( id );

ALTER TABLE ORG_ORGANIZATIONS 
  ADD CONSTRAINT org_organizations_pk PRIMARY KEY ( id ); 
  
ALTER TABLE ORG_ORGANIZATIONS 
  ADD CONSTRAINT relation_270 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id ); 
  

CREATE TABLE ROL_ROLES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (4000) NOT NULL, 
     description      VARCHAR (200), 
     ref_organization NUMERIC (10),
	 version          NUMERIC (10) NOT NULL, 
	 deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user      VARCHAR (80) NOT NULL, 
     update_time      TIMESTAMP, 
	 update_user      VARCHAR (80), 
     delete_time      TIMESTAMP, 
	 delete_user      VARCHAR (80)
  ); 

ALTER TABLE ROL_ROLES 
  ADD CONSTRAINT rol_roles_pk PRIMARY KEY ( id ); 
  
ALTER TABLE ROL_USERS 
  ADD CONSTRAINT ref_role FOREIGN KEY ( ref_role ) REFERENCES ROL_ROLES ( id ); 

ALTER TABLE ROL_USERS 
  ADD CONSTRAINT rol_users_org_orgas_fk FOREIGN KEY ( ref_organization ) 
  REFERENCES ORG_ORGANIZATIONS ( id ); 
  
ALTER TABLE ROL_ROLES 
  ADD CONSTRAINT rol_orgs_rol_roles FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id ); 
  
  CREATE TABLE ROL_PROFILES 
  ( 
     id             NUMERIC (10) NOT NULL, 
     name           VARCHAR (4000) NOT NULL,
  	 version        NUMERIC (10) NOT NULL, 
  	 deleted        BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	 insert_user    VARCHAR (80) NOT NULL, 
     update_time    TIMESTAMP, 
  	 update_user    VARCHAR (80), 
     delete_time    TIMESTAMP, 
  	 delete_user    VARCHAR (80) 
  ); 
ALTER TABLE ROL_PROFILES 
  ADD CONSTRAINT rol_profiles_pk PRIMARY KEY ( id ); 
  
CREATE TABLE ROL_ROLES_ROL_PROFILES 
  ( 
     ref_rol_roles    NUMERIC (10) NOT NULL, 
     ref_rol_profiles NUMERIC (10) NOT NULL 
  );
ALTER TABLE ROL_ROLES_ROL_PROFILES 
  ADD CONSTRAINT rol_roles_rol_profiles_pk PRIMARY KEY ( ref_rol_roles,ref_rol_profiles ); 
  
ALTER TABLE ROL_ROLES_ROL_PROFILES 
  ADD CONSTRAINT "NULL SET GENERATED_305" FOREIGN KEY ( ref_rol_roles ) 
  REFERENCES ROL_ROLES ( id ); 
 
ALTER TABLE ROL_ROLES_ROL_PROFILES 
  ADD CONSTRAINT "NULL SET GENERATED_307" FOREIGN KEY ( ref_rol_profiles ) 
  REFERENCES ROL_PROFILES ( id ); 
  
  
  
  /*
  * CFG
  */
  
  
  CREATE TABLE CFG_ORGANIZATION_PARAMETERS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     param_code       VARCHAR (80), 
     param_value      VARCHAR (255), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL,
	 version        NUMERIC (10) NOT NULL, 
  	 deleted        BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	 insert_user    VARCHAR (80) NOT NULL, 
     update_time    TIMESTAMP, 
  	 update_user    VARCHAR (80), 
     delete_time    TIMESTAMP, 
  	 delete_user    VARCHAR (80) 
  ); 

ALTER TABLE CFG_ORGANIZATION_PARAMETERS 
  ADD CONSTRAINT cfg_organization_parameters_pk PRIMARY KEY ( id ); 
  
  ALTER TABLE CFG_ORGANIZATION_PARAMETERS 
  ADD CONSTRAINT cfg_orga_params_org_orgas_fk FOREIGN KEY ( ref_organization ) 
  REFERENCES ORG_ORGANIZATIONS ( id ); 
  
  
 CREATE TABLE CFG_SYSTEM_PARAMETERS 
  ( 
     id          NUMERIC (10) NOT NULL, 
     param_code  VARCHAR (80), 
     param_value VARCHAR (255), 
     description VARCHAR (255) ,
	  version        NUMERIC (10) NOT NULL, 
  	 deleted        BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	 insert_user    VARCHAR (80) NOT NULL, 
     update_time    TIMESTAMP, 
  	 update_user    VARCHAR (80), 
     delete_time    TIMESTAMP, 
  	 delete_user    VARCHAR (80) 
  ); 

ALTER TABLE CFG_SYSTEM_PARAMETERS 
  ADD CONSTRAINT cfg_system_parameters_pk PRIMARY KEY ( id ); 
  
  
 /*
 * CTX
 */

 CREATE TABLE CTX_ATTACHMENT_TYPES 
  ( 
     id          NUMERIC (10) NOT NULL, 
     code        VARCHAR (50) NOT NULL, 
     description VARCHAR (255) NOT NULL ,
	  version        NUMERIC (10) NOT NULL, 
  	 deleted        BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	 insert_user    VARCHAR (80) NOT NULL, 
     update_time    TIMESTAMP, 
  	 update_user    VARCHAR (80), 
     delete_time    TIMESTAMP, 
  	 delete_user    VARCHAR (80) 
  ); 
  
ALTER TABLE CTX_ATTACHMENT_TYPES 
  ADD CONSTRAINT ctx_attachment_types_pk PRIMARY KEY ( id ); 

ALTER TABLE CTX_ATTACHMENT_TYPES 
  ADD CONSTRAINT attachment_type_un UNIQUE ( code ); 

CREATE TABLE CTX_ATTACHMENTS 
  ( 
     id          NUMERIC (10) NOT NULL, 
     filename    VARCHAR (255) NOT NULL, 
     description VARCHAR (255), 
     mimetype    VARCHAR (50) NOT NULL, 
     data        BYTEA NOT NULL, 
     TYPE        VARCHAR (50) NOT NULL, 
     filesize    NUMERIC (10) NOT NULL, 
     ref_context NUMERIC (10),
	 version        NUMERIC (10) NOT NULL, 
  	 deleted        BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	 insert_user    VARCHAR (80) NOT NULL, 
     update_time    TIMESTAMP, 
  	 update_user    VARCHAR (80), 
     delete_time    TIMESTAMP, 
  	 delete_user    VARCHAR (80) 
  ); 

ALTER TABLE CTX_ATTACHMENTS 
  ADD CONSTRAINT ctx_attachments_pk PRIMARY KEY ( id ); 

CREATE TABLE CTX_CONTEXTS 
  ( 
     id          NUMERIC (10) NOT NULL, 
     class_name  VARCHAR (255) NOT NULL,
	  version        NUMERIC (10) NOT NULL, 
  	 deleted        BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	 insert_user    VARCHAR (80) NOT NULL, 
     update_time    TIMESTAMP, 
  	 update_user    VARCHAR (80), 
     delete_time    TIMESTAMP, 
  	 delete_user    VARCHAR (80) 
  ); 
  
ALTER TABLE CTX_CONTEXTS 
  ADD CONSTRAINT ctx_contexts_pk PRIMARY KEY ( id ); 
  
ALTER TABLE CTX_ATTACHMENTS 
  ADD CONSTRAINT ctx_attachs_ctx_contexts_fk FOREIGN KEY ( ref_context ) 
  REFERENCES CTX_CONTEXTS ( id );

CREATE TABLE PRP_FLOORS 
  ( 
     id               NUMERIC (10) NOT NULL,  
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     "order"          INTEGER, 
     width            FLOAT, 
     height           FLOAT, 
     ref_context      NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 


CREATE UNIQUE INDEX drw_floors__idx 
  ON drw_floors ( ref_context ASC ); 

ALTER TABLE DRW_FLOORS 
  ADD CONSTRAINT drw_floors_pk PRIMARY KEY ( id ); 

CREATE TABLE DRW_SHAPES 
  ( 
     id          NUMERIC (10) NOT NULL, 
     TYPE        VARCHAR (20), 
	 color		 VARCHAR(20),
     start_x     FLOAT, 
     start_y     FLOAT, 
     size_x      FLOAT, 
     size_y      FLOAT, 
     radius      FLOAT, 
     "order"          INTEGER, 
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 



ALTER TABLE DRW_SHAPES 
  ADD CONSTRAINT drw_shapes_pk PRIMARY KEY ( id ); 

CREATE TABLE eqp_equipments (

    id              NUMERIC(10) NOT NULL,
    name            VARCHAR(100),
    description     VARCHAR(255),
    startup_date    TIMESTAMP,
    maintainer      VARCHAR(100),
	ref_supplier    NUMERIC(10) NOT NULL,
	ref_type        NUMERIC(10) NOT NULL,
    ref_frequency   NUMERIC(10) NOT NULL,
	ref_context     NUMERIC(10) NOT NULL,
	ref_organization NUMERIC(10) NOT NULL,
	version       NUMERIC(10) NOT NULL,
  	deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time    TIMESTAMP DEFAULT Now() NOT NULL, 
  	insert_user    VARCHAR (80) NOT NULL, 
    update_time    TIMESTAMP, 
  	update_user    VARCHAR (80), 
    delete_time    TIMESTAMP, 
  	delete_user    VARCHAR (80) 
	
);

CREATE UNIQUE INDEX eqp_equipments__idx ON
    eqp_equipments ( ref_frequency ASC );

CREATE UNIQUE INDEX eqp_equipments__idxv1 ON
    eqp_equipments ( ref_context ASC );


ALTER TABLE EQP_EQUIPMENTS 
  ADD CONSTRAINT eqp_equipments_pk PRIMARY KEY ( id, ref_type ); 


CREATE TABLE EQP_EQUIPMENTTYPES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL ,
	 ref_shape		 NUMERIC (10) NOT NULL ,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 

ALTER TABLE EQP_EQUIPMENTTYPES 
  ADD CONSTRAINT eqp_equipmenttypes_pk PRIMARY KEY ( id ); 

 CREATE TABLE prp_prerequisite_types (
    id            NUMERIC(10) NOT NULL,
    name          VARCHAR2(100 BYTE),
    description   VARCHAR2(255 BYTE),
	version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);
ALTER TABLE prp_prerequisite_types ADD CONSTRAINT prp_prerequisite_types_pk PRIMARY KEY ( id );

CREATE TABLE PRP_WATERSUPPLYTYPES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL ,
	 ref_shape		 NUMERIC (10) NOT NULL ,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 

ALTER TABLE PRP_WATERSUPPLYTYPES 
  ADD CONSTRAINT PRP_WATERSUPPLYTYPES_pk PRIMARY KEY ( id ); 
  
ALTER TABLE PRP_WATERSUPPLYTYPES 
  ADD CONSTRAINT relation_305 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id ); 

CREATE TABLE PRP_AIRCONDITIONINGTYPES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL ,
	 ref_shape		 NUMERIC (10) NOT NULL ,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 

ALTER TABLE PRP_AIRCONDITIONINGTYPES 
  ADD CONSTRAINT PRP_AIRCONDITIONINGtypes_pk PRIMARY KEY ( id ); 
  
ALTER TABLE PRP_AIRCONDITIONINGTYPES 
  ADD CONSTRAINT relation_305 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id ); 

  
CREATE TABLE PRP_WASTEDISPOSALTYPES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL ,
	 ref_shape		 NUMERIC (10) NOT NULL ,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 

ALTER TABLE PRP_WASTEDISPOSALTYPES 
  ADD CONSTRAINT PRP_WASTEDISPOSALtypes_pk PRIMARY KEY ( id ); 
  
ALTER TABLE PRP_WASTEDISPOSALTYPES 
  ADD CONSTRAINT relation_305 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id );

CREATE TABLE PRP_PESTCONTROLTYPES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL ,
	 ref_shape		 NUMERIC (10) NOT NULL ,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 

ALTER TABLE PRP_PESTCONTROLTYPES 
  ADD CONSTRAINT PRP_PESTCONTROLtypes_pk PRIMARY KEY ( id ); 
  
ALTER TABLE PRP_PESTCONTROLTYPES 
  ADD CONSTRAINT relation_305 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id );   



CREATE TABLE PRP_AIRCONDITIONING 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL,
     ref_prerequisite_type    NUMERIC (10) NOT NULL,
	 ref_context      NUMERIC (10) NOT NULL, 
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 


CREATE UNIQUE INDEX prp_airconditioning__idx 
  ON prp_airconditioning ( ref_shape ASC ); 

ALTER TABLE PRP_AIRCONDITIONING 
  ADD CONSTRAINT prp_airconditioning_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_CLEANINGS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL,
     ref_prerequisite_type    NUMERIC (10) NOT NULL,
	 ref_context      NUMERIC (10) NOT NULL, 
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 


CREATE UNIQUE INDEX prp_cleanings__idx 
  ON prp_cleanings ( ref_shape ASC ); 

ALTER TABLE PRP_CLEANINGS 
  ADD CONSTRAINT prp_cleanings_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_EQUIPMENTS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_equipment    NUMERIC (10) NOT NULL, 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_type         NUMERIC (10) NOT NULL,
	 ref_prerequisite_type    NUMERIC (10) NOT NULL,
	 ref_context      NUMERIC (10) NOT NULL, 
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 


CREATE UNIQUE INDEX prp_equipments__idx 
  ON prp_equipments ( ref_equipment ASC, ref_type ASC ); 

ALTER TABLE PRP_EQUIPMENTS 
  ADD CONSTRAINT prp_equipments_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_LAYOUTS 
  ( 
     id               NUMERIC (10) NOT NULL,  
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_riskclasses      NUMERIC (10) NOT NULL,
	 ref_prerequisite_type    NUMERIC (10) NOT NULL,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 



CREATE INDEX prp_layouts__idx 
  ON prp_layouts ( ref_riskclasses ASC ); 

CREATE UNIQUE INDEX prp_layouts__idxv1 
  ON prp_layouts ( ref_shape ASC ); 

ALTER TABLE PRP_LAYOUTS 
  ADD CONSTRAINT prp_layouts_pk PRIMARY KEY ( id, ref_organization, ref_floor ); 

CREATE TABLE PRP_PESTCONTROLS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL,
	 ref_prerequisite_type    NUMERIC (10) NOT NULL,
	 ref_context      NUMERIC (10) NOT NULL, 
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 


CREATE UNIQUE INDEX prp_pestcontrols__idx 
  ON prp_pestcontrols ( ref_shape ASC ); 

ALTER TABLE PRP_PESTCONTROLS 
  ADD CONSTRAINT prp_pestcontrols_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_RISKCLASSES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     color            VARCHAR (20), 
	 "order"          INTEGER, 
     ref_organization NUMERIC (10) NOT NULL,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 


ALTER TABLE PRP_RISKCLASSES 
  ADD CONSTRAINT prp_riskclasses_pk PRIMARY KEY ( id ); 
  
ALTER TABLE prp_riskclasses ADD CONSTRAINT prp_riskclasses_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id) ;

CREATE TABLE PRP_WASTEDISPOSALS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL,
	 ref_prerequisite_type    NUMERIC (10) NOT NULL,
	 ref_context      NUMERIC (10) NOT NULL, 
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 




CREATE UNIQUE INDEX prp_wastedisposals__idx 
  ON prp_wastedisposals ( ref_shape ASC ); 

ALTER TABLE PRP_WASTEDISPOSALS 
  ADD CONSTRAINT prp_wastedisposals_pk PRIMARY KEY ( id, ref_layout, 
  ref_organization, ref_floor ); 

CREATE TABLE PRP_WATERSUPPLIES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL,
     ref_prerequisite_type    NUMERIC (10) NOT NULL,
	 ref_context      NUMERIC (10) NOT NULL, 
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 
  
CREATE TABLE ROL_GRANTS
  ( 
     id          NUMERIC (10) NOT NULL, 
     name        VARCHAR (50),
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
  ); 
  ALTER TABLE ROL_GRANTS
  ADD CONSTRAINT rol_grants_pk PRIMARY KEY ( id ); 
  
  CREATE TABLE ROL_GRANTS_ROL_PROFILES
  (
	ref_rol_profiles NUMERIC (10) NOT NULL, 
	ref_rol_grants NUMERIC (10) NOT NULL

  );
  

CREATE TABLE prp_staffhygiene (
    id                      NUMERIC(10) NOT NULL,
    ref_prerequisite_type   NUMERIC(10) NOT NULL,
    ref_context             NUMERIC(10) NOT null,
	ref_organization        NUMERIC(10) NOT NULL,
	ref_roles				numeric(10) not null,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);
ALTER TABLE prp_staffhygiene ADD CONSTRAINT prp_staffhygiene_pk PRIMARY KEY (id) ;




CREATE TABLE mnt_procedures (
    id                      NUMERIC(10) NOT NULL,
    title                   VARCHAR(100),
    description             VARCHAR(255),
    purpose                 VARCHAR(4000),
    equipments              VARCHAR(4000),
    activities              VARCHAR(4000),
    process_check           VARCHAR(4000),
    results_check           VARCHAR(4000),
    revision                NUMERIC(10),
    private                 BOOLEAN  DEFAULT FALSE,
    ref_riskclass           NUMERIC(10) NOT NULL,
    ref_organization        NUMERIC(10) NOT NULL,
    ref_user_role           NUMERIC(10) NOT NULL,
    ref_prerequisite_type   NUMERIC(10) NOT NULL,
	version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);
	



ALTER TABLE mnt_procedures ADD CONSTRAINT mnt_procedures_pk PRIMARY KEY ( id );

ALTER TABLE mnt_procedures
    ADD CONSTRAINT relation_32 FOREIGN KEY ( ref_riskclass )
        REFERENCES prp_riskclasses ( id );
		
ALTER TABLE mnt_procedures
    ADD CONSTRAINT relation_53 FOREIGN KEY ( ref_organization )
        REFERENCES org_organizations ( id );

ALTER TABLE mnt_procedures
    ADD CONSTRAINT relation_55 FOREIGN KEY ( ref_user_role )
        REFERENCES rol_roles ( id );
		
ALTER TABLE mnt_procedures
    ADD CONSTRAINT relation_66 FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );	
		

CREATE TABLE mnt_frequencies (
    id                  NUMERIC(10) NOT NULL,
    type                VARCHAR(20) NOT NULL,
    "MODE"              VARCHAR(20),
    period              VARCHAR(200),
    value               NUMERIC(10),
	ref_riskclass       NUMERIC(10) NOT NULL,
    ref_organization   NUMERIC(10) NOT NULL,
	ref_prerequisite_type   NUMERIC(10) NOT NULL,
    version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);



ALTER TABLE mnt_frequencies ADD CONSTRAINT mnt_frequencies_pk PRIMARY KEY ( id );

CREATE TABLE mnt_monitorings (
    id                   NUMERIC(10) NOT NULL,
    ref_organization     NUMERIC(10) NOT NULL,
    ref_frequency        NUMERIC(10) NOT NULL,
    ref_context          NUMERIC(10),
    ref_procedure        NUMERIC(10) NOT NULL,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);



ALTER TABLE mnt_monitorings ADD CONSTRAINT mnt_monitorings_pk PRIMARY KEY ( id );

CREATE TABLE mnt_outcomes (
    id                    NUMERIC(10) NOT NULL,
    result                BOOLEAN NOT NULL DEFAULT FALSE,
    note                  VARCHAR(4000),
    ref_context           NUMERIC(10) NOT NULL,
    ref_user              NUMERIC(10) NOT NULL,
    ref_monitorings       NUMERIC(10) NOT NULL,
	version               NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);


CREATE UNIQUE INDEX mnt_outcomes__idx ON
    mnt_outcomes ( ref_context ASC );

ALTER TABLE mnt_outcomes ADD CONSTRAINT mnt_outcomes_pk PRIMARY KEY ( id );




CREATE TABLE trn_courses (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(200) NOT NULL,
    description        VARCHAR(4000),
    trainer            VARCHAR(200),
    ref_organization   NUMERIC(10) NOT NULL,
    ref_context        NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

CREATE UNIQUE INDEX trn_courses__idx ON
    trn_courses ( ref_context ASC );

ALTER TABLE trn_courses ADD CONSTRAINT trn_courses_pk PRIMARY KEY ( id );

CREATE TABLE trn_participants (
    id            NUMERIC(10) NOT NULL,
    passed        BOOLEAN NOT NULL DEFAULT FALSE,
    note          VARCHAR(4000),
    ref_user      NUMERIC(10) NOT NULL,
    ref_context   NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

CREATE UNIQUE INDEX trn_participants__idxv1 ON
    trn_participants ( ref_context ASC );

ALTER TABLE trn_participants ADD CONSTRAINT trn_participants_pk PRIMARY KEY ( id );

CREATE TABLE trn_trainings (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(1000),
    "DATE"             TIMESTAMP,
	status				BOOLEAN DEFAULT FALSE,
    ref_organization   NUMERIC(10),
    ref_course         NUMERIC(10) NOT NULL,
    ref_role           NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

ALTER TABLE trn_trainings ADD CONSTRAINT trn_trainings_pk PRIMARY KEY ( id,
ref_role );

create table trn_patricipants_trn_traininings (
	ref_trn_participants numeric(10) NOT NULL,
	ref_trn_trainings numeric(10) NOT NULL
	)
	
ALTER TABLE trn_patricipants_trn_traininings
  ADD CONSTRAINT trn_patricipants_trn_traininings_pk PRIMARY KEY ( ref_trn_participants, 
  ref_trn_trainings );

ALTER TABLE trn_patricipants_trn_traininings 
  ADD CONSTRAINT trn_patricipants_trn_traininings_fk1 FOREIGN KEY ( ref_trn_trainings ) 
  REFERENCES trn_trainings ( id ); 
  
ALTER TABLE trn_patricipants_trn_traininings 
  ADD CONSTRAINT trn_patricipants_trn_traininings_fk2 FOREIGN KEY ( ref_trn_participants ) 
  REFERENCES trn_participants ( id ); 

ALTER TABLE trn_trainings
    ADD CONSTRAINT ref_rolev2 FOREIGN KEY ( ref_role )
        REFERENCES rol_roles ( id );

ALTER TABLE trn_courses
    ADD CONSTRAINT relation_68 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );

ALTER TABLE trn_trainings
    ADD CONSTRAINT relation_69 FOREIGN KEY ( ref_course )
        REFERENCES trn_courses ( id );

ALTER TABLE trn_participants
    ADD CONSTRAINT relation_72 FOREIGN KEY ( ref_user )
        REFERENCES rol_users ( id );

ALTER TABLE trn_participants
    ADD CONSTRAINT relation_75 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );

CREATE TABLE cks_system_checks (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    description        VARCHAR(4000),
    private            BOOLEAN DEFAULT FALSE,
    ref_organization   NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

ALTER TABLE cks_system_checks ADD CONSTRAINT cks_system_checks_pk PRIMARY KEY ( id );

CREATE TABLE cks_system_check_plannings (
    id                 NUMERIC(10) NOT NULL,
    "DATE"             TIMESTAMP,
	start_date			TIMESTAMP,
	close_date			TIMESTAMP,
    status             VARCHAR(30),
    ref_organization   NUMERIC(10) NOT NULL,
    ref_company        NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);


ALTER TABLE cks_system_check_plannings ADD CONSTRAINT cks_system_check_plannings_pk PRIMARY KEY ( id );


CREATE TABLE cks_system_checks_cks_system_check_plannings (
	ref_cks_system_check_plannings numeric(10) NOT NULL,
	ref_cks_system_checks numeric(10) NOT NULL
	
);

ALTER TABLE cks_system_checks_cks_system_check_plannings
 ADD CONSTRAINT cks_system_checks_cks_system_check_plannings_pk 
 PRIMARY KEY (ref_cks_system_check_plannings, ref_cks_system_checks);
 
ALTER TABLE cks_system_checks_cks_system_check_plannings
 ADD CONSTRAINT "checks_plannings1" FOREIGN KEY (ref_cks_system_check_plannings)
 REFERENCES cks_system_check_plannings(id);
 
ALTER TABLE cks_system_checks_cks_system_check_plannings 
 ADD CONSTRAINT "checks_plannings2" FOREIGN KEY (ref_cks_system_checks)
 REFERENCES cks_system_checks(id);

CREATE TABLE cks_system_check_requirements (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    description        VARCHAR(4000),
    ref_system_check   NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);


ALTER TABLE cks_system_check_requirements ADD CONSTRAINT cks_system_check_requirements_pk PRIMARY KEY ( id );

ALTER TABLE cks_system_check_requirements
    ADD CONSTRAINT relation_103 FOREIGN KEY ( ref_system_check )
        REFERENCES cks_system_checks ( id );


CREATE TABLE cks_process_checks (
    id                      NUMERIC(10) NOT NULL,
    name                    VARCHAR(100),
    description             VARCHAR(4000),
    private                 BOOLEAN NOT NULL DEFAULT FALSE,
    type                    VARCHAR(20) NOT NULL,
    ref_organization        NUMERIC NOT NULL,
    ref_frequency           NUMERIC(10) NOT NULL,
    ref_prerequisite_type   NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

CREATE UNIQUE INDEX cks_process_checks__idx ON
    cks_process_checks ( ref_frequency ASC );

ALTER TABLE cks_process_checks ADD CONSTRAINT cks_process_checks_pk PRIMARY KEY ( id );

ALTER TABLE cks_process_checks
    ADD CONSTRAINT relation_90 FOREIGN KEY ( ref_frequency )
        REFERENCES mnt_frequencies ( id );

ALTER TABLE cks_process_checks
    ADD CONSTRAINT relation_91 FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );
ALTER TABLE cks_process_checks ADD CONSTRAINT cks_process_checks_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id) ;
		

		
		
CREATE TABLE cks_noncompliances (
    id                             NUMERIC(10) NOT NULL,
    close_date                     TIMESTAMP,
	start_date                     TIMESTAMP,
    description                    VARCHAR(1000),
    treatment                      VARCHAR(1000),
    retrieval                      VARCHAR(1000),
    causes                         VARCHAR(1000),
    corrections                    VARCHAR(1000),
    checks                         VARCHAR(1000),
	close_user					   NUMERIC(10),
    ref_organization               NUMERIC(10) NOT NULL,
    ref_processcheck               NUMERIC NOT NULL,
    ref_context      			   NUMERIC NOT NULL,
    ref_system_check_requirement   NUMERIC NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

ALTER TABLE cks_noncompliances ADD CONSTRAINT cks_noncompliances_pk PRIMARY KEY ( id );

ALTER TABLE cks_noncompliances
    ADD CONSTRAINT relation_99 FOREIGN KEY ( ref_processcheck )
        REFERENCES cks_process_checks ( id );
ALTER TABLE cks_noncompliances ADD CONSTRAINT cks_noncompliances_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id) ;
ALTER TABLE cks_noncompliances ADD CONSTRAINT cks_noncompliances_cks_system_check_requirements_fk FOREIGN KEY (ref_system_check_requirement) REFERENCES cks_system_check_requirements(id) ;
ALTER TABLE cks_noncompliances ADD CONSTRAINT cks_noncompliances_ctx_contexts_fk FOREIGN KEY (ref_context) REFERENCES ctx_contexts(id) ;


CREATE TABLE cks_system_check_outcomes (
    id                             NUMERIC(10) NOT NULL,
    evidence                       VARCHAR(4000),
    ref_noncompliance              NUMERIC(10),
    ref_system_check_requirement   NUMERIC(10),
    ref_system_check_planning      NUMERIC(10),
    ref_context                    NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

CREATE UNIQUE INDEX cks_system_check_outcomes__idx ON
    cks_system_check_outcomes ( ref_context ASC );

ALTER TABLE cks_system_check_outcomes ADD CONSTRAINT cks_system_check_outcomes_pk PRIMARY KEY ( id );

ALTER TABLE cks_system_check_outcomes
    ADD CONSTRAINT relation_107 FOREIGN KEY ( ref_system_check_requirement )
        REFERENCES cks_system_check_requirements ( id );

ALTER TABLE cks_system_check_outcomes
    ADD CONSTRAINT relation_108 FOREIGN KEY ( ref_system_check_planning )
        REFERENCES cks_system_check_plannings ( id );

ALTER TABLE cks_system_check_outcomes
    ADD CONSTRAINT relation_109 FOREIGN KEY ( ref_noncompliance )
        REFERENCES cks_noncompliances ( id );

ALTER TABLE cks_system_check_outcomes
    ADD CONSTRAINT relation_115 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );

CREATE TABLE cks_process_check_plannings (
    id                 NUMERIC(10) NOT NULL,
    "DATE"             TIMESTAMP,
	start_date			TIMESTAMP,
	close_date			TIMESTAMP,
    status             VARCHAR,
    ref_organization   NUMERIC(10) NOT NULL,
    ref_company        NUMERIC NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);
ALTER TABLE cks_process_check_plannings ADD CONSTRAINT cks_process_check_plannings_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id);

ALTER TABLE cks_process_check_plannings ADD CONSTRAINT cks_process_check_plannings_org_organizations_fk2 FOREIGN KEY (ref_company) REFERENCES org_organizations(id);


ALTER TABLE cks_process_check_plannings ADD CONSTRAINT cks_process_check_plannings_pk PRIMARY KEY ( id );

CREATE TABLE cks_process_checks_cks_process_check_plannings (
	ref_cks_process_check_plannings numeric(10) NOT NULL,
	ref_cks_process_checks numeric(10) NOT NULL
	
);

ALTER TABLE cks_process_checks_cks_process_check_plannings
 ADD CONSTRAINT cks_process_checks_cks_process_check_plannings_pk 
 PRIMARY KEY (ref_cks_process_check_plannings, ref_cks_process_checks);
 
ALTER TABLE cks_process_checks_cks_process_check_plannings
 ADD CONSTRAINT processchecks_plannings1 FOREIGN KEY (ref_cks_process_check_plannings)
 REFERENCES cks_process_check_plannings(id);
 
ALTER TABLE cks_process_checks_cks_process_check_plannings 
 ADD CONSTRAINT processchecks_plannings2 FOREIGN KEY (ref_cks_process_checks)
 REFERENCES cks_process_checks(id);
 
 CREATE TABLE cks_process_check_outcomes (
    id                           NUMERIC(10) NOT NULL,
    evidence                     VARCHAR(4000),
    ref_process_check_planning   NUMERIC(10),
    ref_context                  NUMERIC(10) NOT NULL,
    ref_noncompliance             NUMERIC(10),
	ref_process_check             NUMERIC(10),
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);

CREATE UNIQUE INDEX cks_process_check_outcomes__idx ON
    cks_process_check_outcomes ( ref_context ASC );

ALTER TABLE cks_process_check_outcomes
   ADD CONSTRAINT relation_processCheckOutcomes FOREIGN KEY ( ref_process_check )
      REFERENCES cks_process_check ( id );
	  
ALTER TABLE cks_process_check_outcomes
    ADD CONSTRAINT relation_114 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );
		
ALTER TABLE cks_process_check_outcomes
    ADD CONSTRAINT relation_94 FOREIGN KEY ( ref_noncompliance )
        REFERENCES cks_noncompliances ( id );

ALTER TABLE cks_process_check_outcomes
    ADD CONSTRAINT relation_97 FOREIGN KEY ( ref_process_check_planning )
        REFERENCES cks_process_check_plannings ( id );
		
CREATE TABLE dng_dangers (
    id                      NUMERIC(10) NOT NULL,
    name                    VARCHAR(100),
    description             VARCHAR(255),
    type                    VARCHAR(20),
    risk                    VARCHAR(20),
    control_measure         VARCHAR(1000),
	is_ccp		  			BOOLEAN NOT NULL DEFAULT FALSE,
    critical_limit          VARCHAR(1000),
	acceptance_limit         VARCHAR(1000),
    procedures              VARCHAR(4000),
    ref_prerequisite_type   NUMERIC(10) NOT NULL,
    ref_context             NUMERIC(10) NOT NULL
	ref_organization        NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);


ALTER TABLE dng_dangers ADD CONSTRAINT dng_dangers_pk PRIMARY KEY ( id );

ALTER TABLE dng_dangers
    ADD CONSTRAINT relation_121 FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );

ALTER TABLE dng_dangers
    ADD CONSTRAINT relation_122 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );	
		
CREATE TABLE anl_analysis_parameters (
    id                   NUMERIC(10) NOT NULL,
    name                 VARCHAR(200),
    description          VARCHAR(1000),
    threshold_value      FLOAT,
    ref_role_in_charge   NUMERIC(10) NOT NULL,
	ref_organization	NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80) 
);
ALTER TABLE anl_analysis_parameters ADD CONSTRAINT anl_analysis_parameters_pk PRIMARY KEY ( id );
ALTER TABLE anl_analysis_parameters
    ADD CONSTRAINT relation_125 FOREIGN KEY ( ref_role_in_charge )
        REFERENCES rol_roles ( id );
		
		
CREATE TABLE anl_analysis_values (
    id                         NUMERIC(10) NOT NULL,
    "DATE"                     TIMESTAMP,
    value                      FLOAT(19),
    note                       VARCHAR(1000),
    ref_prerequisite_context   NUMERIC(19),
    ref_analysis_parameter     NUMERIC(10) NOT NULL,
	ref_organization           NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80)

);

ALTER TABLE anl_analysis_values ADD CONSTRAINT anl_analysis_values_pk PRIMARY KEY ( id );
ALTER TABLE anl_analysis_values
    ADD CONSTRAINT relation_124 FOREIGN KEY ( ref_analysis_parameter )
        REFERENCES anl_analysis_parameters ( id );
ALTER TABLE anl_analysis_values ADD CONSTRAINT anl_analysis_values_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id) ;
ALTER TABLE anl_analysis_values ADD CONSTRAINT anl_analysis_values_ctx_contexts_fk FOREIGN KEY (ref_prerequisite_context) REFERENCES ctx_contexts(id) ;

ALTER TABLE anl_analysis_parameters ADD CONSTRAINT anl_analysis_parameters_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id) ;

CREATE TYPE materialType AS ENUM ('PACKAGING', 'CLEANING', 'FOOD', 'EQUIPMENT');


CREATE TABLE mtr_materials (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    description        VARCHAR(1000),
    ref_organization   NUMERIC(10) NOT NULL,
    ref_context        NUMERIC(10) NOT NULL,
    ref_supplier       NUMERIC(10) NOT NULL,
	ref_prerequisite_type NUMERIC(10) NOT NULL,
	ref_material_category NUMERIC(10) NOT NULL,
    type               VARCHAR(80),
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80)
);
ALTER TABLE mtr_materials ADD CONSTRAINT mtr_materials_pk PRIMARY KEY (id) ;


CREATE TABLE mtr_suppliers (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    description        VARCHAR(1000),
    vat_number         VARCHAR(100),
    contact            VARCHAR(100),
    address            varchar,
    email              VARCHAR(100),
    phone              VARCHAR(100),
    ref_context        NUMERIC(10) NOT NULL,
    ref_organization   NUMERIC(10) NOT NULL,
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80)
);

CREATE TABLE mtr_material_categories (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    description        VARCHAR(1000),
    type                VARCHAR (80),
	version          NUMERIC (10) NOT NULL, 
	deleted		  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time      TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user      VARCHAR (80) NOT NULL, 
    update_time      TIMESTAMP, 
	update_user      VARCHAR (80), 
    delete_time      TIMESTAMP, 
	delete_user      VARCHAR (80)
);
ALTER TABLE mtr_material_categories ADD CONSTRAINT mtr_material_categoriess_pk PRIMARY KEY (id) ;


ALTER TABLE mtr_suppliers ADD CONSTRAINT mtr_suppliers_pk PRIMARY KEY ( id );

ALTER TABLE mtr_materials
    ADD CONSTRAINT relation_127 FOREIGN KEY ( ref_supplier )
        REFERENCES mtr_suppliers ( id );

create table dng_dangers_mtr_materials (
	ref_dng_dangers numeric(10) NOT NULL,
	ref_mtr_materials numeric(10) NOT NULL
	);
ALTER TABLE dng_dangers_mtr_materials ADD CONSTRAINT dng_dangers_mtr_materials_pk PRIMARY KEY (ref_dng_dangers,ref_mtr_materials) ;
ALTER TABLE dng_dangers_mtr_materials ADD CONSTRAINT dng_dangers_mtr_materials_dng_dangers_fk FOREIGN KEY (ref_dng_dangers) REFERENCES dng_dangers(id) ;
ALTER TABLE dng_dangers_mtr_materials ADD CONSTRAINT dng_dangers_mtr_materials_mtr_materials_fk FOREIGN KEY (ref_mtr_materials) REFERENCES mtr_materials(id) ;

CREATE TABLE flw_diagrams (
    id                 NUMERIC(10) NOT NULL,
    name               VARCHAR(100),
    description        VARCHAR(500),
    ref_organization   NUMERIC(10) NOT NULL,
	version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);


ALTER TABLE flw_diagrams ADD CONSTRAINT flw_diagrams_pk PRIMARY KEY ( id );

ALTER TABLE flw_diagrams
    ADD CONSTRAINT flw_diagrams_org_organizations_fk FOREIGN KEY ( ref_organization )
        REFERENCES org_organizations ( id );

CREATE TABLE flw_shapes (
    id            NUMERIC(10) NOT NULL,
    name          VARCHAR(100),
    description   VARCHAR(500),
    center_x      FLOAT(3),
    center_y      FLOAT(3),
    width         FLOAT(3),
    height        FLOAT(3),
    type          VARCHAR(100),
    "order"       NUMERIC(10),
    ref_diagram   NUMERIC(10) NOT NULL,
	version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);


ALTER TABLE flw_shapes ADD CONSTRAINT flw_shapes_pk PRIMARY KEY ( id );
ALTER TABLE flw_shapes
    ADD CONSTRAINT flw_shapes_flw_diagrams_fk FOREIGN KEY ( ref_diagram )
        REFERENCES flw_diagrams ( id );


CREATE TABLE flw_anchor_points (

    id              NUMERIC(10) NOT NULL,
    name            VARCHAR(100),
    description     VARCHAR(500),
    translation_x   FLOAT(3),
    translation_y   FLOAT(3),
    width           FLOAT(3),
    height          FLOAT(3),
    "order"         NUMERIC(10),
    ref_shape       NUMERIC(10) NOT NULL,
	version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);


ALTER TABLE flw_anchor_points ADD CONSTRAINT flw_anchor_points_pk PRIMARY KEY ( id );
ALTER TABLE flw_anchor_points
    ADD CONSTRAINT flw_anchor_points_flw_shapes_fk FOREIGN KEY ( ref_shape )
        REFERENCES flw_shapes ( id );

CREATE TABLE flw_elements (
    id                      NUMERIC(10) NOT NULL,
    name                    VARCHAR(100),
    description             VARCHAR(255),
	risk					 VARCHAR(20),
    type                    VARCHAR(100),
	type_ccp                    VARCHAR(100),
    ref_shape               NUMERIC(10) NOT NULL,
    ref_material            NUMERIC(10) NOT NULL,
	ref_context      	    NUMERIC NOT NULL,
	version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);


CREATE UNIQUE INDEX flw_elements__idx ON
    flw_elements ( ref_shape ASC );

ALTER TABLE flw_elements
    ADD CONSTRAINT flw_elements_pk PRIMARY KEY ( id);
	
ALTER TABLE flw_elements
    ADD CONSTRAINT flw_elements_flw_shapes_fk FOREIGN KEY ( ref_shape )
        REFERENCES flw_shapes ( id );

ALTER TABLE flw_elements ADD CONSTRAINT flw_elements_ctx_contexts_fk FOREIGN KEY (ref_context) REFERENCES ctx_contexts(id) ;


CREATE TABLE flw_relations (
    id                   NUMERIC(10) NOT NULL,
    name                 VARCHAR(100),
    description          VARCHAR(255),
    type                 VARCHAR(100),
    "order"              NUMERIC(10),
    start_anchor_point   NUMERIC(10) NOT NULL,
    end_anchor_point     NUMERIC(10) NOT NULL,
	 version              NUMERIC (10) NOT NULL, 
	 deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
     insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	 insert_user          VARCHAR (80) NOT NULL, 
     update_time          TIMESTAMP, 
	 update_user          VARCHAR (80), 
     delete_time          TIMESTAMP, 
	 delete_user          VARCHAR (80) 
);


ALTER TABLE flw_relations ADD CONSTRAINT flw_relations_pk PRIMARY KEY ( id );



ALTER TABLE flw_relations
    ADD CONSTRAINT flw_relations_flw_anchor_points_fk FOREIGN KEY ( start_anchor_point )
        REFERENCES flw_anchor_points ( id );

ALTER TABLE flw_relations
    ADD CONSTRAINT flw_relations_flw_anchor_points_fkv2 FOREIGN KEY ( end_anchor_point )
        REFERENCES flw_anchor_points ( id );

CREATE TABLE DNG_RISKMAP(
	
	id					NUMERIC(10) NOT NULL,
    value				NUMERIC(10),
	ref_organization    NUMERIC(10) NOT NULL,	
    ref_dng_dangers  	NUMERIC(10) NOT NULL,
    ref_flw_elements    NUMERIC(10) NOT NULL,
    version              NUMERIC (10) NOT NULL, 
	deleted			  BOOLEAN NOT NULL DEFAULT FALSE,
    insert_time          TIMESTAMP DEFAULT Now() NOT NULL, 
	insert_user          VARCHAR (80) NOT NULL, 
    update_time          TIMESTAMP, 
	update_user          VARCHAR (80), 
    delete_time          TIMESTAMP, 
	delete_user          VARCHAR (80)                 

)
ALTER TABLE dng_riskmap ADD CONSTRAINT dng_riskmap_pk PRIMARY KEY (id) ;
CREATE INDEX dng_riskmap_ref_dng_dangers_idx ON dng_riskmap USING btree (ref_dng_dangers, ref_flw_elements);

  

ALTER TABLE mnt_frequencies
    ADD CONSTRAINT relation_34 FOREIGN KEY ( ref_riskclass )
        REFERENCES prp_riskclasses ( id );

ALTER TABLE mnt_frequencies
    ADD CONSTRAINT relation_36 FOREIGN KEY ( ref_organization )
        REFERENCES org_organizations ( id );
		
ALTER TABLE mnt_frequencies ADD CONSTRAINT mnt_frequencies_prp_prerequisite_types_fk FOREIGN KEY (ref_prerequisite_type) REFERENCES prp_prerequisite_types(id) ;

		
--CREATE UNIQUE INDEX freq_unique ON mnt_frequencies (ref_organization, ref_riskclass, ref_prerequisite_type) where mnt_frequencies."type"='DEFAULT';

		
ALTER TABLE mnt_monitorings
    ADD CONSTRAINT relation_37 FOREIGN KEY ( ref_frequency )
        REFERENCES mnt_frequencies ( id );

ALTER TABLE mnt_monitorings
    ADD CONSTRAINT relation_38 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );

ALTER TABLE mnt_monitorings
    ADD CONSTRAINT relation_52 FOREIGN KEY ( ref_procedure )
        REFERENCES mnt_procedures ( id );
		
ALTER TABLE mnt_monitorings ADD CONSTRAINT mnt_monitorings_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id) ;



ALTER TABLE mnt_outcomes
    ADD CONSTRAINT relation_56 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );

ALTER TABLE mnt_outcomes
    ADD CONSTRAINT relation_57 FOREIGN KEY ( ref_user )
        REFERENCES rol_users ( id );

ALTER TABLE mnt_outcomes
    ADD CONSTRAINT relation_64 FOREIGN KEY ( ref_monitorings )
        REFERENCES mnt_monitorings ( id );




ALTER TABLE prp_airconditioning
    ADD CONSTRAINT relation_pair_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );	
ALTER TABLE prp_cleanings
    ADD CONSTRAINT relation_clean_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );	
ALTER TABLE prp_equipments
    ADD CONSTRAINT relation_equip_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );
ALTER TABLE prp_layouts
    ADD CONSTRAINT relation_lay_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );	
ALTER TABLE prp_pestcontrols
    ADD CONSTRAINT relation_pest_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );
ALTER TABLE prp_riskclasses
    ADD CONSTRAINT relation_rclass_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );
ALTER TABLE prp_wastedisposals
    ADD CONSTRAINT relation_waste_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );	
ALTER TABLE prp_watersupplies
    ADD CONSTRAINT relation_water_ptype FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );
		
ALTER TABLE prp_pestcontrols ADD CONSTRAINT prp_pestcontrols_ctx_contexts_fk FOREIGN KEY (ref_context) REFERENCES ctx_contexts(id) ;
ALTER TABLE prp_watersupplies ADD CONSTRAINT prp_watersupplies_org_organizations_fk FOREIGN KEY (ref_organization) REFERENCES org_organizations(id) ;
ALTER TABLE prp_equipments ADD CONSTRAINT prp_equipments_ctx_contexts_fk FOREIGN KEY (ref_context) REFERENCES ctx_contexts(id) ;
ALTER TABLE prp_wastedisposals ADD CONSTRAINT prp_wastedisposals_ctx_contexts_fk FOREIGN KEY (ref_context) REFERENCES ctx_contexts(id) ;
ALTER TABLE prp_airconditioning ADD CONSTRAINT prp_airconditioning_ctx_contexts_fk FOREIGN KEY (ref_context) REFERENCES ctx_contexts(id) ;
ALTER TABLE prp_cleanings ADD CONSTRAINT prp_cleanings_ctx_contexts_fk FOREIGN KEY (ref_context) REFERENCES ctx_contexts(id) ;



	
	

 ALTER TABLE ROL_GRANTS_ROL_PROFILES 
  ADD CONSTRAINT rol_grants_rol_profiles_pk PRIMARY KEY ( ref_rol_profiles, 
  ref_rol_grants );
ALTER TABLE ROL_GRANTS_ROL_PROFILES 
  ADD CONSTRAINT rol_grants_rol_profiles_fk1 FOREIGN KEY ( ref_rol_grants ) 
  REFERENCES ROL_GRANTS ( id ); 

ALTER TABLE ROL_GRANTS_ROL_PROFILES 
  ADD CONSTRAINT rol_grants_rol_profiles_fk2 FOREIGN KEY ( ref_rol_profiles) 
  REFERENCES ROL_PROFILES ( id );  

CREATE UNIQUE INDEX prp_watersupplies__idx 
  ON prp_watersupplies ( ref_shape ASC ); 

ALTER TABLE PRP_WATERSUPPLIES 
  ADD CONSTRAINT prp_watersupplies_pk PRIMARY KEY ( id ); 


ALTER TABLE PRP_LAYOUTS 
  ADD CONSTRAINT relation_276 FOREIGN KEY ( ref_riskclasses ) REFERENCES PRP_RISKCLASSES ( 
  id ); 

ALTER TABLE DRW_FLOORS 
  ADD CONSTRAINT relation_278 FOREIGN KEY ( ref_context ) REFERENCES 
  CTX_CONTEXTS ( id ); 

ALTER TABLE PRP_LAYOUTS 
  ADD CONSTRAINT relation_281 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id ); 

ALTER TABLE DRW_FLOORS 
  ADD CONSTRAINT relation_282 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id ); 

ALTER TABLE PRP_LAYOUTS 
  ADD CONSTRAINT relation_283 FOREIGN KEY ( ref_floor ) REFERENCES DRW_FLOORS ( 
  id ); 

ALTER TABLE PRP_WATERSUPPLIES 
  ADD CONSTRAINT relation_285 FOREIGN KEY ( ref_layout, ref_organization, 
  ref_floor ) REFERENCES PRP_LAYOUTS ( id, ref_organization, ref_floor ); 

ALTER TABLE PRP_LAYOUTS 
  ADD CONSTRAINT relation_287 FOREIGN KEY ( ref_shape ) REFERENCES DRW_SHAPES ( 
  id ); 

ALTER TABLE PRP_WATERSUPPLIES 
  ADD CONSTRAINT relation_288 FOREIGN KEY ( ref_shape ) REFERENCES DRW_SHAPES ( 
  id ); 

ALTER TABLE PRP_AIRCONDITIONING 
  ADD CONSTRAINT relation_290 FOREIGN KEY ( ref_layout, ref_organization, 
  ref_floor ) REFERENCES PRP_LAYOUTS ( id, ref_organization, ref_floor ); 

ALTER TABLE PRP_AIRCONDITIONING 
  ADD CONSTRAINT relation_291 FOREIGN KEY ( ref_shape ) REFERENCES DRW_SHAPES ( 
  id ); 

ALTER TABLE PRP_WASTEDISPOSALS 
  ADD CONSTRAINT relation_292 FOREIGN KEY ( ref_shape ) REFERENCES DRW_SHAPES ( 
  id ); 

ALTER TABLE PRP_WASTEDISPOSALS 
  ADD CONSTRAINT relation_293 FOREIGN KEY ( ref_layout, ref_organization, 
  ref_floor ) REFERENCES PRP_LAYOUTS ( id, ref_organization, ref_floor ); 

ALTER TABLE PRP_CLEANINGS 
  ADD CONSTRAINT relation_294 FOREIGN KEY ( ref_shape ) REFERENCES DRW_SHAPES ( 
  id ); 

ALTER TABLE PRP_CLEANINGS 
  ADD CONSTRAINT relation_295 FOREIGN KEY ( ref_layout, ref_organization, 
  ref_floor ) REFERENCES PRP_LAYOUTS ( id, ref_organization, ref_floor ); 

ALTER TABLE EQP_EQUIPMENTS 
  ADD CONSTRAINT relation_298 FOREIGN KEY ( ref_type ) REFERENCES 
  EQP_EQUIPMENTTYPES ( id ); 
    
ALTER TABLE eqp_equipments
    ADD CONSTRAINT relation_83 FOREIGN KEY ( ref_frequency )
        REFERENCES mnt_frequencies ( id );

ALTER TABLE eqp_equipments
    ADD CONSTRAINT relation_84 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );

ALTER TABLE PRP_EQUIPMENTS 
  ADD CONSTRAINT relation_299 FOREIGN KEY ( ref_equipment, ref_type ) REFERENCES 
  EQP_EQUIPMENTS ( id, ref_type ); 

ALTER TABLE PRP_EQUIPMENTS 
  ADD CONSTRAINT relation_300 FOREIGN KEY ( ref_layout, ref_organization, 
  ref_floor ) REFERENCES PRP_LAYOUTS ( id, ref_organization, ref_floor ); 

ALTER TABLE PRP_EQUIPMENTS 
  ADD CONSTRAINT relation_301 FOREIGN KEY ( ref_shape ) REFERENCES DRW_SHAPES ( 
  id ); 

ALTER TABLE PRP_PESTCONTROLS 
  ADD CONSTRAINT relation_302 FOREIGN KEY ( ref_layout, ref_organization, 
  ref_floor ) REFERENCES PRP_LAYOUTS ( id, ref_organization, ref_floor ); 

ALTER TABLE PRP_PESTCONTROLS 
  ADD CONSTRAINT relation_303 FOREIGN KEY ( ref_shape ) REFERENCES DRW_SHAPES ( 
  id ); 

ALTER TABLE EQP_EQUIPMENTTYPES 
  ADD CONSTRAINT relation_305 FOREIGN KEY ( ref_organization ) REFERENCES 
  ORG_ORGANIZATIONS ( id ); 
  
  ALTER TABLE prp_floors
    ADD CONSTRAINT relation_pt1 FOREIGN KEY ( ref_prerequisite_type )
        REFERENCES prp_prerequisite_types ( id );


ALTER TABLE org_certifications
    ADD CONSTRAINT relation_85 FOREIGN KEY ( ref_context )
        REFERENCES ctx_contexts ( id );
ALTER TABLE org_organizations
  ADD CONSTRAINT relation_80 FOREIGN KEY ( ref_context )
     REFERENCES ctx_contexts ( id );

ALTER TABLE eqp_equipmenttypes ADD CONSTRAINT eqp_equipmenttypes_drw_shapes_fk FOREIGN KEY (ref_shape) REFERENCES drw_shapes(id) ;

ALTER TABLE PRP_PESTCONTROLTYPES ADD CONSTRAINT prp_pestcontroltypes_drw_shapes_fk FOREIGN KEY (ref_shape) REFERENCES drw_shapes(id) ;
ALTER TABLE PRP_AIRCONDITIONINGTYPES ADD CONSTRAINT prp_airconditioningtypes_drw_shapes_fk FOREIGN KEY (ref_shape) REFERENCES drw_shapes(id) ;

ALTER TABLE PRP_WASTEDISPOSALTYPES ADD CONSTRAINT prp_wastedisposalstypes_drw_shapes_fk FOREIGN KEY (ref_shape) REFERENCES drw_shapes(id) ;
ALTER TABLE PRP_WATERSUPPLYTYPES ADD CONSTRAINT prp_watersuppliestypes_drw_shapes_fk FOREIGN KEY (ref_shape) REFERENCES drw_shapes(id) ;


