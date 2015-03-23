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

/*
http://mybatis.github.io/mybatis-3/
http://mybatis.github.io/generator/
http://blog.mybatis.org/p/products.html
*/

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.ibatis.exceptions.PersistenceException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ORMBackendConnector {
	
    protected static ILogger log = LogMgr.getLogger(ORMBackendConnector.class);
    private SqlSessionFactory sqlSessionFactory;

    public ORMBackendConnector (String configURI) throws IOException, PersistenceException {
        log.trace(">>> constructor");
        if ((configURI == null) || configURI.length() == 0) {
            //String fs = System.getProperty("file.separator"); //не работает для чтения из ресурсов
            String fs = "/";
            configURI = getClass().getPackage().getName().replace(".", fs).concat(fs).concat("orm-config.xml");
        }
        log.debug("configURI="+configURI);
        InputStream config = Resources.getResourceAsStream(configURI);
        log.debug("config ready");
        setSqlSessionFactory(new SqlSessionFactoryBuilder().build(config));
        log.debug("SqlSessionFactoryBuilder created");
    }	

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Configuration getConfiguration() {
        log.trace(">>> getConfiguration");
        if (sqlSessionFactory != null) {
            return sqlSessionFactory.getConfiguration(); 
        }
        else return null;
    }

    public SqlSession createDBSession(){
        log.trace(">>> createDBSession");
        if (sqlSessionFactory != null) { 
            //openSession(boolean autoCommit)
            return sqlSessionFactory.openSession(); 
        }
        else return null;
    }

    private void checkORMapperClass(Class mapperClass, Object dao) {
        //проверяем, реализует ли интерфейс mapperClass объект dao.
        //если да, то просто выходим. если нет, ругаемся
        log.trace(">>> checkMapperClass");
        Configuration conf = getConfiguration();
        //conf.getMapperRegistry().getMappers();
        if (conf != null) {
            if (mapperClass == null) {
                if (dao != null) {
                    // массив имплементированных интерфейсов. один из них - искомый интерфейс маппера для MyBatis
                    Class<?>[] itf = dao.getClass().getInterfaces();
                    log.debug("Interfaces.length="+itf.length);
                    for (int i=0; i < itf.length; i++) {
                        log.debug("Interfaces["+i+"]="+itf[i].getName());
                        if (conf.hasMapper(itf[i])) {
                            mapperClass = itf[i];
                            log.debug("mapperClass="+mapperClass.getName());
                            break;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Wrong parameter dao = NULL");
                }
            } else {
                if (! conf.hasMapper(mapperClass)) {
                    throw new IllegalArgumentException("Wrong mapperClass="+mapperClass.getName());
                } else {
                    log.debug("mapperClass="+mapperClass.getName());
                }
            }
        } else {
            log.debug("getConfiguration == null. checkMapperClass failed.");
        }
    }

    private boolean isCompatibleMap(ResultMap rm, Class beanClass) {
        // проверяем, соответствует ли указанный ResultMap классу объекта данных beanClass
        if ((rm != null) && (beanClass != null)) {
            return (beanClass == rm.getType());
        } else return false;
    }

    public List<BeanPropertyMapping> getBeanPropertiesMapping(Class beanClass) {
        log.trace(">>> getDAOProperties()");
        List<BeanPropertyMapping> DAOprops = new ArrayList<BeanPropertyMapping>();
        Configuration conf = getConfiguration();
        if (conf != null) {
            //
            //checkDAOMapperClass(mapperClass, dao);
            Collection<ResultMap> rmc = conf.getResultMaps();
            //log.debug("ResultMaps size="+rmc.size());
            Iterator<ResultMap> rmci = rmc.iterator();
            while (rmci.hasNext()) {
                ResultMap rm = rmci.next();
                // ID=com.example.server.dao.UserMapper.UserMap, Type=com.example.server.dao.UserDTO
                log.debug("ResultMap ID="+rm.getId()+", Type="+rm.getType().getName());
                // проверяем, соответствует ли указанный ResultMap классу объекта данных beanClass
                if (isCompatibleMap(rm, beanClass)) {
                    List<ResultMapping> r = rm.getResultMappings();
                    Iterator<ResultMapping> i2 = r.iterator();
                    while (i2.hasNext()) {
                        ResultMapping r2 = i2.next();
                        // добавляем свойство (столбец) в контейнер. 
                        // в качестве идентификатора свойста используем имя свойства объекта данных 
                        DAOprops.add(new BeanPropertyMapping(r2.getProperty(), r2.getColumn(), r2.getJavaType()));
                        log.debug("Column="+r2.getColumn()+", Property="+r2.getProperty()+", JavaType="+r2.getJavaType());
                    }
                    break; // обрабатываем только первую совместимую карту, остальные игнорируем
                }
            }
        }
        return DAOprops;
    }
    
}
