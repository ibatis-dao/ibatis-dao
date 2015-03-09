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

import fxapp01.dao.sort.SortOrder;

/**
 *
 * @author serg
 */
public class QueryExtraParam {
    private NestedIntRange rowsrange;
    private SortOrder sortOrder;

    public QueryExtraParam(NestedIntRange rowsrange){
        this(rowsrange, null);
    }
    
    public QueryExtraParam(NestedIntRange rowsrange, SortOrder sortOrder){
        this.rowsrange = rowsrange;
        this.sortOrder = sortOrder;
    }
    
    public NestedIntRange getRowsRange() {
        return rowsrange;
    }

    public void setRowsRange(NestedIntRange rowsrange) {
        this.rowsrange = rowsrange;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

}
