package kr.co.farmstory.repository.custum;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductsRepositoryCustom {

    public Page<Products> selectProductsbyCate(PageRequestDTO pageRequestDTO, Pageable pageable);
    // PageRequestDTO에
    //  private  String type;
    //  private  String keyword; 추가

    public Page<Tuple> selectProducts(PageRequestDTO pageRequestDTO, Pageable pageable);
}
