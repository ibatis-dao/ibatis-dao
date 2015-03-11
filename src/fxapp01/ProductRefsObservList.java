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
package fxapp01;

import fxapp01.dao.DataCacheReadOnly;
import fxapp01.dao.IDataRangeFetcher;
import fxapp01.dao.TestItemDAO;
import fxapp01.dto.INestedRange;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import fxapp01.dto.TestItemDTO;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;

/**
 *
 * @author serg
 */
public class ProductRefsObservList implements ObservableList<TestItemDTO>, IDataRangeFetcher {
    
    private static final ILogger log = LogMgr.getLogger(ProductRefsObservList.class);
    private final DataCacheReadOnly<TestItemDTO> cache;
    private final ObservableList<TestItemDTO> dataFacade;
    private final TestItemDAO dao;
    private final List<ListChangeListener<? super TestItemDTO>> changeListeners;
    private final List<InvalidationListener> invListeners;

    public ProductRefsObservList() throws IOException {
        log.trace(">>> constructor");
        this.changeListeners = new ArrayList<>();
        this.invListeners = new ArrayList<>();
        log.debug("before new DataCacheReadOnly");
        IDataRangeFetcher dps = this; // 
        this.cache = new DataCacheReadOnly<>(dps, 20, 40);
        log.debug("before FXCollections.observableList");
        this.dataFacade = FXCollections.observableList(cache);
        log.debug("before new ProductRefsDAO");
        this.dao = new TestItemDAO();
        /*
        List<ProductRefs> l = dao.select();
        this.cache.addAll(l);
        */
        log.debug("before requestDataPage");
        INestedRange<Integer> initRange = cache.getRange().clone();
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
    public boolean addAll(TestItemDTO[] elements) {
        log.trace(">>> addAll");
        return dataFacade.addAll(elements);
    }

    @Override
    public boolean setAll(TestItemDTO[] elements) {
        log.trace(">>> setAll(elements[])");
        return dataFacade.setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends TestItemDTO> col) {
        log.trace(">>> setAll(Collection)");
        return dataFacade.setAll(col);
    }

    @Override
    public boolean removeAll(TestItemDTO[] elements) {
        log.trace(">>> setAll(elements[])");
        return dataFacade.removeAll(elements);
    }

    @Override
    public boolean retainAll(TestItemDTO[] elements) {
        log.trace(">>> retainAll(elements[])");
        return dataFacade.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        log.trace(">>> remove(from, to)");
        dataFacade.remove(from, to);
    }

    @Override
    public void addListener(ListChangeListener<? super TestItemDTO> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> addListener(ListChangeListener)");
        changeListeners.add(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super TestItemDTO> listener) {
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
    public Iterator<TestItemDTO> iterator() {
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
    public boolean add(TestItemDTO e) {
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
    public boolean addAll(Collection<? extends TestItemDTO> c) {
        log.trace(">>> addAll(Collection)");
        return dataFacade.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends TestItemDTO> c) {
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
    public TestItemDTO get(int index) {
        //********************************************************
        //log.trace(">>> get(index="+index+")");
        //индекс List всегда начинается от 0. Индекс данных начинается от нижней границы диапазона данных
        //конвертируем индекс списка в номер строки диапазона
        //возвращаем значение из этой строки
        //assert(cache.containsIndex(index));
        return cache.get(index+cache.getLeftLimit());
    }

    @Override
    public void fetch(INestedRange aRowsRange, int pos) {
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
        List<TestItemDTO> l = dao.select(aRowsRange);
        cache.addAll(pos, l);
        log.debug("cache.size="+cache.size());
        log.trace("<<< fetch");
    }

    @Override
    public TestItemDTO set(int index, TestItemDTO element) {
        log.trace(">>> set(index, element)");
        return dataFacade.set(index, element);
    }

    @Override
    public void add(int index, TestItemDTO element) {
        log.trace(">>> add(index, element)");
        dataFacade.add(index, element);
    }

    @Override
    public TestItemDTO remove(int index) {
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
    public ListIterator<TestItemDTO> listIterator() {
        log.trace(">>> listIterator");
        return dataFacade.listIterator();
    }

    @Override
    public ListIterator<TestItemDTO> listIterator(int index) {
        log.trace(">>> listIterator(index)");
        return dataFacade.listIterator(index);
    }

    @Override
    public List<TestItemDTO> subList(int fromIndex, int toIndex) {
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
