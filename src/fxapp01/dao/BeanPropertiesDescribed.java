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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 *
 * @author serg
 */
public class BeanPropertiesDescribed extends BeanProperties implements IHasDataPropertyDescribed {

    public BeanPropertiesDescribed(Class<?> beanClass) throws IntrospectionException {
        super(beanClass);
    }
    
    @Override
    protected void addAllBeanProperties(PropertyDescriptor[] pds) {
        //заполняем свойства на основе сведений о классе 
        for (int i = 0; i < pds.length; i++) {
            beanProperties.put(i, new BeanPropertyDescribed(beanClass, pds[i]));
            log.debug(pds[i].getName());
        }
    }
    
    /*
    implementation of interface IHasDataPropertyDescribed
    */
    @Override
    public boolean addDescribedDataProperty(Object id, IDataPropertyDescribed property) {
        //log.debug("containsKey="+beanProperties.containsKey(id));
        beanProperties.put(id, property);
        return true;
    }

    @Override
    public IDataPropertyDescribed getDescribedDataProperty(Object id) {
        //извлекаем "базовое" свойство по его ID
        IDataProperty dp = beanProperties.get(id);
        if (dp == null) {
            //если свойство не найдено
            return null;
        } else {
            //если свойство найдено
            if (dp instanceof IDataPropertyDescribed) {
                //если свойство можно реализует интерфейс IDataPropertyDescribed
                return (IDataPropertyDescribed)dp;
            } else {
                //свойство не реализует интерфейс IDataPropertyDescribed. возвращаем null
                return null;
            }
        }    
    }

}
