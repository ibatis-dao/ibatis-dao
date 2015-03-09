package fxapp01.excpt;

/**
 *
 * @author StarukhSA
 */
public class ENullArgument extends IllegalArgumentException {
    public ENullArgument(String methodName) {
        super("Method "+methodName+"() arguments must be not null");
    }    
}
