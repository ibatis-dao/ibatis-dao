package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class IsNull extends SqlFilterBaseImpl {

    private final Object arg;
    
    public IsNull(Object arg) {
        super("{0} is null", 1);
        this.arg = arg;
    }
    
}
