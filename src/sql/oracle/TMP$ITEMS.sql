/*
select * from v$instance;
select * from v$version;
*/
--drop table tmp$items cascade constraints;

create table tmp$items (
  id number not null,
  name varchar2(150)
);

-- Add comments to the table 
comment on table TMP$ITEMS
  is 'Тренировочная таблица';
-- Add comments to the columns 
comment on column TMP$ITEMS.id
  is 'Код (просто первичный ключ)';
comment on column TMP$ITEMS.name
  is 'Наименование';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TMP$ITEMS
  add constraint TMP$ITEMS_PK1 primary key (ID);
-- Create/Recreate check constraints 
alter table TMP$ITEMS
  add constraint TMP$ITEMS_NN01
  check (id is not null);
--
--drop sequence TMP$ITEMS_SQ;
create sequence TMP$ITEMS_SQ order;
--
grant select on TMP$ITEMS to lisa;
grant select on TMP$ITEMS_SQ to lisa;

-- compile package
@@TMP$ITEMS_PKG.pck;

-- generate test data
begin
  tmp$items_pkg.gentestdata;
end;
commit;
--
select count(*) from tmp$items;
--
select * from tmp$items;
--
with t001 as (
        select i.id, 
               i.name
          from lisa.tmp$items i 
         order by i.id
      )
select rn, id, name
  from (
    select rownum as rn, 
           id, 
           name
      from t001
  )
 where rn between 0 and 100;
--
