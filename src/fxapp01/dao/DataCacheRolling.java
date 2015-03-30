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
import fxapp01.dto.NestedIntRange;
import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Скользящий (плавающий) кеш данных. В данной версии только read-only. Хранит "окно" данных 
 * ограниченного размера. "Окно" сдвигается при запросе строки данных за его пределами.
 * Адресация строк кеша во всех публичных методах совпадает с адресацией строк диапазона 
 * данных range и outerLimits.
 * @author StarukhSA
 * @param <T>
 */
public class DataCacheRolling<T> implements List<T> {
    //TODO после отладки заменить реализацию интерфейса List на расширение класса ArrayList
    
    private static final ILogger log = LogMgr.getLogger(DataCacheRolling.class);
    private static final String entering = ">>> ";
    private static final String exiting = "<<< ";
    // фактическое начало (порядковый номер первой строки) и фактический размер 
    // окна данных в рамках источника данных. 
    private final IDataRangeFetcher dataFetcher;
    private final boolean hasWriteSupport;
    private INestedRange<Integer> outerLimits;
    private INestedRange<Integer> range;
    private int defSize;
    private int maxSize;
    private final List<T> readOnlyData;
    private final Map<Integer,T> writableData; //HashMap или Hashtable
    
    public DataCacheRolling(IDataRangeFetcher dataFetcher) throws IOException {
        String methodName = "constructor(dataFetcher)";
        log.trace(entering+methodName);
        if (dataFetcher == null) {
            throw new ENullArgument(methodName);
        } 
        this.dataFetcher = dataFetcher;
        this.hasWriteSupport = (dataFetcher instanceof IDataCrud);
        this.defSize = 100; //defaults
        this.maxSize = 300;
        this.readOnlyData = new ArrayList<>();
        // If a thread-safe highly-concurrent implementation is desired, then it is recommended to use ConcurrentHashMap
        this.writableData = new HashMap<>(16, 0.75f); //defaults
        log.debug("before dataFetcher.getRowTotalRange");
        this.outerLimits = dataFetcher.getRowTotalRange(); 
        this.range = new NestedIntRange(outerLimits.getFirst(), 0, outerLimits); 
        log.trace(exiting+methodName);
    }
    
    public DataCacheRolling(IDataRangeFetcher dataFetcher, int defSize, int maxSize) throws IOException {
        this(dataFetcher);
        String methodName = "constructor(dataFetcher, defSize, maxSize)";
        log.trace(entering+methodName);
        this.defSize = defSize;
        this.maxSize = maxSize;
        //range.setLength(this.defSize);
        log.trace(exiting+methodName);
    }

    public void debugPrintAll() {
        log.debug("----- printAll -----");
        Iterator<T> itr = iterator();
        while (itr.hasNext()) {
            Object o = itr.next();
            log.debug(o.toString());
        }
        log.debug("----- printAll -----");
    }

    private int toCacheIndex(int dataRowNo){
        return dataRowNo-range.getFirst();
    }
    
    private int toDataRowNo(int cacheIndex){
        return cacheIndex+range.getFirst();
    }
    
    public INestedRange<Integer> getRange() {
        return range;
    }
    
    public int getLeftLimit() {
        return outerLimits.getFirst();
    }

    public int getDefSize() {
        return defSize;
    }

    public void setDefSize(int defSize) {
        this.defSize = defSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
    
    public void refresh() {
        log.trace("refresh");
        INestedRange<Integer> r = range.clone();
        clear();
        log.debug("after clear(). size="+size());
        addAll(r.getFirst(), dataFetcher.fetch(r));
    }
    
    /**
     * 
     * @param index номер строки в исходных данных. отличается от индекса внутреннего кеша
     * зависит от нумерации строк конкретного источника данных (конкретной БД)
     * @return возвращает true, если этот номер строки данных найден в кеше
     */
    public boolean containsIndex(int index) {
        if (writableData.containsKey(index)) {
            return true;
        } else {
            return range.IsInbound(index);
        }
    }
    
    private boolean purge(int from, int to) {
        log.trace("remove(from="+from+", to="+to+")");
        if (from > to) {
            throw new EArgumentBreaksRule("remove", "(from <= to)");
        }
        if (! ((from == 0) || (to == readOnlyData.size()-1)) ) {
            log.debug("data.size="+readOnlyData.size());
            throw new EArgumentBreaksRule("remove", "(from == 0) || (to == data.size()-1)");
        }
        for (int i = from; i <= to; i++) {
            //TODO 
            readOnlyData.remove(from);
        }
        log.debug("after data.remove. data.size="+readOnlyData.size());
        int delta = to - from + 1;
        range.incLength(-delta);
        //TODO
        if (from == 0) {
            range.setFirst(range.getFirst()+delta);
        } else {
            range.setFirst(range.getFirst()-delta);
        }
        return (from <= to);
    }

    @Override
    public int size() {
        //**********************************************************************
        int sz = readOnlyData.size();
        log.trace(">>> size()="+sz+", range.length="+range.getLength());
        //return dataFacade.size();
        return sz;
    }
    
    @Override
    public void clear() {
        log.trace(">>> clear");
        readOnlyData.clear();
        range.setLength(0);
        log.trace("<<< clear. size="+size());
    }
    
    @Override
    public boolean isEmpty() {
        return (readOnlyData.isEmpty() && writableData.isEmpty());
    }

    @Override
    public boolean contains(Object o) {
        return writableData.containsValue(o) || readOnlyData.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        //TODO итератор по двум коллекциям
        return readOnlyData.iterator();
    }

    @Override
    public Object[] toArray() {
        int sz = readOnlyData.size() + writableData.size();
        //Object[] res = new Object[sz]{readOnlyData.toArray()};
        return readOnlyData.toArray();
    }

    @Override
    public <T>T[] toArray(T[] a) {
        return readOnlyData.toArray(a);
    }

    @Override
    public boolean add(T e) {
        boolean res = readOnlyData.add(e);
        if (res) {
            range.incLength(1);
        }
        return res;
    }

    @Override
    public boolean remove(Object o) {
        boolean res = readOnlyData.remove(o);
        if (res) {
            range.incLength(-1);
        }
        return res;
    }

    @Override
    public boolean containsAll(Collection c) {
        return readOnlyData.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c != null) {
            range.incLength(c.size());
        }
        return readOnlyData.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        log.trace(entering+"addAll(index="+index+", c)");
        if (c != null) {
            log.debug("before data.addAll()");
            boolean res = readOnlyData.addAll(toCacheIndex(index), c);
            log.debug("size="+size());
            range.incLength(c.size());
            return res;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c != null) {
            boolean res = readOnlyData.removeAll(c);
            range.incLength(- c.size());
            return res;
        } else {
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection c) {
        return readOnlyData.retainAll(c);
    }
    
    /**
     * 
     * @param point номер строки в исходных данных. 
     * @return возвращает номер, выровненный по границе страницы кеша
     */
    private int AlignToCacheDefSize(int point) {
        /* округляем до ближайшего большего целого размера страницы кеша */
        log.debug("AlignToCacheDefSize(point="+point+")");
        int pagePart = (point-getLeftLimit()) % defSize;
        log.debug("pagePart="+pagePart);
        if (point < range.getFirst()) {
            point = point - pagePart;
        } else {
            if (point > range.getLast()) {
                point = point + defSize - pagePart - 1;
            }
        }
        if (point < outerLimits.getFirst()) {
            point = outerLimits.getFirst();
        }
        if (point > outerLimits.getLast()) {
            point = outerLimits.getLast();
        }
        log.debug("point="+point);
        return point;
    }

    /**
     * 
     * @param index номер строки в исходных данных. отличается от индекса внутреннего кеша
     * зависит от нумерации строк конкретного источника данных (конкретной БД)
     * @return возвращает объект данных по указанному номеру сроки. если этот номер 
     * строки данных не найден в кеше, то запрашивает у источника данных требуемый диапазон данных
     */
    @Override
    public T get(int index) {
    /***************************************************************************
        мета-описание логики работы:
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
    ***************************************************************************/
        //index = index-getLeftLimit();
        log.trace(entering+getClass().getName()+".get(index="+index+").-------------------------------------------");
        log.debug("range=("+range+")");
        //проверяем, находится ли строка в пределах текущего диапазона
        if (containsIndex(index)) {
            //внутренний адрес строки в кеше
            int intIdx = toCacheIndex(index);
            log.debug("intIdx="+intIdx);
            //если да, то возвращаем значение из этой строки
            assert((0 <= intIdx) && (intIdx < readOnlyData.size()));
            return readOnlyData.get(intIdx);
        } else {
            log.debug("Out of cache. Try find new range");
            //если строка за пределами текущего диапазона
            //выравниваем строку по границе страниц кеша
            int target = AlignToCacheDefSize(index);
            log.debug("after AlignToCacheDefSize. index="+index+", target="+target);
            //рассчитываем расстояние до указаной строки в целых страницах кеша
            int dist;
            dist = Math.abs(range.getMaxDistance(target));
            log.debug("dist="+dist);
            INestedRange<Integer> aRange;
            //если расчетная длина диапазона меньше максимально допустимого размера кеша
            if (dist <= maxSize) {
                log.debug("dist <= maxSize");
                //вычисляем диапазон строк, который требуется дозагрузить
                //загружаем только новую порцию данных.  
                //ту часть диапазона, что уже есть в кеше, исключаем из загрузки
                aRange = range.Complement(target);
                log.debug("before assert(target < 0); target="+target);
                assert(target >= 0);
                log.debug("after assert(target < 0);");
                if (target < 0) //TODO нелепое условие. убрать
                {
                    addAll(range.getFirst(), dataFetcher.fetch(aRange));
                } else {
                    addAll(range.getLast()+1, dataFetcher.fetch(aRange));
                }
            } else {
                //если расстояние меньше удвоенного макс. размера кеша,
                //сдвинем начало диапазона так, чтобы он включал в себя указанную точку
                //сбросим часть кеша и дозагрузим новую часть 
                if (dist < maxSize * 2) {
                    log.debug("dist < maxSize * 2");
                    aRange = range.Complement(target);
                    if (target < range.getFirst()) {
                        log.debug("target < range.first. before purge(x, len)");
                        //сбрасываем часть строк с правого края кеша
                        log.debug("data.size="+readOnlyData.size()+", aRange.length="+aRange.getLength());
                        purge(readOnlyData.size()-aRange.getLength(), readOnlyData.size()-1);
                        log.debug("before dataFetcher.fetch(aRange, 0);");
                        //дозагружаем данные слева
                        addAll(range.getFirst(), dataFetcher.fetch(aRange));
                    } else {
                        log.debug("target >= 0. before purge(0, x). dist="+dist+", aRange.length="+aRange.getLength());
                        //сбрасываем часть строк с левого края кеша
                        purge(0, aRange.getLength()-1);
                        log.debug("before dataFetcher.fetch(aRange, size()+1);");
                        //дозагружаем данные справа
                        addAll(range.getLast()+1, dataFetcher.fetch(aRange));
                    }
                } else {
                    //расстояние равно или больше удвоенного макс. размера кеша,
                    log.debug("dist >= maxSize * 2");
                    aRange = new NestedIntRange(index, defSize, outerLimits);
                    //сбрасываем кеш полностью
                    clear(); 
                    //загружаем данные 
                    addAll(range.getFirst(), dataFetcher.fetch(aRange));
                    range.setFirst(index);
                }
            }
        }
        log.debug("Cache ranging finshed. Check asserts about range.");
        log.debug("index="+index+", range=("+range+")");
        int intIdx = index-range.getFirst();
        log.debug("intIdx="+intIdx);
        //если да, то возвращаем значение из этой строки
        assert((0 <= intIdx) && (intIdx < readOnlyData.size()));
        return readOnlyData.get(intIdx);
    }

    @Override
    public T set(int index, T element) {
        //return data.set(toCacheIndex(index), element);
        return readOnlyData.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        //data.add(toCacheIndex(index), element);
        readOnlyData.add(index, element);
        range.incLength(1);
    }

    @Override
    public T remove(int index) {
        range.incLength(-1);
        //return data.remove(toCacheIndex(index));
        return readOnlyData.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        log.trace(entering+"indexOf(Object)");
        //return toDataRowNo(data.indexOf(o));
        return readOnlyData.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        log.trace(entering+"lastIndexOf(Object)");
        //return toDataRowNo(data.lastIndexOf(o));
        return readOnlyData.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        log.trace(entering+"listIterator()");
        return readOnlyData.listIterator();
    }

    /*
    * @param index индекс строки, с которой стартует итератор. 
    * этот метод реализует интерфейс List, нумерация начинается с нуля.
    */
    @Override
    public ListIterator<T> listIterator(int index) {
        log.trace(entering+"listIterator(index="+index+")");
        //log.debug("toCacheIndex="+toCacheIndex(index));
        return readOnlyData.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return readOnlyData.subList(fromIndex, toIndex);
        //return data.subList(toCacheIndex(fromIndex), toCacheIndex(toIndex));
    }
}
