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
package fxapp01.dao.filter;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.EUnsupported;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Properties;


public class Filter implements ISqlFilterable, ILocalFilterable {

    private String sqlTemplate;
    private String sqlDialect;
    private Object[] args;
    private int argCount;

    protected Filter(String sqlTemplate, int argCount) {
        this.sqlTemplate = sqlTemplate;
        this.argCount = argCount;
    }
    
    @Override
    public String getText() {
        return format(sqlTemplate, args);
    }

    @Override
    public String toString() {
        return getText();
    }
    
    protected String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }

    /**
     * @param sqlTemplate of the sqlTemplate to set
     */
    protected void setSqlTemplate(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public String getSqlDialect() {
        return sqlDialect;
    }
    
    protected String getSqlDialectFileName() {
        return "Filter_"+sqlDialect+".properties";
    }
    
    protected boolean IsDialectSupported(String sqlDialect) {
        URL url = getClass().getResource(getSqlDialectFileName());
        return (url != null);
    }
    
    public void setSqlDialect(String sqlDialect) throws IOException {
        if (IsDialectSupported(sqlDialect)) {
            this.sqlDialect = sqlDialect;
            sqlTemplate = loadSqlTemplate();
        } else {
            throw new EUnsupported("Sql dialect ("+sqlDialect+")");
        }
    }
    
    public String loadSqlTemplate() throws IOException {
        URL url = getClass().getResource(getSqlDialectFileName());
        if (url != null) {
            URLConnection connection = url.openConnection();
            if (connection != null) {
                connection.setUseCaches(true);
                InputStream stream = connection.getInputStream();
                if (stream != null) {
                    Properties prop = new Properties();
                    prop.load(stream);
                    sqlTemplate = prop.getProperty(getClass().getName(), sqlTemplate);
                    stream.close();
                }
            }
        } else {
            throw new EUnsupported("Sql dialect ("+sqlDialect+")");
        }
        return sqlTemplate;
    }
    
    /**
     * @return the args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(Object[] args) {
        if (((args == null) && (argCount != 0)) || ((args != null) && (args.length != argCount))) {
            throw new EArgumentBreaksRule("setArgs", "args.length == argCount");
        }
        this.args = args;
    }

    /**
     * @param arg the arg to set
     */
    protected void setOneArg(Object arg) {
        setArgs(new Object[]{arg});
    }

    public int getArgCount() {
        return argCount;
    }

    protected void setArgCount(int argCount) {
        this.argCount = argCount;
    }

    @Override
    public boolean passesFilter(Object item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
////////////////////////////////////////////////////////////////////////////

public static class And extends Filter {
    
    public And(Object... arg) {
        super("({0}) and ({1})", 2);
        setArgs(arg);
    }
    
    @Override
    public boolean passesFilter(Object item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

public static class Between extends Filter {
    
    public Between(Object... arg) {
        super("{0} between {1} and {2}", 3);
        setArgs(arg);
    }
}

public static class Equals extends Filter {
    
    public Equals(Object... arg) {
        super("{0} = {1}", 2);
        setArgs(arg);
    }
    
}

public static class NotEquals extends Filter {
    
    public NotEquals(Object... arg) {
        super("{0} <> {1}", 2);
        setArgs(arg);
    }
}

public static class Greater extends Filter {
    
    public Greater(Object... arg) {
        super("{0} > {1}", 2);
        setArgs(arg);
    }
}

public static class GreaterOrEquals extends Filter {
    
    public GreaterOrEquals(Object... arg) {
        super("{0} >= {1}", 2);
        setArgs(arg);
    }
}

public static class IsNull extends Filter {

    public IsNull(Object arg) {
        super("{0} is null", 1);
        setOneArg(arg);
    }
    
}

public static class IsNotNull extends Filter {
    
    public IsNotNull(Object... arg) {
        super("{0} is not null", 1);
        setArgs(arg);
    }
}

public static class Less extends Filter {
    
    public Less(Object... arg) {
        super("{0} < {1}", 2);
        setArgs(arg);
    }
}

public static class LessOrEquals extends Filter {
    
    public LessOrEquals(Object... arg) {
        super("{0} =< {1}", 2);
        setArgs(arg);
    }
}

public static class Like extends Filter {
    
    public Like(Object... arg) {
        super("{0} like {1}", 2);
        setArgs(arg);
    }
}

public static class NotLike extends Filter {
    
    public NotLike(Object... arg) {
        super("{0} not like {1}", 2);
        setArgs(arg);
    }
}

public static class Not extends Filter {
    
    public Not(Object arg) {
        super("not ({0})", 1);
        setOneArg(arg);
    }
    
}

public static class Or extends Filter {
    
    public Or(Object... arg) {
        super("({0}) or ({1})", 2);
        setArgs(arg);
    }
    
}

public static class Containing extends Filter {
    
    public Containing(Object... arg) {
        super("{0} containing {1}", 2);
        setArgs(arg);
    }
    
}

public static class StartsWith extends Filter {
    
    public StartsWith(Object... arg) {
        super("{0} starts with {1}", 2);
        setArgs(arg);
    }
    
}

public static class EndsWith extends Filter {
    
    public EndsWith(Object... arg) {
        super("{0} ends with {1}", 2);
        setArgs(arg);
    }
    
}

public static class In extends Filter {
    
    public In(Object... arg) {
        super("{0} in {1}", 2);
        setArgs(arg);
    }
    
}

public static class NotIn extends Filter {
    
    public NotIn(Object... arg) {
        super("{0} not in {1}", 2);
        setArgs(arg);
    }
    
}

public static class Exists extends Filter {
    
    public Exists(Object... arg) {
        super("exists {0}", 1);
        setArgs(arg);
    }
    
}

public static class NotExists extends Filter {
    
    public NotExists(Object... arg) {
        super("not exists {0}", 1);
        setArgs(arg);
    }
    
}

}
