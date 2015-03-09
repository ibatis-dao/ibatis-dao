-- DROP TABLE test02;

CREATE TABLE test02
(
  id bigserial NOT NULL,
  name character varying(150),
  CONSTRAINT test02_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE test02
  OWNER TO postgres;


-- DROP FUNCTION test02datagenerator();

CREATE OR REPLACE FUNCTION test02datagenerator()
  RETURNS void AS
$BODY$
insert into test02(name) 
select random_string(20)
from generate_series(1, 1000);
$BODY$
LANGUAGE sql VOLATILE
COST 100;

ALTER FUNCTION test01datagenerator()
  OWNER TO postgres;

SELECT test02datagenerator();

-- DROP FUNCTION test02_insertrow(bigint, character varying);

CREATE OR REPLACE FUNCTION test02_insertrow(pid bigint, pname character varying)
  RETURNS test02 AS
$BODY$
declare
  t2 test02;
begin
  insert into test02 (id, name)
  values (pid, pname)
  returning * into t2;
  return t2;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION test02_insertrow(bigint, character varying)
  OWNER TO postgres;


-- DROP FUNCTION "test02_insertRow2"(test02);

CREATE OR REPLACE FUNCTION "test02_insertRow2"("pRow" test02)
  RETURNS test02 AS
$BODY$begin
  insert into test02 
  values (pRow)
  returning *;
end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "test02_insertRow2"(test02)
  OWNER TO postgres;

