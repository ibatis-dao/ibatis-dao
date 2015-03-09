package fxapp01.excpt;

/**
 *
 * @author StarukhSA
 */
public class ENegativeArgument extends IllegalArgumentException {
    public ENegativeArgument(String methodName) {
        super("Method "+methodName+"() arguments must be great than zero");
    }

    public ENegativeArgument(String methodName, String argName) {
        super("Method "+methodName+"("+argName+") argument must be great than zero");
    }    
}
