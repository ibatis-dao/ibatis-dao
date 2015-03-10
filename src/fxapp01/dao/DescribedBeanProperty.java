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
import java.util.Enumeration;

/**
 *
 * @author serg
 */
public class DescribedBeanProperty implements IDescribedDataProperty {

    private static final ILogger log = LogMgr.getLogger(DescribedBeanProperty.class);
    private final Class<?> beanClass;
    private final PropertyDescriptor pd;
    private boolean isReadOnly;

    public DescribedBeanProperty(Class<?> beanClass, PropertyDescriptor pd) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (beanClass == null) {
            throw new IllegalArgumentException("Wrong parameter beanClass (= null)");
        }
        if (pd == null) {
            throw new IllegalArgumentException("Wrong parameter pd (= null)");
        }
        this.beanClass = beanClass;
        this.pd = pd;
        this.isReadOnly = (pd.getWriteMethod() == null);
    }

    /*
    implementation of interface IDataProperty
    */
    @Override
    public Class getType() {
        return pd.getPropertyType();
    }

    @Override
    public Object getValue(Object bean) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        Method m = pd.getReadMethod();
        if (m != null) {
            Object[] params = null;
            return m.invoke(bean, params);
        } else {
            throw new IllegalAccessException("No getter method for property "+pd.getName());
        }
    }

    @Override
    public void setValue(Object bean, Object newValue) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        if (! isReadOnly) {
            Method m = pd.getWriteMethod();
            if (m != null) {
                m.invoke(bean, newValue);
            } else {
                throw new IllegalAccessException("No setter method for property "+pd.getName());
            }
        } else {
            throw new IllegalAccessException("Attempts of writing to read-only property "+pd.getName());
        }
    }
    
    /*
    implementation of interface IDescribedDataProperty
    */
    @Override
    public String getName() {
        return pd.getName();
    }

    @Override
    public void setName(String name) {
        pd.setName(name);
    }

    @Override
    public String getDisplayName() {
        return pd.getDisplayName();
    }

    @Override
    public void setDisplayName(String displayName) {
        pd.setDisplayName(displayName);
    }

    @Override
    public boolean getReadOnly() {
        return (isReadOnly || (pd.getWriteMethod() == null));
    }

    @Override
    public void setReadOnly(boolean ro) {
        this.isReadOnly = (ro || (pd.getWriteMethod() == null));
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return pd.attributeNames();
    }

    @Override
    public boolean getVisible() {
        return (! pd.isHidden());
    }

    @Override
    public void setVisible(boolean v) {
        pd.setHidden(! v);
    }

    @Override
    public void setAttributeValue(String attributeName, Object value) {
        pd.setValue(attributeName, value);
    }

    @Override
    public Object getAttributeValue(String attributeName) {
        return pd.getValue(attributeName);
    }

}
