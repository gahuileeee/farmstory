package kr.co.farmstory.service;

import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public Products productRegister(ProductsDTO productsDTO){

        Products products = modelMapper.map(productsDTO, Products.class);
        log.info("registerProd....1"+ products);

        Products savedProduct = productRepository.save(products);
        log.info("registerProd....2" + savedProduct);

        return savedProduct;

    }


}
