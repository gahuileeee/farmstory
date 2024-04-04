package kr.co.farmstory.repository.custum;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.ProductPageRequestDTO;
import kr.co.farmstory.dto.ProductPageResponseDTO;
import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductsRepositoryCustom {

    public Page<Tuple> selectProductsbyCate(ProductPageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> selectProducts(ProductPageRequestDTO pageRequestDTO, Pageable pageable);
}
