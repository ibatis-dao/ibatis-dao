package test01.dto;

import fxapp01.dto.IntRange;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
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
public class IntRangeTest01 {
    
    private ILogger log = LogMgr.getLogger(this.getClass()); 

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
    public void baseFilelds() {
        IntRange aRange = new IntRange(1, 10);
        Assert.assertNotNull(aRange);
        Assert.assertTrue(aRange.getFirst() == 1);
        Assert.assertTrue(aRange.getLength() == 10);
        Assert.assertTrue(aRange.getLast()== 10);
        //aRowsRange.Sub(cache.getRange());
    }
}
