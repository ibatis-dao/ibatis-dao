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

import fxapp01.dao.filter.ISqlFilterable;
import fxapp01.dao.sort.IDAOSortOrder;
import fxapp01.dao.sort.SortOrder;
import fxapp01.dto.INestedRange;
import fxapp01.dto.SQLParams;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
//import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Адаптер источника данных для табличного представления (например, в javafx TableView).
 * Основное назначение - диспетчеризация и согласование вызовов между потребителем 
 * данных (TableView), кешем данных (DataCacheRolling) и объектом доступа к данным (IDAO).
 * Класс реализует интерфейс ObservableList и делегирует все обращения к данным извне 
 * к плавающему кешу данных DataCacheRolling. При наличии данных в кеше они 
 * извлекаются оттуда. При отсутствии в кеше данных кеш обращается с запросом на 
 * загрузку необходимых данных к данному классу, который в свою очередь делегирует 
 * вызов объекту доступа к данным IDAO.
 * При делегировании вызовов учитывается, что пространства номеров строк в 
 * потребителе (TableView), кеше данных (DataCacheRolling) и объекте доступа к 
 * данным (IDAO) отличаются и требуют согласования между собой. 
 * Данный класс реализует интерфейс List, который предполагает нумерацию строк 
 * с 0 до size()-1 во всех методах, кроме методов, реализующих интерфейс IDataRangeFetcher.
 * Нумерация строк в кеше и объекте доступа к данным зависит от подключенной БД 
 * и ограничена диапазоном, который можно получить вызовом метода IDAO.getRowTotalRange().
 * @author serg
 * @param <DTOclass> - класс объекта, представляющего строку данных
 */
public class DataList<DTOclass> implements IHasData<DTOclass> {
    
    protected final ILogger log = LogMgr.getLogger(this.getClass());
    private final DataCacheRolling<DTOclass> cache;
    private final ObservableList<DTOclass> dataFacade;
    private final IDAO dao;
    //private final List<ListChangeListener<? super DTOclass>> changeListeners;
    //private final List<InvalidationListener> invListeners;
    private IDAOSortOrder sortOrder;
    private ISqlFilterable filter;

    public DataList(IDAO dao) throws IOException {
        log.trace(">>> constructor");
        //this.changeListeners = new ArrayList<>();
        //this.invListeners = new ArrayList<>();
        this.dao = dao;
        this.sortOrder = new SortOrder();
        this.filter = null;
        log.debug("before new DataCacheRolling");
        IDataRangeFetcher dps = this; // 
        //TODO желательно минимальный и максимальный диапазон окна определять 
        //по размеру видимой страницы данных на клиенте
        this.cache = new DataCacheRolling<>(dps, 20, 40);
        log.debug("before FXCollections.observableList");
        this.dataFacade = FXCollections.observableList(cache);
        log.debug("make initRange");
        cache.getRange().setLength(20);
        log.debug("before refresh");
        refresh();
        log.trace("<<< constructor");
    }
    
    public List<String> getColumnNames() {
        return dao.getColumnNames();
    }
    
    public void debugPrintAll() {
        cache.debugPrintAll();
    }
    
    private int toListIndex(int dataRowNo){
        return dataRowNo-cache.getLeftLimit();
    }
    
    /*
    * индекс List всегда начинается от 0. Индекс данных начинается от нижней границы диапазона данных
    * конвертируем индекс списка в номер строки диапазона
    */
    private int toDataRowNo(int listIndex){
        return listIndex+cache.getLeftLimit();
    }
    
/*
    private void fireInvalidationEvent() {
        log.debug("invListeners.size="+dataFacade..size());
        for (InvalidationListener il : invListeners) {
            log.debug("il="+il);
            if (il != null) {
                il.invalidated(dataFacade);
            }
        }
    }
    
    private void fireChangeEvent() {
        log.debug("changeListeners.size="+changeListeners.size());
        for (ListChangeListener<? super DTOclass> cl : changeListeners) {
            log.debug("cl="+cl);
            if (cl != null) {
                ListChangeListener.Change<? extends DTOclass> c;
                cl.onChanged(c);
            }
        }
    }
*/
    
    public void refresh() {
        log.trace("refresh");
        cache.refresh();
        log.debug("size="+size());
        //debugPrintAll();
    }
    
    // ******************* IDataRangeFetcher *******************

    /*
     * @return 
     * @throws java.io.IOException 
    */
    @Override
    public INestedRange getRowTotalRange() throws IOException{
        return dao.getRowTotalRange();
    }
    
    /**
     * запрашивает указанный диапазон адресов (номеров строк aRowsRange) у 
     * источника данных и помещает полученные данные в кеш по адресу pos.
     * @param aRowsRange - диапазон строк, который нужно запросить у источника данных
     * @return 
     */
    @Override
    public List<DTOclass> fetch(INestedRange aRowsRange) {
        log.trace(">>> fetch(aRowsRange="+aRowsRange+")");
        if (aRowsRange == null) {
            throw new ENullArgument("fetch");
        }
        List<DTOclass> l;
        try {
            SQLParams qep = new SQLParams(aRowsRange, getSortOrder(), getFilter());
            l = dao.select(qep);
        } catch (IOException ex) {
            //TODO прятать проблемы нехорошо
            log.error(null, ex);
            l = new ArrayList<>();
        }
        log.trace("<<< fetch");
        return l;
    }

    // ******************* javafx.collections.ObservableList *******************
    
    /**
     * @param elements
     * @return 
     */
    @Override
    public boolean addAll(DTOclass[] elements) {
        log.trace(">>> addAll");
        return dataFacade.addAll(elements);
    }

    @Override
    public boolean setAll(DTOclass[] elements) {
        log.trace(">>> setAll(elements[])");
        return dataFacade.setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends DTOclass> col) {
        log.trace(">>> setAll(Collection)");
        return dataFacade.setAll(col);
    }

    @Override
    public boolean removeAll(DTOclass[] elements) {
        log.trace(">>> setAll(elements[])");
        return dataFacade.removeAll(elements);
    }

    @Override
    public boolean retainAll(DTOclass[] elements) {
        log.trace(">>> retainAll(elements[])");
        return dataFacade.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        log.trace(">>> remove(from, to)");
        dataFacade.remove(toDataRowNo(from), toDataRowNo(to));
    }

    @Override
    public void addListener(ListChangeListener<? super DTOclass> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> addListener(ListChangeListener)"+listener);
        dataFacade.addListener(listener);
        //changeListeners.add(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super DTOclass> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> removeListener(ListChangeListener)"+listener);
        dataFacade.removeListener(listener);
        //changeListeners.remove(listener);
    }

    // ******************* java.util.List *******************
    
    /*
    * @return  
    */
    @Override
    public int size() {
        int sz = dataFacade.size();
        int rc;
        try {
            rc = getRowTotalRange().getLength().intValue();
        } catch (IOException ex) {
            log.error(null, ex);
            rc = 0;
        }
        log.trace(">>> size="+sz+", RowCount="+rc+", isEmpty="+dataFacade.isEmpty());
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
    public Iterator<DTOclass> iterator() {
        log.trace(">>> iterator");
        return dataFacade.iterator();
    }

    @Override
    public Object[] toArray() {
        log.trace(">>> toArray");
        return dataFacade.toArray();
    }

    @Override
    public <DTOclass> DTOclass[] toArray(DTOclass[] a) {
        log.trace(">>> toArray(T[] a)");
        return dataFacade.toArray(a);
    }

    @Override
    public boolean add(DTOclass e) {
        log.trace(">>> add(DTOclass)");
        boolean res;
        log.debug("before dataFacade.add(). size="+size());
        res = dataFacade.add(e);
        log.debug("after dataFacade.add(). size="+size());
        return res;
    }

    @Override
    public boolean remove(Object o) {
        log.trace(">>> remove(Object)");
        boolean res;
        log.debug("before dataFacade.remove(). size="+size());
        res = dataFacade.remove(o);
        log.debug("after dataFacade.remove(). size="+size());
        return res;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        log.trace(">>> containsAll(Collection)");
        return dataFacade.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends DTOclass> c) {
        log.trace(">>> addAll(Collection)");
        return dataFacade.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends DTOclass> c) {
        log.trace(">>> addAll(index, Collection)");
        return dataFacade.addAll(toDataRowNo(index), c);
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
        cache.clear();
        dataFacade.clear();
        log.trace("<<< clear");
    }

    @Override
    public DTOclass get(int index) {
        //log.trace(">>> get(index="+index+")");
        //возвращаем значение из этой строки
        //assert(cache.containsIndex(index));
        return cache.get(toDataRowNo(index));
    }

    @Override
    public DTOclass set(int index, DTOclass element) {
        log.trace(">>> set(index, element)");
        return dataFacade.set(toDataRowNo(index), element);
    }

    @Override
    public void add(int index, DTOclass element) {
        log.trace(">>> add(index, element)");
        dataFacade.add(toDataRowNo(index), element);
    }

    @Override
    public DTOclass remove(int index) {
        log.trace(">>> remove(index)");
        return dataFacade.remove(toDataRowNo(index));
    }

    @Override
    public int indexOf(Object o) {
        log.trace(">>> indexOf(Object)");
        return toListIndex(dataFacade.indexOf(o));
    }

    @Override
    public int lastIndexOf(Object o) {
        log.trace(">>> lastIndexOf(Object)");
        return toListIndex(dataFacade.lastIndexOf(o));
    }

    @Override
    public ListIterator<DTOclass> listIterator() {
        log.trace(">>> listIterator");
        return dataFacade.listIterator();
    }

    @Override
    public ListIterator<DTOclass> listIterator(int index) {
        log.trace(">>> listIterator(index)");
        return dataFacade.listIterator(index);
    }

    @Override
    public List<DTOclass> subList(int fromIndex, int toIndex) {
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
        dataFacade.addListener(listener);
        //invListeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        log.trace(">>> removeListener(InvalidationListener)");
        dataFacade.removeListener(listener);
        //invListeners.remove(listener);
    }

    // ******************* fxapp01.dto.ISortOrder *******************

    @Override
    public IDAOSortOrder getSortOrder() {
        //возвращается копия объекта, чтобы через него нельзя было поменять сортировку
        return new SortOrder(sortOrder);
    }
    
    private boolean isSortOrderChanged(IDAOSortOrder sortOrder) {
        log.trace("isSortOrderChanged");
        if (this.sortOrder == null) {
            return (sortOrder != null);
        } else {
            //обработать sortOrder == null ?
            return (! this.sortOrder.equals(sortOrder));
        }
    }

    @Override
    public void setSortOrder(IDAOSortOrder sortOrder){
        log.trace("setSortOrder");
        if (isSortOrderChanged(sortOrder)) {
            log.debug("isSortOrderChanged=true");
            this.sortOrder = sortOrder;
            refresh();
        } else {
            log.debug("isSortOrderChanged=false");
        }
    }

    // ******************* fxapp01.dao.filter.ISqlFilterable *******************

    @Override
    public ISqlFilterable getFilter() {
        return filter;
    }

    private boolean isFilterChanged(ISqlFilterable filter) {
        log.trace("isFilterChanged");
        if (this.filter == null) {
            return (filter != null);
        } else {
            //обработать filter == null ?
            return (! this.filter.equals(filter));
        }
    }

    @Override
    public void setFilter(ISqlFilterable filter) {
        log.trace("setFilter");
        if (isFilterChanged(filter)) {
            log.debug("isFilterChanged=true");
            this.filter = filter;
            refresh();
        } else {
            log.debug("isFilterChanged=false");
        }
    }
    
}
