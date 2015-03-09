package fxapp01.dto;

/**
 *
 * @author serg
 */
public interface ISqlFilter {
    
    /* возвращает имя поля с направлением сортировки */
    String build();
    int size();
    String getName(int index);
    SqlFilterCondition getCondition(int index);
    void add(String columnName, SqlFilterCondition condition);
    void toggle(int index);
    boolean del(int index);
    void clear();
}
