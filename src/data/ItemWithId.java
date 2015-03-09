package data;

import java.io.Serializable;

/**      based on org.datafx.util.EntityWithId
 * 
 * A base interface for all entities that have a unique id
 * @param <T> type of the id
 */
public interface ItemWithId<T> extends Serializable {

    /**
     * Returns the id
     * @return the id
     */
    T getId();
}
