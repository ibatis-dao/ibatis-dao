package fxapp01.dto;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.ENegativeArgument;
import fxapp01.excpt.ENullArgument;
import fxapp01.excpt.EUnsupported;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author serg
 */
public class LimitedIntRange {
    
    private static final ILogger log = LogMgr.getLogger(LimitedIntRange.class);
    private int first;
    private int length;
    private int leftLimit;
    private int rightLimit;
    private static final LimitedIntRange Singular = new LimitedIntRange(0, 0, 0, 0);

    public LimitedIntRange() {
        first = 0;
        length = 0;
        leftLimit = 0;
        rightLimit = Integer.MAX_VALUE;
    }

    public LimitedIntRange(int first, int length) {
        log.trace(">>> contructor(first="+first+", length="+length+")");
        init(first, length);
    }
    
    public LimitedIntRange(int first, int length, int leftLimit, int rightLimit) {
        this(first, length);
        log.trace(">>> contructor(first="+first+", length="+length+", leftLimit="+leftLimit+", rightLimit="+rightLimit+")");
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }
    
    private void init(int first, int length) {
        init(first, length, 0, Integer.MAX_VALUE);
    }
    
    private void init(int first, int length, int leftLimit, int rightLimit) {
        this.first = first;
        this.length = length;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        IsInternalRulesOk("init(first, length, leftLimit, rightLimit)");
    }
    
    private static boolean IsIntRule1Ok(final String callerMethodName, int leftLimit, int first) throws EArgumentBreaksRule {
        if (first < leftLimit) {
            throw new EArgumentBreaksRule(callerMethodName, "leftLimit <= first");
        }
        return true;
    }

    private static boolean IsIntRule2Ok(final String callerMethodName, int length) throws ENegativeArgument {
        if (length < 0) {
            throw new ENegativeArgument(callerMethodName);
        }
        return true;
    }

    private static boolean IsIntRule3Ok(final String callerMethodName, int first, int length, int rightLimit) throws EArgumentBreaksRule {
        if (first+length-1 > rightLimit) {
            throw new EArgumentBreaksRule(callerMethodName, "first+length-1 <= rightLimit");
        }
        return true;
    }

    private static boolean IsIntRule4Ok(final String callerMethodName, int leftLimit, int rightLimit) throws EArgumentBreaksRule {
        if (leftLimit > rightLimit) {
            throw new EArgumentBreaksRule(callerMethodName, "leftLimit <= rightLimit");
        }
        return true;
    }

    private static boolean IsIntRule5Ok(final String callerMethodName, int leftLimit, int to, int rightLimit) throws EArgumentBreaksRule {
        if ((to < leftLimit) || (to > rightLimit)) {
            throw new EArgumentBreaksRule(callerMethodName, "leftLimit >= to >= rightLimit");
        }
        return true;
    }

    private boolean IsInternalRulesOk(final String callerMethodName) {
        IsIntRule1Ok(callerMethodName, leftLimit, first); // leftLimit <= first
        IsIntRule2Ok(callerMethodName, length); // length >= 0
        IsIntRule3Ok(callerMethodName, first, length, rightLimit); // first+length-1 <= rightLimit
        IsIntRule4Ok(callerMethodName, leftLimit, rightLimit); // leftLimit <= rightLimit
        return true;
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
        IsIntRule1Ok("setFirst", leftLimit, first); // leftLimit <= first
        IsIntRule3Ok("setFirst", first, length, rightLimit); // first+length-1 <= rightLimit
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
        IsIntRule2Ok("setLength", length); // length >= 0
        IsIntRule3Ok("setLength", first, length, rightLimit); // first+length-1 <= rightLimit
        this.length = length;
    }
    
    public void incLength(int increment) {
        log.debug(">>> incLength("+increment+"). old length="+length);
        if (IsIntRule2Ok("incLength", length+increment)) { // length >= 0
            IsIntRule3Ok("incLength", first, length+increment, rightLimit); // first+length-1 <= rightLimit
        }
        this.length = length + increment;
    }
    
    public int getLeftLimit() {
        return leftLimit;
    }

    public void setLeftLimit(int leftLimit) {
        IsIntRule1Ok("setLeftLimit", leftLimit, first); // leftLimit <= first
        IsIntRule4Ok("setLeftLimit", leftLimit, rightLimit); // leftLimit <= rightLimit
        this.leftLimit = leftLimit;
    }

    public int getRightLimit() {
        return rightLimit;
    }

    public void setRightLimit(int rightLimit) {
        IsIntRule3Ok("setRightLimit", first, length, rightLimit); // first+length-1 <= rightLimit
        IsIntRule4Ok("setRightLimit", leftLimit, rightLimit); // leftLimit <= rightLimit
        log.debug("setRightLimit(rightLimit="+rightLimit+")");
        this.rightLimit = rightLimit;
    }
    /**
     * @return the length
     */
    public int getLast() {
        return first+length-1;
    }
    
    
    @Override
    public LimitedIntRange clone() {
        return new LimitedIntRange(first, length, leftLimit, rightLimit);
        /*
        try {
            LimitedIntRange n = (LimitedIntRange) super.clone();
            n.init(first, length, leftLimit, rightLimit);
            return n;
        } catch (CloneNotSupportedException ex) {
            log.error(null, ex);
            return new LimitedIntRange(first, length, leftLimit, rightLimit);
        }
        */
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
     * Определяет, является ли текущий диапазон вырожденным
     * @return 
     */
    public boolean IsSingular() {
        return ((this == Singular) || ((first == 0) && (length == 0)));
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
     * Определяет, накрывает ли целиком указанный диапазон (т.е. помещается ли 
     * текущий диапазон целиком внутри указанного)
     * @param aRange
     * @return 
     */
    public boolean IsInbound(LimitedIntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument("IsCoveredBy");
        } 
        return ((aRange.getFirst() <= first) && (getLast() <= aRange.getLast()));
    }
    
    /*
    Определяет расстояние от указанной точки до до ближайшей границы диапазона
    Если точка указана за макс. границами, то ошибка
    Если точка внутри самого диапазона, то расстояние = 0
    */
    public int getMinDistance(int to) {
        log.trace("getMinDistance(to="+to+")");
        IsIntRule5Ok("getMinDistance", leftLimit, to, rightLimit); // leftLimit >= to >= rightLimit
        if (IsInbound(to)) {
            return 0;
        } else {
            int dist = Math.min(Math.abs(to - first), Math.abs(to - getLast()));
            log.debug("dist="+dist);
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
        log.trace("getMaxDistance(to="+to+")");
        IsIntRule5Ok("getMaxDistance", leftLimit, to, rightLimit); // leftLimit >= to >= rightLimit
        if (IsInbound(to)) {
            return 0;
        } else {
            int dist = Math.max(Math.abs(to - first), Math.abs(to - getLast()));
            log.debug("dist="+dist);
            if (to < first) {
                return - dist;
            } else {
                return dist;
            }
        }
    }
    
    /**
     * Определяет, пересекаются ли указанный диапазон с текущим
     * @param aRange
     * @return 
     */
    public boolean IsOverlapped(LimitedIntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument("IsOverlapped");
        } 
        return !((getLast() < aRange.getFirst()) || (getFirst() > aRange.getLast()));
    }
    
    /**
     * Определяет область пересечения указанного диапазона с текущим
     * @param aRange
     * @return 
   */
    public LimitedIntRange Overlap(LimitedIntRange aRange) {
        log.trace("Overlap(aRange)");
        if (aRange == null) {
            throw new ENullArgument("Overlap");
        }
        if (IsOverlapped(aRange)) {
            log.debug("IsOverlapped");
            int maxStart = Math.max(first, aRange.getFirst());
            int minLast = Math.min(getLast(), aRange.getLast());
            return new LimitedIntRange(maxStart, minLast - maxStart + 1);
        } else {
            log.debug("Is not overlapped. returns Singular");
            return Singular;
        }
    }
    
    /**
     * Добавляет указанный диапазон к текущему. 
     * Результирующий диапазон включает в себя оба диапазона и промежуток между ними (если он был).
     * @param aRange
     * @return 
     */
    public LimitedIntRange Add(LimitedIntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument("Add");
        }
        int minStart = Math.max(leftLimit, Math.min(first, aRange.getFirst()));
        int maxLast = Math.min(Math.max(getLast(), aRange.getLast()), rightLimit);
        log.debug("Add(). minStart="+minStart+", maxLast="+maxLast);
        return new LimitedIntRange(minStart, maxLast - minStart + 1);
    }

    /**
     * Продлевает текущий диапазон до указанной точки. 
     * @param to
     * @return 
     */
    public LimitedIntRange Extend(int to) {
        IsIntRule5Ok("Extend", leftLimit, to, rightLimit); // leftLimit >= to >= rightLimit
        if (IsInbound(to)) {
            return Singular;
        } else {
            int minStart = Math.max(leftLimit, Math.min(first, to));
            int maxLast = Math.min(Math.max(getLast(), to), rightLimit);
            log.debug("Extend(). minStart="+minStart+", maxLast="+maxLast);
            return new LimitedIntRange(minStart, maxLast - minStart + 1);
        }
    }

    /*
    * смещает начало диапазона на указанную величину
    */
    public LimitedIntRange Shift(int value) {
        //setFirst(first+value);
        return new LimitedIntRange(Math.max(leftLimit, first+value), length);
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
    public LimitedIntRange Sub(LimitedIntRange aRange) {
        throw new EUnsupported(); 
    /*
        //а если из охватывающего диапазона вычитают меньший, входящий в него ?
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
            return new LimitedIntRange(maxStart, minLast - maxStart);
        } else {
            //если диапазоны не пересекаются
            log.debug("Sub(). Not overlapped. first="+first+", length="+length);
            return new LimitedIntRange(first, length);
        }
    */
    }

    /**
     * Возвращает диапазон, который дополняет текущий диапазон до указанной точки. 
     * @param to
     * @return 
     */
    public LimitedIntRange Complement(int to) {
        log.trace("Complement(to="+to+")");
        int dist = getMinDistance(to);
        if (dist < 0) {
            return new LimitedIntRange(first + dist, - dist);
        } else {
            if (dist > 0) {
                return new LimitedIntRange(first + dist - 1, dist);
            } else {
                return Singular;
            }
        }
    }

}
