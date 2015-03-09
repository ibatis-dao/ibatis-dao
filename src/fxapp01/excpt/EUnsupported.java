package fxapp01.excpt;

/**
 *
 * @author StarukhSA
 */
public class EUnsupported extends UnsupportedOperationException {
    
    public EUnsupported() {
        super();
    }    

    public EUnsupported(String subject) {
        super(subject+" not supported");
    }    
}
