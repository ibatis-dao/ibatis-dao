package fxapp01.dto;

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author serg
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({NestedIntRangeTest.class, LimitedIntRangeTest.class})
public class IntRangeAllTests {

    private ILogger log = LogMgr.getLogger(this.getClass()); 

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
