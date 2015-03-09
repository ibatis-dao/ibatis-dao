package fxapp01.dto;

import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author serg
 */
public class SortOrder implements ISortOrder {
    
    private static final ILogger log = LogMgr.getLogger(SortOrder.class);
    private final ArrayList<SortOrderItem> list = new ArrayList();
    
    private class SortOrderItem {
        private String colname;
        private ISortOrder.Direction direction;
    }

    @Override
    public String build() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String result = null;
        for (int i = 0; i < list.size(); i++) {
            if (getDirection(i) != ISortOrder.Direction.NONE) {
                if (result == null) {
                    result = getName(i) + " " + getDirection(i).toString();
                } else {
                    result = result + ", " + getName(i) + " " + getDirection(i).toString();
                }
            }
        }
        return result;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String getName(int index) {
        return list.get(index).colname;
    }

    @Override
    public Direction getDirection(int index) {
        return list.get(index).direction;
    }

    @Override
    public void add(String columnName, Direction direction) {
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
    
}
