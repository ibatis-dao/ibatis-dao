package fxapp01;

import fxapp01.dao.ProductRefsDAO;
import fxapp01.dto.IntRange;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import fxapp01.dto.ProductRefs;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author serg
 */
public class ProductRefsObservList implements ObservableList<ProductRefs>{
    
    private static final ILogger log = LogMgr.getLogger(ProductRefsObservList.class);
    private final List<ProductRefs> data;
    private final ObservableList<ProductRefs> dataFacade;
    private final ProductRefsDAO dao;
    private final List<ListChangeListener<? super ProductRefs>> changeListeners;
    private final List<InvalidationListener> invListeners;
    // фактическое начало (порядковый номер первой строки) и фактический размер 
    // окна данных в рамках источника данных. 
    private final IntRange cacheRowsRange; 
    // базовый размер окна 
    private int dataPageBaseSize;
    // размер кеша данных относительно размера окна данных
    private Double dataCacheFactor; 

    public ProductRefsObservList() {
        log.trace(">>> constructor");
        this.changeListeners = new ArrayList<>();
        this.invListeners = new ArrayList<>();
        this.data = new ArrayList<>();
        this.dataFacade = FXCollections.observableList(data);
        this.dao = new ProductRefsDAO();
        this.dataCacheFactor = 3.0; //default cache factor
        this.cacheRowsRange = new IntRange(1, 50);
        List<ProductRefs> l = dao.select(cacheRowsRange);
        this.data.addAll(l);
        log.trace("<<< constructor");
    }
    
    public List<String> getColumnNames() {
        return dao.getContainerProperties().getColumnNames();
    }

    private int calcDataPageSize() {
        return (int)Math.floor(dataPageBaseSize * dataCacheFactor);
    }
 
    public void requestDataPage(IntRange aRowsRange) {
        log.trace(">>> requestDataPage");
        /*
        * если ранее загруженная и запрошенная сейчас страницы пересекаются 
        * (имеют общий диапазон), загружаем только ту часть запрошенной страницы, 
        * которая выходит за рамки ранее загруженной и добавляем её в начало или 
        * в конец кеша - в зависимости от того, с какой стороны находится запрошенная
        * страница по отношению к ранее загруженной
        */
        if (aRowsRange == null) {
            throw new IllegalArgumentException("Method "+getClass().getName()+".requestDataPage() arguments must be not null");
        }
        // вычисляем новую порцию данных дя загрузки
        IntRange aRange;
        //если диапазоны пересекаются
        if (cacheRowsRange.IsOverlapped(aRowsRange)) {
            //загружаем только новую порцию данных. ту часть, что уже есть, не загружаем
            aRange = aRowsRange.Sub(cacheRowsRange);
        } else {
            // если диапазоны не пересекаются, вычислим диапазон, включающий оба 
            aRange = aRowsRange.Add(cacheRowsRange);
            //а затем вычтем из него исходный
            aRange = aRange.Sub(cacheRowsRange);
        }
        //ограничим длину запрашиваемого диапазона
        aRange.setLength(Math.min(aRange.getLength(), calcDataPageSize()));
        // фактически запрашиваем данные для вычисленного диапазона
        List<ProductRefs> l = dao.select(aRange);
        //смотрим, куда добавлять полученные данные - в начало кеша или в конец
        if (aRange.getFirst() <= cacheRowsRange.getFirst()) {
            //добавляем в начало
            l.addAll(data);
            data.clear();
            data.addAll(l);
        } else {
            //добавляем в конец
            data.addAll(l);
        }
        log.trace("<<< requestDataPage");
    }

    /******************* javafx.collections.ObservableList *******************/
    @Override
    public boolean addAll(ProductRefs[] elements) {
        log.trace(">>> addAll");
        return dataFacade.addAll(elements);
    }

    @Override
    public boolean setAll(ProductRefs[] elements) {
        log.trace(">>> setAll(elements[])");
        return dataFacade.setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends ProductRefs> col) {
        log.trace(">>> setAll(Collection)");
        return dataFacade.setAll(col);
    }

    @Override
    public boolean removeAll(ProductRefs[] elements) {
        log.trace(">>> setAll(elements[])");
        return dataFacade.removeAll(elements);
    }

    @Override
    public boolean retainAll(ProductRefs[] elements) {
        log.trace(">>> retainAll(elements[])");
        return dataFacade.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        log.trace(">>> remove(from, to)");
        dataFacade.remove(from, to);
    }

    @Override
    public void addListener(ListChangeListener<? super ProductRefs> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> addListener(ListChangeListener)");
        changeListeners.add(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super ProductRefs> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> removeListener(ListChangeListener)");
        changeListeners.remove(listener);
    }

    /******************* java.util.List *******************/
    @Override
    public int size() {
        //**********************************************************************
        int sz = dataFacade.size();
        int rc = dao.getRowCount();
        log.trace(">>> size="+sz+", RowCount="+rc);
        //return dataFacade.size();
        return rc;
    }

    @Override
    public boolean isEmpty() {
        log.trace(">>> isEmpty");
        return dataFacade.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        log.trace(">>> contains(Object)");
        return dataFacade.contains(o);
    }

    @Override
    public Iterator<ProductRefs> iterator() {
        log.trace(">>> iterator");
        return dataFacade.iterator();
    }

    @Override
    public Object[] toArray() {
        log.trace(">>> toArray");
        return dataFacade.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        log.trace(">>> toArray(T[] a)");
        return dataFacade.toArray(a);
    }

    @Override
    public boolean add(ProductRefs e) {
        log.trace(">>> add(ProductRefs)");
        return dataFacade.add(e);
    }

    @Override
    public boolean remove(Object o) {
        log.trace(">>> remove(Object)");
        return dataFacade.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        log.trace(">>> containsAll(Collection)");
        return dataFacade.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends ProductRefs> c) {
        log.trace(">>> addAll(Collection)");
        return dataFacade.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends ProductRefs> c) {
        log.trace(">>> addAll(index, Collection)");
        return dataFacade.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        log.trace(">>> removeAll(Collection)");
        return dataFacade.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        log.trace(">>> retainAll(Collection)");
        return dataFacade.retainAll(c);
    }

    @Override
    public void clear() {
        log.trace(">>> clear");
        dataFacade.clear();
    }

    @Override
    public ProductRefs get(int index) {
        //********************************************************
        //TODO сделать скользящий кеш
        log.trace(">>> get(index="+index+")");
        return dataFacade.get(index);
        /*
        //проверяем, находится ли строка в пределах диапазона
        if (cacheRowsRange.IsInbound(index)) {
            //если да, то возвращаем значение из этой строки
            return dataFacade.get(index);
        } else {
            //если строка за пределами диапазона
            //проверяем прилегающий диапазон слева
            IntRange l = cacheRowsRange.Shift(- dataPageBaseSize);
            if (l.IsInbound(index)) {
                //если строка входит в диапазон слева, получаем данные для него
                requestDataPage(l);
            } else {
                //проверяем прилегающий диапазон справа
                IntRange r = cacheRowsRange.Shift(dataPageBaseSize);
                if (r.IsInbound(index)) {
                    //если строка входит в диапазон справа, получаем данные для него
                    requestDataPage(r);
                } else {
                    //строка находится за пределами прилегающих диапазонов
                    //сместим его в новое место
                    IntRange n = new IntRange(index, cacheRowsRange.getLength());
                    requestDataPage(n);
                }
            }
        }
        //проверяем, находится ли теперь строка в пределах диапазона
        assert(cacheRowsRange.IsInbound(index));
        //возвращаем значение из этой строки
        return dataFacade.get(index);
        */
    }

    @Override
    public ProductRefs set(int index, ProductRefs element) {
        log.trace(">>> set(index, element)");
        return dataFacade.set(index, element);
    }

    @Override
    public void add(int index, ProductRefs element) {
        log.trace(">>> add(index, element)");
        dataFacade.add(index, element);
    }

    @Override
    public ProductRefs remove(int index) {
        log.trace(">>> remove(index)");
        return dataFacade.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        log.trace(">>> indexOf(Object)");
        return dataFacade.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        log.trace(">>> lastIndexOf(Object)");
        return dataFacade.lastIndexOf(o);
    }

    @Override
    public ListIterator<ProductRefs> listIterator() {
        log.trace(">>> listIterator");
        return dataFacade.listIterator();
    }

    @Override
    public ListIterator<ProductRefs> listIterator(int index) {
        log.trace(">>> listIterator(index)");
        return dataFacade.listIterator(index);
    }

    @Override
    public List<ProductRefs> subList(int fromIndex, int toIndex) {
        log.trace(">>> subList(fromIndex, toIndex)");
        return dataFacade.subList(fromIndex, toIndex);
    }

    /******************* javafx.beans.Observable *******************/
    @Override
    public void addListener(InvalidationListener listener) {
        log.trace(">>> addListener(InvalidationListener)");
        invListeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        log.trace(">>> removeListener(InvalidationListener)");
        invListeners.remove(listener);
    }
    
}
