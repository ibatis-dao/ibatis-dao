package fxapp01.log;

//slf4j
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author serg
 */
public class slf4jLogger implements ILogger {

    private Logger log;
    
    public slf4jLogger(Class<?> cls) {
    	if (log == null) {
            if (cls == null) {
                log = LoggerFactory.getLogger("");
            }
            else {
                log = LoggerFactory.getLogger(cls);
            }
        }
    }
    
    @Override
    public void info(String string) {
        log.info(string);
    }
    
    @Override
    public void trace(String string) {
        log.trace(string);
    }
    
    @Override
    public void debug(String string) {
        log.debug(string);
    }
    
    @Override
    public void warn(String string) {
        log.warn(string);
    }
    
    @Override
    public void warn(String string, Throwable thrwbl) {
        log.warn(string, thrwbl);
    }
    
    @Override
    public void error(String string) {
        log.error(string);
    }
    
    @Override
    public void error(String string, Throwable thrwbl) {
        log.error(string, thrwbl);
    }
    
}

