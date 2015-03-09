package fxapp01.dao.filter;

import fxapp01.excpt.EArgumentBreaksRule;
import java.text.MessageFormat;
import java.util.Iterator;


public class SqlFilterBaseImpl extends FilterImpl implements ISqlFilterable {

    private String template;

    private Object[] args;
    private int argCount;

    protected SqlFilterBaseImpl(String template, int argCount) {
        this.template = template;
        this.argCount = argCount;
    }
    
    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append(format(template, args));
        Filterable f;
        Iterator<Filterable> i = getFilters().iterator();
        while (i.hasNext()){
            f = i.next();
            if (f instanceof ISqlFilterable) {
                sb.append(((ISqlFilterable)f).getText());
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getText();
    }
    
    protected String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    public String getTemplate() {
        return template;
    }

    /**
     * @param template of the template to set
     */
    protected void setTemplate(String template) {
        this.template = template;
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
    
}
