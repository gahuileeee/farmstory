package kr.co.farmstory.service;

import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MarketService {
    private ProductsRepository productsRepository;
    private ModelMapper modelMapper;

    //상품들 조회
    public List<ProductsDTO> selectProducts(){
        log.info(productsRepository.findAll().toString());
        return productsRepository.findAll().stream().map(entity->modelMapper.map(entity, ProductsDTO.class)
        ).toList();
    }

    //상품 하나 조회
    public ProductsDTO selectProduct(int prodNum){
        return modelMapper.map(productsRepository.findById(prodNum) , ProductsDTO.class);
    }

}
