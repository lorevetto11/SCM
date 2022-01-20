/******************************************************************************************************
GPI s.p.a.

SCRIPT DI CREAZIONE VISTE

APPLICAZIONE: SCM

DATA: 2018-03-06

NOTE: Creare la viste default
     

*******************************************************************************************************/

CREATE OR REPLACE FUNCTION organization_tree(_rev numeric)
 RETURNS TABLE(id numeric, name varchar, ref_organization numeric, level int) AS
$func$
   with recursive org_tree as (
   select id, name, ref_organization, 1::int as level
   from org_organizations 
   where id = $1 and deleted = false
   union all
   select c.id, c.name, c.ref_organization, p.level + 1
   from org_organizations c
   join org_tree p on c.ref_organization = p.id
   where c.deleted = false
)
select id, name, ref_organization, level
from org_tree
ORDER  BY id;
$func$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION organization_reverse_tree(_rev numeric)
RETURNS TABLE(id numeric, name varchar, ref_organization numeric) AS 
$func$
with recursive org_fathers as (
  select
    id,name,ref_organization
  from org_organizations
  where
    id = $1 and deleted = false
  union
  select
    o.id, o.name, o.ref_organization
  from org_organizations o
  join org_fathers on org_fathers.ref_organization = o.id
  where c.deleted = false
)
select
  *
from org_fathers
order by
  id;
$func$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION organization_treeD(_rev numeric)
 RETURNS TABLE(id numeric, name varchar, ref_organization numeric,deleted boolean, level int) AS
$func$
   with recursive org_treeD as (
   select id, name, ref_organization,deleted, 1::int as level
   from org_organizations 
   where id = $1 and deleted=false
   union all
   select c.id, c.name, c.ref_organization,c.deleted, p.level + 1
   from org_organizations c
   join org_treeD p on c.ref_organization = p.id  and c.deleted=false
)
select id, name, ref_organization,deleted, level
from org_treeD
ORDER  BY id;
$func$ LANGUAGE sql;