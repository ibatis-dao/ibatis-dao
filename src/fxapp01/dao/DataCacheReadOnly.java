package fxapp01.dao;

import fxapp01.dto.IntRange;
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
    // фактическое начало (порядковый номер первой строки) и фактический размер 
    // окна данных в рамках источника данных. 
    private IntRange range;
    private int defSize;
    private int maxSize;
    private final List<T> data;
    
    public DataCacheReadOnly() {
        log.trace(">>> constructor()");
        this.defSize = 100; //defaults
        this.maxSize = 300;
        this.data = new ArrayList<>();
        log.debug("before new IntRange");
        this.range = new IntRange(0, this.defSize); 
        log.trace("<<< constructor()");
    }
    
    public DataCacheReadOnly(int defSize, int maxSize) {
        this();
        log.trace(">>> constructor(defSize, maxSize)");
        this.defSize = defSize;
        this.maxSize = maxSize;
        range.setLength(this.defSize);
        log.trace("<<< constructor(defSize, maxSize)");
    }

    public IntRange getRange() {
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

    @Override
    public T get(int index) {
    /***************************************************************************
     * 
    ***************************************************************************/
        return data.get(index-range.getFirst());
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
