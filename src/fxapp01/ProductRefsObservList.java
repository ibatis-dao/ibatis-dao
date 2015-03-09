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

    public ProductRefsObservList() {
        log.trace(">>> constructor");
        this.changeListeners = new ArrayList<>();
        this.invListeners = new ArrayList<>();
        this.data = new ArrayList<>();
        this.dataFacade = FXCollections.observableList(data);
        this.dao = new ProductRefsDAO();
        IntRange aRange = new IntRange(1, 50);
        List<ProductRefs> l = dao.select(aRange);
        data.addAll(l);
        log.trace("<<< constructor");
    }
    
    public List<String> getColumnNames() {
        return dao.getContainerProperties().getColumnNames();
    }

    /******************* javafx.collections.ObservableList *******************/
    @Override
    public boolean addAll(ProductRefs[] elements) {
        return dataFacade.addAll(elements);
    }

    @Override
    public boolean setAll(ProductRefs[] elements) {
        return dataFacade.setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends ProductRefs> col) {
        return dataFacade.setAll(col);
    }

    @Override
    public boolean removeAll(ProductRefs[] elements) {
        return dataFacade.removeAll(elements);
    }

    @Override
    public boolean retainAll(ProductRefs[] elements) {
        return dataFacade.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        dataFacade.remove(from, to);
    }

    @Override
    public void addListener(ListChangeListener<? super ProductRefs> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        changeListeners.add(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super ProductRefs> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        changeListeners.remove(listener);
    }

    /******************* java.util.List *******************/
    @Override
    public int size() {
        return dataFacade.size();
    }

    @Override
    public boolean isEmpty() {
        return dataFacade.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return dataFacade.contains(o);
    }

    @Override
    public Iterator<ProductRefs> iterator() {
        return dataFacade.iterator();
    }

    @Override
    public Object[] toArray() {
        return dataFacade.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return dataFacade.toArray(a);
    }

    @Override
    public boolean add(ProductRefs e) {
        return dataFacade.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return dataFacade.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return dataFacade.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends ProductRefs> c) {
        return dataFacade.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends ProductRefs> c) {
        return dataFacade.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return dataFacade.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return dataFacade.retainAll(c);
    }

    @Override
    public void clear() {
        dataFacade.clear();
    }

    @Override
    public ProductRefs get(int index) {
        return dataFacade.get(index);
    }

    @Override
    public ProductRefs set(int index, ProductRefs element) {
        return dataFacade.set(index, element);
    }

    @Override
    public void add(int index, ProductRefs element) {
        dataFacade.add(index, element);
    }

    @Override
    public ProductRefs remove(int index) {
        return dataFacade.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return dataFacade.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return dataFacade.lastIndexOf(o);
    }

    @Override
    public ListIterator<ProductRefs> listIterator() {
        return dataFacade.listIterator();
    }

    @Override
    public ListIterator<ProductRefs> listIterator(int index) {
        return dataFacade.listIterator(index);
    }

    @Override
    public List<ProductRefs> subList(int fromIndex, int toIndex) {
        return dataFacade.subList(fromIndex, toIndex);
    }

    /******************* javafx.beans.Observable *******************/
    @Override
    public void addListener(InvalidationListener listener) {
        invListeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invListeners.remove(listener);
    }
    
}
