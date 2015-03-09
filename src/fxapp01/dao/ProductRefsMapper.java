package fxapp01.dao;

import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.ProductRefs;
import fxapp01.dto.LimitedIntRange;

public interface ProductRefsMapper {
    List<ProductRefs> select(LimitedIntRange rowsrange);
    ProductRefs selectByID(BigInteger id);
    BigInteger selectCount();
    int insertRow(ProductRefs item);
    int insertRowBySP(ProductRefs item);
}
