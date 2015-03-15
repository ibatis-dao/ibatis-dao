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
import java.text.MessageFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author serg
 */
public class Log4jLogger implements ILogger {

    private final Logger log;
    
    public Log4jLogger(Class<?> cls) {
        if (cls == null) {
            log = LogManager.getRootLogger();
        }
        else {
            log = LogManager.getLogger(cls);
        }
    }
    
    @Override
    public void info(String string) {
        if (log.isInfoEnabled()) {
            log.info(string);
        }
    }
    
    @Override
    public void info(String pattern, Object... arguments) {
        if (log.isInfoEnabled()) {
            log.info(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void trace(String string) {
        if (log.isTraceEnabled()) {
            log.trace(string);
        }
    }
    
    @Override
    public void trace(String pattern, Object... arguments) {
        if (log.isTraceEnabled()) {
            log.trace(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void debug(String string) {
        if (log.isDebugEnabled()) {
            log.debug(string);
        }
    }
    
    @Override
    public void debug(String pattern, Object... arguments) {
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void warn(String string) {
        if (log.isEnabledFor(org.apache.log4j.Level.WARN)) {
            log.warn(string);
        }
    }
    
    @Override
    public void warn(String string, Throwable thrwbl) {
        if (log.isEnabledFor(org.apache.log4j.Level.WARN)) {
            log.warn(string, thrwbl);
        }
    }
    
    @Override
    public void warn(String pattern, Object... arguments) {
        if (log.isEnabledFor(org.apache.log4j.Level.WARN)) {
            log.warn(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void error(String string) {
        if (log.isEnabledFor(org.apache.log4j.Level.ERROR)) {
            log.error(string);
        }
    }
    
    @Override
    public void error(String string, Throwable thrwbl) {
        if (log.isEnabledFor(org.apache.log4j.Level.ERROR)) {
            log.error(string, thrwbl);
        }
    }

    @Override
    public void error(String pattern, Object... arguments) {
        if (log.isEnabledFor(org.apache.log4j.Level.ERROR)) {
            log.error(MessageFormat.format(pattern, arguments));
        }
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
        return log.isEnabledFor(convertLevel(lvl));
    }
    
}
