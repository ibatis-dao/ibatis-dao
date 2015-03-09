package fxapp01.dao;

/**
 *
 * @author serg
 */
public enum SqlDialect {
    Oracle("Oracle", "7"),
    PostgreSQL("PostgreSQL", "9");
    
    private final String dialect;
    private final String version;
            
    private SqlDialect(String dialect, String version) {
        this.dialect = dialect;
        this.version = version;
    }
    
    @Override
    public String toString(){
        return dialect;
    }

}
