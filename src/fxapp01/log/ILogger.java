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

/**
 *
 * @author StarukhSA
 */
public interface ILogger {
    
    public enum Level {
        All, Trace, Debug, Info, Warn, Error, Off
    }
    
    public void info(String string);
    
    public void info(String pattern, Object... arguments);
    
    public void trace(String string);
    
    public void trace(String pattern, Object... arguments);
    
    public void debug(String string);
    
    public void debug(String pattern, Object... arguments);

    public void warn(String string);
    
    public void warn(String string, Throwable thrwbl);
    
    public void warn(String pattern, Object... arguments);

    public void error(String string);
    
    public void error(String string, Throwable thrwbl);
    
    public void error(String pattern, Object... arguments);

    public boolean isEnabled(ILogger.Level lvl);
    
}
