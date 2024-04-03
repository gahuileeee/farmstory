package kr.co.farmstory.service;

import kr.co.farmstory.dto.*;
import kr.co.farmstory.dto.ProductPageResponseDTO;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.entity.User;
import kr.co.farmstory.repository.ProductsRepository;
import kr.co.farmstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

    private final ProductsRepository productsRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProductPageResponseDTO selectProductsForAdmin(ProductPageRequestDTO productPageRequestDTO) {
        Pageable pageable = productPageRequestDTO.getPageable("prodNo");
        Page<Products> pageProducts = productsRepository.findAll(pageable);
        log.info("selectAllProd...:" + pageProducts);

        List<ProductsDTO> dtoList = pageProducts.getContent().stream()
                .map(entity -> modelMapper.map(entity, ProductsDTO.class))
                .toList();
        log.info("selectAllProd...2:" + dtoList);

        int total = (int) pageProducts.getTotalElements();

        return ProductPageResponseDTO.builder()
                .productPageRequestDTO(productPageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    public UserPageResponseDTO selectsUserForAdmin(UserPageRequestDTO userPageRequestDTO){
        String sort = "regDate";
        Pageable pageable = userPageRequestDTO.getPageable(sort);
        Page<User> pageUsers = userRepository.findAll(pageable);

        List<UserDTO> dtoList = pageUsers.getContent().stream()
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .toList();
        int total = (int) pageUsers.getTotalElements();

        return UserPageResponseDTO.builder()
                .userPageRequestDTO(userPageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }


}
