-- Generato da Oracle SQL Developer Data Modeler 17.3.0.261.1529 
--   in:        2018-03-16 08:49:51 CET 
--   sito:      Oracle Database 11g 
--   tipo:      Oracle Database 11g 
CREATE TABLE CFG_EMAIL_PARAMETERS 
  ( 
     id                 NUMERIC (10) NOT NULL, 
     version            NUMERIC (10) NOT NULL, 
     insert_time        DATE DEFAULT Now() NOT NULL, 
     update_time        DATE NOT NULL, 
     param_code         VARCHAR (80) NOT NULL, 
     param_value        VARCHAR (255) NOT NULL, 
     description        VARCHAR (255) NOT NULL, 
     ref_email_template NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN CFG_EMAIL_PARAMETERS.id IS 'Email  parameter ID'; 

COMMENT ON COLUMN CFG_EMAIL_PARAMETERS.version IS 'Email parameter version'; 

COMMENT ON COLUMN CFG_EMAIL_PARAMETERS.insert_time IS 
'Email parameter insert time'; 

COMMENT ON COLUMN CFG_EMAIL_PARAMETERS.update_time IS 
'Email parameter update time'; 

COMMENT ON COLUMN CFG_EMAIL_PARAMETERS.param_code IS 'Email parameter code'; 

COMMENT ON COLUMN CFG_EMAIL_PARAMETERS.param_value IS 'Email parameter value'; 

COMMENT ON COLUMN CFG_EMAIL_PARAMETERS.description IS 
'Email parameter description'; 

ALTER TABLE CFG_EMAIL_PARAMETERS 
  ADD CONSTRAINT cfg_email_parameters_pk PRIMARY KEY ( id ); 

CREATE TABLE CFG_EMAIL_TEMPLATES 
  ( 
     id          NUMERIC (10) NOT NULL, 
     version     NUMERIC (10) NOT NULL, 
     insert_time DATE DEFAULT Now() NOT NULL, 
     update_time DATE NOT NULL, 
     name        VARCHAR (80) NOT NULL, 
     description VARCHAR (255) NOT NULL 
  ); 

COMMENT ON COLUMN CFG_EMAIL_TEMPLATES.id IS 'Email template ID'; 

COMMENT ON COLUMN CFG_EMAIL_TEMPLATES.version IS 'Email Template version'; 

COMMENT ON COLUMN CFG_EMAIL_TEMPLATES.insert_time IS 
'Email Template insert time'; 

COMMENT ON COLUMN CFG_EMAIL_TEMPLATES.update_time IS 
'Email Template update time'; 

COMMENT ON COLUMN CFG_EMAIL_TEMPLATES.name IS 'Email Template name'; 

COMMENT ON COLUMN CFG_EMAIL_TEMPLATES.description IS 
'Email Template description'; 

ALTER TABLE CFG_EMAIL_TEMPLATES 
  ADD CONSTRAINT cfg_email_templates_pk PRIMARY KEY ( id ); 


CREATE TABLE DRW_FLOORS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     "order"          NUMBER, 
     width            FLOAT, 
     height           FLOAT, 
     ref_context      NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN DRW_FLOORS.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN DRW_FLOORS.version IS 'Organization parameter version'; 

COMMENT ON COLUMN DRW_FLOORS.insert_time IS 'Organization parameter insert time' 
; 

COMMENT ON COLUMN DRW_FLOORS.update_time IS 'Organization parameter update time' 
; 

COMMENT ON COLUMN DRW_FLOORS.name IS 'Organization parameter code'; 

COMMENT ON COLUMN DRW_FLOORS.description IS 'Organization parameter description' 
; 

CREATE UNIQUE INDEX drw_floors__idx 
  ON drw_floors ( ref_context ASC ); 

ALTER TABLE DRW_FLOORS 
  ADD CONSTRAINT drw_floors_pk PRIMARY KEY ( id ); 

CREATE TABLE DRW_SHAPES 
  ( 
     id          NUMERIC (10) NOT NULL, 
     version     NUMERIC (10) NOT NULL, 
     insert_time DATE DEFAULT Now() NOT NULL, 
     update_time DATE NOT NULL, 
     TYPE        VARCHAR (20), 
     start_x     FLOAT, 
     start_y     FLOAT, 
     size_x      FLOAT, 
     size_y      FLOAT, 
     radius      FLOAT, 
     "order"     NUMBER 
  ); 

COMMENT ON COLUMN DRW_SHAPES.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN DRW_SHAPES.version IS 'Organization parameter version'; 

COMMENT ON COLUMN DRW_SHAPES.insert_time IS 'Organization parameter insert time' 
; 

COMMENT ON COLUMN DRW_SHAPES.update_time IS 'Organization parameter update time' 
; 

COMMENT ON COLUMN DRW_SHAPES.TYPE IS 'Organization parameter code'; 

COMMENT ON COLUMN DRW_SHAPES.start_x IS 'Organization parameter description'; 

ALTER TABLE DRW_SHAPES 
  ADD CONSTRAINT drw_shapes_pk PRIMARY KEY ( id ); 

CREATE TABLE EQP_EQUIPMENTS 
  ( 
     id          NUMERIC (10) NOT NULL, 
     version     NUMERIC (10) NOT NULL, 
     insert_time DATE DEFAULT Now() NOT NULL, 
     update_time DATE NOT NULL, 
     name        VARCHAR (100), 
     description VARCHAR (255), 
     ref_type    NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN EQP_EQUIPMENTS.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN EQP_EQUIPMENTS.version IS 'Organization parameter version'; 

COMMENT ON COLUMN EQP_EQUIPMENTS.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN EQP_EQUIPMENTS.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN EQP_EQUIPMENTS.name IS 'Organization parameter code'; 

COMMENT ON COLUMN EQP_EQUIPMENTS.description IS 
'Organization parameter description'; 

ALTER TABLE EQP_EQUIPMENTS 
  ADD CONSTRAINT eqp_equipments_pk PRIMARY KEY ( id, ref_type ); 

CREATE TABLE EQP_EQUIPMENTTYPES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN EQP_EQUIPMENTTYPES.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN EQP_EQUIPMENTTYPES.version IS 'Organization parameter version' 
; 

COMMENT ON COLUMN EQP_EQUIPMENTTYPES.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN EQP_EQUIPMENTTYPES.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN EQP_EQUIPMENTTYPES.name IS 'Organization parameter code'; 

COMMENT ON COLUMN EQP_EQUIPMENTTYPES.description IS 
'Organization parameter description'; 

ALTER TABLE EQP_EQUIPMENTTYPES 
  ADD CONSTRAINT eqp_equipmenttypes_pk PRIMARY KEY ( id ); 



CREATE TABLE PRP_AIRCONDITIONING 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_AIRCONDITIONING.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_AIRCONDITIONING.version IS 
'Organization parameter version'; 

COMMENT ON COLUMN PRP_AIRCONDITIONING.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_AIRCONDITIONING.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_AIRCONDITIONING.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_AIRCONDITIONING.description IS 
'Organization parameter description'; 

CREATE UNIQUE INDEX prp_airconditioning__idx 
  ON prp_airconditioning ( ref_shape ASC ); 

ALTER TABLE PRP_AIRCONDITIONING 
  ADD CONSTRAINT prp_airconditioning_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_CLEANINGS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_CLEANINGS.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_CLEANINGS.version IS 'Organization parameter version'; 

COMMENT ON COLUMN PRP_CLEANINGS.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_CLEANINGS.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_CLEANINGS.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_CLEANINGS.description IS 
'Organization parameter description'; 

CREATE UNIQUE INDEX prp_cleanings__idx 
  ON prp_cleanings ( ref_shape ASC ); 

ALTER TABLE PRP_CLEANINGS 
  ADD CONSTRAINT prp_cleanings_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_EQUIPMENTS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_equipment    NUMERIC (10) NOT NULL, 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_type         NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_EQUIPMENTS.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_EQUIPMENTS.version IS 'Organization parameter version'; 

COMMENT ON COLUMN PRP_EQUIPMENTS.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_EQUIPMENTS.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_EQUIPMENTS.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_EQUIPMENTS.description IS 
'Organization parameter description'; 

CREATE UNIQUE INDEX prp_equipments__idx 
  ON prp_equipments ( ref_equipment ASC, ref_type ASC ); 

ALTER TABLE PRP_EQUIPMENTS 
  ADD CONSTRAINT prp_equipments_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_LAYOUTS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL, 
     id1              NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_LAYOUTS.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_LAYOUTS.version IS 'Organization parameter version'; 

COMMENT ON COLUMN PRP_LAYOUTS.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_LAYOUTS.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_LAYOUTS.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_LAYOUTS.description IS 
'Organization parameter description'; 

CREATE UNIQUE INDEX prp_layouts__idx 
  ON prp_layouts ( id1 ASC ); 

CREATE UNIQUE INDEX prp_layouts__idxv1 
  ON prp_layouts ( ref_shape ASC ); 

ALTER TABLE PRP_LAYOUTS 
  ADD CONSTRAINT prp_layouts_pk PRIMARY KEY ( id, ref_organization, ref_floor ); 

CREATE TABLE PRP_PESTCONTROLS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_PESTCONTROLS.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_PESTCONTROLS.version IS 'Organization parameter version'; 

COMMENT ON COLUMN PRP_PESTCONTROLS.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_PESTCONTROLS.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_PESTCONTROLS.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_PESTCONTROLS.description IS 
'Organization parameter description'; 

CREATE UNIQUE INDEX prp_pestcontrols__idx 
  ON prp_pestcontrols ( ref_shape ASC ); 

ALTER TABLE PRP_PESTCONTROLS 
  ADD CONSTRAINT prp_pestcontrols_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_RISKCLASSES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     color            VARCHAR (20), 
     ref_organization NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_RISKCLASSES.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_RISKCLASSES.version IS 'Organization parameter version'; 

COMMENT ON COLUMN PRP_RISKCLASSES.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_RISKCLASSES.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_RISKCLASSES.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_RISKCLASSES.description IS 
'Organization parameter description'; 

COMMENT ON COLUMN PRP_RISKCLASSES.ref_organization IS 'Organization reference'; 

ALTER TABLE PRP_RISKCLASSES 
  ADD CONSTRAINT prp_riskclasses_pk PRIMARY KEY ( id ); 

CREATE TABLE PRP_WASTEDISPOSALS 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_WASTEDISPOSALS.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_WASTEDISPOSALS.version IS 'Organization parameter version' 
; 

COMMENT ON COLUMN PRP_WASTEDISPOSALS.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_WASTEDISPOSALS.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_WASTEDISPOSALS.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_WASTEDISPOSALS.description IS 
'Organization parameter description'; 

CREATE UNIQUE INDEX prp_wastedisposals__idx 
  ON prp_wastedisposals ( ref_shape ASC ); 

ALTER TABLE PRP_WASTEDISPOSALS 
  ADD CONSTRAINT prp_wastedisposals_pk PRIMARY KEY ( id, ref_layout, 
  ref_organization, ref_floor ); 

CREATE TABLE PRP_WATERSUPPLIES 
  ( 
     id               NUMERIC (10) NOT NULL, 
     version          NUMERIC (10) NOT NULL, 
     insert_time      DATE DEFAULT Now() NOT NULL, 
     update_time      DATE NOT NULL, 
     name             VARCHAR (100), 
     description      VARCHAR (255), 
     ref_layout       NUMERIC (10) NOT NULL, 
     ref_shape        NUMERIC (10) NOT NULL, 
     ref_organization NUMERIC (10) NOT NULL, 
     ref_floor        NUMERIC (10) NOT NULL 
  ); 

COMMENT ON COLUMN PRP_WATERSUPPLIES.id IS 'Organization parameter ID'; 

COMMENT ON COLUMN PRP_WATERSUPPLIES.version IS 'Organization parameter version'; 

COMMENT ON COLUMN PRP_WATERSUPPLIES.insert_time IS 
'Organization parameter insert time'; 

COMMENT ON COLUMN PRP_WATERSUPPLIES.update_time IS 
'Organization parameter update time'; 

COMMENT ON COLUMN PRP_WATERSUPPLIES.name IS 'Organization parameter code'; 

COMMENT ON COLUMN PRP_WATERSUPPLIES.description IS 
'Organization parameter description'; 

CREATE UNIQUE INDEX prp_watersupplies__idx 
  ON prp_watersupplies ( ref_shape ASC ); 

ALTER TABLE PRP_WATERSUPPLIES 
  ADD CONSTRAINT prp_watersupplies_pk PRIMARY KEY ( id ); 



CREATE TABLE ROL_USER_RIGHTS 
  ( 
     id          NUMERIC (10) NOT NULL, 
     insert_time TIMESTAMP NOT NULL, 
     update_time TIMESTAMP NOT NULL, 
     version     NUMERIC (10), 
     name        VARCHAR (50) 
  ); 

ALTER TABLE ROL_USER_RIGHTS 
  ADD CONSTRAINT rol_user_rights_pk PRIMARY KEY ( id ); 

CREATE TABLE ROL_USERS_ROL_PROFILES 
  ( 
     ref_rol_roles    NUMERIC (10) NOT NULL, 
     ref_rol_profiles NUMERIC (10) NOT NULL 
  ); 

ALTER TABLE ROL_USERS_ROL_PROFILES 
  ADD CONSTRAINT rol_users_rol_profiles_pk PRIMARY KEY ( ref_rol_roles, 
  ref_rol_profiles ); 

ALTER TABLE CFG_EMAIL_PARAMETERS 
  ADD CONSTRAINT cfg_eml_parms_cfg_eml_tmpls_fk FOREIGN KEY ( ref_email_template 
  ) REFERENCES CFG_EMAIL_TEMPLATES ( id ); 


ALTER TABLE ROL_USER_RIGHTS 
  ADD CONSTRAINT fk_profiles_users_rights FOREIGN KEY ( id ) REFERENCES 
  ROL_PROFILES ( id ); 

ALTER TABLE ROL_USERS_ROL_PROFILES 
  ADD CONSTRAINT "NULL SET GENERATED_305" FOREIGN KEY ( ref_rol_roles ) 
  REFERENCES ROL_ROLES ( id ); 

ALTER TABLE ROL_USERS_ROL_PROFILES 
  ADD CONSTRAINT "NULL SET GENERATED_307" FOREIGN KEY ( ref_rol_profiles ) 
  REFERENCES ROL_PROFILES ( id ); 

ALTER TABLE PRP_LAYOUTS 
  ADD CONSTRAINT relation_276 FOREIGN KEY ( id1 ) REFERENCES PRP_RISKCLASSES ( 
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

-- Report sintetico di Oracle SQL Developer Data Modeler:  
--  
-- CREATE TABLE                            25 
-- CREATE INDEX                             9 
-- ALTER TABLE                             58 
-- CREATE VIEW                              0 
-- ALTER VIEW                               0 
-- CREATE PACKAGE                           0 
-- CREATE PACKAGE BODY                      0 
-- CREATE PROCEDURE                         0 
-- CREATE FUNCTION                          0 
-- CREATE TRIGGER                           0 
-- ALTER TRIGGER                            0 
-- CREATE COLLECTION TYPE                   0 
-- CREATE STRUCTURED TYPE                   0 
-- CREATE STRUCTURED TYPE BODY              0 
-- CREATE CLUSTER                           0 
-- CREATE CONTEXT                           0 
-- CREATE DATABASE                          0 
-- CREATE DIMENSION                         0 
-- CREATE DIRECTORY                         0 
-- CREATE DISK GROUP                        0 
-- CREATE ROLE                              0 
-- CREATE ROLLBACK SEGMENT                  0 
-- CREATE SEQUENCE                          0 
-- CREATE MATERIALIZED VIEW                 0 
-- CREATE SYNONYM                           0 
-- CREATE TABLESPACE                        0 
-- CREATE USER                              0 
--  
-- DROP TABLESPACE                          0 
-- DROP DATABASE                            0 
--  
-- REDACTION POLICY                         0 
--  
-- ORDS DROP SCHEMA                         0 
-- ORDS ENABLE SCHEMA                       0 
-- ORDS ENABLE OBJECT                       0 
--  
-- ERRORS                                   0 
-- WARNINGS                                 0 