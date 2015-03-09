package fxapp01.dao;

import fxapp01.dto.INestedRange;
import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.ProductRefs;
import fxapp01.dto.ProductRefsQBE;

public interface ProductRefsMapper {
    List<ProductRefs> select(INestedRange rowsrange);
    ProductRefs selectByID(BigInteger id);
    INestedRange selectTotalRange();
    List<ProductRefs> selectBE(ProductRefsQBE qbe);
    int insertRow(ProductRefs item);
    ProductRefs insertRowBySP(ProductRefs item);
    ProductRefs insertRowBySP2(ProductRefs item);
}
