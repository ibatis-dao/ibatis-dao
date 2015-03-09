package test01.dao;

import fxapp01.ProductRefsObservList;
import fxapp01.dao.DataCacheReadOnly;
import fxapp01.dao.ProductRefsDAO;
import fxapp01.dto.LimitedIntRange;
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
    
    @Test
    public void testSelectByRange() {
        log.trace(">>> testSelectByRange");
        ProductRefsDAO dao = new ProductRefsDAO();
        LimitedIntRange r = new LimitedIntRange(2, 3);
        List<ProductRefs> l = dao.select(r);
        int numRows = 0;
        Iterator<ProductRefs> itr = l.iterator();
        while (itr.hasNext()){
            ProductRefs p = itr.next();
            log.info(p.getName());
            numRows = numRows + 1;
        }
        Assert.assertTrue("ProductRefs retrieved not in 3 rows", (numRows == 3));
        log.trace("<<< testSelectByRange");
    }
    
    @Test
    public void testProductRefsObservList() {
        log.trace(">>> testProductRefsObservList");
        ProductRefsObservList pl = new ProductRefsObservList();
        ProductRefs p;
        log.debug("p(0). "+pl.get(0).toString());
        //Oracle - при первом шаге фактически читается не 20 записей, 
        //а 19 из-за того, что rownum начинается с 1, не с 0.
        //поскольку в буфер загружается 19 строк, диапазон корректируется под эту длину
        //На втором шаге загрузка опять начинается с 19 строки.
        //в итоге - в буфере ДВЕ 19 строки.
        log.debug("p(20). "+pl.get(20).toString());
        log.debug("p(40). "+pl.get(40).toString());
        log.debug("p(60). "+pl.get(60).toString());
        log.debug("p(80). "+pl.get(80).toString());
        log.debug("p(60). "+pl.get(60).toString());
        log.debug("p(40). "+pl.get(40).toString());
        log.debug("p(20). "+pl.get(20).toString());
        log.debug("p(0). "+pl.get(0).toString());
        log.debug("p(50). "+pl.get(50).toString());
        log.debug("p(150). "+pl.get(150).toString());
        pl.debugPrintAll();
        log.trace("<<< testProductRefsObservList");
    }
    
    @Test
    public void testDataCacheReadOnly() {
        log.trace("testDataCacheReadOnly");
    }
    
}