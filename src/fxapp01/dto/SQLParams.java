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

/**
 *
 * @author serg
 */
public class SQLParams {
    private INestedRange rowsRange;
    private ISortOrder sortOrder;
    private Object example; // query by example

    public SQLParams(INestedRange rowsRange){
        this(rowsRange, null, null);
    }
    
    public SQLParams(INestedRange rowsRange, ISortOrder sortOrder){
        this(rowsRange, sortOrder, null);
    }
    
    public SQLParams(INestedRange rowsRange, ISortOrder sortOrder, Object example){
        this.rowsRange = rowsRange;
        this.sortOrder = sortOrder;
        this.example = example;
    }
    
    public INestedRange getRowsRange() {
        return rowsRange;
    }

    public void setRowsRange(INestedRange rowsRange) {
        this.rowsRange = rowsRange;
    }

    public ISortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(ISortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Object getExample() {
        return example; // query by example
    }

    public void setExample(Object example) {
        this.example = example; // query by example
    }

}
