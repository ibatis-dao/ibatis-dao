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

import fxapp01.dto.TestItemDTO;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author StarukhSA
 */
public class BaseDaoTest {
    
    private final ILogger log = LogMgr.getLogger(this.getClass()); 

    public BaseDaoTest() {
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
     * Test of createDBSession method, of class BaseDao.
     * @throws java.io.IOException
     */
    @Test
    public void testCreateDBSession() throws IOException {
        log.trace("createDBSession");
        BaseDao instance = new BaseDao();
        SqlSession result = instance.createDBSession();
        assertNotNull(result);
    }

    /**
     * Test of getDBSession method, of class BaseDao.
     * @throws java.io.IOException
     */
    @Test
    public void testGetDBSession() throws IOException {
        log.trace("getDBSession");
        BaseDao instance = new BaseDao();
        SqlSession result = instance.getDBSession();
        assertNotNull(result);
    }

    /**
     * Test of closeDBSession method, of class BaseDao.
     * @throws java.io.IOException
     */
    @Test
    public void testCloseDBSession() throws IOException {
        log.trace("closeDBSession");
        BaseDao instance = new BaseDao();
        instance.closeDBSession();
    }

    /**
     * Test of getMapper method, of class BaseDao.
     * @throws java.io.IOException
     */
    @Test
    public void testGetMapper() throws IOException {
        log.trace("getMapper");
        BaseDao instance = new BaseDao();
        Object result = instance.getMapper(TestItemMapper.class);
        assertNotNull(result);
    }

    /**
     * Test of getDBConnection method, of class BaseDao.
     * @throws java.io.IOException
     */
    @Test
    public void testGetDBConnection() throws IOException {
        log.trace("getDBConnection");
        BaseDao instance = new BaseDao();
        Connection result = instance.getDBConnection();
        assertNotNull(result);
    }

    /**
     * Test of getConfiguration method, of class BaseDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetConfiguration() throws Exception {
        log.trace("getConfiguration");
        BaseDao instance = new BaseDao();
        Configuration result = instance.getConfiguration();
        assertNotNull(result);
    }

    /**
     * Test of getBeanPropertiesMapping method, of class BaseDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetBeanPropertiesMapping() throws Exception {
        log.trace("getBeanPropertiesMapping");
        BaseDao instance = new BaseDao();
        List<BeanPropertyMapping> result = instance.getBeanPropertiesMapping(TestItemDTO.class);
        assertNotNull(result);
    }

    /**
     * Test of commit method, of class BaseDao.
     * @throws java.io.IOException
     */
    @Test
    public void testCommit() throws IOException {
        log.trace("commit");
        BaseDao instance = new BaseDao();
        instance.commit();
    }

    /**
     * Test of rollback method, of class BaseDao.
     * @throws java.io.IOException
     */
    @Test
    public void testRollback() throws IOException {
        log.trace("rollback");
        BaseDao instance = new BaseDao();
        instance.rollback();
    }

    /**
     * Test of getDatabaseId method, of class BaseDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDatabaseId() throws Exception {
        log.trace("getDatabaseId");
        BaseDao instance = new BaseDao();
        String notExpResult = "";
        String result = instance.getDatabaseId();
        log.debug("DatabaseId="+result);
        assertFalse(notExpResult.contentEquals(result));
    }

    /**
     * Test of getEnvironmentId method, of class BaseDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetEnvironmentId() throws Exception {
        log.trace("getEnvironmentId");
        BaseDao instance = new BaseDao();
        String notExpResult = "";
        String result = instance.getEnvironmentId();
        log.debug("EnvironmentId="+result);
        assertFalse(notExpResult.contentEquals(result));
    }

    /**
     * Test of getSQLFragment method, of class BaseDao.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSQLFragment() throws Exception {
        log.trace("getSQLFragment");
        String ID = "And";
        BaseDao instance = new BaseDao();
        String notExpResult = "";
        String result = instance.getSQLFragment(ID);
        log.debug("SQLFragment="+result);
        assertFalse(notExpResult.contentEquals(result));
    }
    
}
