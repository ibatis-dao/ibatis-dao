package fxapp01.dao;

import fxapp01.dto.INestedRange;
import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.ProductRefs;

public interface ProductRefsMapper {
    List<ProductRefs> select(INestedRange rowsrange);
    ProductRefs selectByID(BigInteger id);
    BigInteger selectCount();
    int insertRow(ProductRefs item);
    int insertRowBySP(ProductRefs item);
}
