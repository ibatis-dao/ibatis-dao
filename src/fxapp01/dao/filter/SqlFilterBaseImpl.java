package fxapp01.dao.filter;

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
        Filterable f;
        Iterator<Filterable> i = getFilters().iterator();
        while (i.hasNext()){
            f = i.next();
            if (f instanceof ISqlFilterable) {
                sb.append(((ISqlFilterable)f).getText());
            }
        }
        sb.append(MessageFormat.format(template, args));
        return sb.toString();
    }

    public String getTemplate() {
        return template;
    }

    /**
     * @param text of the template to set
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
        this.args = args;
    }

    public int getArgCount() {
        return argCount;
    }

    protected void setArgCount(int argCount) {
        this.argCount = argCount;
    }
    
}
