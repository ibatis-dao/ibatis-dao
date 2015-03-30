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

import fxapp01.dao.IDAOreadonly;
import fxapp01.excpt.ENullArgument;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.util.ArrayList;
import java.util.List;

/**
 * класс для поддержки списка сортировки в объектах доступа к данным
 * @author serg
 */
public class SortOrder implements IDAOSortOrder {
    
    protected static final ILogger log = LogMgr.getLogger(SortOrder.class);
    private final ArrayList<SortOrderItem> list;
    private IDAOreadonly dao;
    private List<String> daoColumnNames;
    private static final Direction[] allDirs = Direction.values(); //ordered array of all enum values

    private class SortOrderItem {
        private String colName;
        private ISortOrder.Direction direction;
        private boolean isSortable = true; // по умолчанию все колонки можно сортировать
    }

    public SortOrder() {
        this.list = new ArrayList<>();
        this.dao = null;
    }
    
    public SortOrder(IDAOreadonly dao) {
        this();
        setDAO(dao);
    }
    
    public SortOrder(IDAOSortOrder sortOrder) {
        this();
        clone(sortOrder);
    }
    
    // *************** IDAOSortOrder ********************
    
    @Override
    public IDAOreadonly getDAO() {
        return dao;
    }
    
    private void setDAO(IDAOreadonly dao) {
        this.dao = dao;
        if (dao != null) {
            daoColumnNames = dao.getColumnNames();
        } else {
            daoColumnNames = null;
        }
        setIsSortable();
    }

    private boolean isDaoContainsColumnName(String columnName) {
        if (daoColumnNames != null) {
            return daoColumnNames.contains(columnName);
        } else {
            return true;
        }
    }

    private void setIsSortable(){
        for (SortOrderItem item : list) {
            if (item != null) {
                item.isSortable = isDaoContainsColumnName(item.colName); 
            }
        }
    }
    
    // *************** ISortOrder ********************

    private void clone(IDAOSortOrder sortOrder) {
        if (sortOrder != null) {
            clear();
            setDAO(sortOrder.getDAO());
            for (int i = 0; i < sortOrder.size(); i++) {
                add(sortOrder.getName(i), sortOrder.getDirection(i));
            }
        }
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
    public boolean isSortable(int index) {
        SortOrderItem item = list.get(index);
        if (item != null) {
            // TODO добавить фактический обработчик, который будет определять, 
            // можно ли сортировать по указанной колонке
            return item.isSortable; 
        }
        return false;
    }

    @Override
    public String getName(int index) {
        SortOrderItem item = list.get(index);
        if (item != null) {
            return item.colName;
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
        item.colName = columnName;
        item.direction = direction;
        item.isSortable = isDaoContainsColumnName(columnName); 
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
            if ((isSortable(i)) && (getDirection(i) != Direction.NONE)) {
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
        if (allDirs.length != 0) {
            return allDirs[(aDir.ordinal()+1) % allDirs.length];
        } else {
            return null;
        }
    }

    @Override
    public void toggle(int index) {
        SortOrderItem item = list.get(index);
        item.direction = nextEnumValue(item.direction);
        list.set(index, item);
    }
    
}
