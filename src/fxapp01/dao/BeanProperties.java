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

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author serg
 */
public class BeanProperties implements IHasDataProperty {

    private static final ILogger log = LogMgr.getLogger(BeanProperties.class);
    private final Object bean;
    private final Map<Object,IDataProperty> beanProperties;

    public BeanProperties(Object bean) throws IntrospectionException {
        if (bean == null) {
            throw new IllegalArgumentException("Wrong parameter dataClass (= null)");
        }
        this.bean = bean;
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        beanProperties = new HashMap<>(pds.length);
        for (int i = 0; i < pds.length; i++) {
            beanProperties.put(i, new BeanProperty(bean, pds[i]));
            //log.debug(pd.getName());
        }
    }
    
    @Override
    public boolean addDataProperty(Object id, IDataProperty property) {
        return (beanProperties.put(id, property) != null);
    }

    @Override
    public IDataProperty getDataProperty(Object id) {
        return beanProperties.get(id);
    }

    @Override
    public Collection<?> getDataPropertyIds() {
        return beanProperties.keySet(); //values();
    }

    @Override
    public boolean removeDataProperty(Object id) {
        return (beanProperties.remove(id) != null);
    }
    
}
