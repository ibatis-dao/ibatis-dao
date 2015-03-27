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

import fxapp01.dao.sort.SortOrder;
import fxapp01.dto.INestedRange;
import fxapp01.dao.sort.ISortOrder;
import fxapp01.dto.NestedIntRange;
import fxapp01.dto.SQLParams;
import fxapp01.dto.TestItemDTO;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author serg
 */
public class TestItemDAOIT {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 
    private TestItemDTO item01 = null;
    private TestItemDTO item02 = null;

    public TestItemDAOIT() {
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
     * Test of getContainerProperties method, of class TestItemDAO.
     * @throws java.io.IOException
     */
    @Test
    public void testGetContainerProperties() throws IOException, IntrospectionException {
        log.trace(">>> getContainerProperties");
        TestItemDAO instance = new TestItemDAO();
        IDAOProperties result = instance.getBeanProperties();
        assertNotNull(result);
    }

    /**
     * Test of getBeanFieldValue method, of class TestItemDAO.
     * @throws java.io.IOException
     */
    @Test
    public void testGetBeanProperty() throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        log.trace(">>> getBeanProperty");
        Object bean = new TestItemDTO();
        int propIndex = 1;
        TestItemDAO instance = new TestItemDAO();
        Object result = instance.getBeanPropertyValue(bean, propIndex);
        assertNotNull(result);
    }

    /**
     * Test of sgetBeanFieldValue method, of class TestItemDAO.
     * @throws java.io.IOException
     */
    @Test
    public void testSetBeanProperty() throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        log.trace(">>> setBeanProperty");
        Object bean = new TestItemDTO();
        TestItemDAO instance = new TestItemDAO();
        int propIndex = 2;
        log.debug("propIndex="+propIndex);
        Object propValue = "test_0001";
        instance.setBeanPropertyValue(bean, propIndex, propValue);
    }

    /**
     * Test of getRowTotalRange method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetRowTotalRange() throws Exception {
        log.trace(">>> getRowTotalRange");
        TestItemDAO instance = new TestItemDAO();
        INestedRange result = instance.getRowTotalRange();
        assertNotNull(result);
    }

    /**
     * Test of selectTotalRange method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testSelectTotalRange() throws Exception {
        log.trace(">>> selectTotalRange");
        TestItemDAO instance = new TestItemDAO();
        INestedRange result = instance.selectTotalRange();
        assertNotNull(result);
    }

    /**
     * Test of select method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    /*
    @Test
    public void testSelect() throws Exception {
        log.trace(">>> testSelectByRange");
        TestItemDAO dao = new TestItemDAO();
        INestedRange r = new NestedIntRange(2, 3, null);
        List<TestItemDTO> l = dao.select(r);
        int numRows = 0;
        Iterator<TestItemDTO> itr = l.iterator();
        while (itr.hasNext()){
            TestItemDTO p = itr.next();
            log.debug(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("ProductRefs retrieved not in 3 rows", (numRows == 3));
        log.trace("<<< testSelectByRange");
    }
    */

    /**
     * Test of selectByID method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testSelectByID() throws Exception {
        log.trace(">>> selectByID");
        BigInteger id = BigInteger.ONE;
        TestItemDAO instance = new TestItemDAO();
        TestItemDTO result = instance.selectByID(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    /**
     * Test of select(QueryExtraParam qep) method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testSelectWithParams() throws Exception {
        log.trace(">>> testSelectWithParams");
        TestItemDAO dao = new TestItemDAO();
        NestedIntRange range = new NestedIntRange(0, 10, null);
        TestItemDTO example = new TestItemDTO();
        example.setId(BigInteger.ONE);
        SQLParams par = new SQLParams(range, null, example);
        List<TestItemDTO> l = dao.select(par);
        int numRows = 0;
        Iterator<TestItemDTO> itr = l.iterator();
        while (itr.hasNext()){
            TestItemDTO p = itr.next();
            log.debug(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("TestItemDTO retrieved not in one row", (numRows == 1));
        example = new TestItemDTO();
        example.setName("test");
        par.setExample(example);
        l = dao.select(par);
        numRows = 0;
        itr = l.iterator();
        while (itr.hasNext()){
            TestItemDTO p = itr.next();
            log.debug(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("TestItemDTO retrieved more than one row", (numRows > 0));
        SortOrder so = new SortOrder();
        List<String> colNames = dao.getColumnNames();
        String s;
        for (int i = 0; i < colNames.size(); i++) {
            s = colNames.get(i);
            so.add(s, ISortOrder.Direction.ASC);
        }
        
        par = new SQLParams(range, so, example);
        l = dao.select(par);
        
        log.trace("<<< testSelectWithParams");
    }

    /**
     * Test of insertRow method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertRow() throws Exception {
        log.trace(">>> testInsertRow");
        item01 = new TestItemDTO();
        item01.setName("test_"+Math.random());
        TestItemDAO dao = new TestItemDAO();
        int numRows = dao.insertRow(item01);
        log.debug("Rows inserted="+numRows+", item:"+item01);
        TestItemDTO exp = dao.selectByID(item01.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item01.equals(exp));
        log.trace("<<< testInsertRow");
    }

    /**
     * Test of insertRowBySP method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertRowBySP() throws Exception {
        log.trace(">>> testInsertRowBySP");
        item02 = new TestItemDTO();
        item02.setName("test_"+Math.random());
        TestItemDAO dao = new TestItemDAO();
        //Map<String, Object> params = TestItemDTO.toMap(item);
        dao.insertRowBySP(item02);
        //item = TestItemDTO.fromMap(params);
        log.debug("Inserted item:"+item02);
        TestItemDTO exp = dao.selectByID(item02.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item02.getName().equals(exp.getName()));
        log.trace("<<< testInsertRowBySP");
    }

    /**
     * Test of insertRowBySP2 method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertRowBySP2() throws Exception {
        log.trace(">>> testInsertRowBySP2");
        TestItemDTO item = new TestItemDTO();
        item.setName("test_"+Math.random());
        TestItemDAO dao = new TestItemDAO();
        int cnt = dao.insertRowBySP2(item);
        log.debug("Inserted item:"+item+", affected rows="+cnt);
        TestItemDTO exp = dao.selectByID(item.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item.equals(exp));
        log.trace("<<< testInsertRowBySP2");
    }

    /**
     * Test of updateRow method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateRow() throws Exception {
        log.trace(">>> updateRow");
        TestItemDTO item = new TestItemDTO();
        if (item01 == null) {
            item.setId(BigInteger.ONE);
        } else {
            item = item01;
        }
        item.setName("test_"+Math.random());
        TestItemDAO instance = new TestItemDAO();
        int expResult = 0;
        int result = instance.updateRow(item);
        assertFalse(expResult == result);
    }

    /**
     * Test of deleteRow method, of class TestItemDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteRow() throws Exception {
        log.trace("deleteRow");
        TestItemDTO item = new TestItemDTO();
        if (item02 == null) {
            item.setId(BigInteger.ZERO);
        } else {
            item = item02;
        }
        TestItemDAO instance = new TestItemDAO();
        int expResult = 0;
        int result = instance.deleteRow(item);
        assertFalse(expResult == result);
    }
    
}
