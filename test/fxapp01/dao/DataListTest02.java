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
package fxapp01.dao;

import fxapp01.TestItemObservList;
import fxapp01.dto.TestItemDTO;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.math.BigInteger;
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
public class DataListTest02 {
    
    private final ILogger log = LogMgr.getLogger(this.getClass());
    private final DataList<TestItemDTO,Integer> instance;

    public DataListTest02() throws IOException, IntrospectionException {
        instance = newDataList();
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

    private DataList<TestItemDTO,Integer> newDataList() throws IOException, IntrospectionException {
        return new TestItemObservList();
    }
    
    private TestItemDTO newDataRow(BigInteger id){
        TestItemDTO d = new TestItemDTO();
        if (id != null) {
            d.setId(id);
        } else {
            d.setId(BigInteger.valueOf(Math.round(Math.random() * 1000000)));
        }
        d.setName("test_"+Math.random());
        return d;
    }
    
    @Test
    public void testAdd_IHasDataChanges() {
        log.trace("testAdd_IHasDataChanges");
        TestItemDTO e = newDataRow(null);
        boolean result = instance.add(e);
        assertTrue(result);
        assertTrue(instance.hasDataChanges());
    }

}
