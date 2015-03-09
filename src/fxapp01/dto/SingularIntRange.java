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

/**
 *
 * @author StarukhSA
 */
public class SingularIntRange implements INestedRange<Integer> {

    private static final String ns = "Singular range does not support any operation on it.";
    
    SingularIntRange() {
    }

    @Override
    public Integer getFirst() {
        return 0;
    }

    @Override
    public void setFirst(Integer first) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public Integer getLength() {
        return 0;
    }

    @Override
    public void setLength(Integer length) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public void incLength(Integer increment) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public INestedRange<Integer> getParentRange() {
        return null;
    }

    @Override
    public void setParentRange(INestedRange<Integer> parentRange) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public Integer getLast() {
        return 0;
    }

    @Override
    public boolean IsSingular() {
        return true;
    }

    @Override
    public boolean IsInbound(Integer value) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public boolean IsInbound(INestedRange<Integer> aRange) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public Integer getMinDistance(Integer to) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public Integer getMaxDistance(Integer to) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public boolean IsOverlapped(INestedRange<Integer> aRange) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public INestedRange<Integer> Overlap(INestedRange<Integer> aRange) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public INestedRange<Integer> Add(INestedRange<Integer> aRange) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public INestedRange<Integer> Extend(Integer to) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public INestedRange<Integer> Shift(Integer value) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public INestedRange<Integer> Complement(Integer to) {
        throw new UnsupportedOperationException(ns);
    }

    @Override
    public INestedRange<Integer> clone() {
        throw new UnsupportedOperationException(ns);
    }
    
}
