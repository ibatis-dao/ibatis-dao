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

import fxapp01.orm.ORMFacade;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author serg
 */
public class DAOProperties extends BeanPropertiesDescribed implements IDAOProperties {

    private final List<BeanPropertyMapping> beanPropertiesMap;
    private List<String> columnNames;
    private List<String> propertyNames;
    
    public DAOProperties(Class<?> beanClass) throws IntrospectionException, IOException {
        super(beanClass);
        ORMFacade dao = new ORMFacade();
        this.beanPropertiesMap = dao.getBeanPropertiesMapping(beanClass);
    }
    
    @Override
    public List<String> getColumnNames() {
        log.trace(">>> selectTotalRange");
        synchronized(this) {
            if (columnNames == null) {
                columnNames = new ArrayList<>();
                Iterator<BeanPropertyMapping> bpi = beanPropertiesMap.iterator();
                while (bpi.hasNext()) {
                    BeanPropertyMapping bp = bpi.next();
                    columnNames.add(bp.getColumn());
                }
            }
        }
        return columnNames;
    }
    
    @Override
    public List<String> getPropertiesNames() {
        log.trace(">>> selectTotalRange");
        synchronized(this) {
            if (propertyNames == null) {
                propertyNames = new ArrayList<>();
                Iterator<BeanPropertyMapping> bpi = beanPropertiesMap.iterator();
                while (bpi.hasNext()) {
                    BeanPropertyMapping bp = bpi.next();
                    propertyNames.add(bp.getProperty());
                }
            }
        }
        return propertyNames;
    }
    
    public Object getBeanPropertyValue(Object bean, int propIndex) throws InvocationTargetException, IllegalAccessException {
        log.trace(">>> getBeanProperty");
        IDataProperty dp = getDataProperty(propIndex);
        if (dp != null) {
            return dp.getValue(bean);
        } else {
            throw new IllegalArgumentException("No data property found for index "+propIndex);
        }
    }
    
    public void setBeanPropertyValue(Object bean, int propIndex, Object propValue) throws InvocationTargetException, IllegalAccessException {
        log.trace(">>> setBeanProperty");
        IDataProperty dp = getDataProperty(propIndex);
        if (dp != null) {
            dp.setValue(bean, propValue);
        } else {
            throw new IllegalArgumentException("No data property found for index "+propIndex);
        }
    }
}
