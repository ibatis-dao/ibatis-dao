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

import fxapp01.dto.INestedRange;
import fxapp01.dto.SQLParams;
import fxapp01.dto.TestItemDTO;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
public class DataCacheRollingTest {

    private final ILogger log = LogMgr.getLogger(this.getClass());
    
    IDataRangeFetcher<TestItemDTO,Integer> dataFetcher;
    private final DataCacheRolling<TestItemDTO> instance;
    
    private class DataRangeFetcher<DTOclass,Integer> implements IDataRangeFetcher  {

        IDAOreadonly dao;
        
        public DataRangeFetcher(IDAOreadonly dao) {
            this.dao = dao;
        }
        
        @Override
        public INestedRange getRowTotalRange() throws IOException {
            return dao.getRowTotalRange();
        }

        @Override
        public List<DTOclass> fetch(INestedRange aRowsRange) {
            log.trace(">>> fetch(aRowsRange="+aRowsRange+")");
        if (aRowsRange == null) {
            throw new ENullArgument("fetch");
        }
        List<DTOclass> l;
        try {
            SQLParams qep = new SQLParams(aRowsRange, null, null);
            l = dao.select(qep);
        } catch (IOException ex) {
            log.error(null, ex);
            l = new ArrayList<>();
        }
        log.trace("<<< fetch");
        return l;
        }
        
    }
    
    public DataCacheRollingTest() throws IOException, IntrospectionException {
        dataFetcher = new DataRangeFetcher<TestItemDTO,Integer>(new TestItemDAO());
        instance = new DataCacheRolling<>(dataFetcher, 20, 40);
        instance.getRange().setLength(20);
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

    private TestItemDTO newDataRow(BigInteger id){
        TestItemDTO d = new TestItemDTO();
        if (id != null) {
            d.setId(id);
        } else {
            d.setId(BigInteger.valueOf(Math.round(Math.random() * 1000000)));
        }
        d.setName("test_"+Math.random());
        return d;
    }
    
    private Collection newDataRow_Collection() {
        Collection c = new ArrayList();
        c.add(newDataRow(null));
        return c;
    }
    
    private int getLeftLimit() {
        return instance.getLeftLimit();
    }

    /**
     * Test of debugPrintAll method, of class DataCacheRolling.
     */
    @Test
    public void testDebugPrintAll() {
        log.trace("debugPrintAll");
        instance.debugPrintAll();
    }

    /**
     * Test of getRange method, of class DataCacheRolling.
     */
    @Test
    public void testGetRange() {
        log.trace("getRange");
        INestedRange<Integer> result = instance.getRange();
        assertNotNull(result);
    }

    /**
     * Test of getLeftLimit method, of class DataCacheRolling.
     */
    @Test
    public void testGetLeftLimit() {
        log.trace("getLeftLimit");
        int result = instance.getLeftLimit();
        assertTrue(result >= 0);
    }

    /**
     * Test of getDefSize method, of class DataCacheRolling.
     */
    @Test
    public void testGetDefSize() {
        log.trace("getDefSize");
        int result = instance.getDefSize();
        assertTrue(result >= 0);
    }

    /**
     * Test of setDefSize method, of class DataCacheRolling.
     */
    @Test
    public void testSetDefSize() {
        log.trace("setDefSize");
        int defSize = 50;
        instance.setDefSize(defSize);
    }

    /**
     * Test of getMaxSize method, of class DataCacheRolling.
     */
    @Test
    public void testGetMaxSize() {
        log.trace("getMaxSize");
        int result = instance.getMaxSize();
        assertTrue(result >= 0);
    }

    /**
     * Test of setMaxSize method, of class DataCacheRolling.
     */
    @Test
    public void testSetMaxSize() {
        log.trace("setMaxSize");
        int maxSize = 50;
        instance.setMaxSize(maxSize);
    }

    /**
     * Test of refresh method, of class DataCacheRolling.
     */
    @Test
    public void testRefresh() {
        log.trace("refresh");
        instance.refresh();
    }

    /**
     * Test of containsIndex method, of class DataCacheRolling.
     */
    @Test
    public void testContainsIndex() {
        log.trace("containsIndex");
        int index = instance.getLeftLimit();
        boolean result = instance.containsIndex(index);
        assertTrue(result);
    }

    /**
     * Test of size method, of class DataCacheRolling.
     */
    @Test
    public void testSize() {
        log.trace("size");
        instance.refresh();
        int result = instance.size();
        assertTrue(result >= 0);
    }

    /**
     * Test of clear method, of class DataCacheRolling.
     */
    @Test
    public void testClear() {
        log.trace("clear");
        instance.refresh();
        instance.clear();
        int result = instance.size();
        assertEquals(result, 0);
    }

    /**
     * Test of isEmpty method, of class DataCacheRolling.
     */
    @Test
    public void testIsEmpty() {
        log.trace("isEmpty");
        boolean expResult = (instance.size() == 0);
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class DataCacheRolling.
     */
    @Test
    public void testContains() {
        log.trace("contains");
        TestItemDTO o = newDataRow(null);
        instance.add(o);
        boolean result = instance.contains(o);
        assertTrue(result);
    }

    /**
     * Test of iterator method, of class DataCacheRolling.
     */
    @Test
    public void testIterator() {
        log.trace("iterator");
        Iterator result = instance.iterator();
        assertNotNull(result);
    }

    /**
     * Test of toArray method, of class DataCacheRolling.
     */
    @Test
    public void testToArray_0args() {
        log.trace("toArray");
        instance.refresh();
        boolean expResult = instance.isEmpty();
        Object[] result = instance.toArray();
        assertEquals(expResult, (result.length == 0));
    }

    /**
     * Test of toArray method, of class DataCacheRolling.
     */
    @Test
    public void testToArray_GenericType() {
        log.trace("toArray");
        Object[] a = new Object[]{newDataRow(null)};
        instance.refresh();
        boolean expResult = instance.isEmpty();
        Object[] result = instance.toArray(a);
        assertEquals(expResult, (result.length == 0));
    }

    /**
     * Test of add method, of class DataCacheRolling.
     */
    @Test
    public void testAdd_GenericType() {
        log.trace("add");
        TestItemDTO e = newDataRow(null);
        boolean result = instance.add(e);
        assertTrue(result);
    }

    /**
     * Test of remove method, of class DataCacheRolling.
     */
    @Test
    public void testRemove_Object() {
        log.trace("remove");
        TestItemDTO o = newDataRow(null);
        boolean expResult = instance.add(o);
        boolean result = instance.remove(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of containsAll method, of class DataCacheRolling.
     */
    @Test
    public void testContainsAll() {
        log.trace("containsAll");
        Collection c = newDataRow_Collection();
        boolean expResult = instance.addAll(c);
        boolean result = instance.containsAll(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of addAll method, of class DataCacheRolling.
     */
    @Test
    public void testAddAll_Collection() {
        log.trace("addAll");
        Collection c = newDataRow_Collection();
        boolean result = instance.addAll(c);
        assertTrue(result);
    }

    /**
     * Test of addAll method, of class DataCacheRolling.
     */
    @Test
    public void testAddAll_int_Collection() {
        log.trace("addAll");
        int index = instance.getLeftLimit();
        Collection c = newDataRow_Collection();
        boolean result = instance.addAll(index, c);
        assertTrue(result);
    }

    /**
     * Test of removeAll method, of class DataCacheRolling.
     */
    @Test
    public void testRemoveAll() {
        log.trace("removeAll");
        Collection c = newDataRow_Collection();
        boolean expResult = instance.addAll(c);
        boolean result = instance.removeAll(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of retainAll method, of class DataCacheRolling.
     */
    @Test
    public void testRetainAll() {
        log.trace("retainAll");
        instance.clear();
        Collection c = newDataRow_Collection();
        boolean result = instance.addAll(c);
        assertTrue(result);
        result = instance.retainAll(c);
        assertFalse(result); //set should not change
    }

    /**
     * Test of get method, of class DataCacheRolling.
     */
    @Test
    public void testGet() {
        log.trace("get");
        instance.refresh();
        int index = instance.getLeftLimit();
        Object result = instance.get(index);
        assertNotNull(result);
    }

    /**
     * Test of set method, of class DataCacheRolling.
     */
    @Test
    public void testSet() {
        log.trace("set");
        instance.refresh();
        int index = instance.getLeftLimit();
        TestItemDTO element = newDataRow(null);
        Object result = instance.set(index, element);
        assertNotNull(result);
    }

    /**
     * Test of add method, of class DataCacheRolling.
     */
    @Test
    public void testAdd_int_GenericType() {
        log.trace("add");
        int index = 0; //instance.getLeftLimit();
        TestItemDTO element = newDataRow(null);
        instance.add(index, element);
    }

    /**
     * Test of remove method, of class DataCacheRolling.
     */
    @Test
    public void testRemove_int() {
        log.trace("remove");
        instance.refresh();
        int index = instance.getLeftLimit();
        Object result = instance.remove(index);
        assertNotNull(result);
    }

    /**
     * Test of indexOf method, of class DataCacheRolling.
     */
    @Test
    public void testIndexOf() {
        log.trace("indexOf");
        TestItemDTO o = newDataRow(null);
        boolean expResult = instance.add(o);
        assertTrue(expResult);
        int result = instance.indexOf(o);
        assertTrue(result >= 0);
    }

    /**
     * Test of lastIndexOf method, of class DataCacheRolling.
     */
    @Test
    public void testLastIndexOf() {
        log.trace("lastIndexOf");
        TestItemDTO o = newDataRow(null);
        boolean expResult = instance.add(o);
        assertTrue(expResult);
        int result = instance.lastIndexOf(o);
        assertTrue(result >= 0);
    }

    /**
     * Test of listIterator method, of class DataCacheRolling.
     */
    @Test
    public void testListIterator_0args() {
        log.trace("listIterator");
        ListIterator result = instance.listIterator();
        assertNotNull(result);
    }

    /**
     * Test of listIterator method, of class DataCacheRolling.
     */
    @Test
    public void testListIterator_int() {
        log.trace("listIterator");
        int index = 0; //instance.getLeftLimit();
        ListIterator result = instance.listIterator(index);
        assertNotNull(result);
    }

    /**
     * Test of subList method, of class DataCacheRolling.
     */
    @Test
    public void testSubList() {
        log.trace("subList");
        instance.refresh();
        boolean wasEmpty = instance.isEmpty();
        int fromIndex = instance.getLeftLimit();
        int toIndex = instance.getLeftLimit()+1;
        int expResult = 1;
        List result = instance.subList(fromIndex, toIndex);
        assertTrue((wasEmpty || (result.size() == expResult)));
    }
    
}
