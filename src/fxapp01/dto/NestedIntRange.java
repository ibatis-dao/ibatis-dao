package fxapp01.dto;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.excpt.ENegativeArgument;
import fxapp01.excpt.ENullArgument;
import fxapp01.excpt.EUnsupported;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.util.Objects;

/**
 *
 * @author serg
 * @param <<error>>
 */
public class NestedIntRange implements INestedRange<java.lang.Integer> {
    
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

    private Integer first;
    private Integer length;
    private NestedIntRange parentRange;
    private static final INestedRange Singular = new SingularIntRange();
    
    public NestedIntRange(Integer first, Integer length, NestedIntRange parentRange) {
        init(first, length, null);
        log.trace(entering+contructorMthdName+"(first="+first+", length="+length+", parentRange="+parentRange+")");
        this.parentRange = parentRange;
    }
    /*
    private void init(Integer first, Integer length) {
        init(first, length, null);
    }
    */
    private void init(Integer first, Integer length, NestedIntRange parentRange) {
        this.first = first;
        this.length = length;
        this.parentRange = parentRange;
        IsInternalRulesOk("init(first, length, parentRange)");
    }
    
    private static boolean IsIntRule1Ok(final String callerMethodName, NestedIntRange parentRange, Integer first) throws EArgumentBreaksRule {
        if (parentRange != null) {
            if (first < parentRange.getFirst()) {
                throw new EArgumentBreaksRule(callerMethodName, "parentRange.first <= first");
            }
        }
        return true;
    }

    private static boolean IsIntRule2Ok(final String callerMethodName, Integer length) throws ENegativeArgument {
        if (length < 0) {
            throw new ENegativeArgument(callerMethodName);
        }
        return true;
    }

    private static boolean IsIntRule3Ok(final String callerMethodName, Integer first, Integer length, NestedIntRange parentRange) throws EArgumentBreaksRule {
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
    
    private static boolean IsIntRule5Ok(final String callerMethodName, NestedIntRange parentRange, Integer to) throws EArgumentBreaksRule {
        if (parentRange != null) {
            if ((to < parentRange.getFirst()) || (to > parentRange.getLast())) {
                throw new EArgumentBreaksRule(callerMethodName, "parentRange.first >= to >= parentRange.last");
            }
        }
        return true;
    }
    
    private boolean IsInternalRulesOk(final String callerMethodName) {
        IsIntRule1Ok(callerMethodName, parentRange, first); // leftLimit <= first
        IsIntRule2Ok(callerMethodName, length); // length >= 0
        IsIntRule3Ok(callerMethodName, first, length, parentRange); // first+length-1 <= rightLimit
        IsIntRule4Ok(callerMethodName, parentRange, this); //parentRange.first >= aRange >= parentRange.last
        return true;
    }

    /**
     * @return the first
     */
    @Override
    public Integer getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    @Override
    public void setFirst(Integer first) {
        log.debug(entering+setFirstMthdName+"("+first+"). old value="+this.first);
        IsIntRule1Ok(setFirstMthdName, parentRange, first); // leftLimit <= first
        IsIntRule3Ok(setFirstMthdName, first, length, parentRange); // first+length-1 <= rightLimit
        this.first = first;
    }

    /**
     * @return the length
     */
    @Override
    public Integer getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    @Override
    public void setLength(Integer length) {
        log.debug(entering+setLengthMthdName+"("+length+")");
        IsIntRule2Ok(setLengthMthdName, length); // length >= 0
        IsIntRule3Ok(setLengthMthdName, first, length, parentRange); // first+length-1 <= rightLimit
        this.length = length;
    }
    
    @Override
    public void incLength(Integer increment) {
        log.debug(entering+incLengthMthdName+"("+increment+"). old length="+length);
        if (IsIntRule2Ok(incLengthMthdName, length+increment)) { // length >= 0
            IsIntRule3Ok(incLengthMthdName, first, length+increment, parentRange); // first+length-1 <= rightLimit
        }
        this.length = length + increment;
    }
    
    @Override
    public NestedIntRange getParentRange() {
        return parentRange;
    }
    
    private static NestedIntRange castItf(INestedRange itf) {
        if (itf != null) {
            if (itf.getClass().isAssignableFrom(NestedIntRange.class)) {
                return (NestedIntRange)itf;
            } else {
                throw new ClassCastException();
            }
        } else {
            return null;
        }
    }

    @Override
    public void setParentRange(INestedRange parentRange) {
        NestedIntRange p = castItf(parentRange);
        IsIntRule1Ok(setLeftLimitMthdName, p, first); // leftLimit <= first
        IsIntRule3Ok(setLeftLimitMthdName, first, length, p); // first+length-1 <= rightLimit
        this.parentRange = p;
    }

    /**
     * @return the length
     */
    @Override
    public Integer getLast() {
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
                NestedIntRange r = (NestedIntRange)o;
                return (Objects.equals(parentRange, r.getParentRange()) && Objects.equals(first, r.getFirst()) && Objects.equals(length, r.getLength()));
                //return (hashCode() == o.hashCode());
            }
        }
    }

    @Override
    public int hashCode() {
        Integer hash = 5;
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
    @Override
    public boolean IsSingular() {
        return ((this == Singular) || ((first == 0) && (length == 0) /*&& (parentRange == null)*/ ));
    }
    
    /**
     * Определяет, входит ли указанная отметка в текущий диапазон
     * @param value
     * @return 
     */
    @Override
    public boolean IsInbound(Integer value) {
        log.trace(IsInboundMthdName+"(value="+value+"). first="+first+", last="+getLast());
        return (first <= value) && (value <= getLast());
    }
    
    /**
     * Определяет, накрывает ли целиком указанный диапазон (т.е. помещается ли 
     * текущий диапазон целиком внутри указанного)
     * @param aRange
     * @return 
     */
    @Override
    public boolean IsInbound(INestedRange aRange) {
        if (aRange == null) {
            throw new ENullArgument(IsInboundMthdName);
        } 
        return ((aRange.getFirst().intValue() <= first) && (getLast() <= aRange.getLast().intValue()));
    }
    
    /*
    Определяет расстояние от указанной точки до до ближайшей границы диапазона
    Если точка указана за макс. границами, то ошибка
    Если точка внутри самого диапазона, то расстояние = 0
    */
    @Override
    public Integer getMinDistance(Integer to) {
        log.trace(getMinDistanceMthdName+"(to="+to+")");
        IsIntRule5Ok(getMinDistanceMthdName, parentRange, to); // leftLimit >= to >= rightLimit
        if (IsInbound(to)) {
            return 0;
        } else {
            Integer dist = Math.min(Math.abs(to - first), Math.abs(to - getLast()));
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
    @Override
    public Integer getMaxDistance(Integer to) {
        log.trace(getMaxDistanceMthdName+"(to="+to+")");
        IsIntRule5Ok(getMaxDistanceMthdName, parentRange, to); // leftLimit >= to >= rightLimit
        if (IsInbound(to)) {
            return 0;
        } else {
            Integer dist = Math.max(Math.abs(to - first), Math.abs(to - getLast()));
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
    @Override
    public boolean IsOverlapped(INestedRange aRange) {
        if (aRange == null) {
            throw new ENullArgument(IsOverlappedMthdName);
        } 
        return !((getLast() < aRange.getFirst().intValue()) || (getFirst() > aRange.getLast().intValue()));
    }
    
    /**
     * Определяет область пересечения указанного диапазона с текущим
     * @param aRange
     * @return 
   */
    @Override
    public INestedRange Overlap(INestedRange aRange) {
        log.trace(OverlapMthdName+"(aRange)");
        if (aRange == null) {
            throw new ENullArgument(OverlapMthdName);
        }
        if (IsOverlapped(aRange)) {
            log.debug("is overlapped");
            Integer maxStart = Math.max(first, aRange.getFirst().intValue());
            Integer minLast = Math.min(getLast(), aRange.getLast().intValue());
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
    @Override
    public INestedRange Add(INestedRange aRange) {
        if (aRange == null) {
            throw new ENullArgument(AddMthdName);
        }
        NestedIntRange r = castItf(aRange);
        IsIntRule4Ok(AddMthdName, parentRange, r); //parentRange.first >= aRange >= parentRange.last
        Integer minStart = Math.min(first, r.getFirst());
        Integer maxLast = Math.max(getLast(), r.getLast());
        log.debug(AddMthdName+"(). minStart="+minStart+", maxLast="+maxLast);
        return new NestedIntRange(minStart, maxLast - minStart + 1, parentRange);
    }

    /**
     * Продлевает текущий диапазон до указанной точки. 
     * @param to
     * @return 
     */
    @Override
    public INestedRange Extend(Integer to) {
        IsIntRule5Ok(ExtendMthdName, parentRange, to); // leftLimit >= to >= rightLimit
        if (IsInbound(to)) {
            return Singular;
        } else {
            IsIntRule5Ok(ExtendMthdName, parentRange, to); // leftLimit >= to >= rightLimit
            Integer minStart = Math.min(first, to);
            Integer maxLast = Math.max(getLast(), to);
            log.debug(ExtendMthdName+"(). minStart="+minStart+", maxLast="+maxLast);
            return new NestedIntRange(minStart, maxLast - minStart + 1, parentRange);
        }
    }

    /*
    * смещает начало диапазона на указанную величину
    */
    @Override
    public NestedIntRange Shift(Integer value) {
        //setFirst(first+value);
        IsIntRule5Ok(ShiftMthdName, parentRange, first+value); // leftLimit >= first+value >= rightLimit
        return new NestedIntRange(first+value, length, parentRange);
    }
    
    /**
     * Возвращает диапазон, который дополняет текущий диапазон до указанной точки. 
     * @param to
     * @return 
     */
    @Override
    public INestedRange Complement(Integer to) {
        log.trace(ComplementMthdName+"(to="+to+")");
        Integer dist = getMinDistance(to);
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
