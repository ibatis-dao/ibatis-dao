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
public class LogMgr {
    
    public static ILogger getLogger(Class<?> cls) {
        //TODO build logger based on settings
        //return new JuLogger(cls);
        //return new slf4jLogger(cls);
        return new Log4jLogger(cls);
        //return new LoggerGroupFactory(cls);
    }
    
    private class LoggerGroupFactory implements ILogger {
        
        private ILogger[] logs;
        
        private LoggerGroupFactory(Class<?> cls) {
            int cnt = 0;
            // try to initiate each logger
            ILogger juLog;
            try {
                juLog = new JuLogger(cls);
                cnt++;
            } catch (Exception e) {
                juLog = null;
            }
            ILogger slf4jLog;
            try {
                slf4jLog = new slf4jLogger(cls);
                cnt++;
            } catch (Exception e) {
                slf4jLog = null;
            }
            ILogger log4jLog;
            try {
                log4jLog = new Log4jLogger(cls);
                cnt++;
            } catch (Exception e) {
                log4jLog = null;
            }
            logs = new ILogger[cnt];
            int idx = 0;
            if (juLog != null) {
                logs[idx] = juLog;
                idx++;
            }
            if (slf4jLog != null) {
                logs[idx] = slf4jLog;
                idx++;
            }
            if (log4jLog != null) {
                logs[idx] = log4jLog;
                idx++;
            }
        }

        @Override
        public void info(String string) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].info(string);
            }
        }

        @Override
        public void info(String pattern, Object... arguments) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].info(pattern, arguments);
            }
        }

        @Override
        public void trace(String string) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].trace(string);
            }
        }

        @Override
        public void trace(String pattern, Object... arguments) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].trace(pattern, arguments);
            }
        }

        @Override
        public void debug(String string) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].debug(string);
            }
        }

        @Override
        public void debug(String pattern, Object... arguments) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].debug(pattern, arguments);
            }
        }

        @Override
        public void warn(String string) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].warn(string);
            }
        }

        @Override
        public void warn(String string, Throwable thrwbl) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].warn(string, thrwbl);
            }
        }

        @Override
        public void warn(String pattern, Object... arguments) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].warn(pattern, arguments);
            }
        }

        @Override
        public void error(String string) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].error(string);
            }
        }

        @Override
        public void error(String string, Throwable thrwbl) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].error(string, thrwbl);
            }
        }

        @Override
        public void error(String pattern, Object... arguments) {
            for (int i = 0; i<logs.length; i++) {
                logs[i].error(pattern, arguments);
            }
        }

        @Override
        public boolean isEnabled(Level lvl) {
            for (int i = 0; i<logs.length; i++) {
                if (logs[i].isEnabled(lvl)) {
                    return true;
                }
            }
            return false;
        }
        
    }

}
