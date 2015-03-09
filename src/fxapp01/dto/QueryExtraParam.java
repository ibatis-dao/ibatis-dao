package fxapp01.dto;

/**
 *
 * @author serg
 */
public class QueryExtraParam {
    private NestedIntRange rowsrange;
    private SortOrder sortOrder;

    public QueryExtraParam(NestedIntRange rowsrange){
        this(rowsrange, null);
    }
    
    public QueryExtraParam(NestedIntRange rowsrange, SortOrder sortOrder){
        this.rowsrange = rowsrange;
        this.sortOrder = sortOrder;
    }
    
    public NestedIntRange getRowsRange() {
        return rowsrange;
    }

    public void setRowsRange(NestedIntRange rowsrange) {
        this.rowsrange = rowsrange;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

}
