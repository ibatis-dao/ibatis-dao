CREATE TABLE test02
(
  id serial NOT NULL,
  name character varying(150),
  CONSTRAINT test02_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE test01
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