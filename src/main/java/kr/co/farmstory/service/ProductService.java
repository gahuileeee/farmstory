package kr.co.farmstory.service;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.farmstory.dto.FileDTO;
import kr.co.farmstory.dto.ProdImageDTO;
import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.ProdImage;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.repository.ProdImageRepository;
import kr.co.farmstory.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;
    private final ProductsRepository productsRepository;
    private final ProdImageRepository prodImageRepository;

    public Products productRegister(ProductsDTO productsDTO) {

        Products products = modelMapper.map(productsDTO, Products.class);


        MultipartFile image1 = productsDTO.getMultImage1();
        MultipartFile image2 = productsDTO.getMultImage2();
        MultipartFile image3 = productsDTO.getMultImage3();

        List<MultipartFile> files = List.of(image1, image2, image3);

        List<ProdImageDTO> uploadedImages = fileUpload(files);
        //image1,2,3 set 해서 sname 넣기.
        if(!uploadedImages .isEmpty()){
            for (int i = 0; i < uploadedImages.size(); i++) {
                ProdImageDTO imageDTO = uploadedImages.get(i);
                if (i == 0) {
                    products.setImage1(imageDTO.getSName());
                } else if (i == 1) {
                    products.setImage2(imageDTO.getSName());
                } else if (i == 2) {
                    products.setImage3(imageDTO.getSName());
                }
            }
        }



        /*
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
        */


        log.info("registerProd....1"+ products);

        Products savedProduct = productsRepository.save(products);
        log.info("registerProd....2" + savedProduct);

        for (ProdImageDTO prodImageDTO : uploadedImages){
            prodImageDTO.setPNo(savedProduct.getProdNo());

            ProdImage prodImage = modelMapper.map(prodImageDTO, ProdImage.class);
            prodImageRepository.save(prodImage);
        }

        return savedProduct;

    }

    public ResponseEntity<?> deleteProducts(int prodNo){
        Optional<Products> optProducts = productsRepository.findById(prodNo);
        log.info("deleteProdAtService..1:"+optProducts);

        if (optProducts.isPresent()){
            productsRepository.deleteById(prodNo);
            log.info("deleteProdAtService..2:"+prodNo);
            return ResponseEntity
                    .ok()
                    .body(optProducts.get());

        }else {
            log.info("deleteProdAtService..3:");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }

    }

    @Value("${file.upload.path}")
    private  String fileUploadPath;

    public List<ProdImageDTO> fileUpload(List<MultipartFile> files){
        String path = new File(fileUploadPath).getAbsolutePath();

        // 이미지 정보 리턴을 위한 리스트
        List<ProdImageDTO> imageDTOS = new ArrayList<>();

        log.info("fileUploadPath..1 : " + path);

        for(MultipartFile mf : files){
            log.info("oName..2 : ");

            if(!mf.isEmpty()){

                String oName = mf.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf(".")); //확장자
                String sName = UUID.randomUUID().toString()+ ext;

                log.info("sName..3 : "+sName);

                try {
                    // 저장
                    mf.transferTo(new File(path, sName));

                    //파일 정보 생성(imageDB 에 들어갈)
                    ProdImageDTO prodImageDTO = ProdImageDTO.builder()
                            .oName(oName)
                            .sName(sName)
                            .build();

                    imageDTOS.add(prodImageDTO);
                    log.info("fileUpload....4:" + prodImageDTO);

                }catch (IOException e){
                    log.error("fileUpload : " + e.getMessage());
                }
            }
        }

        return imageDTOS;
    }


}