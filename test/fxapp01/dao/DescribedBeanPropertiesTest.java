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
public class DescribedBeanPropertiesTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public DescribedBeanPropertiesTest() {
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
     * Test of addDescribedDataProperty method, of class DescribedBeanProperties.
     */
    @Test
    public void testAddDescribedDataProperty() throws IntrospectionException {
        log.trace("addDescribedDataProperty");
        DescribedBeanProperties instance = new DescribedBeanProperties(TestItemDTO.class);
        PropertyDescriptor[] pds = instance.getBeanPropertyDescriptors(TestItemDTO.class);
        IDescribedDataProperty property = new DescribedBeanProperty(TestItemDTO.class, pds[0]);
        boolean result = instance.addDescribedDataProperty(pds.length, property);
        assertTrue(result);
    }

    /**
     * Test of getDescribedDataProperty method, of class DescribedBeanProperties.
     */
    @Test
    public void testGetDescribedDataProperty() {
        log.trace("getDescribedDataProperty");
        Object id = null;
        DescribedBeanProperties instance = null;
        IDescribedDataProperty expResult = null;
        IDescribedDataProperty result = instance.getDescribedDataProperty(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDataProperty method, of class DescribedBeanProperties.
     */
    @Test
    public void testAddDataProperty() {
        log.trace("addDataProperty");
        Object id = null;
        IDataProperty property = null;
        DescribedBeanProperties instance = null;
        boolean expResult = false;
        boolean result = instance.addDataProperty(id, property);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataProperty method, of class DescribedBeanProperties.
     */
    @Test
    public void testGetDataProperty() {
        log.trace("getDataProperty");
        Object id = null;
        DescribedBeanProperties instance = null;
        IDataProperty expResult = null;
        IDataProperty result = instance.getDataProperty(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataPropertyIds method, of class DescribedBeanProperties.
     */
    @Test
    public void testGetDataPropertyIds() {
        log.trace("getDataPropertyIds");
        DescribedBeanProperties instance = null;
        Collection expResult = null;
        Collection result = instance.getDataPropertyIds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeDataProperty method, of class DescribedBeanProperties.
     */
    @Test
    public void testRemoveDataProperty() {
        log.trace("removeDataProperty");
        Object id = null;
        DescribedBeanProperties instance = null;
        boolean expResult = false;
        boolean result = instance.removeDataProperty(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
