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
package fxapp01;

import fxapp01.dao.DAOAllTests;
import fxapp01.dto.DTOAllTests;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DTOAllTests.class, 
    DAOAllTests.class
})
public class TestRunner {
    
    private static final ILogger log = LogMgr.getLogger(TestRunner.class); 
    
    public static void main(String[] args) {
        run(DTOAllTests.class);
        run(DAOAllTests.class);
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
