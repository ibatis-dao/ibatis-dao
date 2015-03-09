package fxapp01.dao;

import java.math.BigInteger;
import java.util.List;
import fxapp01.dto.ProductRefs;
import fxapp01.dto.IntRange;

public interface ProductRefsMapper {
	
	List<ProductRefs> select(IntRange rowsrange);
	ProductRefs selectByID(BigInteger id);
        BigInteger selectCount();

}
