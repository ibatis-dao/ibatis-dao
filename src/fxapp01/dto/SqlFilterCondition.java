package fxapp01.dto;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.ENullArgument;
import java.text.MessageFormat;

/**
 *
 * @author serg
 */
public enum SqlFilterCondition {
    
    Not("not", 0),
    And("and", 0),
    Or("or", 0),
    Equals("{0} = {1}", 2), 
    NotEquals("{0} <> {1}", 2), 
    LessOrEquals("{0} =< {1}", 2), 
    Less("{0} < {1}", 2), 
    Greater("{0} > {1}", 2), 
    GreaterOrEquals("{0} >= {1}", 2), 
    Between("{0} between {1} and {2}", 3),
    IsNull("{0} is null", 1), 
    IsNotNull("{0} is not null", 1), 
    Like("{0} like {1}", 2), 
    NotLike("{0} not like {1}", 2), 
    Containing("{0} containing {1}", 2),
    StartsWith("{0} starts with {1}", 2),
    EndsWith("{0} ends with {1}", 2),
    In("{0} in {1}", 2), 
    NotIn("{0} not in {1}", 2), 
    Exists("exists {0}", 1), 
    NotExists("not exists {0}", 1);
    
    private final String text;
    private final int argCount;
            
    SqlFilterCondition(String text, int argCount) {
        this.text = text;
        this.argCount = argCount;
    }
    
    @Override
    public String toString(){
        return super.name()+", text="+text+", argCount="+argCount;
    }

    public String build(Object... args){
        if (args == null) {
            throw new ENullArgument("build");
        }
        if (args.length != argCount) {
            throw new EArgumentBreaksRule("build", "args.length == Default argument count");
        }
        String result = MessageFormat.format(text, args);
        return result;
    }

}
