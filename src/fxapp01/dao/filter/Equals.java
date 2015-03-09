package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class Equals extends SqlFilterBaseImpl {
    public Equals() {
        super("{0} = {1}", 2);
    }
}
