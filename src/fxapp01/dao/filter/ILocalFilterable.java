package fxapp01.dao.filter;

/**
 *
 * @author serg
 */
public interface ILocalFilterable {
    
    /* Check if an item passes the filter (in-memory filtering). 
    */
    boolean passesFilter(Object item);
          
}
