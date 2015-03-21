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

import fxapp01.dto.INestedRange;
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
    private final List<ListChangeListener<? super DTOclass>> changeListeners;
    private final List<InvalidationListener> invListeners;

    public DataList(IDAO dao) throws IOException {
        log.trace(">>> constructor");
        this.changeListeners = new ArrayList<>();
        this.invListeners = new ArrayList<>();
        this.dao = dao;
        log.debug("before new DataCacheRolling");
        IDataRangeFetcher dps = this; // 
        //TODO желательно минимальный и максимальный диапазон окна определять 
        //по размеру видимой страницы данных на клиенте
        this.cache = new DataCacheRolling<>(dps, 20, 40);
        log.debug("before FXCollections.observableList");
        this.dataFacade = FXCollections.observableList(cache);
        log.debug("before new ProductRefsDAO");
        log.debug("before requestDataPage");
        INestedRange<Integer> initRange = cache.getRange().clone();
        initRange.setLength(20);
        dps.fetch(initRange, cache.getLeftLimit());
        log.trace("<<< constructor");
    }
    
    public List<String> getColumnNames() {
        return dao.getColumnNames();
    }
    
    public void debugPrintAll() {
        cache.debugPrintAll();
    }

    /******************* IDataRangeFetcher *******************
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
     * @param pos - адрес (номер строки) в кеше, куда нужно поместить полученые из источника данные. 
     * номер строки определяется адресацией кеша.
     */
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
        List<DTOclass> l;
        try {
            l = dao.select(aRowsRange);
        } catch (IOException ex) {
            log.error(null, ex);
            l = new ArrayList<>();
        }
        cache.addAll(pos, l);
        log.debug("cache.size="+cache.size());
        log.trace("<<< fetch");
    }

    /******************* javafx.collections.ObservableList *******************/
    
    /**
     * javafx.collections.ObservableList
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
        dataFacade.remove(from+cache.getLeftLimit(), to+cache.getLeftLimit());
    }

    @Override
    public void addListener(ListChangeListener<? super DTOclass> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> addListener(ListChangeListener)");
        changeListeners.add(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super DTOclass> listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
        log.trace(">>> removeListener(ListChangeListener)");
        changeListeners.remove(listener);
    }

    /******************* java.util.List *******************/
    @Override
    public int size() {
        //**********************************************************************
        int sz = dataFacade.size();
        int rc;
        try {
            rc = dao.getRowTotalRange().getLength().intValue();
        } catch (IOException ex) {
            log.error(null, ex);
            rc = 0;
        }
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
    public <T> T[] toArray(T[] a) {
        log.trace(">>> toArray(T[] a)");
        return dataFacade.toArray(a);
    }

    @Override
    public boolean add(DTOclass e) {
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
    public boolean addAll(Collection<? extends DTOclass> c) {
        log.trace(">>> addAll(Collection)");
        return dataFacade.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends DTOclass> c) {
        log.trace(">>> addAll(index, Collection)");
        return dataFacade.addAll(index+cache.getLeftLimit(), c);
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
    public DTOclass get(int index) {
        //********************************************************
        //log.trace(">>> get(index="+index+")");
        //индекс List всегда начинается от 0. Индекс данных начинается от нижней границы диапазона данных
        //конвертируем индекс списка в номер строки диапазона
        //возвращаем значение из этой строки
        //assert(cache.containsIndex(index));
        return cache.get(index+cache.getLeftLimit());
    }

    @Override
    public DTOclass set(int index, DTOclass element) {
        log.trace(">>> set(index, element)");
        return dataFacade.set(index+cache.getLeftLimit(), element);
    }

    @Override
    public void add(int index, DTOclass element) {
        log.trace(">>> add(index, element)");
        dataFacade.add(index+cache.getLeftLimit(), element);
    }

    @Override
    public DTOclass remove(int index) {
        log.trace(">>> remove(index)");
        return dataFacade.remove(index+cache.getLeftLimit());
    }

    @Override
    public int indexOf(Object o) {
        log.trace(">>> indexOf(Object)");
        return dataFacade.indexOf(o)-cache.getLeftLimit();
    }

    @Override
    public int lastIndexOf(Object o) {
        log.trace(">>> lastIndexOf(Object)");
        return dataFacade.lastIndexOf(o)-cache.getLeftLimit();
    }

    @Override
    public ListIterator<DTOclass> listIterator() {
        log.trace(">>> listIterator");
        return dataFacade.listIterator();
    }

    @Override
    public ListIterator<DTOclass> listIterator(int index) {
        log.trace(">>> listIterator(index)");
        return dataFacade.listIterator(index+cache.getLeftLimit());
    }

    @Override
    public List<DTOclass> subList(int fromIndex, int toIndex) {
        log.trace(">>> subList(fromIndex, toIndex)");
        return dataFacade.subList(fromIndex+cache.getLeftLimit(), toIndex+cache.getLeftLimit());
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
