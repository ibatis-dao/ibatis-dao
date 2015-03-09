package fxapp01.dao;

import fxapp01.ProductRefsObservList;
import fxapp01.dto.ProductRefs;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author serg
 */
public class DataCacheTest01 {
    
    private ILogger log = LogMgr.getLogger(this.getClass()); 

    public DataCacheTest01() {
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
    /*
    @Test
    public void testDataCacheReadOnly() {
        log.trace("testDataCacheReadOnly");
    }
    */
}
