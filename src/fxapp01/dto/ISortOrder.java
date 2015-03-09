package fxapp01.dto;

/**
 * список сортировки
 * @author serg
 */
public interface ISortOrder {
    
    public enum Direction {
        ASC, DESC, NONE
    }
    
    /* возвращает имя поля с направлением сортировки */
    String build();
    int size();
    String getName(int index);
    ISortOrder.Direction getDirection(int index);
    void add(String columnName, ISortOrder.Direction direction);
    boolean del(int index);
    void clear();
}
