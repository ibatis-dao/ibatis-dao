create or replace package TMP$ITEMS_PKG is

  -- Author  : serg
  -- Created : 03.02.2015 11:12:03
  -- Purpose : package for testing purpose

------------------------------------------------
/* filling out of table tmp$items by random data for tests */
procedure genTestData(dataSize in numeric default 1000);
------------------------------------------------

procedure insertData(
    p_id in out tmp$items.id%type, 
    p_name in out tmp$items.name%type
    );
------------------------------------------------

procedure updateData(
    p_id in out tmp$items.id%type, 
    p_name in out tmp$items.name%type
    );
------------------------------------------------

procedure deleteData(
    p_id in tmp$items.id%type
    );
------------------------------------------------

end TMP$ITEMS_PKG;
/
create or replace package body TMP$ITEMS_PKG is

------------------------------------------------
/* filling out of table tmp$items by random data for tests */
procedure genTestData(dataSize in numeric default 1000)
as
  lid  tmp$items.id%type;
begin
  for i in 1 .. dataSize loop
    lid := tmp$items_sq.nextval;
    insert into tmp$items (id, name)
    values (lid, 'testitem_'||lid||'_'||dbms_random.string('p', 20));
  end loop;
end;
------------------------------------------------

procedure insertData(
    p_id in out tmp$items.id%type, 
    p_name in out tmp$items.name%type
    )
as
begin
  insert into tmp$items(id, name) 
  values (tmp$items_sq.nextval, p_name)
  returning id into p_id; 
end;
------------------------------------------------

procedure updateData(
    p_id in out tmp$items.id%type, 
    p_name in out tmp$items.name%type
    )
as
begin
  update tmp$items
     set name = p_name
   where id = p_id
  returning id, name into p_id, p_name; 
end;
------------------------------------------------

procedure deleteData(
    p_id in tmp$items.id%type
    )
as
begin
  delete from tmp$items
   where id = p_id; 
end;
------------------------------------------------

end TMP$ITEMS_PKG;
/
