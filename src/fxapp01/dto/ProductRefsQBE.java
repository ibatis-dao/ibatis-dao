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
public class ProductRefsQBE extends QueryExtraParam {

    private ProductRefs example;
    
    public ProductRefsQBE(ProductRefs example, NestedIntRange rowsrange) {
        this(example, rowsrange, null);
    }

    public ProductRefsQBE(ProductRefs example, NestedIntRange rowsrange, SortOrder sortOrder) {
        super(rowsrange, sortOrder);
        this.example = example;
    }
    
    public ProductRefs getExample() {
        return this.example;
    }

    public void setExample(ProductRefs example) {
        this.example = example;
    }

}
