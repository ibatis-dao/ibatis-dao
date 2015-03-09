package fxapp01;

import fxapp01.dao.DAOAllTests;
import fxapp01.dto.IntRangeAllTests;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author serg
 */
public class TestRunner {
    
    private static final ILogger log = LogMgr.getLogger(TestRunner.class); 
    
    public static void main(String[] args) {
        run(IntRangeAllTests.class);
        run(DAOAllTests.class);
        
    }
    
    private static void run(Class clazz) {
        Result result = JUnitCore.runClasses(clazz);
        for (Failure failure : result.getFailures()) {
            log.debug(failure.toString());
        }
        log.debug((result.wasSuccessful()) ? "Successful" : "Failed");
    }
}
