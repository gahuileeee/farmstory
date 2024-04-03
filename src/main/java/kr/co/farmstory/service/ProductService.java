package kr.co.farmstory.service;

import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;
    private final ProductsRepository productsRepository;

    // 블록타입으로 저장된 사진을 set 하기위해 Products 엔티티에 setter 추가함
    public Products productRegister(ProductsDTO productsDTO) throws IOException {

        Products products = modelMapper.map(productsDTO, Products.class);

        String image1 =  productsDTO.getImage1();
        String image2 =  productsDTO.getImage2();
        String image3 =  productsDTO.getImage3();

        if (image1 != null && !image1.isEmpty()) {
            byte[] image1Bytes = image1.getBytes();
            String image1Base64 = Base64.getEncoder().encodeToString(image1Bytes);
            products.setImage1(image1Base64);
        }

        if (image2 != null && !image2.isEmpty()) {
            byte[] image2Bytes = image2.getBytes();
            String image2Base64 = Base64.getEncoder().encodeToString(image2Bytes);
            products.setImage2(image2Base64);
        }

        if (image3 != null && !image3.isEmpty()) {
            byte[] image3Bytes = image3.getBytes();
            String image3Base64 = Base64.getEncoder().encodeToString(image3Bytes);
            products.setImage3(image3Base64);
        }


        log.info("registerProd....1"+ products);

        Products savedProduct = productsRepository.save(products);
        log.info("registerProd....2" + savedProduct);

        return savedProduct;

    }


}
