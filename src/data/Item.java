package data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author https://vaadin.com/api/com/vaadin/data/Item.html
 */
public interface Item extends Serializable {

    /*
    * Tries to add a new Property into the Item.
    */
    boolean addItemProperty(Object id, Property property);

    /*
    * Gets the Property corresponding to the given Property ID stored in the Item.
    */
    Property getItemProperty(Object id);

    /*
    * Gets the collection of IDs of all Properties stored in the Item.
    */
    Collection<?> getItemPropertyIds();

    /*
    * Removes the Property identified by ID from the Item.
    */
    boolean removeItemProperty(Object id);
          
}
