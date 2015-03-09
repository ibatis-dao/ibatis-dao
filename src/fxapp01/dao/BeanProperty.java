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
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author serg
 */
public class BeanProperty implements IDataProperty {
    
    private static final ILogger log = LogMgr.getLogger(BeanProperty.class);
    private final Object bean;
    private final PropertyDescriptor pd;
    
    public BeanProperty(Object bean, PropertyDescriptor pd) {
        if (bean == null) {
            throw new IllegalArgumentException("Wrong parameter bean (= null)");
        }
        if (pd == null) {
            throw new IllegalArgumentException("Wrong parameter pd (= null)");
        }
        this.bean = bean;
        this.pd = pd;
    }

    @Override
    public Class getType() {
        return pd.getPropertyType();
    }

    @Override
    public Object getValue() {
        Method m = pd.getReadMethod();
        try {
            Object[] params = null;
            return m.invoke(bean, params);
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
            log.error(null, ex);
            return null;
        }
    }

    @Override
    public void setValue(Object newValue) {
        Method m = pd.getWriteMethod();
        try {
            m.invoke(bean, newValue);
        } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
            log.error(null, ex);
        }
    }
    
}
