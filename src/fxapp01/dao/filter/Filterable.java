package fxapp01.dao.filter;

import java.util.Collection;

/**
 *
 * @author serg
 */
public interface Filterable {
        /* https://vaadin.com/api/com/vaadin/data/Container.Filterable.html */
        
        //Filterable getParentFilter();
        
        /* Adds a filter for the container. */
        void addFilter(Filterable filter);

        /* Returns the filters which have been applied to the container */
        Collection<Filterable> getFilters();

        /* Remove all active filters from the container. */
        void removeAllFilters();

        /* Removes a filter from the container. */
        void removeFilter(Filterable filter);
}
