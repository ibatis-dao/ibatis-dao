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
package fxapp01.dao.sort;

import fxapp01.dto.ISortOrder;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.util.ArrayList;

/**
 *
 * @author serg
 */
public class SortOrder implements ISortOrder {
    
    private static final ILogger log = LogMgr.getLogger(SortOrder.class);
    private final ArrayList<SortOrderItem> list = new ArrayList<>();
    private static final Direction[] allDirs = Direction.values(); //ordered array of all enum values

    private class SortOrderItem {
        private String colname;
        private ISortOrder.Direction direction;
    }

    @Override
    public String toString() {
        return build();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String getName(int index) {
        SortOrderItem item = list.get(index);
        if (item != null) {
            return item.colname;
        }
        return null;
    }

    @Override
    public Direction getDirection(int index) {
        SortOrderItem item = list.get(index);
        if (item != null) {
            return item.direction;
        }
        return null;
    }

    @Override
    public void add(String columnName, Direction direction) {
        if ((columnName == null) || (columnName.isEmpty()) || (direction == null)) {
            throw new ENullArgument("add");
        }
        SortOrderItem item = new SortOrderItem();
        item.colname = columnName;
        item.direction = direction;
        list.add(item);
    }

    @Override
    public boolean del(int index) {
        return (list.remove(index) != null);
    }

    @Override
    public void clear() {
        list.clear();
    }
    
    @Override
    public String build() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            if (getDirection(i) != Direction.NONE) {
                String s = getName(i) + " " + getDirection(i).toString();
                if (result.isEmpty()) {
                    result = s;
                } else {
                    result = result + ", " + s;
                }
            }
        }
        return result;
    }
    
    private Direction nextEnumValue(Direction aDir) {
        if (aDir == null) {
            throw new ENullArgument("nextEnumValue");
        }
        assert(allDirs.length != 0);
        return allDirs[(aDir.ordinal()+1) % allDirs.length];
    }

    @Override
    public void toggle(int index) {
        SortOrderItem item = list.get(index);
        item.direction = nextEnumValue(item.direction);
        list.set(index, item);
    }
    
}
