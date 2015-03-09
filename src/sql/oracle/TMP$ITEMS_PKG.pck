create or replace package TMP$ITEMS_PKG is

  -- Author  : STARUKHSA
  -- Created : 03.02.2015 11:12:03
  -- Purpose : ������������� �����

------------------------------------------------
/* ���������� ������� tmp$items ��������� ������� */
procedure genTestData;
------------------------------------------------

end TMP$ITEMS_PKG;
/
create or replace package body TMP$ITEMS_PKG is

------------------------------------------------
/* ���������� ������� tmp$items ��������� ������� */
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
end TMP$ITEMS_PKG;
/
