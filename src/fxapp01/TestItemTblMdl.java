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

/*
 * data model for swing JTable
 */

package fxapp01;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import fxapp01.dao.TestItemDAO;
import fxapp01.dto.INestedRange;
import fxapp01.dto.TestItemDTO;
import fxapp01.dto.NestedIntRange;
import fxapp01.dto.SQLParams;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * data model for swing JTable
 TestItemTblMdl
 */
public class TestItemTblMdl extends AbstractTableModel {
    
    private static final ILogger log = LogMgr.getLogger(TestItemTblMdl.class);
    private static ObservableList<BarChart.Series> bcData;
    
    private final TestItemDAO dao;
    private final ObservableList<TestItemDTO> data = FXCollections.observableArrayList();
    //TODO modified data cache
    
    // фактическое начало (порядковый номер первой строки) и фактический размер 
    // окна данных в рамках источника данных. 
    private final NestedIntRange outerLimits; 
    private final NestedIntRange cacheRowsRange; 
    // базовый размер окна 
    private int baseDataPageSize;
    // размер кеша данных относительно размера окна данных
    private Double dataCacheFactor; 

    public TestItemTblMdl() throws IOException, IntrospectionException {
        dao = new TestItemDAO();
        dataCacheFactor = 3.0; //defaul cache factor
        outerLimits = new NestedIntRange(1, Integer.MAX_VALUE, null); 
        cacheRowsRange = new NestedIntRange(1, 100, outerLimits); //default data window start and size
    }
    
    public double getTickUnit() {
        return 1000; //для шкалы графика
    }

    public List<String> getColumnNames() {
        return dao.getColumnNames();
    }

    public int getDataPageStart() {
        return cacheRowsRange.getFirst();
    }

    public void setDataPageStart(int dataPageStart) {
        cacheRowsRange.setFirst(dataPageStart);
    }

    /**
     * @return the pageSize
     */
    public int getDataPageSize() {
        return cacheRowsRange.getLength();
    }

    /**
     * @return the pageSize
     */
    public int getBaseDataPageSize() {
        return baseDataPageSize;
    }

    /**
     * @param dataPageSize the pageSize to set
     */
    public void setBaseDataPageSize(int dataPageSize) {
        this.baseDataPageSize = dataPageSize;
        cacheRowsRange.setLength((int)Math.floor(dataPageSize * dataCacheFactor));
    }

    public Double getDataCacheFactor() {
        return dataCacheFactor;
    }

    public void setDataCacheFactor(Double dataCacheFactor) {
        this.dataCacheFactor = dataCacheFactor;
        cacheRowsRange.setLength(calcDataPageSize());
    }
    
    private int calcDataPageSize() {
        return (int)Math.floor(baseDataPageSize * dataCacheFactor);
    }
 
    private void ThrowNullArg(String methodName) {
        throw new IllegalArgumentException("Method "+getClass().getName()+"."+methodName+"() arguments must be not null");
    }
    
    public void requestDataPage(INestedRange<Integer> aRowsRange) {
        log.trace(">>> requestDataPage");
        /*
        * если ранее загруженная и запрошенная сейчас страницы пересекаются 
        * (имеют общий диапазон), загружаем только ту часть запрошенной страницы, 
        * которая выходит за рамки ранее загруженной и добавляем её в начало или 
        * в конец кеша - в зависимости от того, с какой стороны находится запрошенная
        * страница по отношению к ранее загруженной
        */
        if (aRowsRange == null) {
            ThrowNullArg("requestDataPage");
        }
        //ограничим длину запрашиваемого диапазона
        aRowsRange.setLength(Math.min(aRowsRange.getLength(), calcDataPageSize()));
        // фактически запрашиваем данные для вычисленного диапазона
        List<TestItemDTO> l;
        try {
            l = dao.select(new SQLParams<>(aRowsRange));
        } catch (IOException ex) {
            log.error(null, ex);
            l = new ArrayList<>();
        }
        //смотрим, куда добавлять полученные данные - в начало кеша или в конец
        if (aRowsRange.compareXandY(aRowsRange.getFirst(), cacheRowsRange.getFirst()) <= 0) {
            //добавляем в начало
            l.addAll(data);
            data.setAll(l);
        } else {
            //добавляем в конец
            data.addAll(l);
        }
        log.trace("<<< requestDataPage");
    }

    private void releaseData() {
        
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        //проверяем, находится ли строка в пределах диапазона
        if (cacheRowsRange.IsInbound(row)) {
            try {
                //если да, то возвращаем значение из этой строки
                return dao.getBeanPropertyValue(row, column);
            } catch (InvocationTargetException | IllegalAccessException ex) {
                log.error(null, ex);
                return null;
            }
        } else {
            //если строка за пределами диапазона
            //проверяем прилегающий диапазон слева
            INestedRange<Integer> l = cacheRowsRange.Shift(- baseDataPageSize);
            if (l.IsInbound(row)) {
                //если строка входит в диапазон слева, получаем данные для него
                requestDataPage(l);
            } else {
                //проверяем прилегающий диапазон справа
                INestedRange<Integer> r = cacheRowsRange.Shift(baseDataPageSize);
                if (r.IsInbound(row)) {
                    //если строка входит в диапазон справа, получаем данные для него
                    requestDataPage(r);
                } else {
                    //строка находится за пределами прилегающих диапазонов
                    //сместим его в новое место
                    INestedRange<Integer> n = cacheRowsRange.Complement(row);
                    requestDataPage(n);
                }
            }
        }
        //проверяем, находится ли теперь строка в пределах диапазона
        assert(cacheRowsRange.IsInbound(row));
        try {
            //возвращаем значение из этой строки
            return dao.getBeanPropertyValue(row, column);
        } catch (InvocationTargetException | IllegalAccessException ex) {
            log.error(null, ex);
            return null;
        }
    }

    @Override
    public int getRowCount() {
        int rc;
        try {
            rc = dao.getRowTotalRange().getLength().intValue();
        } catch (IOException ex) {
            log.error(null, ex);
            rc = 0;
        }
        outerLimits.setLength(rc);
        return rc;
    }

    @Override
    public int getColumnCount() {
        return dao.getColumnNames().size();
    }

    @Override
    public String getColumnName(int column) {
        return dao.getColumnNames().get(column).toString();
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        List<TestItemDTO> l;
        try {
            
            l = dao.select(new SQLParams<>(new NestedIntRange(row, 1, outerLimits)));
        } catch (IOException ex) {
            log.error(null, ex);
            l = new ArrayList<>();
        }
        TestItemDTO rowData = l.get(1);
        try {
            dao.setBeanPropertyValue(rowData, column, value);
        } catch (InvocationTargetException | IllegalAccessException ex) {
            log.error(null, ex);
        }
        fireTableCellUpdated(row, column);
    }

    public ObservableList<BarChart.Series> getBarChartData() {
        if (bcData == null) {
            bcData = FXCollections.<BarChart.Series>observableArrayList();
            for (int row = 0; row < getRowCount(); row++) {
                ObservableList<BarChart.Data<String,Object>> series = FXCollections.<BarChart.Data<String,Object>>observableArrayList();
                for (int column = 0; column < getColumnCount(); column++) {
                    series.add(new BarChart.Data<String,Object>(getColumnName(column), getValueAt(row, column)));
                }
                bcData.add(new BarChart.Series<>(series));
            }
        }
        return bcData;
    }

}
