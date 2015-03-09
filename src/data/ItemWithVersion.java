package data;

import java.io.Serializable;

/**
 * 
 * A base interface for all entities that have a version (generation) info
 * can be used for optimistic locking
 * @param <T> type of the version
 */
public interface ItemWithVersion<T> extends Serializable {

    /**
     * Returns the Version data
     * @return the Version
     */
    T getVersion();
}

