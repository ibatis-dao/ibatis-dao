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

import fxapp01.dto.INestedRange;
import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.TestItemDTO;
import fxapp01.dto.TestItemQBE;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author StarukhSA
 */
public class TestItemDAO implements TestItemMapper{
    
    private static final ILogger log = LogMgr.getLogger(TestItemDAO.class);
    private INestedRange rowRange = null;
    private final ItemProperties properties;
    private int pageSize;
    
    public TestItemDAO() throws IOException {
        log.trace(">>> constructor");
        BaseDao dao = new BaseDao();
        List<BeanPropertyMapping> beanPropertiesMap = dao.getBeanPropertiesMapping(TestItemDTO.class);
        properties = new ItemProperties(beanPropertiesMap);
        log.trace("<<< constructor");
    }
    
    public int getRowCount() {
        //log.trace(">>> getRowCount");
        if (rowRange == null) {
            rowRange = selectTotalRange();
        }
        //пока условно считаем, что кол-во записей в таблице не меняется
        //в дальнейшем надо будет продумать, как и когда это должно обновляться
        return rowRange.getLast().intValue();
    }
    
    public ItemProperties getContainerProperties() {
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
    public List<TestItemDTO> select(INestedRange rowsrange) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> select");
            BaseDao dao = new BaseDao();
            try {
                TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
                List<TestItemDTO> res = mapper.select(rowsrange);
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
    public TestItemDTO selectByID(BigInteger id) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> selectByID");
            BaseDao dao = new BaseDao();
            try {
                TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
                TestItemDTO res = mapper.selectByID(id);
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
    public INestedRange selectTotalRange() {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> selectTotalRange");
            BaseDao dao = new BaseDao();
            try {
                TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
                INestedRange res = mapper.selectTotalRange();
                log.trace("<<< selectTotalRange");
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
    public int insertRow(TestItemDTO item) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> insertRow");
            BaseDao dao = new BaseDao();
            try {
                TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
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
    public TestItemDTO insertRowBySP(TestItemDTO item) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> insertRowBySP");
            BaseDao dao = new BaseDao();
            try {
                TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
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
    public TestItemDTO insertRowBySP2(TestItemDTO item) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> insertRowBySP2");
            BaseDao dao = new BaseDao();
            try {
                TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
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
    public List<TestItemDTO> selectBE(TestItemQBE qbe) {
        try {
            //throw new UnsupportedOperationException("Not supported yet.");
            log.trace(">>> selectBE");
            BaseDao dao = new BaseDao();
            try {
                TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
                List<TestItemDTO> res = mapper.selectBE(qbe);
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
