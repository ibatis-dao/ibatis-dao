package fxapp01.dto;

/**
 *
 * @author StarukhSA
 * @param <T>
 */
public interface INestedRange<T extends Number> {

    public T getFirst();

    public void setFirst(T first);
    
    public T getLength();

    public void setLength(T length);
    
    public void incLength(T increment);
    
    public INestedRange getParentRange();
    
    public void setParentRange(INestedRange parentRange);
    
    public T getLast();
    
    public boolean IsSingular();
    
    public boolean IsInbound(T value);
    
    public boolean IsInbound(INestedRange aRange);
    
    public T getMinDistance(T to);
    
    public T getMaxDistance(T to);
    
    public boolean IsOverlapped(INestedRange aRange);
    
    public INestedRange Overlap(INestedRange aRange);
    
    public INestedRange Add(INestedRange aRange);
    
    public INestedRange Extend(T to);
    
    public INestedRange Shift(T value);
    
    public INestedRange Complement(T to);
}
