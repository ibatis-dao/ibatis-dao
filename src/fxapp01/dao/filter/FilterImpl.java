package fxapp01.dao.filter;

import java.util.ArrayList;
import java.util.Collection;


public class FilterImpl implements Filterable {
    
    private Collection<Filterable> filters;

    @Override
    public Collection<Filterable> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        return filters;
    }

    @Override
    public void addFilter(Filterable filter) {
        getFilters().add(filter);
    }

    @Override
    public void removeAllFilters() {
        getFilters().clear();
    }

    @Override
    public void removeFilter(Filterable filter) {
        getFilters().remove(filter);
    }
    
}
