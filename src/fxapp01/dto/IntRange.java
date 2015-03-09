package fxapp01.dto;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.ENegativeArgument;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author serg
 */
public class IntRange {
    
    private static final ILogger log = LogMgr.getLogger(IntRange.class);
    private int first;
    private int length;
    private int leftLimit;
    private int rightLimit;
    private static final IntRange Singular = new IntRange(0, 0, 0, 0);

    public IntRange() {
        first = 0;
        length = 0;
        leftLimit = 0;
        rightLimit = Integer.MAX_VALUE;
    }

    public IntRange(int first, int length) {
        log.trace(">>> contructor(first="+first+", length="+length+")");
        if ((first < 0) || (length < 0)) {
            throw new ENegativeArgument("Constructor");
        }
        this.first = first;
        this.length = length;
        leftLimit = 0;
        rightLimit = Integer.MAX_VALUE;
    }
    
    public IntRange(int first, int length, int leftLimit, int rightLimit) {
        this(first, length);
        log.trace(">>> contructor(first="+first+", length="+length+", leftLimit="+leftLimit+", rightLimit="+rightLimit+")");
        if (first < leftLimit) {
            throw new EArgumentBreaksRule("Constructor", "leftLimit <= first");
        }
        if (first+length > rightLimit) {
            throw new EArgumentBreaksRule("Constructor", "first+length <= rightLimit");
        }
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }
    
    /**
     * @return the first
     */
    public int getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(int first) {
        if (first < leftLimit) {
            throw new EArgumentBreaksRule("setFirst", "leftLimit <= first");
        }
        this.first = first;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        log.debug(">>> setLength("+length+")");
        if (length < 0) {
            throw new ENegativeArgument("setLength");
        }
        if (first+length > rightLimit) {
            throw new EArgumentBreaksRule("setLength", "first+length <= rightLimit");
        }
        this.length = length;
    }
    
    public void incLength(int increment) {
        log.debug(">>> incLength("+increment+"). old length="+length);
        if (length+increment < 0) {
            throw new EArgumentBreaksRule("incLength", "length+increment >= 0");
        } else {
            if (first+length+increment > rightLimit) {
                throw new EArgumentBreaksRule("incLength", "first+length <= rightLimit");
            }
        }
        this.length = length + increment;
    }
    
    public int getLeftLimit() {
        return leftLimit;
    }

    public void setLeftLimit(int leftLimit) {
        if (leftLimit <= 0) {
            throw new ENegativeArgument("setLeftLimit");
        }
        if (first < leftLimit) {
            throw new EArgumentBreaksRule("setLeftLimit", "leftLimit <= first");
        }
        this.leftLimit = leftLimit;
    }

    public int getRightLimit() {
        return rightLimit;
    }

    public void setRightLimit(int rightLimit) {
        if (rightLimit <= 0) {
            throw new ENegativeArgument("setRightLimit");
        }
        if (first+length > rightLimit) {
            throw new EArgumentBreaksRule("setRightLimit", "first+length <= rightLimit");
        }
        this.rightLimit = rightLimit;
    }
    /**
     * @return the length
     */
    public int getLast() {
        return first+length-1;
    }
    
    
    @Override
    public IntRange clone() {
        //IntRange n = (IntRange) super.clone();
        return new IntRange(first, length, leftLimit, rightLimit);
    }
    
    /**
     * Определяет, равны ли (полностью совпадают) указанный диапазон с текущим
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
        //если параметр = null, то он не может быть равен текущему экземпляру
            return false;
        } else {
            //если тип входного параметра нельзя присвоить текущему типу, 
            //то их нельзя сравнить. он не может быть равен текущему экземпляру
            if (! o.getClass().isAssignableFrom(this.getClass())) {
                log.debug("!!! equals("+o.getClass().getName()+")=FALSE");
                return false;
            } else {
                return (hashCode() == o.hashCode());
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.first;
        hash = 89 * hash + this.length;
        hash = 89 * hash + this.leftLimit;
        hash = 89 * hash + this.rightLimit;
        return hash;
    }
    
    /**
     * Определяет, входит ли указанная отметка в текущий диапазон
     * @param value
     * @return 
     */
    public boolean IsInbound(int value) {
        log.trace("IsInbound(value="+value+"). first="+first+", last="+getLast());
        return (first <= value) && (value <= getLast());
    }
    
    /**
     * Определяет, пересекаются ли указанный диапазон с текущим
     * @param aRange
     * @return 
     */
    public boolean IsOverlapped(IntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument("IsOverlapped");
        } 
        return !((getLast() < aRange.getFirst()) || (getFirst() > aRange.getLast()));
    }
    
    /*
    Определяет расстояние от указанной точки до до ближайшей границы диапазона
    Если точка указана за макс. границами, то ошибка
    Если точка внутри самого диапазона, то расстояние = 0
    */
    public int getMinDistance(int to) {
        if ((to < leftLimit) || (to > rightLimit)) {
            throw new EArgumentBreaksRule("getMinDistance", "leftLimit >= to >= rightLimit");
        }
        if (IsInbound(to)) {
            return 0;
        } else {
            int dist = Math.min(Math.abs(to - first), Math.abs(to - getLast()));
            if (to < first) {
                return - dist;
            } else {
                return dist;
            }
        }
    }
    
    /*
    Определяет расстояние от указанной точки до до ближайшей границы диапазона
    Если точка указана за макс. границами, то ошибка
    Если точка внутри самого диапазона, то расстояние = 0
    */
    public int getMaxDistance(int to) {
        if ((to < leftLimit) || (to > rightLimit)) {
            throw new EArgumentBreaksRule("getMaxDistance", "leftLimit >= to >= rightLimit");
        }
        if (IsInbound(to)) {
            return 0;
        } else {
            int dist = Math.max(Math.abs(to - first), Math.abs(to - getLast()));
            if (to < first) {
                return - dist;
            } else {
                return dist;
            }
        }
    }
    
    /**
     * Определяет, является ли текущий диапазон вырожденным
     * @return 
     */
    public boolean IsSingular() {
        return (this == Singular);
    }
    
    /**
     * Определяет область пересечения указанного диапазона с текущим
     * @param aRange
     * @return 
   */
    public IntRange Overlap(IntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument("Overlap");
        }
        if (IsOverlapped(aRange)) {
            int maxStart = Math.max(first, aRange.getFirst());
            int minLast = Math.min(getLast(), aRange.getLast());
            return new IntRange(maxStart, minLast - maxStart);
        } else {
            return Singular;
        }
    }
    
    /**
     * Добавляет указанный диапазон к текущему. 
     * Результирующий диапазон включает в себя оба диапазона и промежуток между ними (если он был).
     * @param aRange
     * @return 
     */
    public IntRange Add(IntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument("Add");
        }
        int minStart = Math.max(leftLimit, Math.min(first, aRange.getFirst()));
        int maxLast = Math.min(Math.max(getLast(), aRange.getLast()), rightLimit);
        log.debug("Add(). minStart="+minStart+", maxLast="+maxLast);
        return new IntRange(minStart, maxLast - minStart);
    }

    /**
     * Вычитает указанный диапазон из текущего. 
     * Результирующий диапазон включает в себя часть текущего диапазона, не 
     * совпадающую с указанным (т.е за вычетом указанного).
     * Если указанный диапазон не пересекается (не имеет общего участка) с имеющимся,
     * то результатом будет текущий диапазон
     * @param aRange
     * @return 
     */
    public IntRange Sub(IntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument("Sub");
        }
        if (IsOverlapped(aRange)) {
            //если диапазоны пересекаются
            int maxStart = Math.max(first, aRange.getFirst());
            int minLast = Math.min(getLast(), aRange.getLast());
            log.debug("Sub(). IsOverlapped. this.first="+first+", this.last="+getLast()+
                ", aRange.first="+aRange.getFirst()+", aRange.last="+aRange.getLast()+
                ", maxStart="+maxStart+", minLast="+minLast);
            return new IntRange(maxStart, minLast - maxStart);
        } else {
            //если диапазоны не пересекаются
            log.debug("Sub(). Not overlapped. first="+first+", length="+length);
            return new IntRange(first, length);
        }
    }

    /*
    * смещает начало диапазона на указанную величину
    */
    public IntRange Shift(int value) {
        //setFirst(first+value);
        return new IntRange(Math.max(leftLimit, first+value), length);
    }

}
