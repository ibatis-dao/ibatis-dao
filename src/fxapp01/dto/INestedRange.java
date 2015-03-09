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
    
    public INestedRange<T> getParentRange();
    
    public void setParentRange(INestedRange<T> parentRange);
    
    public T getLast();
    
    public INestedRange<T> clone();
    
    public boolean IsSingular();
    
    public boolean IsInbound(T value);
    
    public boolean IsInbound(INestedRange<T> aRange);
    
    public T getMinDistance(T to);
    
    //public T getMinDistance(INestedRange<T> aRange);
    
    public T getMaxDistance(T to);
    
    //public T getMaxDistance(INestedRange<T> aRange);

    public boolean IsOverlapped(INestedRange<T> aRange);
    
    public INestedRange<T> Overlap(INestedRange<T> aRange);
    
    public INestedRange<T> Add(INestedRange<T> aRange);
    
    public INestedRange<T> Extend(T to);
    
    public INestedRange<T> Shift(T value);
    
    public INestedRange<T> Complement(T to);

    //public INestedRange Complement(INestedRange<T> aRange);
}
