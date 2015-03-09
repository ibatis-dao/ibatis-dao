package fxapp01;

import fxapp01.dao.DataCacheReadOnly;
import fxapp01.dao.IDataRangeFetcher;
import fxapp01.dao.ProductRefsDAO;
import fxapp01.dto.LimitedIntRange;
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
import fxapp01.excpt.ENullArgument;
import fxapp01.excpt.EUnsupported;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author serg
 */
public class ProductRefsObservList implements ObservableList<ProductRefs>, IDataRangeFetcher {
    
    private static final ILogger log = LogMgr.getLogger(ProductRefsObservList.class);
    private final DataCacheReadOnly<ProductRefs> cache;
    private final ObservableList<ProductRefs> dataFacade;
    private final ProductRefsDAO dao;
    private final List<ListChangeListener<? super ProductRefs>> changeListeners;
    private final List<InvalidationListener> invListeners;

    public ProductRefsObservList() {
        log.trace(">>> constructor");
        this.changeListeners = new ArrayList<>();
        this.invListeners = new ArrayList<>();
        log.debug("before new DataCacheReadOnly");
        IDataRangeFetcher dps = this; // 
        this.cache = new DataCacheReadOnly<>(dps, 20, 40);
        log.debug("before FXCollections.observableList");
        this.dataFacade = FXCollections.observableList(cache);
        log.debug("before new ProductRefsDAO");
        this.dao = new ProductRefsDAO();
        /*
        List<ProductRefs> l = dao.select();
        this.cache.addAll(l);
        */
        log.debug("before requestDataPage");
        LimitedIntRange initRange = cache.getRange().clone();
        initRange.setLength(20);
        dps.fetch(initRange, 0);
        log.trace("<<< constructor");
    }
    
    public List<String> getColumnNames() {
        return dao.getContainerProperties().getColumnNames();
    }
    
    public void debugPrintAll() {
        cache.debugPrintAll();
    }

    /******************* javafx.collections.ObservableList *******************/
    
    /**
     * javafx.collections.ObservableList
     * @param elements
     * @return 
     */
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
        //log.trace(">>> get(index="+index+")");
        //проверяем, находится ли теперь строка в пределах диапазона
        //assert(cache.containsIndex(index));
        //возвращаем значение из этой строки
        return cache.get(index);
    }

    @Override
    public void fetch(LimitedIntRange aRowsRange, int pos) {
    /* мета-описание логики работы:
    1. проверяем, есть ли в кеше данные (первоначальная загрузка)
    если данных нет, а запрошенный диапазон равен дипазону кеша, то считаем, что это первая загрузка
    загружаем данные и корректируем дипазон в соответствии с фактически загруженным кол-вом строк
    корректировка нужна, чтобы: 
        1.учесть особенности нумерации строк в разных БД, (н-р, в postgres offset 
        начинается с 0, в oracle rownum начинается с 1)
        2.учесть вероятность того, что из БД будут возвращено не то кол-во записей, 
        которое изначально предполагалось, поскольку записи могут быть добавлены/удалены 
        другими пользователями и кол-во строк в таблице изменится
    2. если ранее загруженная и запрошенная сейчас страницы пересекаются 
    (имеют общий диапазон), загружаем только ту часть запрошенной страницы, 
    которая выходит за рамки ранее загруженной. корректируем диапазон в соответствии 
    с фактически загруженным кол-вом. проверяем, не превышен ли размер кеша и 
    удаляем лишние данные. помещаем загруженные данные в начало или в конец кеша - 
    в зависимости от того, с какой стороны находится запрошенная страница по отношению 
    к ранее загруженной.
    3. если запрошенная страница не пересекается с текущей.
    смотрим, как далеко она находится. если расстояние от текущей до запрошенной 
    не превышает максимальный размер кеша, загружаем весь диапазон. корректируем 
    дипазон под кол-во фактически загруженных строк. добавляем в кеш.
    4. если расстояние от текущей до запрошенной страницы превышает максимальный 
    размер кеша, очищаем текущий кеш и загружаем данные запрошенного диапазона, 
    как при первоначальной загрузке
    */
        log.trace(">>> fetch(aRowsRange="+aRowsRange+"), pos="+pos+")");
        if (aRowsRange == null) {
            throw new ENullArgument("fetch");
        }
        List<ProductRefs> l = dao.select(aRowsRange);
        cache.addAll(pos, l);
        log.debug("cache.size="+cache.size());
        log.trace("<<< fetch");
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
    
    /**
     * javafx.beans.Observable
     * @param listener
     */
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
