package fxapp01;

import fxapp01.dao.DAOAllTests;
import fxapp01.dto.GeoLocationTest;
import fxapp01.dto.DTOAllTests;
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
        run(DTOAllTests.class);
        run(DAOAllTests.class);
        run(GeoLocationTest.class);
    }
    
    private static void run(Class clazz) {
        log.debug("---------------Start tests of: "+clazz.getName()+"---------------");
        Result result = JUnitCore.runClasses(clazz);
        for (Failure failure : result.getFailures()) {
            log.debug(failure.toString());
        }
        log.debug("===============Tests of: "+clazz.getName()+" was "+((result.wasSuccessful()) ? "Successful" : "Failed"));
    }
}
