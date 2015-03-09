package fxapp01.dao;

import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.ProductRefs;
import fxapp01.dto.IntRange;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author StarukhSA
 */
public class ProductRefsDAO implements ProductRefsMapper{
    
    private static final ILogger log = LogMgr.getLogger(ProductRefsDAO.class);
    private BigInteger rowCount = null;
    private final ContainerProperties properties;
    private int pageSize;
    
    public ProductRefsDAO() {
        log.trace(">>> constructor");
        BaseDao dao = new BaseDao();
        List<BeanPropertyMapping> beanPropertiesMap = dao.getBeanPropertiesMapping(ProductRefs.class);
        properties = new ContainerProperties(beanPropertiesMap);
        log.trace("<<< constructor");
    }
    
    public int getRowCount() {
        log.trace(">>> getRowCount");
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
    public List<ProductRefs> select(IntRange rowsrange) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> select");
        BaseDao dao = new BaseDao();
        try {
            ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
            List<ProductRefs> res = mapper.select(rowsrange);
            log.trace("<<< select");
            return res;
        } finally {
            dao.closeDBSession();
        }
    }

    @Override
    public ProductRefs selectByID(BigInteger id) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> selectByID");
        BaseDao dao = new BaseDao();
        try {
            ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
            ProductRefs res = mapper.selectByID(id);
            log.trace("<<< selectByID");
            return res;
        } finally {
            dao.closeDBSession();
        }
    }

    @Override
    public BigInteger selectCount() {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> selectCount");
        BaseDao dao = new BaseDao();
        try {
            ProductRefsMapper mapper = dao.getMapper(ProductRefsMapper.class);
            BigInteger res = mapper.selectCount();
            log.trace("<<< selectCount");
            return res;
        } finally {
            dao.closeDBSession();
        }
    }

}
