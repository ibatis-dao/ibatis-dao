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

/**
 *
 * @author serg
 */
public class DescribedBeanProperties extends BeanProperties implements IHasDescribedDataProperty {

    public DescribedBeanProperties(Class<?> beanClass) throws IntrospectionException {
        super(beanClass);
    }
    
    @Override
    protected void addAllBeanProperties(PropertyDescriptor[] pds) {
        //заполняем свойства на основе сведений о классе 
        for (int i = 0; i < pds.length; i++) {
            beanProperties.put(i, new DescribedBeanProperty(beanClass, pds[i]));
            log.debug(pds[i].getName());
        }
    }
    
    /*
    implementation of interface IHasDescribedDataProperty
    */
    @Override
    public boolean addDescribedDataProperty(Object id, IDescribedDataProperty property) {
        //log.debug("containsKey="+beanProperties.containsKey(id));
        beanProperties.put(id, property);
        return true;
    }

    @Override
    public IDescribedDataProperty getDescribedDataProperty(Object id) {
        //извлекаем "базовое" свойство по его ID
        IDataProperty dp = beanProperties.get(id);
        if (dp == null) {
            //если свойство не найдено
            return null;
        } else {
            //если свойство найдено
            if (dp instanceof IDescribedDataProperty) {
                //если свойство можно реализует интерфейс IDescribedDataProperty
                return (IDescribedDataProperty)dp;
            } else {
                //свойство не реализует интерфейс IDescribedDataProperty. возвращаем null
                return null;
            }
        }    
    }

}
