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

package fxapp01.dto;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.EUnsupported;
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
public class LimitedIntRangeTest {
    
    private ILogger log = LogMgr.getLogger(this.getClass()); 
    private final static String mustFR = "must follow rules";

    public LimitedIntRangeTest() {
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

    private LimitedIntRange createZeroRange() {
        return new LimitedIntRange(0, 0, 0, Integer.MAX_VALUE);
    }

    private LimitedIntRange createRange1_10() {
        return new LimitedIntRange(1, 10, 1, Integer.MAX_VALUE);
    }
    
    private LimitedIntRange createRange10_10() {
        return new LimitedIntRange(10, 10, 1, Integer.MAX_VALUE);
    }
    
    private LimitedIntRange createRange5_5() {
        return new LimitedIntRange(5, 5, 1, Integer.MAX_VALUE);
    }
    
    private LimitedIntRange createRange50() {
        return new LimitedIntRange(0, 50, 0, Integer.MAX_VALUE);
    }
    private LimitedIntRange createRange100() {
        return new LimitedIntRange(0, 100, 0, Integer.MAX_VALUE);
    }

    /**
     * Test of getFirst method, of class LimitedIntRange.
     */
    @Test
    public void testGetFirst() {
        log.debug("getFirst");
        LimitedIntRange instance = createZeroRange();
        int expResult = 0;
        int result = instance.getFirst();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFirst method, of class LimitedIntRange.
     */
    @Test
    public void testSetFirst() {
        log.debug("setFirst");
        int first = 1;
        LimitedIntRange instance = createRange100();
        instance.setFirst(first);
        int result = instance.getFirst();
        assertEquals(result, first);
        try {
            instance.setFirst(-1);
            fail("setFirst() "+mustFR);
        } catch(EArgumentBreaksRule e) {
        }
    }

    /**
     * Test of getLength method, of class LimitedIntRange.
     */
    @Test
    public void testGetLength() {
        log.debug("getLength");
        LimitedIntRange instance = createZeroRange();
        int result = instance.getLength();
        assertEquals(0, result);
    }

    /**
     * Test of setLength method, of class LimitedIntRange.
     */
    @Test
    public void testSetLength() {
        log.debug("setLength");
        int length = 10;
        LimitedIntRange instance = createRange5_5();
        instance.setLength(length);
        int result = instance.getLength();
        assertEquals(length, result);
    }

    /**
     * Test of incLength method, of class LimitedIntRange.
     */
    @Test
    public void testIncLength() {
        log.debug("incLength");
        int increment = 5;
        LimitedIntRange instance = createRange5_5();
        instance.incLength(increment);
        int result = instance.getLength();
        assertEquals(10, result);
    }

    /**
     * Test of getLeftLimit method, of class LimitedIntRange.
     */
    @Test
    public void testGetLeftLimit() {
        log.debug("getLeftLimit");
        LimitedIntRange instance = createRange5_5();
        int result = instance.getLeftLimit();
        assertEquals(1, result);
    }

    /**
     * Test of setLeftLimit method, of class LimitedIntRange.
     */
    @Test
    public void testSetLeftLimit() {
        log.debug("setLeftLimit");
        int leftLimit = 0;
        LimitedIntRange instance = createRange5_5();
        instance.setLeftLimit(leftLimit);
        int result = instance.getLeftLimit();
        assertEquals(leftLimit, result);
    }

    /**
     * Test of getRightLimit method, of class LimitedIntRange.
     */
    @Test
    public void testGetRightLimit() {
        log.debug("getRightLimit");
        LimitedIntRange instance = createZeroRange();
        int expResult = Integer.MAX_VALUE;
        int result = instance.getRightLimit();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRightLimit method, of class LimitedIntRange.
     */
    @Test
    public void testSetRightLimit() {
        log.debug("setRightLimit");
        int rightLimit = 10;
        LimitedIntRange instance = createRange5_5();
        instance.setRightLimit(rightLimit);
        int result = instance.getRightLimit();
        assertEquals(rightLimit, result);
    }

    /**
     * Test of getLast method, of class LimitedIntRange.
     */
    @Test
    public void testGetLast() {
        log.debug("getLast");
        LimitedIntRange instance = createRange5_5();
        int expResult = 9;
        int result = instance.getLast();
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class LimitedIntRange.
     */
    @Test
    public void testClone() {
        log.debug("clone");
        LimitedIntRange instance = createRange5_5();
        LimitedIntRange expResult = createRange5_5();
        LimitedIntRange result = instance.clone();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class LimitedIntRange.
     */
    @Test
    public void testEquals() {
        log.debug("equals");
        Object o = createRange5_5();
        LimitedIntRange instance = createRange5_5();
        boolean result = instance.equals(o);
        assertTrue(result);
        instance = createRange10_10();
        result = instance.equals(o);
        assertFalse(result);
    }

    /**
     * Test of hashCode method, of class LimitedIntRange.
     */
    @Test
    public void testHashCode() {
        log.debug("hashCode");
        LimitedIntRange instance = createRange1_10();
        int expResult = (((89 * 5 + 1) * 89 + 10) * 89 + 1) * 89 + Integer.MAX_VALUE;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class LimitedIntRange.
     */
    @Test
    public void testToString() {
        log.debug("toString");
        LimitedIntRange instance = createZeroRange();
        String expResult = "first=0, length=0, leftLimit=0, rightLimit="+Integer.MAX_VALUE;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of IsSingular method, of class LimitedIntRange.
     */
    @Test
    public void testIsSingular() {
        log.debug("IsSingular");
        LimitedIntRange instance = createZeroRange();
        assertTrue(instance.IsSingular());
        instance = createRange100();
        assertFalse(instance.IsSingular());
    }

    /**
     * Test of IsInbound method, of class LimitedIntRange.
     */
    @Test
    public void testIsInbound_int() {
        log.debug("IsInbound");
        LimitedIntRange instance = createRange1_10();
        assertFalse(instance.IsInbound(0));
        assertTrue(instance.IsInbound(5));
    }

    /**
     * Test of IsInbound method, of class LimitedIntRange.
     */
    @Test
    public void testIsInbound_LimitedIntRange() {
        log.debug("IsInbound");
        LimitedIntRange aRange = createRange1_10();
        LimitedIntRange instance = createRange5_5();
        assertTrue(instance.IsInbound(aRange));
        assertFalse(aRange.IsInbound(instance));
    }

    /**
     * Test of getMinDistance method, of class LimitedIntRange.
     */
    @Test
    public void testGetMinDistance() {
        log.debug("getMinDistance");
        int to = 10;
        LimitedIntRange instance = createRange5_5();
        int expResult = 1;
        int result = instance.getMinDistance(to);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxDistance method, of class LimitedIntRange.
     */
    @Test
    public void testGetMaxDistance() {
        log.debug("getMaxDistance");
        int to = 10;
        LimitedIntRange instance = createRange5_5();
        int expResult = 5;
        int result = instance.getMaxDistance(to);
        assertEquals(expResult, result);
    }

    /**
     * Test of IsOverlapped method, of class LimitedIntRange.
     */
    @Test
    public void testIsOverlapped() {
        log.debug("IsOverlapped");
        LimitedIntRange aRange = createRange5_5();
        LimitedIntRange instance = createRange1_10();
        assertTrue(instance.IsOverlapped(aRange));
        assertTrue(aRange.IsOverlapped(instance));
    }

    /**
     * Test of Overlap method, of class LimitedIntRange.
     */
    @Test
    public void testOverlap() {
        log.debug("Overlap");
        LimitedIntRange aRange = createRange5_5();
        LimitedIntRange instance = createRange1_10();
        LimitedIntRange expResult = createRange5_5();
        LimitedIntRange result = instance.Overlap(aRange);
        assertEquals(expResult, result);
    }

    /**
     * Test of Add method, of class LimitedIntRange.
     */
    @Test
    public void testAdd() {
        log.debug("Add");
        LimitedIntRange aRange = createRange5_5();
        LimitedIntRange instance = createRange10_10();
        LimitedIntRange expResult = new LimitedIntRange(5, 15, 1, Integer.MAX_VALUE);
        LimitedIntRange result = instance.Add(aRange);
        assertEquals(expResult, result);
    }

    /**
     * Test of Extend method, of class LimitedIntRange.
     */
    @Test
    public void testExtend() {
        log.debug("Extend");
        int to = 49;
        LimitedIntRange instance = createZeroRange();
        LimitedIntRange expResult = createRange50();
        LimitedIntRange result = instance.Extend(to);
        assertEquals(expResult, result);
    }

    /**
     * Test of Shift method, of class LimitedIntRange.
     */
    @Test
    public void testShift() {
        log.debug("Shift");
        int value = 10;
        LimitedIntRange instance = createRange10_10();
        LimitedIntRange expResult = new LimitedIntRange(20, 10, 1, Integer.MAX_VALUE);
        LimitedIntRange result = instance.Shift(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of Sub method, of class LimitedIntRange.
     */
    @Test
    public void testSub() {
        log.debug("Sub");
        LimitedIntRange aRange = createRange5_5();
        LimitedIntRange instance = createRange10_10();
        
        try {
            LimitedIntRange result = instance.Sub(aRange);
            fail("wrong operation");
        } catch (EUnsupported e) {
        }
    }

    /**
     * Test of Complement method, of class LimitedIntRange.
     */
    @Test
    public void testComplement() {
        log.debug("Complement");
        int to = 99;
        LimitedIntRange instance = createRange50();
        LimitedIntRange expResult = new LimitedIntRange(50, 50, 0, Integer.MAX_VALUE);
        LimitedIntRange result = instance.Complement(to);
        assertEquals(expResult, result);
    }
    
    @Test
    public void checkSetupBaseFilelds() {
        log.trace(">>> checkSetupBaseFilelds");
        LimitedIntRange aRange = createRange1_10();
        assertNotNull(aRange);
        assertTrue(aRange.getFirst() == 1);
        assertTrue(aRange.getLength() == 10);
        assertTrue(aRange.getLast()== 10);
        assertTrue(aRange.getLeftLimit() == 1);
        assertTrue(aRange.getRightLimit() == Integer.MAX_VALUE);
    }

    @Test
    public void checkLengths() {
        log.trace(">>> checkLengths");
        LimitedIntRange aRange = createRange1_10();
        assertNotNull(aRange);
        assertTrue(aRange.getLength() == 10);
        aRange.incLength(5);
        assertTrue(aRange.getLength() == 15);
        aRange.incLength(-10);
        assertTrue(aRange.getLength() == 5);
        assertTrue(aRange.getLast() == 5);
    }

    @Test
    public void checkMoveLimits() {
        log.trace(">>> checkMoveLimits");
        LimitedIntRange aRange = createRange1_10();
        assertNotNull(aRange);
        assertTrue(aRange.getLeftLimit() == 1);
        try {
            aRange.setLeftLimit(5);
            fail("wrong param value");
        } catch (EArgumentBreaksRule e) {
        }
        assertTrue(aRange.getLeftLimit() == 1);
        try {
            aRange.setRightLimit(-1);
            fail("wrong param value");
        } catch (EArgumentBreaksRule e) {
        }
        assertTrue(aRange.getRightLimit()== Integer.MAX_VALUE);
        
        aRange.setRightLimit(10);
        try {
            aRange.setFirst(5);
            fail("wrong param value");
        } catch (EArgumentBreaksRule e) {
        }
        //log.debug("first="+aRange.getFirst());
        assertTrue(aRange.getFirst() == 1);
        aRange.setLength(3);
        aRange.setFirst(5);
        assertTrue(aRange.getLast()== 7);
        aRange.incLength(2);
        assertTrue(aRange.getLast() == 9);
    }
    
    @Test
    public void checkClone() {
        log.trace(">>> checkClone");
        LimitedIntRange aRange = createRange1_10();
        assertNotNull(aRange);
        LimitedIntRange aRange2 = aRange.clone();
        assertTrue(aRange.equals(aRange2));
        aRange.setFirst(2);
        assertFalse(aRange.equals(aRange2));
    }

    @Test
    public void checkIsInbound() {
        log.trace(">>> checkIsInbound");
        LimitedIntRange aRange = createRange1_10();
        assertNotNull(aRange);
        assertFalse(aRange.IsInbound(0));
        assertTrue(aRange.IsInbound(1));
        assertTrue(aRange.IsInbound(5));
        assertTrue(aRange.IsInbound(10));
        assertFalse(aRange.IsInbound(11));
        aRange.setFirst(2);
        assertFalse(aRange.IsInbound(1));
        assertTrue(aRange.IsInbound(11));
        assertFalse(aRange.IsInbound(12));
    }

    @Test
    public void checkIsInboundRange() {
        log.trace(">>> checkIsInbound");
        LimitedIntRange aRange1 = createRange10_10();
        assertNotNull(aRange1);
        LimitedIntRange aRange2 = createRange10_10();
        assertNotNull(aRange2);
        assertTrue(aRange1.IsInbound(aRange2));
        aRange2.setFirst(11);
        assertFalse(aRange1.IsInbound(aRange2));
        aRange2.setFirst(9);
        assertFalse(aRange1.IsInbound(aRange2));
        aRange2.setLength(11);
        assertTrue(aRange1.IsInbound(aRange2));
    }
    
    @Test
    public void checkMinMaxDistance() {
        log.trace(">>> checkMinMaxDistance");
        LimitedIntRange aRange = createRange5_5();
        assertNotNull(aRange);
        assertTrue(aRange.getMinDistance(1) == -4);
        assertTrue(aRange.getMaxDistance(1) == -8);
        assertTrue(aRange.getMinDistance(7) == 0);
        assertTrue(aRange.getMaxDistance(7) == 0);
        assertTrue(aRange.getMinDistance(10) == 1);
        assertTrue(aRange.getMaxDistance(10) == 5);
    }

    @Test
    public void checkIsOverlapped() {
        log.trace(">>> checkIsOverlapped");
        LimitedIntRange aRange1 = createRange10_10();
        assertNotNull(aRange1);
        LimitedIntRange aRange2 = createRange10_10();
        assertNotNull(aRange2);
        assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(1);
        assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(5);
        assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(10);
        assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(15);
        assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(15);
        assertTrue(aRange1.IsOverlapped(aRange2));
        aRange2.setFirst(20);
        assertFalse(aRange1.IsOverlapped(aRange2));
    }

    @Test
    public void checkOverlap() {
        log.trace(">>> checkOverlap");
        LimitedIntRange aRange1 = createRange10_10();
        assertNotNull(aRange1);
        LimitedIntRange aRange2 = createRange10_10();
        assertNotNull(aRange2);
        LimitedIntRange aRange3 = aRange1.Overlap(aRange2);
        assertTrue(aRange3.equals(aRange1));
        log.debug("before aRange2.setFirst(5)");
        aRange2.setFirst(5);
        aRange3 = aRange1.Overlap(aRange2);
        assertTrue(aRange3.getFirst() == 10);
        assertTrue(aRange3.getLength()== 5);
        log.debug("before aRange2.setFirst(1)");
        aRange2.setFirst(100);
        aRange3 = aRange1.Overlap(aRange2);
        assertTrue(aRange3.IsSingular());
    }
    
    @Test
    public void checkIsSingular() {
        log.trace(">>> checkIsSingular");
        LimitedIntRange aRange = createZeroRange();
        assertNotNull(aRange);
        assertTrue(aRange.IsSingular());
    }

    @Test
    public void checkAdd() {
        log.trace(">>> checkAdd");
        LimitedIntRange aRange1 = createRange5_5();
        assertNotNull(aRange1);
        LimitedIntRange aRange2 = createRange10_10();
        assertNotNull(aRange2);
        LimitedIntRange aRange3 = aRange1.Add(aRange2);
        assertTrue(aRange3.getFirst() == 5);
        assertTrue(aRange3.getLength()== 15);
        log.debug("before aRange2.setFirst(0)");
        aRange1.setFirst(1);
        aRange3 = aRange1.Add(aRange2);
        assertTrue(aRange3.getFirst() == 1);
        assertTrue(aRange3.getLength()== 19);
        log.debug("before aRange2.setFirst(20)");
        aRange1.setFirst(20);
        aRange3 = aRange1.Add(aRange2);
        assertTrue(aRange3.getFirst() == 10);
        assertTrue(aRange3.getLength()== 15);
    }
    
    @Test
    public void checkExtend() {
        log.trace(">>> checkExtend");
        LimitedIntRange aRange1 = createRange5_5();
        assertNotNull(aRange1);
        log.debug("before aRange1.Extend(1)");
        LimitedIntRange aRange2 = aRange1.Extend(1);
        assertTrue(aRange2.getFirst() == 1);
        assertTrue(aRange2.getLast() == 9);
        log.debug("before aRange1.Extend(7)");
        aRange2 = aRange1.Extend(7);
        assertTrue(aRange2.IsSingular());
        log.debug("before aRange1.Extend(15)");
        aRange2 = aRange1.Extend(15);
        assertTrue(aRange2.getFirst() == 5);
        assertTrue(aRange2.getLast() == 15);
    }

    @Test
    public void checkShift() {
        log.trace(">>> checkShift");
        LimitedIntRange aRange1 = createRange5_5();
        assertNotNull(aRange1);
        log.debug("before aRange1.Shift(-4)");
        LimitedIntRange aRange2 = aRange1.Shift(-4);
        assertTrue(aRange2.getFirst() == 1);
        assertTrue(aRange2.getLength() == 5);
        log.debug("before aRange1.Shift(2)");
        aRange2 = aRange1.Shift(2);
        assertTrue(aRange2.getFirst() == 7);
        assertTrue(aRange2.getLength() == 5);
        log.debug("before aRange1.Shift(15)");
        aRange2 = aRange1.Shift(15);
        assertTrue(aRange2.getFirst() == 20);
        assertTrue(aRange2.getLength() == 5);
    }
    
    @Test
    public void checkComplement() {
        log.trace(">>> checkComplement");
        LimitedIntRange aRange1 = createRange5_5();
        assertNotNull(aRange1);
        log.debug("before aRange1.Complement(1)");
        LimitedIntRange aRange2 = aRange1.Complement(1);
        assertTrue(aRange2.getFirst() == 1);
        assertTrue(aRange2.getLast() == 4);
        log.debug("before aRange1.Complement(7)");
        aRange2 = aRange1.Complement(7);
        assertTrue(aRange2.IsSingular());
        log.debug("before aRange1.Complement(15)");
        aRange2 = aRange1.Complement(15);
        assertTrue(aRange2.getFirst() == 10);
        assertTrue(aRange2.getLast() == 15);
        log.debug("before aRange1.Complement(39)");
        aRange1.setFirst(1);
        aRange1.setLength(20);
        aRange2 = aRange1.Complement(39);
        assertTrue(aRange2.getFirst() == 21);
        assertTrue(aRange2.getLast() == 39);
    }
    
    @Test
    public void checkSub() {
        log.trace(">>> checkSub");
        LimitedIntRange aRange1 = createRange5_5();
        assertNotNull(aRange1);
        LimitedIntRange aRange2 = createRange10_10();
        assertNotNull(aRange2);
        
        try {
            LimitedIntRange aRange3 = aRange1.Sub(aRange2);
            fail("not supported yet");
        } catch (EUnsupported e) {
        }
    }
    
}
