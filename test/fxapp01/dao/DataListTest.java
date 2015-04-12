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

import fxapp01.TestItemObservList;
import fxapp01.dao.sort.IDAOSortOrder;
import fxapp01.dao.sort.SortOrder;
import fxapp01.dto.INestedRange;
import fxapp01.dto.NestedIntRange;
import fxapp01.dto.TestItemDTO;
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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
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
public final class DataListTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass());
    private final DataList<TestItemDTO,Integer> instance;
    private final INestedRange<Integer> totalRange;

    public DataListTest() throws Exception {
        instance = newDataList();
        totalRange = getRowTotalRange();
    }
    
    private int getRangeFirst() throws IOException {
        return instance.getRowTotalRange().getFirst();
    }
    
    private int getRangeLength() throws IOException {
        return instance.getRowTotalRange().getLength();
    }
    
    private int getRangeLast() throws IOException {
        return instance.getRowTotalRange().getLast();
    }
    
    private DataList<TestItemDTO,Integer> newDataList() throws IOException, IntrospectionException {
        return new TestItemObservList();
    }
    
    private TestItemDTO newBlankDataRow(){
        return new TestItemDTO();
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
    
    private TestItemDTO newDataRow01(){
        return newDataRow(BigInteger.ONE);
    }
    
    private TestItemDTO[] newDataRow_GenericType() {
        return new TestItemDTO[] {newDataRow01()};
    }
    
    private Collection newDataRow_Collection() {
        Collection c = new ArrayList();
        c.add(newDataRow(null));
        return c;
    }
    
    public INestedRange getRowTotalRange() throws Exception {
        return instance.getRowTotalRange();
    }
    
    private INestedRange getNewRange10() {
        NestedIntRange res = new NestedIntRange(totalRange);
        res.setLength(10);
        return res;
    }
    
    private IDAOSortOrder newSortOrder() {
        return new SortOrder();
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
     * Test of getColumnNames method, of class DataList.
     */
    @Test
    public void testGetColumnNames() {
        log.trace("getColumnNames");
        String[] expResult = new String[] {"ID", "NAME"};
        List<String> result = instance.getColumnNames();
        assertArrayEquals(expResult, result.toArray());
    }

    /**
     * Test of debugPrintAll method, of class DataList.
    @Test
    public void testDebugPrintAll() {
        log.trace("debugPrintAll");
        instance.debugPrintAll();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of refresh method, of class DataList.
     */
    @Test
    public void testRefresh() {
        log.trace("refresh");
        boolean expResult = instance.isEmpty();
        instance.refresh();
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRowTotalRange method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testGetRowTotalRange() throws IOException {
        log.trace("getRowTotalRange");
        INestedRange result = instance.getRowTotalRange();
        assertNotNull(result);
    }

    /**
     * Test of fetch method, of class DataList.
     */
    @Test
    public void testFetch() {
        log.trace("fetch");
        INestedRange aRowsRange = getNewRange10();
        int pos = aRowsRange.getFirst().intValue();
        List l = instance.fetch(aRowsRange);
        assertNotNull(l);
    }

    /**
     * Test of addAll method, of class DataList.
     */
    @Test
    public void testAddAll_GenericType() {
        log.trace("addAll");
        TestItemDTO[] elements = newDataRow_GenericType();
        boolean result = instance.addAll(elements);
        assertTrue(result);
    }

    /**
     * Test of setAll method, of class DataList.
     */
    @Test
    public void testSetAll_GenericType() {
        log.trace("setAll");
        TestItemDTO[] elements = newDataRow_GenericType();
        boolean result = instance.setAll(elements);
        assertTrue(result);
    }

    /**
     * Test of setAll method, of class DataList.
     */
    @Test
    public void testSetAll_Collection() {
        log.trace("setAll");
        Collection col = newDataRow_Collection();
        boolean result = instance.setAll(col);
        assertTrue(result);
    }

    /**
     * Test of removeAll method, of class DataList.
     */
    @Test
    public void testRemoveAll_GenericType() {
        log.trace("removeAll");
        TestItemDTO[] elements = newDataRow_GenericType();
        boolean result = instance.addAll(elements);
        assertTrue(result);
        result = instance.removeAll(elements);
        assertTrue(result);
    }

    /**
     * Test of retainAll method, of class DataList.
     */
    @Test
    public void testRetainAll_GenericType() {
        log.trace("retainAll");
        boolean wasEmpty = instance.isEmpty();
        TestItemDTO[] elements = newDataRow_GenericType();
        boolean result = instance.addAll(elements);
        assertTrue(result);
        result = wasEmpty || instance.retainAll(elements);
        assertTrue(result); //set should change only if was not empty
    }

    /**
     * Test of remove method, of class DataList.
     */
    @Test
    public void testRemove_int_int() {
        log.trace("remove");
        int from = 0;
        int to = 2;
        TestItemDTO[] elements = new TestItemDTO[] {newDataRow(null), newDataRow(null), newDataRow(null)};
        boolean result = instance.addAll(elements);
        assertTrue(result);
        instance.remove(from, to);
    }

    /**
     * Test of addListener method, of class DataList.
     */
    @Test
    public void testAddListener_ListChangeListener() {
        log.trace("addListener");
        ListChangeListener listener = new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        instance.addListener(listener);
        instance.removeListener(listener);
    }

    /**
     * Test of removeListener method, of class DataList.
     */
    @Test
    public void testRemoveListener_ListChangeListener() {
        log.trace("removeListener");
        ListChangeListener listener = new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        instance.addListener(listener);
        instance.removeListener(listener);
    }

    /**
     * Test of size method, of class DataList.
     */
    @Test
    public void testSize() {
        log.trace("size");
        int resultInt = instance.size();
    }

    /**
     * Test of isEmpty method, of class DataList.
     */
    @Test
    public void testIsEmpty() {
        log.trace("isEmpty");
        boolean expResult = (instance.size() == 0);
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class DataList.
     */
    @Test
    public void testContains() {
        log.trace("contains");
        TestItemDTO o = null;
        boolean expResult = false;
        boolean result = instance.contains(o);
        assertFalse(result);
        TestItemDTO[] elements = newDataRow_GenericType();
        result = instance.addAll(elements);
        assertTrue(result);
        result = instance.contains(elements[0]);
        assertTrue(result);
    }

    /**
     * Test of iterator method, of class DataList.
     */
    @Test
    public void testIterator() {
        log.trace("iterator");
        Iterator result = instance.iterator();
        assertNotNull(result);
    }

    /**
     * Test of toArray method, of class DataList.
     */
    @Test
    public void testToArray_0args() {
        log.trace("toArray");
        boolean expResult = instance.isEmpty();
        Object[] result = instance.toArray();
        assertEquals(expResult, (result.length == 0));
    }

    /**
     * Test of toArray method, of class DataList.
     */
    @Test
    public void testToArray_GenericType() {
        log.trace("toArray");
        Object[] a = newDataRow_GenericType();
        boolean expResult = instance.isEmpty();
        Object[] result = instance.toArray(a);
        assertEquals(expResult, (result.length == 0));
    }

    /**
     * Test of add method, of class DataList.
     */
    @Test
    public void testAdd_GenericType() {
        log.trace("add");
        TestItemDTO e = newDataRow(null);
        boolean result = instance.add(e);
        assertTrue(result);
    }

    /**
     * Test of remove method, of class DataList.
     */
    @Test
    public void testRemove_Object() {
        log.trace("remove");
        TestItemDTO o = newDataRow(null);
        boolean result = instance.add(o);
        assertTrue(result);
        result = instance.remove(o);
        assertTrue(result);
    }

    /**
     * Test of containsAll method, of class DataList.
     */
    @Test
    public void testContainsAll() {
        log.trace("containsAll");
        Collection c = newDataRow_Collection();
        boolean result = instance.containsAll(c);
        assertFalse(result);
    }

    /**
     * Test of addAll method, of class DataList.
     */
    @Test
    public void testAddAll_Collection() {
        log.trace("addAll");
        Collection c = newDataRow_Collection();
        boolean result = instance.addAll(c);
        assertTrue(result);
    }

    /**
     * Test of addAll method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testAddAll_int_Collection() throws IOException {
        log.trace("addAll");
        int index = getRangeFirst();
        Collection c = newDataRow_Collection();
        boolean expResult = false;
        boolean result = instance.addAll(index, c);
        assertTrue(result);
    }

    /**
     * Test of removeAll method, of class DataList.
     */
    @Test
    public void testRemoveAll_Collection() {
        log.trace("removeAll");
        Collection c = newDataRow_Collection();
        boolean result = instance.removeAll(c);
        assertFalse(result);
    }

    /**
     * Test of retainAll method, of class DataList.
     */
    @Test
    public void testRetainAll_Collection() {
        log.trace("retainAll");
        boolean wasEmpty = instance.isEmpty();
        Collection c = newDataRow_Collection();
        boolean result = wasEmpty || instance.retainAll(c);
        assertTrue(result); //set should change only if was not empty
    }

    /**
     * Test of clear method, of class DataList.
     */
    @Test
    public void testClear() {
        log.trace("clear");
        boolean wasEmpty = instance.isEmpty();
        instance.clear();
        boolean result = wasEmpty || instance.isEmpty();
        assertTrue(result);
    }

    /**
     * Test of get method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testGet() throws IOException {
        log.trace("get");
        boolean wasEmpty = instance.isEmpty();
        int index = getRangeFirst();
        Object result = instance.get(index);
        assertTrue((wasEmpty || (result != null)));
    }

    /**
     * Test of set method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testSet() throws IOException {
        log.trace("set");
        boolean wasEmpty = instance.isEmpty();
        int index = getRangeFirst();
        TestItemDTO element = newDataRow(null);
        Object result = instance.set(index, element);
        assertTrue((wasEmpty || (result != null)));
    }

    /**
     * Test of add method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testAdd_int_GenericType() throws IOException {
        log.trace("add");
        int index = getRangeFirst();
        TestItemDTO element = newDataRow(null);
        instance.add(index, element);
    }

    /**
     * Test of remove method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testRemove_int() throws IOException {
        log.trace("remove");
        boolean wasEmpty = instance.isEmpty();
        int index = getRangeFirst();
        Object result = instance.remove(index);
        assertTrue((wasEmpty || (result != null)));
    }

    /**
     * Test of indexOf method, of class DataList.
     */
    @Test
    public void testIndexOf() {
        log.trace("indexOf");
        TestItemDTO o = newDataRow(null);
        instance.add(o);
        int result = instance.indexOf(o);
        assertTrue(result >= 0);
    }

    /**
     * Test of lastIndexOf method, of class DataList.
     */
    @Test
    public void testLastIndexOf() {
        log.trace("lastIndexOf");
        TestItemDTO o = newDataRow(null);
        instance.add(o);
        int result = instance.lastIndexOf(o);
        assertTrue(result >= 0);
    }

    /**
     * Test of listIterator method, of class DataList.
     */
    @Test
    public void testListIterator_0args() {
        log.trace("listIterator");
        ListIterator result = instance.listIterator();
        assertNotNull(result);
    }

    /**
     * Test of listIterator method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testListIterator_int() throws IOException {
        log.trace("listIterator");
        int index = getRangeFirst();
        ListIterator result = instance.listIterator(index);
        assertNotNull(result);
    }

    /**
     * Test of subList method, of class DataList.
     * @throws java.io.IOException
     */
    @Test
    public void testSubList() throws IOException {
        log.trace("subList");
        boolean wasEmpty = instance.isEmpty();
        int fromIndex = 0;
        int toIndex = 1; 
        int expResult = 1;
        List result = instance.subList(fromIndex, toIndex);
        assertTrue((wasEmpty || (result.size() == expResult)));
    }

    /**
     * Test of addListener method, of class DataList.
     */
    @Test
    public void testAddListener_InvalidationListener() {
        log.trace("addListener");
        InvalidationListener listener = new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        instance.addListener(listener);
        instance.removeListener(listener);
    }

    /**
     * Test of removeListener method, of class DataList.
     */
    @Test
    public void testRemoveListener_InvalidationListener() {
        log.trace("removeListener");
        InvalidationListener listener = new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        instance.addListener(listener);
        instance.removeListener(listener);
    }

    /**
     * Test of getSortOrder method, of class DataList.
     */
    @Test
    public void testGetSortOrder() {
        log.trace("getSortOrder");
        IDAOSortOrder result = instance.getSortOrder();
        assertNotNull(result);
    }

    /**
     * Test of setSortOrder method, of class DataList.
     */
    @Test
    public void testSetSortOrder() {
        log.trace("setSortOrder");
        IDAOSortOrder sortOrder = newSortOrder();
        instance.setSortOrder(sortOrder);
    }
    
    private void logDataRow(int index) {
        log.debug("p("+index+"). "+instance.get(index).toString());
    }
    
    @Test
    public void testGetFewRows() throws IOException, IntrospectionException {
        log.trace(">>> testGetFewRows");
        logDataRow(getRangeFirst());
        logDataRow(getRangeFirst() + 20);
        logDataRow(getRangeFirst() + 40);
        logDataRow(getRangeFirst() + 60);
        logDataRow(getRangeFirst() + 80);
        logDataRow(getRangeFirst() + 60);
        logDataRow(getRangeFirst() + 40);
        logDataRow(getRangeFirst() + 20);
        logDataRow(getRangeFirst());
        logDataRow(getRangeFirst() + 50);
        logDataRow(getRangeFirst() + 150);
        instance.debugPrintAll();
        log.trace("<<< testGetFewRows");
    }
    
    /**
     * Test of add method, of class DataList.
     */
    @Test
    public void testAdd_IHasDataChanges() {
        log.trace("testAdd_IHasDataChanges");
        TestItemDTO e = newDataRow(null);
        boolean result = instance.add(e);
        assertTrue(result);
        assertTrue(instance.hasDataChanges());
    }


}
