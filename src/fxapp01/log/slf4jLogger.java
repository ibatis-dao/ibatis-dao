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

    @Override
    public boolean isEnabled(ILogger.Level lvl) {
        //throw new UnsupportedOperationException("Not supported yet.");
        switch (lvl) {
            case All: return true;
            case Trace: return log.isTraceEnabled();
            case Debug: return log.isDebugEnabled();
            case Info: return log.isInfoEnabled();
            case Warn: return log.isWarnEnabled();
            case Error: return log.isErrorEnabled();
            case Off: return false;
            default: return log.isInfoEnabled();
        }
    }
    
}

