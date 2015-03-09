package data;

import java.io.Serializable;

/**
 * @author https://vaadin.com/api/com/vaadin/data/Property.html
 */
public interface Property<T> extends Serializable {

    /*
    * Returns the type of the Property.
    */
    Class<? extends T> getType();

    /*
    * Gets the value stored in the Property.
    */
    T getValue();

    /*
    * Tests if the Property is in read-only mode.
    */
    boolean isReadOnly();

    /*
    * Sets the Property's read-only mode to the specified status.
    */
    void setReadOnly(boolean newStatus);

    /*
    * Sets the value of the Property.
    */
    void setValue(T newValue);
          
}
