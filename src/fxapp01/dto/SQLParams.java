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

import fxapp01.dao.sort.IDAOSortOrder;

/**
 *
 * @author serg
 */
public class SQLParams {
    private NestedIntRange rowsRange;
    private IDAOSortOrder sortOrder;
    private Object example; // query by example

    public SQLParams(INestedRange rowsRange){
        this(rowsRange, null, null);
    }
    
    public SQLParams(INestedRange rowsRange, IDAOSortOrder sortOrder){
        this(rowsRange, sortOrder, null);
    }
    
    public SQLParams(INestedRange rowsRange, IDAOSortOrder sortOrder, Object example){
        this.rowsRange = new NestedIntRange(rowsRange);
        this.sortOrder = sortOrder;
        this.example = example;
    }
    
    public NestedIntRange getRowsRange() {
        return rowsRange;
    }

    public void setRowsRange(INestedRange rowsRange) {
        this.rowsRange = new NestedIntRange(rowsRange);
    }

    public IDAOSortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(IDAOSortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Object getExample() {
        return example; // query by example
    }

    public void setExample(Object example) {
        this.example = example; // query by example
    }

}
