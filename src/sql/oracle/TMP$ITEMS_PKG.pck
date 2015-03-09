create or replace package TMP$ITEMS_PKG is

  -- Author  : STARUKHSA
  -- Created : 03.02.2015 11:12:03
  -- Purpose : Тренировочный пакет

------------------------------------------------
/* заполнение таблицы tmp$items тестовыми данными */
procedure genTestData;
------------------------------------------------

procedure insertData(
    p_id in out tmp$items.id%type, 
    p_name in out tmp$items.name%type
    );
------------------------------------------------

end TMP$ITEMS_PKG;
/
create or replace package body TMP$ITEMS_PKG is

------------------------------------------------
/* заполнение таблицы tmp$items тестовыми данными */
procedure genTestData
as
  lid  tmp$items.id%type;
begin
  for i in 1 .. 1000 loop
    lid := tmp$items_sq.nextval;
    insert into tmp$items (id, name)
    values (lid, 'item_'||lid||'_'||dbms_random.string('p', 20));
  end loop;
end;
------------------------------------------------

procedure insertData(
    p_id in out tmp$items.id%type, 
    p_name in out tmp$items.name%type
    )
as
begin
  insert into lisa.tmp$items(id, name) 
  values (tmp$items_sq.nextval, p_name)
  returning id into p_id; 
end;
------------------------------------------------

end TMP$ITEMS_PKG;
/
