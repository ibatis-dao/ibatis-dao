package fxapp01.dto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author serg
 */
public class SortOrderTest {
    
    public SortOrderTest() {
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

    /**
     * Test of build method, of class SortOrder.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        SortOrder instance = new SortOrder();
        String expResult = "";
        String result = instance.build();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class SortOrder.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        SortOrder instance = new SortOrder();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class SortOrder.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        int index = 0;
        SortOrder instance = new SortOrder();
        String expResult = "";
        String result = instance.getName(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirection method, of class SortOrder.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        int index = 0;
        SortOrder instance = new SortOrder();
        ISortOrder.Direction expResult = null;
        ISortOrder.Direction result = instance.getDirection(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class SortOrder.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        String columnName = "";
        ISortOrder.Direction direction = null;
        SortOrder instance = new SortOrder();
        instance.add(columnName, direction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of del method, of class SortOrder.
     */
    @Test
    public void testDel() {
        System.out.println("del");
        int index = 0;
        SortOrder instance = new SortOrder();
        boolean expResult = false;
        boolean result = instance.del(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class SortOrder.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        SortOrder instance = new SortOrder();
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
