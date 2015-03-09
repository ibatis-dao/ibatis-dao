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
     * Test of getText method, of class SqlFilterBaseImpl.
     */
    @Test
    public void testGetText() {
        log.debug("getText");
        ISqlFilterable instance = new IsNull("test01");
        String expResult = "test01 is null";
        String result = instance.getText();
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
    }
}
