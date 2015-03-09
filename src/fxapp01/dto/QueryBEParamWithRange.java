package fxapp01.dto;

/**
 *
 * @author serg
 */
public class QueryBEParamWithRange<T> {
    private T example;
    private INestedRange rowsrange;

    public QueryBEParamWithRange(T example, INestedRange rowsrange){
        this.example = example;
        this.rowsrange = rowsrange;
    }
    
    public T getExample() {
        return example;
    }

    public void setExample(T example) {
        this.example = example;
    }

    public INestedRange getRowsRange() {
        return rowsrange;
    }

    public void setRowsRange(INestedRange rowsrange) {
        this.rowsrange = rowsrange;
    }
}
