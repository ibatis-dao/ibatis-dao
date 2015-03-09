package fxapp01.dto;

/**
 *
 * @author serg
 */
public class QueryExtraParam {
    private NestedIntRange rowsrange;
    private String sortOrder;

    public QueryExtraParam(NestedIntRange rowsrange){
        this(rowsrange, null);
    }
    
    public QueryExtraParam(NestedIntRange rowsrange, String sortOrder){
        this.rowsrange = rowsrange;
        this.sortOrder = sortOrder;
    }
    
    public NestedIntRange getRowsRange() {
        return rowsrange;
    }

    public void setRowsRange(NestedIntRange rowsrange) {
        this.rowsrange = rowsrange;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}
