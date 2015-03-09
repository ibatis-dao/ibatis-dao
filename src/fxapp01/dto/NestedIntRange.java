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
public class NestedIntRange {
    
    private static final ILogger log = LogMgr.getLogger(NestedIntRange.class);
    private static final String entering = ">>> ";
    private static final String contructorMthdName = "contructor";
    private static final String setFirstMthdName = "setFirst";
    private static final String setLengthMthdName = "setLength";
    private static final String incLengthMthdName = "incLength";
    private static final String setLeftLimitMthdName = "setLeftLimit";
    private static final String equalsMthdName = "equals";
    private static final String IsInboundMthdName = "IsInbound";
    private static final String getMinDistanceMthdName = "getMinDistance";
    private static final String getMaxDistanceMthdName = "getMaxDistance";
    private static final String IsOverlappedMthdName = "IsOverlapped";
    private static final String OverlapMthdName = "Overlap";
    private static final String AddMthdName = "Add";
    private static final String ExtendMthdName = "Extend";
    private static final String ShiftMthdName = "Shift";
    private static final String ComplementMthdName = "Complement";

    private int first;
    private int length;
    private NestedIntRange parentRange;
    private static final NestedIntRange Singular = new NestedIntRange(0, 0, null);
    
    public NestedIntRange() {
        first = 0;
        length = 0;
        parentRange = null;
    }

    public NestedIntRange(int first, int length) {
        log.trace(entering+contructorMthdName+"(first="+first+", length="+length+")");
        init(first, length);
    }
    
    public NestedIntRange(int first, int length, NestedIntRange parentRange) {
        this(first, length);
        log.trace(entering+contructorMthdName+"(first="+first+", length="+length+", parentRange="+parentRange+")");
        this.parentRange = parentRange;
    }
    
    private void init(int first, int length) {
        init(first, length, null);
    }
    
    private void init(int first, int length, NestedIntRange parentRange) {
        this.first = first;
        this.length = length;
        this.parentRange = parentRange;
        IsInternalRulesOk("init(first, length, parentRange)");
    }
    
    private static boolean IsIntRule1Ok(final String callerMethodName, NestedIntRange parentRange, int first) throws EArgumentBreaksRule {
        if (parentRange != null) {
            if (first < parentRange.getFirst()) {
                throw new EArgumentBreaksRule(callerMethodName, "parentRange.first <= first");
            }
        }
        return true;
    }

    private static boolean IsIntRule2Ok(final String callerMethodName, int length) throws ENegativeArgument {
        if (length < 0) {
            throw new ENegativeArgument(callerMethodName);
        }
        return true;
    }

    private static boolean IsIntRule3Ok(final String callerMethodName, int first, int length, NestedIntRange parentRange) throws EArgumentBreaksRule {
        if (parentRange != null) {
            if (first+length-1 > parentRange.getLast()) {
                throw new EArgumentBreaksRule(callerMethodName, "first+length-1 <= parentRange.last");
            }
        }
        return true;
    }
    
    

    private static boolean IsIntRule4Ok(final String callerMethodName, NestedIntRange parentRange, NestedIntRange aRange) throws EArgumentBreaksRule {
        if ((parentRange != null) && (aRange != null)) {
            if (! aRange.IsInbound(parentRange)) {
                throw new EArgumentBreaksRule(callerMethodName, "parentRange.first >= aRange >= parentRange.last");
            }
        }
        return true;
    }
    
    private static boolean IsIntRule5Ok(final String callerMethodName, NestedIntRange parentRange, int to) throws EArgumentBreaksRule {
        if (parentRange != null) {
            if ((to < parentRange.getFirst()) || (to > parentRange.getLast())) {
                throw new EArgumentBreaksRule(callerMethodName, "parentRange.first >= to >= parentRange.last");
            }
        }
        return true;
    }
    
    private static boolean IsIntRule6Ok(NestedIntRange instance, final String callerMethodName, int first, int length, NestedIntRange parentRange) throws EArgumentBreaksRule {
        if (instance == Singular) {
            if ((first != 0) || (length != 0) || (parentRange != null)) {
                throw new EArgumentBreaksRule(callerMethodName, "Singular range can not have non zero params");
            }
        }
        return true;
    }
    
    private boolean IsInternalRulesOk(final String callerMethodName) {
        IsIntRule1Ok(callerMethodName, parentRange, first); // leftLimit <= first
        IsIntRule2Ok(callerMethodName, length); // length >= 0
        IsIntRule3Ok(callerMethodName, first, length, parentRange); // first+length-1 <= rightLimit
        IsIntRule6Ok(this, callerMethodName, first, length, parentRange); //check Singular for non-zeros
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
        log.debug(entering+setFirstMthdName+"("+first+"). old value="+this.first);
        IsIntRule1Ok(setFirstMthdName, parentRange, first); // leftLimit <= first
        IsIntRule3Ok(setFirstMthdName, first, length, parentRange); // first+length-1 <= rightLimit
        IsIntRule6Ok(this, setFirstMthdName, first, length, parentRange); //check Singular for non-zeros
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
        log.debug(entering+setLengthMthdName+"("+length+")");
        IsIntRule2Ok(setLengthMthdName, length); // length >= 0
        IsIntRule3Ok(setLengthMthdName, first, length, parentRange); // first+length-1 <= rightLimit
        IsIntRule6Ok(this, setLengthMthdName, first, length, parentRange); //check Singular for non-zeros
        this.length = length;
    }
    
    public void incLength(int increment) {
        log.debug(entering+incLengthMthdName+"("+increment+"). old length="+length);
        if (IsIntRule2Ok(incLengthMthdName, length+increment)) { // length >= 0
            IsIntRule3Ok(incLengthMthdName, first, length+increment, parentRange); // first+length-1 <= rightLimit
        }
        IsIntRule6Ok(this, incLengthMthdName, first, length, parentRange); //check Singular for non-zeros
        this.length = length + increment;
    }
    
    public NestedIntRange getParentRange() {
        return parentRange;
    }

    public void setParentRange(NestedIntRange parentRange) {
        IsIntRule1Ok(setLeftLimitMthdName, parentRange, first); // leftLimit <= first
        IsIntRule3Ok(setLeftLimitMthdName, first, length, parentRange); // first+length-1 <= rightLimit
        IsIntRule6Ok(this, setLeftLimitMthdName, first, length, parentRange); //check Singular for non-zeros
        this.parentRange = parentRange;
    }

    /**
     * @return the length
     */
    public int getLast() {
        return first+length-1;
    }
        
    @Override
    public NestedIntRange clone() {
        return new NestedIntRange(first, length, parentRange);
        /*
        try {
            NestedIntRange n = (NestedIntRange) super.clone();
            n.init(first, length, leftLimit, rightLimit);
            return n;
        } catch (CloneNotSupportedException ex) {
            log.error(null, ex);
            return new NestedIntRange(first, length, leftLimit, rightLimit);
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
                log.debug(equalsMthdName+"("+o.getClass().getName()+")=FALSE");
                return false;
            } else {
                return (hashCode() == o.hashCode());
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + first;
        hash = 89 * hash + length;
        if (parentRange != null) {
            hash = 89 * hash + parentRange.getFirst();
            hash = 89 * hash + parentRange.getLast();
        } else {
            hash = 89 * hash;
        }
        return hash;
    }
    
    @Override
    public String toString() {
        return "first="+first+", length="+length+", parentRange="+parentRange;
    }
    
    /**
     * Определяет, является ли текущий диапазон вырожденным
     * @return 
     */
    public boolean IsSingular() {
        return ((this == Singular) || ((first == 0) && (length == 0) /*&& (parentRange == null)*/ ));
    }
    
    /**
     * Определяет, входит ли указанная отметка в текущий диапазон
     * @param value
     * @return 
     */
    public boolean IsInbound(int value) {
        log.trace(IsInboundMthdName+"(value="+value+"). first="+first+", last="+getLast());
        return (first <= value) && (value <= getLast());
    }
    
    /**
     * Определяет, накрывает ли целиком указанный диапазон (т.е. помещается ли 
     * текущий диапазон целиком внутри указанного)
     * @param aRange
     * @return 
     */
    public boolean IsInbound(NestedIntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument(IsInboundMthdName);
        } 
        return ((aRange.getFirst() <= first) && (getLast() <= aRange.getLast()));
    }
    
    /*
    Определяет расстояние от указанной точки до до ближайшей границы диапазона
    Если точка указана за макс. границами, то ошибка
    Если точка внутри самого диапазона, то расстояние = 0
    */
    public int getMinDistance(int to) {
        log.trace(getMinDistanceMthdName+"(to="+to+")");
        IsIntRule5Ok(getMinDistanceMthdName, parentRange, to); // leftLimit >= to >= rightLimit
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
        log.trace(getMaxDistanceMthdName+"(to="+to+")");
        IsIntRule5Ok(getMaxDistanceMthdName, parentRange, to); // leftLimit >= to >= rightLimit
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
    public boolean IsOverlapped(NestedIntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument(IsOverlappedMthdName);
        } 
        return !((getLast() < aRange.getFirst()) || (getFirst() > aRange.getLast()));
    }
    
    /**
     * Определяет область пересечения указанного диапазона с текущим
     * @param aRange
     * @return 
   */
    public NestedIntRange Overlap(NestedIntRange aRange) {
        log.trace(OverlapMthdName+"(aRange)");
        if (aRange == null) {
            throw new ENullArgument(OverlapMthdName);
        }
        if (IsOverlapped(aRange)) {
            log.debug("is overlapped");
            int maxStart = Math.max(first, aRange.getFirst());
            int minLast = Math.min(getLast(), aRange.getLast());
            return new NestedIntRange(maxStart, minLast - maxStart + 1, parentRange);
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
    public NestedIntRange Add(NestedIntRange aRange) {
        if (aRange == null) {
            throw new ENullArgument(AddMthdName);
        }
        IsIntRule4Ok(AddMthdName, parentRange, aRange); //parentRange.first >= aRange >= parentRange.last
        int minStart = Math.min(first, aRange.getFirst());
        int maxLast = Math.max(getLast(), aRange.getLast());
        log.debug(AddMthdName+"(). minStart="+minStart+", maxLast="+maxLast);
        return new NestedIntRange(minStart, maxLast - minStart + 1, parentRange);
    }

    /**
     * Продлевает текущий диапазон до указанной точки. 
     * @param to
     * @return 
     */
    public NestedIntRange Extend(int to) {
        IsIntRule5Ok(ExtendMthdName, parentRange, to); // leftLimit >= to >= rightLimit
        if (IsInbound(to)) {
            return Singular;
        } else {
            IsIntRule5Ok(ExtendMthdName, parentRange, to); // leftLimit >= to >= rightLimit
            int minStart = Math.min(first, to);
            int maxLast = Math.max(getLast(), to);
            log.debug(ExtendMthdName+"(). minStart="+minStart+", maxLast="+maxLast);
            return new NestedIntRange(minStart, maxLast - minStart + 1, parentRange);
        }
    }

    /*
    * смещает начало диапазона на указанную величину
    */
    public NestedIntRange Shift(int value) {
        //setFirst(first+value);
        IsIntRule5Ok(ShiftMthdName, parentRange, first+value); // leftLimit >= first+value >= rightLimit
        return new NestedIntRange(first+value, length, parentRange);
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
    public NestedIntRange Sub(NestedIntRange aRange) {
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
            return new NestedIntRange(maxStart, minLast - maxStart);
        } else {
            //если диапазоны не пересекаются
            log.debug("Sub(). Not overlapped. first="+first+", length="+length);
            return new NestedIntRange(first, length);
        }
    */
    }

    /**
     * Возвращает диапазон, который дополняет текущий диапазон до указанной точки. 
     * @param to
     * @return 
     */
    public NestedIntRange Complement(int to) {
        log.trace(ComplementMthdName+"(to="+to+")");
        int dist = getMinDistance(to);
        if (dist < 0) {
            return new NestedIntRange(first + dist, - dist, parentRange);
        } else {
            if (dist > 0) {
                return new NestedIntRange(first + length, dist, parentRange);
            } else {
                return Singular;
            }
        }
    }

}
