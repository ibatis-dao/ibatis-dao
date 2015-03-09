package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class Not extends SqlFilterBaseImpl {
    
    public Not(Object arg) {
        super("not ({0})", 1);
        setOneArg(arg);
    }
    
}
