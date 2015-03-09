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
import fxapp01.dto.ProductRefs;
import fxapp01.dto.ProductRefsQBE;
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
        ProductRefsDAO dao = new ProductRefsDAO();
        INestedRange r = new NestedIntRange(2, 3, null);
        List<ProductRefs> l = dao.select(r);
        int numRows = 0;
        Iterator<ProductRefs> itr = l.iterator();
        while (itr.hasNext()){
            ProductRefs p = itr.next();
            log.debug(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("ProductRefs retrieved not in 3 rows", (numRows == 3));
        log.trace("<<< testSelectByRange");
    }
    
    @Test
    public void testSelectBE() throws IOException {
        log.trace(">>> testSelectBE");
        ProductRefsDAO dao = new ProductRefsDAO();
        NestedIntRange range = new NestedIntRange(0, 10, null);
        ProductRefs example = new ProductRefs();
        example.setId(BigInteger.ONE);
        ProductRefsQBE qbe = new ProductRefsQBE(example, range, null);
        List<ProductRefs> l = dao.selectBE(qbe);
        int numRows = 0;
        Iterator<ProductRefs> itr = l.iterator();
        while (itr.hasNext()){
            ProductRefs p = itr.next();
            log.debug(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("ProductRefs retrieved not in one row", (numRows == 1));
        example = new ProductRefs();
        example.setName("test");
        qbe.setExample(example);
        l = dao.selectBE(qbe);
        numRows = 0;
        itr = l.iterator();
        while (itr.hasNext()){
            ProductRefs p = itr.next();
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
        
        qbe = new ProductRefsQBE(example, range, so);
        l = dao.selectBE(qbe);
        
        log.trace("<<< testSelectBE");
    }
    
    @Test
    public void testInsertRow() throws IOException {
        log.trace(">>> testInsertRow");
        ProductRefs item = new ProductRefs();
        item.setName("test_"+Math.random());
        ProductRefsDAO dao = new ProductRefsDAO();
        int numRows = dao.insertRow(item);
        log.debug("Rows inserted="+numRows+", item:"+item);
        ProductRefs exp = dao.selectByID(item.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item.equals(exp));
        log.trace("<<< testInsertRow");
    }
    
    @Test
    public void testInsertRowBySP() throws IOException {
        log.trace(">>> testInsertRowBySP");
        ProductRefs item = new ProductRefs();
        item.setName("test_"+Math.random());
        ProductRefsDAO dao = new ProductRefsDAO();
        item = dao.insertRowBySP(item);
        log.debug("Inserted item:"+item);
        ProductRefs exp = dao.selectByID(item.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item.equals(exp));
        log.trace("<<< testInsertRowBySP");
    }
    
    @Test
    public void testInsertRowBySP2() throws IOException {
        log.trace(">>> testInsertRowBySP2");
        ProductRefs item = new ProductRefs();
        item.setName("test_"+Math.random());
        ProductRefsDAO dao = new ProductRefsDAO();
        item = dao.insertRowBySP2(item);
        log.debug("Inserted item:"+item);
        ProductRefs exp = dao.selectByID(item.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item.equals(exp));
        log.trace("<<< testInsertRowBySP2");
    }
    
}