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
package fxapp01.dao.filter;

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;
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
    public void getSqlTemplate() throws IOException {
        log.trace("getTemplate");
        String expResult = "{0} = {1}";
        Filter instance = new Filter.Equals("A", "B");
        String result = instance.getSqlTemplate();
        assertEquals(expResult, result);
        instance = new Filter.And("A", "B");
        expResult = "({0}) and ({1})";
        result = instance.getSqlTemplate();
        assertTrue(expResult.contentEquals(result));
    }

    /**
     * Test of getArgCount method, of class Filter.
     */
    @Test
    public void getArgCount() throws IOException {
        log.trace("getArgCount");
        int expResult = 2;
        Filter instance = new Filter.Equals("A", "B");
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
    public void getArgs() throws IOException {
        log.trace("getArgs");
        Filter instance = new Filter.Equals("A", "B");
        Object[] expResult = new Object[]{"A", "B"};
        instance.setArgs(expResult);
        Object[] result = instance.getArgs();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getText method, of class Filter.
     */
    @Test
    public void testGetText() throws IOException {
        log.trace("getText");
        Filter instance = new Filter.IsNull(1);
        instance.setOneArg("test01");
        String expResult = "test01 is null";
        String result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
    }
    
    /**
     * Test of few classes implementing simple filters.
     */
    @Test
    public void testSimpleFilters() throws IOException {
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
        result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
        // Between
        instance = new Filter.Between(fldName1, argName1, argName2);
        expResult = fldName1+" between "+argName1+" and "+argName2;
        result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
        // Equals
        instance = new Filter.Equals(fldName1, fldName2);
        expResult = fldName1+" = "+fldName2;
        result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
        // NotEquals
        instance = new Filter.NotEquals(fldName1, fldName2);
        expResult = fldName1+" <> "+fldName2;
        result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
        // Greater
        instance = new Filter.Greater(fldName1, fldName2);
        expResult = fldName1+" > "+fldName2;
        result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
        // GreaterOrEquals
        instance = new Filter.GreaterOrEquals(fldName1, fldName2);
        expResult = fldName1+" >= "+fldName2;
        result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
        // IsNull
        instance = new Filter.IsNull(fldName1);
        expResult = fldName1+" is null";
        result = instance.getFilterSqlText();
        assertTrue(expResult.contentEquals(result));
        // IsNotNull
        instance = new Filter.IsNotNull(fldName1);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // Less
        instance = new Filter.Less(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // LessOrEquals
        instance = new Filter.LessOrEquals(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // Like
        instance = new Filter.Like(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // NotLike
        instance = new Filter.NotLike(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // Not
        instance = new Filter.Not(fldName1);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // Or
        instance = new Filter.Or(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // Containing
        instance = new Filter.Containing(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // StartsWith
        instance = new Filter.StartsWith(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // EndsWith
        instance = new Filter.EndsWith(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // In
        instance = new Filter.In(fldName1, fldName2);
        expResult = "";
        result = instance.getFilterSqlText();
        log.debug(result);
        assertFalse(expResult.contentEquals(result));
        // NotIn
        instance = new Filter.NotIn(fldName1, fldName2);
        expResult = fldName1+" not in "+fldName2;
        result = instance.getFilterSqlText();
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
        // Exists
        instance = new Filter.Exists(fldName1);
        expResult = "exists "+fldName1;
        result = instance.getFilterSqlText();
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
        // NotExists
        instance = new Filter.NotExists(fldName1);
        expResult = "not exists "+fldName1;
        result = instance.getFilterSqlText();
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
    }

    /**
     * Test of few classes implementing simple filters.
     */
    @Test
    public void testComplexFilter() throws IOException {
        log.trace("testComplexFilter");
        String fldName1 = "test01";
        String fldName2 = "test02";
        // IsNull
        ISqlFilterable IsNullInstance = new Filter.IsNull(fldName1);
        // Equals
        ISqlFilterable EqualsInstance = new Filter.Equals(fldName1, fldName2);
        // Or
        ISqlFilterable OrInstance = new Filter.Or(IsNullInstance, EqualsInstance);
        // Not
        ISqlFilterable NotInstance = new Filter.Not(OrInstance);
        String expResult = "not (("+fldName1+" is null) or ("+fldName1+" = "+fldName2+"))";
        String result = NotInstance.getFilterSqlText();
        log.debug(result);
        assertTrue(expResult.contentEquals(result));
    }
}
