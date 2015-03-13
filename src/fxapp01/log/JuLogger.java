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

import java.text.MessageFormat;
import java.util.logging.Logger;


public class JuLogger implements ILogger {

    private final Logger log;
    
    public JuLogger(Class<?> cls) {
        if (cls == null) {
            log = Logger.getAnonymousLogger();
        }
        else {
            log = Logger.getLogger(cls.getName());
        }
    }
    
    @Override
    public void info(String string) {
        if (log.isLoggable(java.util.logging.Level.INFO)) {
            log.info(string);
        }
    }
    
    @Override
    public void info(String pattern, Object... arguments) {
        if (log.isLoggable(java.util.logging.Level.INFO)) {
            log.info(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void trace(String string) {
        if (log.isLoggable(java.util.logging.Level.FINEST)) {
            log.finest(string);
        }
    }
    
    @Override
    public void trace(String pattern, Object... arguments) {
        if (log.isLoggable(java.util.logging.Level.FINEST)) {
            log.finest(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void debug(String string) {
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.fine(string);
        }
    }
    
    @Override
    public void debug(String pattern, Object... arguments) {
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.fine(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void warn(String string) {
        if (log.isLoggable(java.util.logging.Level.WARNING)) {
            log.warning(string);
        }
    }
    
    @Override
    public void warn(String string, Throwable thrwbl) {
        if (log.isLoggable(java.util.logging.Level.WARNING)) {
            log.log(java.util.logging.Level.WARNING, string, thrwbl);
        }
    }
    
    @Override
    public void warn(String pattern, Object... arguments) {
        if (log.isLoggable(java.util.logging.Level.WARNING)) {
            log.warning(MessageFormat.format(pattern, arguments));
        }
    }
    
    @Override
    public void error(String string) {
        if (log.isLoggable(java.util.logging.Level.SEVERE)) {
            log.severe(string);
        }
    }
    
    @Override
    public void error(String string, Throwable thrwbl) {
        if (log.isLoggable(java.util.logging.Level.SEVERE)) {
            log.log(java.util.logging.Level.SEVERE, string, thrwbl);
        }
    }
    
    @Override
    public void error(String pattern, Object... arguments) {
        if (log.isLoggable(java.util.logging.Level.SEVERE)) {
            log.severe(MessageFormat.format(pattern, arguments));
        }
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
        return log.isLoggable(convertLevel(lvl));
    }
    
}
