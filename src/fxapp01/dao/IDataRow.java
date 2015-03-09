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
package fxapp01.dao;

import data.Property;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author serg
 */
public interface IDataRow extends Serializable {
    
    /*
    * Tries to add a new Property into the Item.
    */
    boolean addDataRowProperty(Object id, Property property);

    /*
    * Gets the Property corresponding to the given Property ID stored in the Item.
    */
    Property getDataRowProperty(Object id);

    /*
    * Gets the collection of IDs of all Properties stored in the Item.
    */
    Collection<?> getDataRowPropertyIds();

    /*
    * Removes the Property identified by ID from the Item.
    */
    boolean removeDataRowProperty(Object id);
          
}
