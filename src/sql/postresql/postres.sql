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


-- Function: random_string(integer)

-- DROP FUNCTION random_string(integer);

CREATE OR REPLACE FUNCTION random_string(lengh integer)
  RETURNS character varying AS
$BODY$ 
  SELECT array_to_string(ARRAY(
          SELECT substr('abcdefghijklmnopqrstuv',trunc(random()*21+1)::int,1)       
             FROM generate_series(1,$1)),'')
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION random_string(integer)
  OWNER TO postgres;

-- Function: test01datagenerator()

-- DROP FUNCTION test01datagenerator();

CREATE OR REPLACE FUNCTION test01datagenerator()
  RETURNS void AS
$BODY$insert into test01(b, c) 
select random_string(20), random_string(30) 
from generate_series(1, 1000000);$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION test01datagenerator()
  OWNER TO postgres;

-- Function: test02datagenerator()

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
ALTER FUNCTION test02datagenerator()
  OWNER TO postgres;


SELECT test02datagenerator();

-- Function: test02_insertrow(numeric, character varying)

-- DROP FUNCTION test02_insertrow(numeric, character varying);

CREATE OR REPLACE FUNCTION test02_insertrow(pid numeric, pname character varying)
  RETURNS numeric AS
$BODY$
declare
  t2 test02;
begin
  insert into test02 (id, name)
  values (DEFAULT, pname)
  returning * into t2;
  return t2.id;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION test02_insertrow(numeric, character varying)
  OWNER TO postgres;

-- Function: test02_insertrow2(bigint, character varying)

-- DROP FUNCTION test02_insertrow2(bigint, character varying);

CREATE OR REPLACE FUNCTION test02_insertrow2(pid bigint, pname character varying)
  RETURNS bigint AS
$BODY$
declare
  t2 test02;
begin
  insert into test02 (id, name)
  values (DEFAULT, pname)
  returning * into t2;
  return t2.id;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION test02_insertrow2(bigint, character varying)
  OWNER TO postgres;

-- Function: test02_insertrow2(test02)

-- DROP FUNCTION test02_insertrow2(test02);

CREATE OR REPLACE FUNCTION test02_insertrow2(prow test02)
  RETURNS numeric AS
$BODY$
declare
  result test02;
begin
  if (prow.id is null) or (prow.id = 0) then
    prow.id = nextval('test02_id_seq');
  end if;
  insert into test02 
  values (prow.*)
  returning * into result;
  return result.id;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION test02_insertrow2(test02)
  OWNER TO postgres;
