package fxapp01.dao.filter;

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
 * @author serg
 */
public class SqlFilterTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public SqlFilterTest() {
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
     * Test of getTemplate method, of class SqlFilterBaseImpl.
     */
    @Test
    public void getTemplate() {
        log.trace("getTemplate");
        String expResult = "{0} = {1}";
        SqlFilterBaseImpl instance = new SqlFilterBaseImpl(expResult, 2);
        String result = instance.getTemplate();
        assertTrue(expResult.contentEquals(result));
        expResult = "({0}) and ({1})";
        instance.setTemplate(expResult);
        result = instance.getTemplate();
        assertTrue(expResult.contentEquals(result));
    }

    /**
     * Test of getArgCount method, of class SqlFilterBaseImpl.
     */
    @Test
    public void getArgCount() {
        log.trace("getArgCount");
        int expResult = 2;
        SqlFilterBaseImpl instance = new SqlFilterBaseImpl("{0} = {1}", expResult);
        int result = instance.getArgCount();
        assertEquals(expResult, result);
        expResult = 1;
        instance.setArgCount(expResult);
        result = instance.getArgCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArgs method, of class SqlFilterBaseImpl.
     */
    @Test
    public void getArgs() {
        log.trace("getArgs");
        SqlFilterBaseImpl instance = new SqlFilterBaseImpl("{0} = {1}", 2);
        Object[] expResult = new Object[]{"A", "B"};
        instance.setArgs(expResult);
        Object[] result = instance.getArgs();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getText method, of class SqlFilterBaseImpl.
     */
    @Test
    public void testGetText() {
        log.trace("getText");
        SqlFilterBaseImpl instance = new SqlFilterBaseImpl("{0} is null", 1);
        instance.setOneArg("test01");
        String expResult = "test01 is null";
        String result = instance.getText();
        assertTrue(expResult.contentEquals(result));
    }
    
    /**
     * Test of few classes implementing simple filters.
     */
    @Test
    public void testSimpleFilters() {
        log.trace("testSimpleFilters");
        String fldName1 = "test01";
        String fldName2 = "test02";
        // IsNull
        ISqlFilterable instance = new IsNull(fldName1);
        String expResult = fldName1+" is null";
        String result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Not
        instance = new Not(fldName1);
        expResult = "not ("+fldName1+")";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // And
        instance = new And(fldName1, fldName2);
        expResult = "("+fldName1+") and ("+fldName2+")";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Or
        instance = new Or(fldName1, fldName2);
        expResult = "("+fldName1+") or ("+fldName2+")";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Equals
        instance = new Equals(fldName1, fldName2);
        expResult = fldName1+" = "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
    }

    /**
     * Test of few classes implementing simple filters.
     */
    @Test
    public void testComplexFilter() {
        log.trace("testComplexFilter");
        String fldName1 = "test01";
        String fldName2 = "test02";
        // IsNull
        ISqlFilterable IsNullinstance = new IsNull(fldName1);
        // Equals
        ISqlFilterable Equalsinstance = new Equals(fldName1, fldName2);
        // Or
        ISqlFilterable Orinstance = new Or(IsNullinstance, Equalsinstance);
        // Not
        ISqlFilterable Notinstance = new Not(Orinstance);
        String expResult = "not (("+fldName1+" is null) or ("+fldName1+" = "+fldName2+"))";
        String result = Notinstance.getText();
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
    }
}
