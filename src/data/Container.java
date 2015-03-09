package data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author https://vaadin.com/api/com/vaadin/data/Container.html
 */
public interface Container extends Serializable {
    /* 
    * Adds a new Property to all Items in the Container.
    */
    boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue);

    /*
    * Creates a new Item into the Container, and assign it an automatic ID.
    */
    Object addItem();

    /*
    * Creates a new Item with the given ID in the Container.
    */
    Item addItem(Object itemId);

    /*
    * Tests if the Container contains the specified Item.
    */
    boolean containsId(Object itemId);

    /*
    * Gets the Property identified by the given itemId and propertyId from the Container.
    */
    Property getContainerProperty(Object itemId, Object propertyId);

    /*
    * Gets the ID's of all Properties stored in the Container.
    */
    Collection<?> getContainerPropertyIds();

    /*
    * Gets the Item with the given Item ID from the Container.
    */
    Item getItem(Object itemId);

    /*
    * Gets the ID's of all visible (after filtering and sorting) Items stored in the Container.
    */
    Collection<?> getItemIds();

    /*
    * Gets the data type of all Properties identified by the given Property ID.
    */
    Class<?> getType(Object propertyId);

    /*
    * Removes all Items from the Container.
    */
    boolean removeAllItems();

    /*
    * Removes a Property specified by the given Property ID from the Container.
    */
    boolean removeContainerProperty(Object propertyId);

    /*
    * Removes the Item identified by ItemId from the Container.
    */
    boolean removeItem(Object itemId);

    /*
    * Gets the number of visible Items in the Container.
    */
    int size();
    
    public interface Filter extends Serializable {
        /* https://vaadin.com/api/com/vaadin/data/Container.Filter.html */
        
        /* Check if a change in the value of a property can affect the filtering result. */
        boolean appliesToProperty(Object propertyId);
          
        /* Check if an item passes the filter (in-memory filtering). */
        boolean passesFilter(Object itemId, Item item);
    }
    
    public interface Filterable extends Container, Serializable {
        /* https://vaadin.com/api/com/vaadin/data/Container.Filterable.html */
        
        /* Adds a filter for the container. */
        void addContainerFilter(Container.Filter filter);

        /* Returns the filters which have been applied to the container */
        Collection<Container.Filter> getContainerFilters();

        /* Remove all active filters from the container. */
        void removeAllContainerFilters();

        /* Removes a filter from the container. */
        void removeContainerFilter(Container.Filter filter);
    }

}
