package kr.co.farmstory.repository.custum;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.ProductPageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductsRepositoryCustom {

    public Page<Tuple> selectProductsbyCate(ProductPageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> selectProducts(ProductPageRequestDTO pageRequestDTO, Pageable pageable);
}
