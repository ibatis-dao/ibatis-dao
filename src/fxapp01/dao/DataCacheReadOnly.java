package fxapp01.dao;

import fxapp01.dto.LimitedIntRange;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author StarukhSA
 */
public class DataCacheReadOnly<T> implements List {
    //TODO после отладки заменить реализацию интерфейса List на расширение класса ArrayList
    
    private static final ILogger log = LogMgr.getLogger(DataCacheReadOnly.class);
    private static final String entering = ">>> ";
    private static final String exiting = "<<< ";
    // фактическое начало (порядковый номер первой строки) и фактический размер 
    // окна данных в рамках источника данных. 
    private IDataRangeFetcher dataFetcher;
    private LimitedIntRange range;
    private int defSize;
    private int maxSize;
    private final List<T> data;
    
    public DataCacheReadOnly(IDataRangeFetcher dataFetcher) {
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
        this.range = new LimitedIntRange(0, 0); 
        log.trace(exiting+methodName);
    }
    
    public DataCacheReadOnly(IDataRangeFetcher dataFetcher, int defSize, int maxSize) {
        this(dataFetcher);
        String methodName = "constructor(dataFetcher, defSize, maxSize)";
        log.trace(entering+methodName);
        this.defSize = defSize;
        this.maxSize = maxSize;
        //range.setLength(this.defSize);
        log.trace(exiting+methodName);
    }

    public LimitedIntRange getRange() {
        return range;
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
        for (int i = from; i <= to; i++) {
            data.remove(i);
        }
        range.incLength(from-to-1);
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
        return data.contains((T)o);
    }

    @Override
    public Iterator iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean add(Object e) {
        boolean res = data.add((T)e);
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
    public boolean addAll(Collection c) {
        if (c != null) {
            range.incLength(c.size());
        }
        return data.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        if (c != null) {
            range.incLength(c.size());
        }
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
        int pagePart = defSize - (point % defSize);
        log.debug("pagePart="+pagePart);
        if (pagePart != 0) {
            if (point < 0) {
                point = point - pagePart + 1;
            } else {
                point = point + pagePart - 1;
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
        log.debug("get(index="+index+")");
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
            //рассчитываем расстояние до указаной строки
            int target = range.getMaxDistance(index);
            log.debug("before AlignToCacheDefSize. target="+target);
            // строка (позиция, точка), выровненная по границе страниц кеша
            target = AlignToCacheDefSize(target);
            log.debug("after AlignToCacheDefSize. target="+target);
            // расстояние в целых страницах кеша
            int dist = Math.abs(target);
            log.debug("dist="+dist);
            LimitedIntRange aRange;
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
                    dataFetcher.fetch(aRange, size()+1);
                }
            } else {
                //если расстояние меньше удвоенного макс. размера кеша,
                //сдвинем начало диапазона так, чтобы он включал в себя указанную точку
                //сбросим часть кеша и дозагрузим новую часть 
                if (dist < maxSize * 2) {
                    aRange = range.Complement(target);
                    if (target < 0) {
                        //сбрасываем часть строк с левого края кеша
                        remove(0, dist - aRange.getLength());
                        //дозагружаем данные слева
                        dataFetcher.fetch(aRange, 0);
                    } else {
                        //сбрасываем часть строк с правого края кеша
                        remove(dist - maxSize, aRange.getLength());
                        //дозагружаем данные справа
                        dataFetcher.fetch(aRange, size()+1);
                    }
                } else {
                    //расстояние равно или больше удвоенного макс. размера кеша,
                    //
                    aRange = new LimitedIntRange(index, defSize);
                    //сбрасываем кеш полностью
                    clear(); 
                    //загружаем данные 
                    dataFetcher.fetch(aRange, 0);
                }
            }
        }
        log.debug("Cache ranging finshed. Check asserts about range");
        int intIdx = index-range.getFirst();
        log.debug("intIdx="+intIdx);
        //если да, то возвращаем значение из этой строки
        assert((0 <= intIdx) && (intIdx < data.size()));
        return data.get(intIdx);
    }

    @Override
    public Object set(int index, Object element) {
        return data.set(index, (T)element);
    }

    @Override
    public void add(int index, Object element) {
        range.incLength(1);
        data.add(index, (T)element);
    }

    @Override
    public Object remove(int index) {
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
    public ListIterator listIterator() {
        return data.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return data.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return data.subList(fromIndex, toIndex);
    }
}