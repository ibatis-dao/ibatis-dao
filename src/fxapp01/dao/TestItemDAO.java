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

import fxapp01.orm.ORMFacade;
import fxapp01.dto.INestedRange;
import fxapp01.dto.NestedIntRange;
import fxapp01.dto.SQLParams;
import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.TestItemDTO;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author StarukhSA
 */
public class TestItemDAO implements TestItemMapper {
    
    private final ILogger log = LogMgr.getLogger(this.getClass());
    private INestedRange<Integer> rowRange = null;
    private final DAOProperties beanProperties;
    
    public TestItemDAO() throws IOException, IntrospectionException {
        log.trace(">>> constructor");
        beanProperties = new DAOProperties(TestItemDTO.class);
        log.trace("<<< constructor");
    }
    
    @Override
    public IDAOProperties getBeanProperties() {
        log.trace(">>> getBeanProperties");
        return beanProperties;
    }
    
    public Object getBeanPropertyValue(Object bean, int propIndex) throws InvocationTargetException, IllegalAccessException {
        log.trace(">>> getBeanProperty");
        IDataProperty<Object,Object> dp = beanProperties.getDataProperty(propIndex);
        if (dp != null) {
            return dp.getValue(bean);
        } else {
            throw new IllegalArgumentException("No data property found for index "+propIndex);
        }
    }
    
    public void setBeanPropertyValue(Object bean, int propIndex, Object propValue) throws InvocationTargetException, IllegalAccessException {
        log.trace(">>> setBeanProperty");
        IDataProperty<Object,Object> dp = beanProperties.getDataProperty(propIndex);
        if (dp != null) {
            dp.setValue(bean, propValue);
        } else {
            throw new IllegalArgumentException("No data property found for index "+propIndex);
        }
    }
    
    @Override
    public List<String> getColumnNames() {
        return beanProperties.getColumnNames();
    }

    @Override
    public INestedRange<Integer> getRowTotalRange() throws IOException {
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
    public INestedRange<Integer> selectTotalRange() throws IOException {
        log.trace(">>> selectTotalRange");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            INestedRange<Integer> res = mapper.selectTotalRange();
            //NestedIntRange nir = (NestedIntRange)res;
            log.debug("range="+res);
            log.trace("<<< selectTotalRange");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }
    
    @Override
    public List<TestItemDTO> select(SQLParams prm) throws IOException {
        log.trace(">>> selectBE");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            List<TestItemDTO> res = mapper.select(prm);
            log.trace("<<< selectBE");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }

    @Override
    public TestItemDTO selectByPKey(BigInteger PKey) throws IOException {
        log.trace(">>> selectByPKey");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            TestItemDTO res = mapper.selectByPKey(PKey);
            log.trace("<<< selectByPKey");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }

    @Override
    public int insertRow(TestItemDTO item) throws IOException {
        log.trace(">>> insertRow");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            int res = mapper.insertRow(item);
            orm.commit();
            log.trace("<<< insertRow");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }

    @Override
    public int insertRowBySP(TestItemDTO item) throws IOException {
        log.trace(">>> insertRowBySP");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            int res = mapper.insertRowBySP(item);
            orm.commit();
            log.trace("<<< insertRowBySP");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }

    @Override
    public int insertRowBySP2(TestItemDTO item) throws IOException {
        log.trace(">>> insertRowBySP2");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            int res = mapper.insertRowBySP2(item);
            orm.commit();
            log.trace("<<< insertRowBySP2");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }

    @Override
    public int updateRow(TestItemDTO item) throws IOException {
        log.trace(">>> updateRow");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            int res = mapper.updateRow(item);
            orm.commit();
            log.trace("<<< updateRow");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }

    @Override
    public int deleteRow(TestItemDTO item) throws IOException {
        log.trace(">>> deleteRow");
        ORMFacade orm = new ORMFacade();
        try {
            TestItemMapper mapper = orm.getMapper(TestItemMapper.class);
            int res = mapper.deleteRow(item);
            orm.commit();
            log.trace("<<< deleteRow");
            return res;
        } catch (Exception e) {
            log.error(null, e);
            throw e;
        } finally {
            orm.closeDBSession();
        }
    }

}
