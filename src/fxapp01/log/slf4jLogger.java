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
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author serg
 */
public class slf4jLogger implements ILogger {

    private final Logger log;
    
    public slf4jLogger(Class<?> cls) {
        if (cls == null) {
            log = LoggerFactory.getLogger("");
        }
        else {
            log = LoggerFactory.getLogger(cls);
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
        if (log.isWarnEnabled()) {
            log.warn(string);
        }
    }
    
    @Override
    public void warn(String string, Throwable thrwbl) {
        if (log.isWarnEnabled()) {
            log.warn(string, thrwbl);
        }
    }
    
    @Override
    public void warn(String pattern, Object... arguments) {
        if (log.isWarnEnabled()) {
            log.warn(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void error(String string) {
        if (log.isErrorEnabled()) {
            log.error(string);
        }
    }
    
    @Override
    public void error(String string, Throwable thrwbl) {
        if (log.isErrorEnabled()) {
            log.error(string, thrwbl);
        }
    }

    @Override
    public void error(String pattern, Object... arguments) {
        if (log.isErrorEnabled()) {
            log.error(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public boolean isEnabled(ILogger.Level lvl) {
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

