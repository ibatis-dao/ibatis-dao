/*
 * Copyright 2015 serg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fxapp01.log;

//log4j
//import org.apache.log4j.Logger;
//import org.apache.log4j.LogManager;

//slf4j
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//java.util.logging
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
        log.log(java.util.logging.Level.WARNING, string, thrwbl); //java.util.logging
    }
    
    @Override
    public void error(String string) {
        //log.error(string); // slf4j, log4j
    	log.severe(string); //java.util.logging
    }
    
    @Override
    public void error(String string, Throwable thrwbl) {
        //log.error(string, thrwbl); // slf4j, log4j
        log.log(java.util.logging.Level.SEVERE, string, thrwbl); //java.util.logging
    }
    
    private java.util.logging.Level convertLevel(ILogger.Level lvl) {
        switch (lvl) {
            case All: return java.util.logging.Level.ALL;
            case Trace: return java.util.logging.Level.FINEST;
            case Debug: return java.util.logging.Level.FINE;
            case Info: return java.util.logging.Level.INFO;
            case Warn: return java.util.logging.Level.WARNING;
            case Error: return java.util.logging.Level.SEVERE;
            case Off: return java.util.logging.Level.OFF;
            default: return java.util.logging.Level.INFO;
        }
    }

    @Override
    public boolean isEnabled(ILogger.Level lvl) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return log.isLoggable(convertLevel(lvl));
    }
    
}
