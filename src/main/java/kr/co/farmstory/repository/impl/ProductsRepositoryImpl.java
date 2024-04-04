package kr.co.farmstory.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.ProductPageRequestDTO;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.entity.QCategories;
import kr.co.farmstory.entity.QProducts;
import kr.co.farmstory.entity.QUser;
import kr.co.farmstory.repository.custum.ProductsRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private QProducts qProducts = QProducts.products;
    private QCategories qCategories = QCategories.categories;
    private final ModelMapper modelMapper;

    @Override
    public Page<Tuple> selectProductsbyCate(ProductPageRequestDTO pageRequestDTO, Pageable pageable) {
        String cate =pageRequestDTO.getCate();

        //부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qProducts, qCategories.cateName)
                .from(qProducts)
                .join(qCategories)
                .on(qProducts.cateNo.eq(qCategories.cateNo))
                .where(qProducts.cateNo.eq(Integer.parseInt(cate)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Tuple> content  =results.getResults();

        long total = results.getTotal();

        //페이징 처리를 위해 page 객체 리턴
        return  new PageImpl<>(content, pageable, total);
    };

    @Override
    public Page<Tuple> selectProducts(ProductPageRequestDTO pageRequestDTO, Pageable pageable) {

        //부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qProducts, qCategories.cateName)
                .from(qProducts)
                .join(qCategories)
                .on(qProducts.cateNo.eq(qCategories.cateNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qProducts.prodNo.desc())
                .fetchResults();

        List<Tuple> content =results.getResults();
        long total = results.getTotal();

        //페이징 처리를 위해 page 객체 리턴
        return  new PageImpl<>(content, pageable, total);
    }
}
