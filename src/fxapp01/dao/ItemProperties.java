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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author serg
 */
public class ItemProperties {
    
    private static ILogger log;
    private List<BeanPropertyMapping> beanPropertiesMap;
    
    public ItemProperties(List<BeanPropertyMapping> beanPropertiesMap) {
        if (beanPropertiesMap == null) {
            throw new IllegalArgumentException("Wrong parameter beanPropertiesMap (= null)");
        }
        this.beanPropertiesMap = beanPropertiesMap;
        log = LogMgr.getLogger(this.getClass());
    }
    
    public BeanPropertyMapping get(int propIndex) {
        return beanPropertiesMap.get(propIndex);
    }
    
    private Field getBeanField(Object bean, int propIndex) {
        if (bean != null) {
            Class c = bean.getClass();
            BeanPropertyMapping prop = beanPropertiesMap.get(propIndex);
            if (prop != null) {
                String propName = prop.getProperty();
                try {
                    Field f = c.getDeclaredField(propName);
                    return f;
                } catch (NoSuchFieldException | SecurityException ex) {
                    log.error("", ex);
                }
                return null;
            } else {
                throw new IllegalArgumentException("Wrong parameter propIndex. No property found for value "+propIndex);
            }
        } else {
            throw new IllegalArgumentException("Wrong parameter bean = NULL");
        }
    }

    public Object getBeanProperty(Object bean, int propIndex) {
        Field f = getBeanField(bean, propIndex);
        try {
            return f.get(bean);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            log.error("", ex);
        }
        return null;
    }

    public void setBeanProperty(Object bean, int propIndex, Object propValue) {
        Field f = getBeanField(bean, propIndex);
        try {
            f.set(bean, propValue);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            log.error("", ex);
        }
    }
    
    public List<String> getColumnNames() {
        List<String> colNames = new ArrayList<>();
        Iterator<BeanPropertyMapping> bpi = beanPropertiesMap.iterator();
        while (bpi.hasNext()) {
            BeanPropertyMapping bp = bpi.next();
            colNames.add(bp.getProperty());
        }
        return colNames;
    }

    public int getSize() {
        return beanPropertiesMap.size();
    }
    
}
