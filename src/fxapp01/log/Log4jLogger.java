package fxapp01.log;

//log4j
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author serg
 */
public class Log4jLogger implements ILogger {

    private Logger log;
    
    public Log4jLogger(Class<?> cls) {
    	if (log == null) {
            if (cls == null) {
                log = LogManager.getRootLogger();
            }
            else {
                log = LogManager.getLogger(cls);
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

    private org.apache.log4j.Level convertLevel(ILogger.Level lvl) {
        switch (lvl) {
            case All: return org.apache.log4j.Level.ALL;
            case Trace: return org.apache.log4j.Level.TRACE;
            case Debug: return org.apache.log4j.Level.DEBUG;
            case Info: return org.apache.log4j.Level.INFO;
            case Warn: return org.apache.log4j.Level.WARN;
            case Error: return org.apache.log4j.Level.ERROR;
            case Off: return org.apache.log4j.Level.OFF;
            default: return org.apache.log4j.Level.INFO;
        }
    }

    @Override
    public boolean isEnabled(ILogger.Level lvl) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return log.isEnabledFor(convertLevel(lvl));
    }
    
}
