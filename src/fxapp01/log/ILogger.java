package fxapp01.log;

/**
 *
 * @author StarukhSA
 */
public interface ILogger {
    
    public enum Level {
        All, Trace, Debug, Info, Warn, Error, Off
    }
    
    public void info(String string);
    
    public void trace(String string);
    
    public void debug(String string);
    
    public void warn(String string);
    
    public void warn(String string, Throwable thrwbl);
    
    public void error(String string);
    
    public void error(String string, Throwable thrwbl);
    
    public boolean isEnabled(ILogger.Level lvl);
    
}
