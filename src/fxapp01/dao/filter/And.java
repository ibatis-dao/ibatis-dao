package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class And extends SqlFilterBaseImpl {
    
    public And(Object... arg) {
        super("({0}) and ({1})", 2);
        setArgs(arg);
    }
    
}
