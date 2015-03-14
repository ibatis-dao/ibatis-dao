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
import fxapp01.dto.ISortOrder;
import fxapp01.dto.NestedIntRange;
import fxapp01.dto.TestItemDTO;
import fxapp01.dto.TestItemQBE;
import fxapp01.dao.sort.SortOrder;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author serg
 */
public class DAOTest01 {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public DAOTest01() {
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
    
    @Test
    public void testSelectByRange() throws IOException {
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
    
    @Test
    public void testSelectBE() throws IOException {
        log.trace(">>> testSelectBE");
        TestItemDAO dao = new TestItemDAO();
        NestedIntRange range = new NestedIntRange(0, 10, null);
        TestItemDTO example = new TestItemDTO();
        example.setId(BigInteger.ONE);
        TestItemQBE qbe = new TestItemQBE(example, range, null);
        List<TestItemDTO> l = dao.selectBE(qbe);
        int numRows = 0;
        Iterator<TestItemDTO> itr = l.iterator();
        while (itr.hasNext()){
            TestItemDTO p = itr.next();
            log.debug(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("ProductRefs retrieved not in one row", (numRows == 1));
        example = new TestItemDTO();
        example.setName("test");
        qbe.setExample(example);
        l = dao.selectBE(qbe);
        numRows = 0;
        itr = l.iterator();
        while (itr.hasNext()){
            TestItemDTO p = itr.next();
            log.debug(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("ProductRefs retrieved more than one row", (numRows > 0));
        SortOrder so = new SortOrder();
        ItemProperties cp = dao.getContainerProperties();
        List<String> colNames = cp.getColumnNames();
        String s;
        for (int i = 0; i < colNames.size(); i++) {
            s = colNames.get(i);
            so.add(s, ISortOrder.Direction.ASC);
        }
        
        qbe = new TestItemQBE(example, range, so);
        l = dao.selectBE(qbe);
        
        log.trace("<<< testSelectBE");
    }
    
    @Test
    public void testInsertRow() throws IOException {
        log.trace(">>> testInsertRow");
        TestItemDTO item = new TestItemDTO();
        item.setName("test_"+Math.random());
        TestItemDAO dao = new TestItemDAO();
        int numRows = dao.insertRow(item);
        log.debug("Rows inserted="+numRows+", item:"+item);
        TestItemDTO exp = dao.selectByID(item.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item.equals(exp));
        log.trace("<<< testInsertRow");
    }
    
    @Test
    public void testInsertRowBySP() throws IOException {
        log.trace(">>> testInsertRowBySP");
        TestItemDTO item = new TestItemDTO();
        item.setName("test_"+Math.random());
        TestItemDAO dao = new TestItemDAO();
        //Map<String, Object> params = TestItemDTO.toMap(item);
        dao.insertRowBySP(item);
        //item = TestItemDTO.fromMap(params);
        log.debug("Inserted item:"+item);
        TestItemDTO exp = dao.selectByID(item.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item.getName().equals(exp.getName()));
        log.trace("<<< testInsertRowBySP");
    }
    
    @Test
    public void testInsertRowBySP2() throws IOException {
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
    
}