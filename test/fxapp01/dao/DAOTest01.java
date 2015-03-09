package fxapp01.dao;

import fxapp01.ProductRefsObservList;
import fxapp01.dao.DataCacheReadOnly;
import fxapp01.dao.ProductRefsDAO;
import fxapp01.dto.INestedRange;
import fxapp01.dto.LimitedIntRange;
import fxapp01.dto.NestedIntRange;
import fxapp01.dto.ProductRefs;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
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
    
    private ILogger log = LogMgr.getLogger(this.getClass()); 

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
    /*
    @Test
    public void testSelectByRange() {
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
    public void testInsertRow() {
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
    */
    @Test
    public void testInsertRowBySP() {
        log.trace(">>> testInsertRowBySP");
        ProductRefs item = new ProductRefs();
        item.setName("test_"+Math.random());
        ProductRefsDAO dao = new ProductRefsDAO();
        int numRows = dao.insertRowBySP(item);
        log.debug("Rows inserted="+numRows+", item:"+item);
        ProductRefs exp = dao.selectByID(item.getId());
        log.debug("Item selected:"+exp);
        Assert.assertTrue(item.equals(exp));
        log.trace("<<< testInsertRowBySP");
    }
    
}