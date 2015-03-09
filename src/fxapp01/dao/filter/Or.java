package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class Or extends SqlFilterBaseImpl {
    
    public Or(Object... arg) {
        super("({0}) or ({1})", 2);
        setArgs(arg);
    }
    
}
