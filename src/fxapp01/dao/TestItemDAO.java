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
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author StarukhSA
 */
public class TestItemDAO implements TestItemMapper, IDAO<TestItemDTO>{
    
    private final ILogger log = LogMgr.getLogger(this.getClass());
    private INestedRange rowRange = null;
    private final BeanPropertiesDescribed beanProperties;
    private int pageSize;
    
    public TestItemDAO() throws IOException, IntrospectionException {
        log.trace(">>> constructor");
        BaseDao dao = new BaseDao();
        List<BeanPropertyMapping> beanPropertiesMap = dao.getBeanPropertiesMapping(TestItemDTO.class);
        beanProperties = new BeanPropertiesDescribed(TestItemDTO.class);
        log.trace("<<< constructor");
    }
    
    @Override
    public BeanProperties getBeanProperties() {
        log.trace(">>> getBeanProperties");
        return beanProperties;
    }
    
    public Object getBeanPropertyValue(Object bean, int propIndex) throws InvocationTargetException, IllegalAccessException {
        log.trace(">>> getBeanProperty");
        IDataProperty dp = beanProperties.getDataProperty(propIndex);
        if (dp != null) {
            return dp.getValue(bean);
        } else {
            throw new IllegalArgumentException("No data property found for index "+propIndex);
        }
    }
    
    public void setBeanPropertyValue(Object bean, int propIndex, Object propValue) throws InvocationTargetException, IllegalAccessException {
        log.trace(">>> setBeanProperty");
        IDataProperty dp = beanProperties.getDataProperty(propIndex);
        if (dp != null) {
            dp.setValue(bean, propValue);
        } else {
            throw new IllegalArgumentException("No data property found for index "+propIndex);
        }
    }
    
    @Override
    public INestedRange getRowTotalRange() throws IOException {
        //log.trace(">>> getRowCount");
        //TODO сделать обновление диапазона
        if (rowRange == null) {
            rowRange = selectTotalRange();
        }
        //пока условно считаем, что кол-во записей в таблице не меняется
        //в дальнейшем надо будет продумать, как и когда это должно обновляться
        return rowRange;
    }
    
    @Override
    public INestedRange selectTotalRange() throws IOException {
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
    }

    @Override
    public List<TestItemDTO> select(INestedRange rowsrange) throws IOException {
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
    }

    @Override
    public TestItemDTO selectByID(BigInteger id) throws IOException {
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
    }

    @Override
    public List<TestItemDTO> selectBE(TestItemQBE qbe) throws IOException {
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
    }

    @Override
    public int insertRow(TestItemDTO item) throws IOException {
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
    }

    @Override
    public int insertRowBySP(TestItemDTO item) throws IOException {
        log.trace(">>> insertRowBySP");
        BaseDao dao = new BaseDao();
        try {
            TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
            int res = mapper.insertRowBySP(item);
            dao.commit();
            log.trace("<<< insertRowBySP");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            dao.closeDBSession();
        }
    }

    @Override
    public int insertRowBySP2(TestItemDTO item) throws IOException {
        log.trace(">>> insertRowBySP2");
        BaseDao dao = new BaseDao();
        try {
            TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
            int res = mapper.insertRowBySP2(item);
            dao.commit();
            log.trace("<<< insertRowBySP2");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            dao.closeDBSession();
        }
    }

    @Override
    public int updateRow(TestItemDTO item) throws IOException {
        log.trace(">>> updateRow");
        BaseDao dao = new BaseDao();
        try {
            TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
            int res = mapper.updateRow(item);
            dao.commit();
            log.trace("<<< updateRow");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            dao.closeDBSession();
        }
    }

    @Override
    public int deleteRow(TestItemDTO item) throws IOException {
        log.trace(">>> deleteRow");
        BaseDao dao = new BaseDao();
        try {
            TestItemMapper mapper = dao.getMapper(TestItemMapper.class);
            int res = mapper.deleteRow(item);
            dao.commit();
            log.trace("<<< deleteRow");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            dao.closeDBSession();
        }
    }

}
