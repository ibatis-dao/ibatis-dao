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

import fxapp01.excpt.ENullArgument;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.util.Map;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;

public class BaseDao {

    protected static final ILogger log = LogMgr.getLogger(BaseDao.class);
    private static BatisORM borm = null;
    private SqlSession session; 

    public BaseDao() throws IOException, PersistenceException {
        log.trace(">>> constructor");
        createDaoFactory();
    }

    private void createDaoFactory() throws IOException, PersistenceException {
        log.trace(">>> createDaoFactory");
        synchronized(this) {
            if (borm == null) {
                try {
                    borm = new BatisORM(null);
                } catch (IOException | PersistenceException e ) {
                    log.error("createDaoFactory() failed", e);
                    throw e;
                }
            }
        }
    }

    public SqlSession createDBSession(){
        log.trace(">>> createDBSession");
        if (borm == null) { session = null; }
        else { session = borm.createDBSession(); }
        return session;
    }

    public SqlSession getDBSession(){
        log.trace(">>> getDBSession");
        if (session == null) {
            return createDBSession();
        }
        else {
            return session;
        }
    }

    public void closeDBSession(){
        log.trace(">>> closeDBSession. session="+session);
        if (session != null) { session.close(); }
        session = null;
    }

    public <T extends Object> T getMapper(Class<T> type) {
        log.trace(">>> getMapper");
        //TODO разобраться с приведением типов. посмотреть исходники SqlSession и сделать аналогично 
        return getDBSession().getMapper(type);
    }

    public Connection getDBConnection(){
        log.trace(">>> getDBConnection");
        return getDBSession().getConnection();
    }

    public Configuration getConfiguration() throws IOException {
        log.trace(">>> getConfiguration");
        if (borm == null) { createDaoFactory(); }
        return borm.getConfiguration();
    }
    
    public List<BeanPropertyMapping> getBeanPropertiesMapping(Class beanClass) throws IOException {
        log.trace(">>> getBeanPropertiesMapping");
        if (borm == null) { createDaoFactory(); }
        return borm.getBeanPropertiesMapping(beanClass);
    }

    public void commit(){
        log.trace(">>>  commit(); session="+session);
        if (session != null) { session.commit(); }
    }

    public void rollback(){
        log.trace(">>> rollback(); session="+session);
        if (session != null) { session.rollback(); }
    }

    public String getDatabaseId() throws IOException {
        log.trace(">>> getDatabaseId");
        Configuration conf = getConfiguration();
        return conf.getDatabaseId();
    }
    
    public String getEnvironmentId() throws IOException {
        log.trace(">>> getEnvironmentId");
        Configuration conf = getConfiguration();
        Environment env = conf.getEnvironment();
        return env.getId();
    }
    
    public String getSQLFragment(String ID) throws IOException {
        String mthdName = "getSQLFragment";
        log.trace(mthdName);
        Configuration conf = getConfiguration();
        if (conf != null) {
            //log.debug("conf != null");
            Map<String, XNode> sqlNodes = conf.getSqlFragments();
            if (sqlNodes != null) {
                //log.debug("sqlNodes != null");
                /*
                for (Map.Entry<String, XNode> es : sqlNodes.entrySet()) {
                    XNode node = es.getValue();
                    log.debug("node.key="+es.getKey()+", node.name="+node.getName()
                        +", node.path="+node.getPath()+", node.body="+node.getStringBody()
                        +", node.toString="+node.toString()
                        +", node.Attribute="+node.getStringAttribute("lang"));
                }
                */
                /*
                node.key=fxapp01.dao.filter.FilterMapper.And, node.name=sql, node.path=mapper/sql, node.body=({0}) and ({1}), node.toString=<sql databaseId="oracle" id="And">({0}) and ({1})</sql>
                node.key=And, node.name=sql, node.path=mapper/sql, node.body=({0}) and ({1}), node.toString=<sql databaseId="oracle" id="And">({0}) and ({1})</sql>
                */
                XNode node = sqlNodes.get(ID);
                if (node != null) {
                    //log.debug("node != null");
                    //evalString(String expression)
                    //getStringBody()
                    //toString()
                    return node.getStringBody();
                } else {
                    //log.debug("node == null");
                    return null;
                }
            } else {
                throw new ENullArgument(mthdName, "getSqlFragments()");
            }
        } else {
            throw new ENullArgument(mthdName, "getConfiguration()");
        }
    }
    
}
