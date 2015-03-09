package fxapp01;

import fxapp01.dao.DataCacheReadOnly;
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
import fxapp01.excpt.ENullArgument;
import fxapp01.excpt.EUnsupported;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author serg
 */
public class ProductRefsObservList implements ObservableList<ProductRefs>{
    
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
        this.cache = new DataCacheReadOnly<>(20, 300);
        this.dataFacade = FXCollections.observableList(cache);
        this.dao = new ProductRefsDAO();
        List<ProductRefs> l = dao.select(cache.getRange());
        this.cache.addAll(l);
        log.trace("<<< constructor");
    }
    
    public List<String> getColumnNames() {
        return dao.getContainerProperties().getColumnNames();
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
        log.trace(">>> get(index="+index+")");
        //проверяем, находится ли строка в пределах диапазона
        if (cache.containsIndex(index)) {
            //если да, то возвращаем значение из этой строки
            return cache.get(index);
        } else {
            log.debug("Out of cache. Try find new range");
            //если строка за пределами диапазона
            //проверяем прилегающий диапазон слева
            IntRange l = cache.getRange().Shift(- cache.getDefSize());
            if (l.IsInbound(index)) {
                //если строка входит в диапазон слева, получаем данные для него
                log.debug("Shift cache to left");
                requestDataPage(l);
            } else {
                //проверяем прилегающий диапазон справа
                IntRange r = cache.getRange().Shift(cache.getDefSize());
                if (r.IsInbound(index)) {
                    log.debug("Shift cache to right");
                    //если строка входит в диапазон справа, получаем данные для него
                    requestDataPage(r);
                } else {
                    log.debug("Cache shift failed. Create new range");
                    //строка находится за пределами прилегающих диапазонов
                    //сместим его в новое место
                    IntRange n = new IntRange(index, cache.getRange().getLength());
                    requestDataPage(n);
                }
            }
        }
        log.debug("Cache ranging finshed. Check asserts about range");
        //проверяем, находится ли теперь строка в пределах диапазона
        assert(cache.containsIndex(index));
        //возвращаем значение из этой строки
        return cache.get(index);
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
            throw new ENullArgument("requestDataPage");
        }
        // вычисляем новую порцию данных дя загрузки
        IntRange aRange;
        //если диапазоны пересекаются
        if (cache.getRange().IsOverlapped(aRowsRange)) {
            log.debug("cache range overlapped");
            //загружаем только новую порцию данных. ту часть, что уже есть, не загружаем
            aRange = aRowsRange.Sub(cache.getRange());
        } else {
            log.debug("cache range not overlapped");
            // если диапазоны не пересекаются, вычислим диапазон, включающий оба 
            aRange = aRowsRange.Add(cache.getRange());
            //а затем вычтем из него исходный
            aRange = cache.getRange().Sub(aRange);
        }
        //ограничим длину запрашиваемого диапазона
        aRange.setLength(Math.min(aRange.getLength(), cache.getMaxSize()));
        log.debug("define new cache range. first="+aRange.getFirst()+", length="+aRange.getLength());
        // фактически запрашиваем данные для вычисленного диапазона
        throw new EUnsupported();
        /*
        List<ProductRefs> l = dao.select(aRange);
        //смотрим, куда добавлять полученные данные - в начало кеша или в конец
        if (aRange.getFirst() <= cache.getRange().getFirst()) {
            //добавляем в начало
            log.debug("insert into start. Range.first<= cache.first.");
            cache.addAll(1, l);
        } else {
            //добавляем в конец
            log.debug("add at end. Range.first<= cache.first.");
            cache.addAll(l);
        }
        log.trace("<<< requestDataPage");
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
