package fxapp01.dto;

/**
 *
 * @author serg
 */
public class ProductRefsQBE extends QueryExtraParam {

    private ProductRefs example;
    
    public ProductRefsQBE(ProductRefs example, NestedIntRange rowsrange) {
        this(example, rowsrange, null);
    }

    public ProductRefsQBE(ProductRefs example, NestedIntRange rowsrange, SortOrder sortOrder) {
        super(rowsrange, sortOrder);
        this.example = example;
    }
    
    public ProductRefs getExample() {
        return this.example;
    }

    public void setExample(ProductRefs example) {
        this.example = example;
    }

}
