package test01.dto;

import fxapp01.dto.LimitedIntRange;
import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.EUnsupported;
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
        LimitedIntRange aRange = new LimitedIntRange(1, 10);
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
        LimitedIntRange aRange = new LimitedIntRange(1, 10);
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
        LimitedIntRange aRange = new LimitedIntRange(1, 10);
        Assert.assertNotNull(aRange);
        Assert.assertTrue(aRange.getLeftLimit() == 0);
        try {
            aRange.setLeftLimit(5);
            Assert.fail("wrong param value");
        } catch (EArgumentBreaksRule e) {
        }
        Assert.assertTrue(aRange.getLeftLimit() == 0);
        try {
            aRange.setRightLimit(-1);
            Assert.fail("wrong param value");
        } catch (EArgumentBreaksRule e) {
        }
        Assert.assertTrue(aRange.getRightLimit()== Integer.MAX_VALUE);
        
        aRange.setRightLimit(10);
        try {
            aRange.setFirst(5);
            Assert.fail("wrong param value");
        } catch (EArgumentBreaksRule e) {
        }
        //log.debug("first="+aRange.getFirst());
        Assert.assertTrue(aRange.getFirst() == 1);
        aRange.setLength(3);
        aRange.setFirst(5);
        Assert.assertTrue(aRange.getLast()== 7);
        aRange.incLength(2);
        Assert.assertTrue(aRange.getLast() == 9);
    }
    
    @Test
    public void checkClone() {
        log.trace(">>> checkClone");
        LimitedIntRange aRange = new LimitedIntRange(1, 10);
        Assert.assertNotNull(aRange);
        LimitedIntRange aRange2 = aRange.clone();
        Assert.assertTrue(aRange.equals(aRange2));
        aRange.setFirst(2);
        Assert.assertFalse(aRange.equals(aRange2));
    }

    @Test
    public void checkIsInbound() {
        log.trace(">>> checkIsInbound");
        LimitedIntRange aRange = new LimitedIntRange(1, 10);
        Assert.assertNotNull(aRange);
        Assert.assertFalse(aRange.IsInbound(0));
        Assert.assertTrue(aRange.IsInbound(1));
        Assert.assertTrue(aRange.IsInbound(5));
        Assert.assertTrue(aRange.IsInbound(10));
        Assert.assertFalse(aRange.IsInbound(11));
        aRange.setFirst(2);
        Assert.assertFalse(aRange.IsInbound(1));
        Assert.assertTrue(aRange.IsInbound(11));
        Assert.assertFalse(aRange.IsInbound(12));
    }

    @Test
    public void checkIsInboundRange() {
        log.trace(">>> checkIsInbound");
        LimitedIntRange aRange1 = new LimitedIntRange(10, 10);
        Assert.assertNotNull(aRange1);
        LimitedIntRange aRange2 = new LimitedIntRange(10, 10);
        Assert.assertNotNull(aRange2);
        Assert.assertTrue(aRange1.IsInbound(aRange2));
        aRange2.setFirst(11);
        Assert.assertFalse(aRange1.IsInbound(aRange2));
        aRange2.setFirst(9);
        Assert.assertFalse(aRange1.IsInbound(aRange2));
        aRange2.setLength(11);
        Assert.assertTrue(aRange1.IsInbound(aRange2));
    }
    
    @Test
    public void checkMinMaxDistance() {
        log.trace(">>> checkMinMaxDistance");
        LimitedIntRange aRange = new LimitedIntRange(5, 5);
        Assert.assertNotNull(aRange);
        Assert.assertTrue(aRange.getMinDistance(1) == -4);
        Assert.assertTrue(aRange.getMaxDistance(1) == -8);
        Assert.assertTrue(aRange.getMinDistance(7) == 0);
        Assert.assertTrue(aRange.getMaxDistance(7) == 0);
        Assert.assertTrue(aRange.getMinDistance(10) == 1);
        Assert.assertTrue(aRange.getMaxDistance(10) == 5);
    }

    @Test
    public void checkIsOverlapped() {
        log.trace(">>> checkIsOverlapped");
        LimitedIntRange aRange1 = new LimitedIntRange(10, 10);
        Assert.assertNotNull(aRange1);
        LimitedIntRange aRange2 = new LimitedIntRange(10, 10);
        Assert.assertNotNull(aRange2);
        Assert.assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(0);
        Assert.assertFalse(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(5);
        Assert.assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(10);
        Assert.assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(15);
        Assert.assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(15);
        Assert.assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(20);
        Assert.assertFalse(aRange1.IsOverlapped(aRange2));
    }

    @Test
    public void checkOverlap() {
        log.trace(">>> checkOverlap");
        LimitedIntRange aRange1 = new LimitedIntRange(10, 10);
        Assert.assertNotNull(aRange1);
        LimitedIntRange aRange2 = new LimitedIntRange(10, 10);
        Assert.assertNotNull(aRange2);
        LimitedIntRange aRange3 = aRange1.Overlap(aRange2);
        Assert.assertTrue(aRange3.equals(aRange1));
        log.debug("before aRange2.setFirst(5)");
        aRange2.setFirst(5);
        aRange3 = aRange1.Overlap(aRange2);
        Assert.assertTrue(aRange3.getFirst() == 10);
        Assert.assertTrue(aRange3.getLength()== 5);
        log.debug("before aRange2.setFirst(1)");
        aRange2.setFirst(0);
        aRange3 = aRange1.Overlap(aRange2);
        Assert.assertTrue(aRange3.IsSingular());
    }
    
    @Test
    public void checkIsSingular() {
        log.trace(">>> checkIsSingular");
        LimitedIntRange aRange = new LimitedIntRange(0, 0);
        Assert.assertNotNull(aRange);
        Assert.assertTrue(aRange.IsSingular());
    }

    @Test
    public void checkAdd() {
        log.trace(">>> checkAdd");
        LimitedIntRange aRange1 = new LimitedIntRange(5, 5);
        Assert.assertNotNull(aRange1);
        LimitedIntRange aRange2 = new LimitedIntRange(10, 5);
        Assert.assertNotNull(aRange2);
        LimitedIntRange aRange3 = aRange1.Add(aRange2);
        Assert.assertTrue(aRange3.getFirst() == 5);
        Assert.assertTrue(aRange3.getLength()== 10);
        log.debug("before aRange2.setFirst(0)");
        aRange1.setFirst(0);
        aRange3 = aRange1.Add(aRange2);
        Assert.assertTrue(aRange3.getFirst() == 0);
        Assert.assertTrue(aRange3.getLength()== 15);
        log.debug("before aRange2.setFirst(20)");
        aRange1.setFirst(20);
        aRange3 = aRange1.Add(aRange2);
        Assert.assertTrue(aRange3.getFirst() == 10);
        Assert.assertTrue(aRange3.getLength()== 15);
    }
    
    @Test
    public void checkExtend() {
        log.trace(">>> checkExtend");
        LimitedIntRange aRange1 = new LimitedIntRange(5, 5);
        Assert.assertNotNull(aRange1);
        log.debug("before aRange1.Extend(1)");
        LimitedIntRange aRange2 = aRange1.Extend(1);
        Assert.assertTrue(aRange2.getFirst() == 1);
        Assert.assertTrue(aRange2.getLast() == 9);
        log.debug("before aRange1.Extend(7)");
        aRange2 = aRange1.Extend(7);
        Assert.assertTrue(aRange2.IsSingular());
        log.debug("before aRange1.Extend(15)");
        aRange2 = aRange1.Extend(15);
        Assert.assertTrue(aRange2.getFirst() == 5);
        Assert.assertTrue(aRange2.getLast() == 15);
    }

    @Test
    public void checkShift() {
        log.trace(">>> checkShift");
        LimitedIntRange aRange1 = new LimitedIntRange(5, 5);
        Assert.assertNotNull(aRange1);
        log.debug("before aRange1.Shift(-4)");
        LimitedIntRange aRange2 = aRange1.Shift(-4);
        Assert.assertTrue(aRange2.getFirst() == 1);
        Assert.assertTrue(aRange2.getLength() == 5);
        log.debug("before aRange1.Shift(2)");
        aRange2 = aRange1.Shift(2);
        Assert.assertTrue(aRange2.getFirst() == 7);
        Assert.assertTrue(aRange2.getLength() == 5);
        log.debug("before aRange1.Shift(15)");
        aRange2 = aRange1.Shift(15);
        Assert.assertTrue(aRange2.getFirst() == 20);
        Assert.assertTrue(aRange2.getLength() == 5);
    }

    @Test
    public void checkComplement() {
        log.trace(">>> checkComplement");
        LimitedIntRange aRange1 = new LimitedIntRange(5, 5);
        Assert.assertNotNull(aRange1);
        log.debug("before aRange1.Complement(1)");
        LimitedIntRange aRange2 = aRange1.Complement(1);
        Assert.assertTrue(aRange2.getFirst() == 1);
        Assert.assertTrue(aRange2.getLast() == 4);
        log.debug("before aRange1.Complement(7)");
        aRange2 = aRange1.Complement(7);
        Assert.assertTrue(aRange2.IsSingular());
        log.debug("before aRange1.Complement(15)");
        aRange2 = aRange1.Complement(15);
        Assert.assertTrue(aRange2.getFirst() == 10);
        Assert.assertTrue(aRange2.getLast() == 15);
    }
    
    @Test
    public void checkSub() {
        log.trace(">>> checkSub");
        LimitedIntRange aRange1 = new LimitedIntRange(5, 5);
        Assert.assertNotNull(aRange1);
        LimitedIntRange aRange2 = new LimitedIntRange(10, 5);
        Assert.assertNotNull(aRange2);
        
        try {
            LimitedIntRange aRange3 = aRange1.Sub(aRange2);
            Assert.fail("not supported yet");
        } catch (EUnsupported e) {
        }
    }
}
