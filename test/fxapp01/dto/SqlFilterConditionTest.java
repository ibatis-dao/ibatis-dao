package fxapp01.dto;

import fxapp01.dao.filter.SqlFilterCondition;
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
public class SqlFilterConditionTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public SqlFilterConditionTest() {
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
        log.debug("build");
        SqlFilterCondition instance = SqlFilterCondition.IsNull;
        String expResult = "test01 is null";
        String result = instance.build("test01");
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
    }
}
