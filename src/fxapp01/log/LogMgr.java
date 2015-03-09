package fxapp01.log;

/**
 *
 * @author StarukhSA
 */
public class LogMgr {
    
    public static ILogger getLogger(Class<?> cls) {
        //TODO build logger based on settings
        //return new JuLogger(cls);
        //return new slf4jLogger(cls);
        return new Log4jLogger(cls);
    }

}
