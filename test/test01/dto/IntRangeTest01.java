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
    public void checkSetupBaseFilelds() {
        log.trace(">>> checkSetupBaseFilelds");
        IntRange aRange = new IntRange(1, 10);
        Assert.assertNotNull(aRange);
        Assert.assertTrue(aRange.getFirst() == 1);
        Assert.assertTrue(aRange.getLength() == 10);
        Assert.assertTrue(aRange.getLast()== 10);
        Assert.assertTrue(aRange.getLeftLimit() == 0);
        Assert.assertTrue(aRange.getRightLimit() == Integer.MAX_VALUE);
    }

    @Test
    public void checkLengths() {
        log.trace(">>> checkLengths");
        IntRange aRange = new IntRange(1, 10);
        Assert.assertNotNull(aRange);
        Assert.assertTrue(aRange.getLength() == 10);
        aRange.incLength(5);
        Assert.assertTrue(aRange.getLength() == 15);
        aRange.incLength(-10);
        Assert.assertTrue(aRange.getLength() == 5);
        Assert.assertTrue(aRange.getLast() == 5);
    }

    @Test
    public void checkMoveLimits() {
        log.trace(">>> checkMoveLimits");
        IntRange aRange = new IntRange(1, 10);
        Assert.assertNotNull(aRange);
        Assert.assertTrue(aRange.getLeftLimit() == 0);
        aRange.setLeftLimit(5);
    }
    
    @Test
    public void checkClone() {
        log.trace(">>> checkClone");
        IntRange aRange = new IntRange(1, 10);
        Assert.assertNotNull(aRange);
        IntRange aRange2 = aRange.clone();
        Assert.assertTrue(aRange.equals(aRange2));
        aRange.setFirst(2);
        Assert.assertFalse(aRange.equals(aRange2));
    }
}
