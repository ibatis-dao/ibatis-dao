/*
 * Copyright 2015 serg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fxapp01.dto;

import fxapp01.dao.filter.ISqlFilterable;
import fxapp01.dao.sort.IDAOSortOrder;

/**
 *
 * @author serg
 * @param <RangeKeyClass>
 */
public class SQLParams {
    private NestedIntRange rowsRange;
    private IDAOSortOrder sortOrder;
    private ISqlFilterable filter;
    private Object example; // query by example

    public SQLParams(INestedRange rowsRange){
        this(rowsRange, null, null);
    }
    
    public SQLParams(INestedRange rowsRange, IDAOSortOrder sortOrder){
        this(rowsRange, sortOrder, null);
    }
    
    public SQLParams(INestedRange rowsRange, ISqlFilterable filter){
        this(rowsRange, null, filter);
    }
    
    public SQLParams(INestedRange rowsRange, IDAOSortOrder sortOrder, ISqlFilterable filter){
        if (rowsRange != null) {
            this.rowsRange = new NestedIntRange(rowsRange);
            //this.rowsRange = rowsRange.clone();
        } else {
            this.rowsRange = null;
        }
        this.sortOrder = sortOrder;
        this.filter = filter;
        this.example = null;
    }
    
    public NestedIntRange getRowsRange() {
        return rowsRange;
    }

    public void setRowsRange(NestedIntRange rowsRange) {
        if (rowsRange != null) {
            this.rowsRange = rowsRange.clone();
        } else {
            this.rowsRange = null;
        }
    }

    public IDAOSortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(IDAOSortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ISqlFilterable getFilter() {
        return filter;
    }

    public void setFilter(ISqlFilterable filter) {
        this.filter = filter;
    }

    public Object getExample() {
        return example; // query by example
    }

    public void setExample(Object example) {
        this.example = example; // query by example
    }

}
