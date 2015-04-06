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

import fxapp01.dto.TestItemDTO;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
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
public class BeanPropertiesDescribedTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public BeanPropertiesDescribedTest() {
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
     * Test of addDescribedDataProperty method, of class BeanPropertiesDescribed.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testAddDescribedDataProperty() throws IntrospectionException {
        log.trace("addDescribedDataProperty");
        BeanPropertiesDescribed instance = new BeanPropertiesDescribed(TestItemDTO.class);
        //log.debug("Properties.size="+instance.beanProperties.size());
        PropertyDescriptor[] pds = instance.getBeanPropertyDescriptors(TestItemDTO.class);
        int expResult = pds.length+1;
        IDataPropertyDescribed property = new BeanPropertyDescribed(TestItemDTO.class, pds[0]);
        instance.addDescribedDataProperty(pds.length, property);
        int result = instance.beanProperties.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescribedDataProperty method, of class BeanPropertiesDescribed.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testGetDescribedDataProperty() throws IntrospectionException {
        log.trace("getDescribedDataProperty");
        Object id = null;
        BeanPropertiesDescribed instance = new BeanPropertiesDescribed(TestItemDTO.class);
        IDataPropertyDescribed result = instance.getDescribedDataProperty(0);
        assertNotNull(result);
    }

    /**
     * Test of addDataProperty method, of class BeanPropertiesDescribed.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testAddDataProperty() throws IntrospectionException {
        log.trace("addDataProperty");
        BeanPropertiesDescribed instance = new BeanPropertiesDescribed(TestItemDTO.class);
        PropertyDescriptor[] pds = instance.getBeanPropertyDescriptors(TestItemDTO.class);
        IDataProperty property = new BeanProperty(TestItemDTO.class, pds[0]);
        int expResult = pds.length+1;
        instance.addDataProperty(pds.length, property);
        int result = instance.beanProperties.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDataProperty method, of class BeanPropertiesDescribed.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testGetDataProperty() throws IntrospectionException {
        log.trace("getDataProperty");
        BeanPropertiesDescribed instance = new BeanPropertiesDescribed(TestItemDTO.class);
        IDataProperty result = instance.getDataProperty(0);
        assertNotNull(result);
    }

    /**
     * Test of getDataPropertyIds method, of class BeanPropertiesDescribed.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testGetDataPropertyIds() throws IntrospectionException {
        log.trace("getDataPropertyIds");
        BeanPropertiesDescribed instance = new BeanPropertiesDescribed(TestItemDTO.class);
        int expResult = instance.beanProperties.size();
        int result = instance.getDataPropertyIds().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of removeDataProperty method, of class BeanPropertiesDescribed.
     * @throws java.beans.IntrospectionException
     */
    @Test
    public void testRemoveDataProperty() throws IntrospectionException {
        log.trace("removeDataProperty");
        Object id = null;
        BeanPropertiesDescribed instance = new BeanPropertiesDescribed(TestItemDTO.class);
        int expResult = instance.beanProperties.size()-1;
        instance.removeDataProperty(0);
        int result = instance.getDataPropertyIds().size();
        assertEquals(expResult, result);
    }
    
}
