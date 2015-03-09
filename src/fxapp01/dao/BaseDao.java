package fxapp01.dao;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import org.apache.ibatis.exceptions.PersistenceException;

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
        if (borm == null) {
            try {
                borm = new BatisORM(null);
            } catch (IOException | PersistenceException e ) {
                log.error("createDaoFactory() failed", e);
                throw e;
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

}
