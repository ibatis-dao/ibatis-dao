package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public class Equals extends SqlFilterBaseImpl {
    public Equals(Object... arg) {
        super("{0} = {1}", 2);
        setArgs(arg);
    }
}
