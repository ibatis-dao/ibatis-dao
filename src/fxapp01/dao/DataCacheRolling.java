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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author StarukhSA
 */
public class DataCacheRolling<T> implements List<T> {
    //TODO после отладки заменить реализацию интерфейса List на расширение класса ArrayList
    
    private static final ILogger log = LogMgr.getLogger(DataCacheRolling.class);
    private static final String entering = ">>> ";
    private static final String exiting = "<<< ";
    // фактическое начало (порядковый номер первой строки) и фактический размер 
    // окна данных в рамках источника данных. 
    private IDataRangeFetcher dataFetcher;
    private INestedRange<Integer> outerLimits;
    private INestedRange<Integer> range;
    private int defSize;
    private int maxSize;
    private final List<T> data;
    
    public DataCacheRolling(IDataRangeFetcher dataFetcher) throws IOException {
        String methodName = "constructor(dataFetcher)";
        log.trace(entering+methodName);
        if (dataFetcher == null) {
            throw new ENullArgument(methodName);
        } 
        this.dataFetcher = dataFetcher;
        this.defSize = 100; //defaults
        this.maxSize = 300;
        this.data = new ArrayList<>();
        log.debug("before new IntRange");
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

    public boolean containsIndex(int index) {
        /*
        index = 99
        data.size() = 99 [0 .. 98]
        range.first=0, last=99
        */
        return range.IsInbound(index);
    }

    public boolean remove(int from, int to) {
        log.trace("remove(from="+from+", to="+to+")");
        if (from > to) {
            throw new EArgumentBreaksRule("remove", "(from <= to)");
        }
        if (! ((from == getLeftLimit()) || (to == data.size()-1)) ) {
            log.debug("data.size="+data.size());
            throw new EArgumentBreaksRule("remove", "(from == 0) || (to == data.size()-1)");
        }
        for (int i = from; i <= to; i++) {
            data.remove(from);
        }
        log.debug("after data.remove. data.size="+data.size());
        int delta = to - from + 1;
        range.incLength(-delta);
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
        int sz = data.size();
        log.trace(">>> size()="+sz+", range.length="+range.getLength());
        //return dataFacade.size();
        return sz;
    }
    
    @Override
    public void clear() {
        log.trace(">>> clear");
        data.clear();
        range.setLength(0);
    }
    
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T>T[] toArray(T[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean add(T e) {
        boolean res = data.add(e);
        if (res) {
            range.incLength(1);
        }
        return res;
    }

    @Override
    public boolean remove(Object o) {
        boolean res = data.remove(o);
        if (res) {
            range.incLength(-1);
        }
        return res;
    }

    @Override
    public boolean containsAll(Collection c) {
        return data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c != null) {
            range.incLength(c.size());
        }
        return data.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        log.trace(entering+"addAll(index="+index+", c)");
        if (c != null) {
            range.incLength(c.size());
        }
        log.debug("before data.addAll()");
        return data.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c != null) {
            range.incLength(- c.size());
        }
        return data.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return data.retainAll(c);
    }
    
    private int AlignToCacheDefSize(int point) {
        /* округляем до ближайшего большего целого размера страницы кеша */
        log.debug("AlignToCacheDefSize(point="+point+")");
        int pagePart = (point % defSize);
        log.debug("pagePart="+pagePart);
        if (point < range.getFirst()) {
            point = point - pagePart;
        } else {
            if (point > range.getLast()) {
                point = point + defSize - pagePart - 1;
            }
        }
        log.debug("point="+point);
        return point;
    }

    @Override
    public T get(int index) {
    /***************************************************************************
     * 
    ***************************************************************************/
        index = /*range.getLeftLimit() +*/ index;
        log.trace(entering+getClass().getName()+".get(index="+index+").-------------------------------------------");
        log.debug("range=("+range+")");
        //проверяем, находится ли строка в пределах текущего диапазона
        if (containsIndex(index)) {
            //внутренний адрес строки в кеше
            int intIdx = index-range.getFirst();
            log.debug("intIdx="+intIdx);
            //если да, то возвращаем значение из этой строки
            assert((0 <= intIdx) && (intIdx < data.size()));
            return data.get(intIdx);
        } else {
            log.debug("Out of cache. Try find new range");
            //если строка за пределами текущего диапазона
            //выравниваем строку по границе страниц кеша
            int target = AlignToCacheDefSize(index);
            log.debug("after AlignToCacheDefSize. index="+index+", target="+target);
            //рассчитываем расстояние до указаной строки в целых страницах кеша
            int dist = Math.abs(range.getMaxDistance(target));
            log.debug("dist="+dist);
            INestedRange<Integer> aRange;
            //если расчетная длина диапазона меньше максимально допустимого размера кеша
            if (dist <= maxSize) {
                log.debug("dist <= maxSize");
                //вычисляем диапазон строк, который требуется дозагрузить
                //загружаем только новую порцию данных.  
                //ту часть диапазона, что уже есть в кеше, исключаем из загрузки
                aRange = range.Complement(target);
                if (target < 0) {
                    dataFetcher.fetch(aRange, 0);
                } else {
                    dataFetcher.fetch(aRange, size());
                }
            } else {
                //если расстояние меньше удвоенного макс. размера кеша,
                //сдвинем начало диапазона так, чтобы он включал в себя указанную точку
                //сбросим часть кеша и дозагрузим новую часть 
                if (dist < maxSize * 2) {
                    log.debug("dist < maxSize * 2");
                    aRange = range.Complement(target);
                    if (target < range.getFirst()) {
                        log.debug("target < range.first. before remove(x, len)");
                        //сбрасываем часть строк с правого края кеша
                        log.debug("data.size="+data.size()+", aRange.length="+aRange.getLength());
                        remove(data.size()-aRange.getLength(), data.size()-1);
                        log.debug("before dataFetcher.fetch(aRange, 0);");
                        //дозагружаем данные слева
                        dataFetcher.fetch(aRange, 0);
                    } else {
                        log.debug("target >= 0. before remove(0, x). dist="+dist+", aRange.length="+aRange.getLength());
                        //сбрасываем часть строк с левого края кеша
                        remove(0, aRange.getLength()-1);
                        log.debug("before dataFetcher.fetch(aRange, size()+1);");
                        //дозагружаем данные справа
                        dataFetcher.fetch(aRange, size());
                    }
                } else {
                    //расстояние равно или больше удвоенного макс. размера кеша,
                    log.debug("dist >= maxSize * 2");
                    aRange = new NestedIntRange(index, defSize, outerLimits);
                    //сбрасываем кеш полностью
                    clear(); 
                    //загружаем данные 
                    dataFetcher.fetch(aRange, 0);
                    range.setFirst(index);
                }
            }
        }
        log.debug("Cache ranging finshed. Check asserts about range.");
        log.debug("index="+index+", range=("+range+")");
        int intIdx = index-range.getFirst();
        log.debug("intIdx="+intIdx);
        //если да, то возвращаем значение из этой строки
        assert((0 <= intIdx) && (intIdx < data.size()));
        return data.get(intIdx);
    }

    @Override
    public T set(int index, T element) {
        return data.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        range.incLength(1);
        data.add(index, element);
    }

    @Override
    public T remove(int index) {
        range.incLength(-1);
        return data.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return data.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return data.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return data.subList(fromIndex, toIndex);
    }
}
