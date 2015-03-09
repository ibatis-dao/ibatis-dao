package fxapp01.excpt;

/**
 *
 * @author StarukhSA
 */
public class ENullArgument extends IllegalArgumentException {
    public ENullArgument(String methodName) {
        super("Method "+methodName+"() arguments must be not null");
    }    

    public ENullArgument(String methodName, String argName) {
        super("Method "+methodName+"("+argName+") argument must be not null");
    }    
}
