package fxapp01.dao;

import fxapp01.dto.INestedRange;
import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.ProductRefs;
import fxapp01.dto.ProductRefsQBE;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author StarukhSA
 */
public class ProductRefsDAO implements ProductRefsMapper{
    
    private static final ILogger log = LogMgr.getLogger(ProductRefsDAO.class);
    private BigInteger rowCount = null;
    private final ContainerProperties properties;
    private int pageSize;
    
    public ProductRefsDAO() throws IOException {
        log.trace(">>> constructor");
        BaseDao dao = new BaseDao();
        List<BeanPropertyMapping> beanPropertiesMap = dao.getBeanPropertiesMapping(ProductRefs.class);
        properties = new ContainerProperties(beanPropertiesMap);
        log.trace("<<< constructor");
    }
    
    public int getRowCount() {
        //log.trace(">>> getRowCount");
        if (rowCount == null) {
            rowCount = selectCount();
        }
        //пока условно считаем, что кол-во записей в таблице не меняется
        //в дальнейшем надо будет продумать, как и когда это должно обновляться
        return rowCount.intValue();
    }
    
    public ContainerProperties getContainerProperties() {
        log.trace(">>> getContainerProperties");
        return properties;
    }
    
    public Object getBeanProperty(Object bean, int propIndex) {
        log.trace(">>> getBeanProperty");
        return properties.getBeanProperty(bean, propIndex);
    }
    
    public void setBeanProperty(Object bean, int propIndex, Object propValue) {
        log.trace(">>> setBeanProperty");
        properties.setBeanProperty(bean, propIndex, propValue);
    }
    
    @Override
    public List<ProductRefs> select(INestedRange rowsrange) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> select");
            BaseDao dao = new BaseDao();
            try {
                ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
                List<ProductRefs> res = mapper.select(rowsrange);
                log.trace("<<< select");
                return res;
            } catch (Exception e) {
                log.error(null, e);
                throw e;
            } finally {
                dao.closeDBSession();
            }
        } catch (IOException | PersistenceException ex) {
            log.error(null, ex);
            return null;
        }
    }

    @Override
    public ProductRefs selectByID(BigInteger id) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> selectByID");
            BaseDao dao = new BaseDao();
            try {
                ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
                ProductRefs res = mapper.selectByID(id);
                log.trace("<<< selectByID");
                return res;
            } catch (Exception e) {
                log.error(null, e);
                throw e;
            } finally {
                dao.closeDBSession();
            }
        } catch (IOException | PersistenceException ex) {
            log.error(null, ex);
            return null;
        }
    }

    @Override
    public BigInteger selectCount() {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> selectCount");
            BaseDao dao = new BaseDao();
            try {
                ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
                BigInteger res = mapper.selectCount();
                log.trace("<<< selectCount");
                return res;
            } catch (Exception e) {
                log.error(null, e);
                throw e;
            } finally {
                dao.closeDBSession();
            }
        } catch (IOException | PersistenceException ex) {
            log.error(null, ex);
            return null;
        }
    }

    @Override
    public int insertRow(ProductRefs item) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> insertRow");
            BaseDao dao = new BaseDao();
            try {
                ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
                int res = mapper.insertRow(item);
                dao.commit();
                log.trace("<<< insertRow");
                return res;
            } catch (Exception e) {
                log.error(null, e);
                throw e;
            } finally {
                dao.closeDBSession();
            }
        } catch (IOException | PersistenceException ex) {
            log.error(null, ex);
            return 0;
        }
    }

    @Override
    public ProductRefs insertRowBySP(ProductRefs item) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> insertRowBySP");
            BaseDao dao = new BaseDao();
            try {
                ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
                item = mapper.insertRowBySP(item);
                dao.commit();
                log.trace("<<< insertRowBySP");
                return item;
            } catch (Exception e) {
                log.error(null, e);
                throw e;
            } finally {
                dao.closeDBSession();
            }
        } catch (IOException | PersistenceException ex) {
            log.error(null, ex);
            return null;
        }
    }

    @Override
    public ProductRefs insertRowBySP2(ProductRefs item) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> insertRowBySP2");
            BaseDao dao = new BaseDao();
            try {
                ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
                item = mapper.insertRowBySP2(item);
                dao.commit();
                log.trace("<<< insertRowBySP2");
                return item;
            } catch (Exception e) {
                log.error(null, e);
                throw e;
            } finally {
                dao.closeDBSession();
            }
        } catch (IOException | PersistenceException ex) {
            log.error(null, ex);
            return null;
        }
    }

    @Override
    public List<ProductRefs> selectBE(ProductRefsQBE qbe) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> selectBE");
            BaseDao dao = new BaseDao();
            try {
                ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
                List<ProductRefs> res = mapper.selectBE(qbe);
                log.trace("<<< selectBE");
                return res;
            } finally {
                dao.closeDBSession();
            }
        } catch (IOException | PersistenceException ex) {
            log.error(null, ex);
            return null;
        }
    }

}
