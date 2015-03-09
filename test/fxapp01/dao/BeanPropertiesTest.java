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

import fxapp01.dto.ProductRefs;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author serg
 */
public class BeanPropertiesTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public BeanPropertiesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addDataProperty method, of class BeanProperties.
     * @throws java.io.IOException
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testAddDataProperty() throws IOException, IntrospectionException {
        log.trace("addDataProperty");
        int id = 0;
        Object bean = new ProductRefs();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        IDataProperty property = new BeanProperty(bean, pds[0]);
        BeanProperties instance = new BeanProperties(bean);
        boolean result = instance.addDataProperty(id, property);
        assertTrue(result);
    }

    /**
     * Test of getDataProperty method, of class BeanProperties.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testGetDataProperty() throws IntrospectionException {
        log.trace("getDataProperty");
        int id = 0;
        Object bean = new ProductRefs();
        BeanProperties instance = new BeanProperties(bean);
        IDataProperty result = instance.getDataProperty(id);
        assertNotNull(result);
    }

    /**
     * Test of getDataPropertyIds method, of class BeanProperties.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testGetDataPropertyIds() throws IntrospectionException {
        log.trace("getDataPropertyIds");
        Object bean = new ProductRefs();
        BeanProperties instance = new BeanProperties(bean);
        int expResult = 3;
        Collection result = instance.getDataPropertyIds();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of removeDataProperty method, of class BeanProperties.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testRemoveDataProperty() throws IntrospectionException {
        log.trace("removeDataProperty");
        int id = 0;
        Object bean = new ProductRefs();
        BeanProperties instance = new BeanProperties(bean);
        boolean result = instance.removeDataProperty(id);
        assertTrue(result);
    }
    
}
