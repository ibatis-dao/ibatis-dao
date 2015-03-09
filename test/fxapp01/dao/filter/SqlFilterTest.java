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
     * Test of getSqlTemplate method, of class Filter.
     */
    @Test
    public void getSqlTemplate() {
        log.trace("getTemplate");
        String expResult = "{0} = {1}";
        Filter instance = new Filter(expResult, 2);
        String result = instance.getSqlTemplate();
        assertTrue(expResult.contentEquals(result));
        expResult = "({0}) and ({1})";
        instance.setSqlTemplate(expResult);
        result = instance.getSqlTemplate();
        assertTrue(expResult.contentEquals(result));
    }

    /**
     * Test of getArgCount method, of class Filter.
     */
    @Test
    public void getArgCount() {
        log.trace("getArgCount");
        int expResult = 2;
        Filter instance = new Filter("{0} = {1}", expResult);
        int result = instance.getArgCount();
        assertEquals(expResult, result);
        expResult = 1;
        instance.setArgCount(expResult);
        result = instance.getArgCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArgs method, of class Filter.
     */
    @Test
    public void getArgs() {
        log.trace("getArgs");
        Filter instance = new Filter("{0} = {1}", 2);
        Object[] expResult = new Object[]{"A", "B"};
        instance.setArgs(expResult);
        Object[] result = instance.getArgs();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getText method, of class Filter.
     */
    @Test
    public void testGetText() {
        log.trace("getText");
        Filter instance = new Filter("{0} is null", 1);
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
        String argName1 = "A01";
        String argName2 = "B02";
        ISqlFilterable instance;
        String expResult;
        String result;
        // And
        instance = new Filter.And(fldName1, fldName2);
        expResult = "("+fldName1+") and ("+fldName2+")";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Between
        instance = new Filter.Between(fldName1, argName1, argName2);
        expResult = fldName1+" between "+argName1+" and "+argName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Equals
        instance = new Filter.Equals(fldName1, fldName2);
        expResult = fldName1+" = "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // NotEquals
        instance = new Filter.NotEquals(fldName1, fldName2);
        expResult = fldName1+" <> "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Greater
        instance = new Filter.Greater(fldName1, fldName2);
        expResult = fldName1+" > "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // GreaterOrEquals
        instance = new Filter.GreaterOrEquals(fldName1, fldName2);
        expResult = fldName1+" >= "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // IsNull
        instance = new Filter.IsNull(fldName1);
        expResult = fldName1+" is null";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // IsNotNull
        instance = new Filter.IsNotNull(fldName1);
        expResult = fldName1+" is not null";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Less
        instance = new Filter.Less(fldName1, fldName2);
        expResult = fldName1+" < "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // LessOrEquals
        instance = new Filter.LessOrEquals(fldName1, fldName2);
        expResult = fldName1+" =< "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Like
        instance = new Filter.Like(fldName1, fldName2);
        expResult = fldName1+" like "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // NotLike
        instance = new Filter.NotLike(fldName1, fldName2);
        expResult = fldName1+" not like "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Not
        instance = new Filter.Not(fldName1);
        expResult = "not ("+fldName1+")";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Or
        instance = new Filter.Or(fldName1, fldName2);
        expResult = "("+fldName1+") or ("+fldName2+")";
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Containing
        instance = new Filter.Containing(fldName1, fldName2);
        expResult = fldName1+" containing "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // StartsWith
        instance = new Filter.StartsWith(fldName1, fldName2);
        expResult = fldName1+" starts with "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // EndsWith
        instance = new Filter.EndsWith(fldName1, fldName2);
        expResult = fldName1+" ends with "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // In
        instance = new Filter.In(fldName1, fldName2);
        expResult = fldName1+" in "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // NotIn
        instance = new Filter.NotIn(fldName1, fldName2);
        expResult = fldName1+" not in "+fldName2;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // Exists
        instance = new Filter.Exists(fldName1);
        expResult = "exists "+fldName1;
        result = instance.getText();
        assertTrue(expResult.contentEquals(result));
        // NotExists
        instance = new Filter.NotExists(fldName1);
        expResult = "not exists "+fldName1;
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
        ISqlFilterable IsNullinstance = new Filter.IsNull(fldName1);
        // Equals
        ISqlFilterable Equalsinstance = new Filter.Equals(fldName1, fldName2);
        // Or
        ISqlFilterable Orinstance = new Filter.Or(IsNullinstance, Equalsinstance);
        // Not
        ISqlFilterable Notinstance = new Filter.Not(Orinstance);
        String expResult = "not (("+fldName1+" is null) or ("+fldName1+" = "+fldName2+"))";
        String result = Notinstance.getText();
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
    }
}
