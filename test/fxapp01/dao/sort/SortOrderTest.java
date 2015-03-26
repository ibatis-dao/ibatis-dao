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
package fxapp01.dao.sort;

import fxapp01.dao.sort.SortOrder;
import fxapp01.dto.ISortOrder;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
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
public class SortOrderTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public SortOrderTest() {
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
     * Test of build method, of class SortOrder.
     */
    @Test
    public void testBuild() {
        log.trace("build");
        SortOrder instance = new SortOrder();
        String expResult;
        String result = instance.build();
        assertTrue(result.isEmpty());
        expResult = "test01 ASC";
        instance.add("test01", ISortOrder.Direction.ASC);
        result = instance.build();
        assertTrue(result.contentEquals(expResult));
        instance.add("test02", ISortOrder.Direction.DESC);
        expResult = "test01 ASC, test02 DESC";
        result = instance.build();
        assertTrue(result.contentEquals(expResult));
        instance.add("test03", ISortOrder.Direction.NONE);
        result = instance.build();
        assertTrue(result.contentEquals(expResult));
    }

    /**
     * Test of size method, of class SortOrder.
     */
    @Test
    public void testSize() {
        log.trace("size");
        SortOrder instance = new SortOrder();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        instance.add("test01", ISortOrder.Direction.ASC);
        expResult = 1;
        result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class SortOrder.
     */
    @Test
    public void testGetName() {
        log.trace("getName");
        int index = 0;
        SortOrder instance = new SortOrder();
        String expResult = "test01";
        instance.add(expResult, ISortOrder.Direction.ASC);
        String result = instance.getName(index);
        assertTrue(expResult.contentEquals(result));
    }

    /**
     * Test of getDirection method, of class SortOrder.
     */
    @Test
    public void testGetDirection() {
        log.trace("getDirection");
        int index = 0;
        SortOrder instance = new SortOrder();
        ISortOrder.Direction expResult = ISortOrder.Direction.ASC;
        instance.add("test01", expResult);
        ISortOrder.Direction result = instance.getDirection(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class SortOrder.
     */
    @Test
    public void testAdd() {
        log.trace("add");
        SortOrder instance = new SortOrder();
        int result = instance.size();
        assertEquals(0, result);
        try {
            instance.add("", ISortOrder.Direction.NONE);
            fail("add() with empty columnName");
        } catch (IllegalArgumentException ex) {
        }
        result = instance.size();
        assertEquals(0, result);
        instance.add("test01", ISortOrder.Direction.NONE);
        result = instance.size();
        assertEquals(1, result);
    }

    /**
     * Test of del method, of class SortOrder.
     */
    @Test
    public void testDel() {
        log.trace("del");
        int index = 0;
        SortOrder instance = new SortOrder();
        instance.add("test01", ISortOrder.Direction.NONE);
        boolean result = instance.del(index);
        assertTrue(result);
    }

    /**
     * Test of clear method, of class SortOrder.
     */
    @Test
    public void testClear() {
        log.trace("clear");
        SortOrder instance = new SortOrder();
        instance.add("test01", ISortOrder.Direction.NONE);
        instance.clear();
        int result = instance.size();
        assertEquals(0, result);
    }
    
    /**
     * Test of clear method, of class SortOrder.
     */
    @Test
    public void testToggle() {
        log.trace("toggle");
        SortOrder instance = new SortOrder();
        instance.add("test01", ISortOrder.Direction.NONE);
        int index = 0;
        instance.toggle(index);
        ISortOrder.Direction expected = ISortOrder.Direction.ASC;
        ISortOrder.Direction result = instance.getDirection(index);
        assertEquals(expected, result);
        instance.toggle(index);
        expected = ISortOrder.Direction.DESC;
        result = instance.getDirection(index);
        assertEquals(expected, result);
        instance.toggle(index);
        expected = ISortOrder.Direction.NONE;
        result = instance.getDirection(index);
        assertEquals(expected, result);
    }

    /**
     * Test of toString method, of class SortOrder.
     */
    @Test
    public void testToString() {
        log.trace("toString");
        SortOrder instance = new SortOrder();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
