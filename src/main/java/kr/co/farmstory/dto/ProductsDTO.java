package kr.co.farmstory.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductsDTO {
    private int prodNo;
    private int cateNo;
    private String prodName;
    private int prodPrice;
    private int prodStock;
    private int prodSold; //판매액
    private int prodDiscount;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;
    private int point;
    private String etc;
    private int delivery;
    private LocalDateTime RegProdDate;

}
