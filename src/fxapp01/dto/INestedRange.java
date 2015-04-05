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
package fxapp01.dto;

import fxapp01.excpt.ENullArgument;
import fxapp01.excpt.EUnsupported;

/**
 *
 * @author serg
 * @param <T>
 */
public interface INestedRange<T extends Number & Comparable<T>> {

    public T getFirst();

    public void setFirst(Number first);
    
    public T getLength();

    public void setLength(Number length);
    
    public void incLength(Number increment);
    
    public INestedRange<T> getParentRange();
    
    public void setParentRange(INestedRange<T> parentRange);
    
    public T getLast();
    
    public INestedRange<T> clone();
    
    public boolean IsSingular();
    
    public boolean IsInbound(Number value);
    
    public boolean IsInbound(INestedRange<T> aRange);
    
    public Number getMinDistance(Number to);
    
    //public T getMinDistance(INestedRange<T> aRange);
    
    public Number getMaxDistance(Number to);
    
    //public T getMaxDistance(INestedRange<T> aRange);

    public boolean IsOverlapped(INestedRange<T> aRange);
    
    public INestedRange<T> Overlap(INestedRange<T> aRange);
    
    public INestedRange<T> Add(INestedRange<T> aRange);
    
    public INestedRange<T> Extend(T to);
    
    public INestedRange<T> Shift(T value);
    
    public INestedRange<T> Complement(Number to);

    public T valueOf(Number v);
    
    public T NumberAdd(Number x, Number y);
    
    public T NumberSub(Number x, Number y);
    
    public int compareXandY(Number x, Number y);

    //public INestedRange Complement(INestedRange<T> aRange);
    
    @SuppressWarnings("unchecked")
    public static Object newInstance(Class rangeKeyClass, Number first, Number length, INestedRange parentRange) throws InstantiationException, IllegalAccessException {
        if (rangeKeyClass != null) {
            if (rangeKeyClass == Integer.class) {
                Integer f = null;
                if (first != null) {
                    f = first.intValue();
                }
                Integer l = null;
                if (length != null) {
                    l = length.intValue();
                }
                return new NestedIntRange(f, l, parentRange);
            }
            if (rangeKeyClass == Long.class) {
                Long f = null;
                if (first != null) {
                    f = first.longValue();
                }
                Long l = null;
                if (length != null) {
                    l = length.longValue();
                }
                return new NestedLongRange(f, l, parentRange);
            }
            throw new EUnsupported("Unsupported range key class "+rangeKeyClass.getName());
        } else {
            throw new ENullArgument("createRangeOf", "rangeKeyClass");
        }
    }
}
