package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class IsNull extends SqlFilterBaseImpl {

    public IsNull() {
        super("{0} is null", 1);
    }
    
}
