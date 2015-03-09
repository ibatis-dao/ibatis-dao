package fxapp01.dao;

/**
 *
 * @author serg
 */
public class BeanPropertyMapping {
    
// properties got from org.apache.ibatis.mapping.ResultMapping
    
    private String property;
    private String column;
    private Class<?> javaType;
    
    
    public BeanPropertyMapping(String property, String column) {
        this(property, column, null);
    }
    
    public BeanPropertyMapping(String property, String column, Class<?> javaType) {
        this.property = property;
        this.column = column;
        this.javaType = javaType;
    }
    
    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

}
