/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxapp01.dto;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.ENegativeArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author StarukhSA
 */
public class NestedIntRangeTest {
    
    private ILogger log = LogMgr.getLogger(this.getClass()); 
    private final static String mustFR = "must follow rules";

    public NestedIntRangeTest() {
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
    
    private NestedIntRange createZeroRange() {
        return new NestedIntRange(0, 0, null);
    }

    private NestedIntRange createRange50() {
        return new NestedIntRange(0, 50, null);
    }
    private NestedIntRange createRange100() {
        return new NestedIntRange(0, 100, null);
    }

    private NestedIntRange createNestedRange() {
        NestedIntRange parentInstance = createRange100();
        return new NestedIntRange(0, 0, parentInstance);
    }

    /**
     * Test of getFirst method, of class NestedIntRange.
     */
    @Test
    public void testGetFirst() {
        log.debug("getFirst");
        NestedIntRange instance = createZeroRange();
        int result = instance.getFirst();
        assertEquals(result, 0);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setFirst method, of class NestedIntRange.
     */
    @Test
    public void testSetFirst() {
        log.debug("setFirst");
        int first = 1;
        NestedIntRange instance = createZeroRange();
        instance.setFirst(first);
        int result = instance.getFirst();
        assertEquals(result, first);
        instance = createNestedRange();
        try {
            instance.setFirst(-1);
            fail("setFirst() "+mustFR);
        } catch(EArgumentBreaksRule e) {
        }
    }

    /**
     * Test of getLength method, of class NestedIntRange.
     */
    @Test
    public void testGetLength() {
        log.debug("getLength");
        NestedIntRange instance = createZeroRange();
        int result = instance.getLength();
        assertEquals(result, 0);
    }

    /**
     * Test of setLength method, of class NestedIntRange.
     */
    @Test
    public void testSetLength() {
        log.debug("setLength");
        int length = 1;
        NestedIntRange instance = createZeroRange();
        instance.setLength(length);
        int result = instance.getLength();
        assertEquals(result, length);
        instance = createNestedRange();
        try {
            instance.setLength(-1);
            fail("setLength() "+mustFR);
        } catch(ENegativeArgument e) {
        }
    }

    /**
     * Test of incLength method, of class NestedIntRange.
     */
    @Test
    public void testIncLength() {
        log.debug("incLength");
        int increment = 1;
        NestedIntRange instance = createZeroRange();
        instance.incLength(increment);
        int result = instance.getLength();
        try {
            instance.incLength(-2);
            fail("testIncLength() "+mustFR);
        } catch(ENegativeArgument e) {
        }
        assertEquals(result, increment);
        instance = createNestedRange();
        try {
            instance.incLength(-1);
            fail("testIncLength() "+mustFR);
        } catch(ENegativeArgument e) {
        }
    }

    /**
     * Test of getParentRange method, of class NestedIntRange.
     */
    @Test
    public void testGetParentRange() {
        log.debug("getParentRange");
        INestedRange instance = createZeroRange();
        INestedRange result = instance.getParentRange();
        assertEquals(null, result);
    }

    /**
     * Test of setParentRange method, of class NestedIntRange.
     */
    @Test
    public void testSetParentRange() {
        log.debug("setParentRange");
        INestedRange instance = createNestedRange();
        INestedRange parentRange = null;
        instance.setParentRange(parentRange);
        INestedRange result = instance.getParentRange();
        assertEquals(parentRange, result);
    }

    /**
     * Test of getLast method, of class NestedIntRange.
     */
    @Test
    public void testGetLast() {
        log.debug("getLast");
        NestedIntRange instance = createZeroRange();
        int expResult = -1;
        int result = instance.getLast();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class NestedIntRange.
     */
    @Test
    public void testClone() {
        log.debug("clone");
        NestedIntRange instance = createZeroRange();
        NestedIntRange result = instance.clone();
        assertEquals(instance.getFirst(), result.getFirst());
        assertEquals(instance.getLength(), result.getLength());
        assertEquals(instance.getParentRange(), result.getParentRange());
    }

    /**
     * Test of equals method, of class NestedIntRange.
     */
    @Test
    public void testEquals() {
        log.debug("equals");
        Object o = null;
        NestedIntRange instance = createZeroRange();
        assertFalse(instance.equals(o));
    }

    /**
     * Test of hashCode method, of class NestedIntRange.
     */
    @Test
    public void testHashCode() {
        log.debug("hashCode");
        NestedIntRange instance = createZeroRange();
        int expResult = 5 * 89 * 89 * 89;
        int result = instance.hashCode();
        log.debug("hashCode()="+result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class NestedIntRange.
     */
    @Test
    public void testToString() {
        log.debug("toString");
        NestedIntRange instance = createZeroRange();
        String expResult = "first=0, length=0, parentRange=null";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of IsSingular method, of class NestedIntRange.
     */
    @Test
    public void testIsSingular() {
        log.debug("IsSingular");
        NestedIntRange instance = createZeroRange();
        assertTrue(instance.IsSingular());
    }

    /**
     * Test of IsInbound method, of class NestedIntRange.
     */
    @Test
    public void testIsInbound_int() {
        log.debug("IsInbound");
        NestedIntRange instance = createRange100();
        assertTrue(instance.IsInbound(0));
        assertTrue(instance.IsInbound(50));
        assertTrue(instance.IsInbound(99));
        assertFalse(instance.IsInbound(-1));
        assertFalse(instance.IsInbound(100));
    }

    /**
     * Test of IsInbound method, of class NestedIntRange.
     */
    @Test
    public void testIsInbound_NestedIntRange() {
        log.debug("IsInbound");
        NestedIntRange aRange = createRange50();
        NestedIntRange instance = createRange100();
        assertTrue(aRange.IsInbound(instance));
    }

    /**
     * Test of getMinDistance method, of class NestedIntRange.
     */
    @Test
    public void testGetMinDistance() {
        log.debug("getMinDistance");
        int to = 1;
        NestedIntRange instance = createZeroRange();
        int result = instance.getMinDistance(to);
        assertEquals(to, result);
    }

    /**
     * Test of getMaxDistance method, of class NestedIntRange.
     */
    @Test
    public void testGetMaxDistance() {
        log.debug("getMaxDistance");
        NestedIntRange instance = createRange50();
        int result = instance.getMaxDistance(100);
        assertEquals(50, 50);
    }

    /**
     * Test of IsOverlapped method, of class NestedIntRange.
     */
    @Test
    public void testIsOverlapped() {
        log.debug("IsOverlapped");
        NestedIntRange aRange = createRange50();
        NestedIntRange instance = createRange100();
        boolean result = instance.IsOverlapped(aRange);
        assertTrue(result);
    }

    /**
     * Test of Overlap method, of class NestedIntRange.
     */
    @Test
    public void testOverlap() {
        log.debug("Overlap");
        INestedRange aRange = createRange50();
        INestedRange instance = createRange100();
        INestedRange result = instance.Overlap(aRange);
        assertEquals(result, aRange);
    }

    /**
     * Test of Add method, of class NestedIntRange.
     */
    @Test
    public void testAdd() {
        log.debug("Add");
        INestedRange aRange = createZeroRange();
        INestedRange instance = createRange100();
        INestedRange result = instance.Add(aRange);
        assertEquals(result, instance);
    }

    /**
     * Test of Extend method, of class NestedIntRange.
     */
    @Test
    public void testExtend() {
        log.debug("Extend");
        INestedRange instance = createRange50();
        int to = 99;
        INestedRange expResult = createRange100();
        INestedRange result = instance.Extend(to);
        assertEquals(expResult, result);
    }

    /**
     * Test of Shift method, of class NestedIntRange.
     */
    @Test
    public void testShift() {
        log.debug("Shift");
        int value = 50;
        INestedRange instance = createRange50();
        INestedRange result = instance.Shift(value);
        assertEquals(instance.getLength(), result.getLength());
        assertEquals(value, result.getFirst());
    }

    /**
     * Test of Complement method, of class NestedIntRange.
     */
    @Test
    public void testComplement() {
        log.debug("Complement");
        INestedRange instance = createRange50();
        INestedRange expResult = new NestedIntRange(50, 50, null);
        INestedRange result = instance.Complement(99);
        assertEquals(expResult, result);
    }
    
}
