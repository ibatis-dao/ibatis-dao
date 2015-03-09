package fxapp01.excpt;

/**
 *
 * @author StarukhSA
 */
public class EArgumentBreaksRule extends IllegalArgumentException {
    public EArgumentBreaksRule(String methodName, String rule) {
        super("Method "+methodName+"() arguments should match rule ("+rule+")");
    }

    public EArgumentBreaksRule(String methodName, String argName, String rule) {
        super("Method "+methodName+"("+argName+") argument should match rule ("+rule+")");
    }    
}
