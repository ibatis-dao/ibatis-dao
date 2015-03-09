package fxapp01.dto;

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
    private static IntRange Singular = new IntRange(0, 0, 0, 0);

    public IntRange() {
        first = 0;
        length = 0;
        leftLimit = 0;
        rightLimit = Integer.MAX_VALUE;
    }

    public IntRange(int first, int length) {
        if ((first < 0) || (length < 0)) {
            ThrowNegativeArg("Constructor");
        }
        this.first = first;
        this.length = length;
        leftLimit = 0;
        rightLimit = Integer.MAX_VALUE;
    }
    
    public IntRange(int first, int length, int leftLimit, int rightLimit) {
        this(first, length);
        if (first < leftLimit) {
            ThrowArgBreaksRule("Constructor", "leftLimit <= first");
        }
        if (first+length > rightLimit) {
            ThrowArgBreaksRule("Constructor", "first+length <= rightLimit");
        }
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }
    
    private void ThrowArgBreaksRule(String methodName, String rule) {
        IllegalArgumentException e = new IllegalArgumentException("Method "+getClass().getName()+"."+methodName+"() arguments should match rule ("+rule+")");
        log.error("", e);
        throw e;
    }
    
    private void ThrowNegativeArg(String methodName) {
        IllegalArgumentException e = new IllegalArgumentException("Method "+getClass().getName()+"."+methodName+"() arguments must be great than zero");
        log.error("", e);
        throw e;
    }
    
    private void ThrowNullArg(String methodName) {
        IllegalArgumentException e = new IllegalArgumentException("Method "+getClass().getName()+"."+methodName+"() arguments must be not null");
        log.error("", e);
        throw e;
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
            ThrowArgBreaksRule("setFirst", "leftLimit <= first");
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
        if (length < 0) {
            ThrowNegativeArg("setLength");
        }
        if (first+length > rightLimit) {
            ThrowArgBreaksRule("setLength", "first+length <= rightLimit");
        }
        this.length = length;
    }
    
    public int getLeftLimit() {
        return leftLimit;
    }

    public void setLeftLimit(int leftLimit) {
        if (leftLimit <= 0) {
            ThrowNegativeArg("setLeftLimit");
        }
        if (first < leftLimit) {
            ThrowArgBreaksRule("setLeftLimit", "leftLimit <= first");
        }
        this.leftLimit = leftLimit;
    }

    public int getRightLimit() {
        return rightLimit;
    }

    public void setRightLimit(int rightLimit) {
        if (rightLimit <= 0) {
            ThrowNegativeArg("setRightLimit");
        }
        if (first+length > rightLimit) {
            ThrowArgBreaksRule("setRightLimit", "first+length <= rightLimit");
        }
        this.rightLimit = rightLimit;
    }
    /**
     * @return the length
     */
    public int getLast() {
        return first+length-1;
    }
    
    /**
     * Определяет, равны ли (полностью совпадают) указанный диапазон с текущим
     * @param aRange
     * @return 
     */
    public boolean IsEqual(IntRange aRange) {
        if (aRange == null) {
            ThrowNullArg("IsEqual");
        }
        return (first == aRange.getFirst()) && (length == aRange.getLength());
    }
    
    /**
     * Определяет, входит ли указанная отметка в текущий диапазон
     * @param value
     * @return 
     */
    public boolean IsInbound(int value) {
        return (first <= value) && (getLast() >= value);
    }
    
    /**
     * Определяет, пересекаются ли указанный диапазон с текущим
     * @param aRange
     * @return 
     */
    public boolean IsOverlapped(IntRange aRange) {
        if (aRange == null) {
            ThrowNullArg("IsOverlapped");
        } 
        return !((getLast() < aRange.getFirst()) || (getFirst() > aRange.getLast()));
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
            ThrowNullArg("Overlap");
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
            ThrowNullArg("Add");
        }
        int minStart = Math.max(leftLimit, Math.min(first, aRange.getFirst()));
        int maxLast = Math.min(Math.max(getLast(), aRange.getLast()), rightLimit);
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
            ThrowNullArg("Sub");
        }
        if (IsOverlapped(aRange)) {
            //если диапазоны пересекаются
            int maxStart = Math.max(first, aRange.getFirst());
            int minLast = Math.min(getLast(), aRange.getLast());
            return new IntRange(maxStart, minLast - maxStart);
        } else {
            //если диапазоны не пересекаются
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
