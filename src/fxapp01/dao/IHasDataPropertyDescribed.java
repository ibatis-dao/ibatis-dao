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

/**
 *
 * @author serg
 */
public interface IHasDataPropertyDescribed<B,V> extends IHasDataProperty<B,V> {
    
    /*
    * Tries to add a new Property into the Item.
    */
    boolean addDescribedDataProperty(Object id, IDataPropertyDescribed<B,V> property);

    /*
    * Gets the Property corresponding to the given Property ID stored in the Item.
    */
    IDataPropertyDescribed<B,V> getDescribedDataProperty(Object id);
    
}
