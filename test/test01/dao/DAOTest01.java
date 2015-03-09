package test01.dao;

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
    }
}
