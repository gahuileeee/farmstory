package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Products;
import kr.co.farmstory.repository.custum.ProductsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer > , ProductsRepositoryCustom {
}
