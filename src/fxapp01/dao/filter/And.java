package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class And extends SqlFilterBaseImpl {
    
    public And() {
        super("{0} and {1}", 2);
    }
}
