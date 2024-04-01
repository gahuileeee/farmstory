package kr.co.farmstory.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoriesDTO {
    private int catNo;
    private  String cateName;
}
