package kr.co.farmstory.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoriesDTO {
    private int cateNo;
    private  String cateName;
}
