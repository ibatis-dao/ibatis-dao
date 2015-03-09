package fxapp01.log;

//log4j
//import org.apache.log4j.Logger;
//import org.apache.log4j.LogManager;

//slf4j
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//java.util.logging
import java.util.logging.Level;
import java.util.logging.Logger;


public class JuLogger implements ILogger {

    private Logger log;
    
    public JuLogger(Class<?> cls) {
        //if (log == null) log = LoggerFactory.getLogger(cls); //slf4j
    	//if (log == null) log = LogManager.getLogger(cls); //log4j
    	
    	if (log == null) {
            if (cls == null) {
                log = Logger.getAnonymousLogger();
            }
            else {
                log = Logger.getLogger(cls.getName());
            }
        }
    }
    
    @Override
    public void info(String string) {
        log.info(string);
    }
    
    @Override
    public void trace(String string) {
        //log.trace(string); // slf4j, log4j
        log.finest(string); //java.util.logging
    }
    
    @Override
    public void debug(String string) {
        //log.debug(string); // slf4j, log4j
    	log.fine(string); //java.util.logging
    }
    
    @Override
    public void warn(String string) {
        //log.warn(string); // slf4j, log4j
    	log.warning(string); //java.util.logging
    }
    
    @Override
    public void warn(String string, Throwable thrwbl) {
        //log.warn(string, thrwbl); // slf4j, log4j
        log.log(Level.WARNING, string, thrwbl); //java.util.logging
    }
    
    @Override
    public void error(String string) {
        //log.error(string); // slf4j, log4j
    	log.severe(string); //java.util.logging
    }
    
    @Override
    public void error(String string, Throwable thrwbl) {
        //log.error(string, thrwbl); // slf4j, log4j
        log.log(Level.SEVERE, string, thrwbl); //java.util.logging
    }
    
}
