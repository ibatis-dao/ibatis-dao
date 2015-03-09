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
import fxapp01.dao.ProductRefsDAO;
import fxapp01.dto.INestedRange;
import fxapp01.dto.ProductRefs;
import fxapp01.dto.LimitedIntRange;
import fxapp01.dto.NestedIntRange;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;

/**
 * data model for swing JTable
 * ProductRefsTblMdl
 */
public class ProductRefsTblMdl extends AbstractTableModel {
    
    private static final ILogger log = LogMgr.getLogger(ProductRefsTblMdl.class);
    private static ObservableList<BarChart.Series> bcData;
    
    private final ProductRefsDAO dao;
    private final ObservableList<ProductRefs> data = FXCollections.observableArrayList();
    //TODO modified data cache
    
    // фактическое начало (порядковый номер первой строки) и фактический размер 
    // окна данных в рамках источника данных. 
    private final INestedRange<Integer> outerLimits; 
    private final INestedRange<Integer> cacheRowsRange; 
    // базовый размер окна 
    private int baseDataPageSize;
    // размер кеша данных относительно размера окна данных
    private Double dataCacheFactor; 

    public ProductRefsTblMdl() throws IOException {
        dao = new ProductRefsDAO();
        dataCacheFactor = 3.0; //defaul cache factor
        outerLimits = new NestedIntRange(1, Integer.MAX_VALUE, null); 
        cacheRowsRange = new NestedIntRange(1, 100, outerLimits); //default data window start and size
    }
    
    public double getTickUnit() {
        return 1000; //для шкалы графика
    }

    public List<String> getColumnNames() {
        return dao.getContainerProperties().getColumnNames();
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
        List<ProductRefs> l = dao.select(aRowsRange);
        //смотрим, куда добавлять полученные данные - в начало кеша или в конец
        if (aRowsRange.getFirst() <= cacheRowsRange.getFirst()) {
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
            //если да, то возвращаем значение из этой строки
            return dao.getBeanProperty(row, column);
        } else {
            //если строка за пределами диапазона
            //проверяем прилегающий диапазон слева
            INestedRange l = cacheRowsRange.Shift(- baseDataPageSize);
            if (l.IsInbound(row)) {
                //если строка входит в диапазон слева, получаем данные для него
                requestDataPage(l);
            } else {
                //проверяем прилегающий диапазон справа
                INestedRange r = cacheRowsRange.Shift(baseDataPageSize);
                if (r.IsInbound(row)) {
                    //если строка входит в диапазон справа, получаем данные для него
                    requestDataPage(r);
                } else {
                    //строка находится за пределами прилегающих диапазонов
                    //сместим его в новое место
                    INestedRange n = cacheRowsRange.Complement(row);
                    requestDataPage(n);
                }
            }
        }
        //проверяем, находится ли теперь строка в пределах диапазона
        assert(cacheRowsRange.IsInbound(row));
        //возвращаем значение из этой строки
        return dao.getBeanProperty(row, column);
    }

    @Override
    public int getRowCount() {
        int rc = dao.getRowCount();
        outerLimits.setLength(rc);
        return rc;
    }

    @Override
    public int getColumnCount() {
        return dao.getContainerProperties().getSize();
    }

    @Override
    public String getColumnName(int column) {
        return dao.getContainerProperties().get(column).getColumn();
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
        List<ProductRefs> l = dao.select(new NestedIntRange(row, 1, outerLimits));
        ProductRefs rowData = l.get(1);
        dao.setBeanProperty(rowData, column, value);
        fireTableCellUpdated(row, column);
    }

    public ObservableList<BarChart.Series> getBarChartData() {
        if (bcData == null) {
            bcData = FXCollections.<BarChart.Series>observableArrayList();
            for (int row = 0; row < getRowCount(); row++) {
                ObservableList<BarChart.Data> series = FXCollections.<BarChart.Data>observableArrayList();
                for (int column = 0; column < getColumnCount(); column++) {
                    series.add(new BarChart.Data(getColumnName(column), getValueAt(row, column)));
                }
                bcData.add(new BarChart.Series(series));
            }
        }
        return bcData;
    }

}
