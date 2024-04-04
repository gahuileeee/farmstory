package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;


    public void productList(){}

    @PostMapping("/product/register")
    public String productRegister(Model model, HttpServletRequest req, ProductsDTO productsDTO) throws IOException{

        log.info("Controller.productsDTO....:" + productsDTO.toString());

        Products products = productService.productRegister(productsDTO);

        return "redirect:/admin/product/list";

    }

    @PutMapping("admin/product/{selectedProdList}")
    public ResponseEntity<?> deleteProd(@RequestBody Map<String, List<Integer>> map, HttpServletRequest req){

        List<Integer> proNoList = map.get("prodNo");
        Map<String, String> response = new HashMap<>();
        try {
            for (Integer prodNo : proNoList){
                productService.deleteProducts(req, prodNo);
                log.info("deleteProd..!"+prodNo);
                response.put("message", "상품이 성공적으로 삭제되었습니다.");
            }
            return ResponseEntity
                    .ok()
                    .body(response);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "상품 삭제에 실패했습니다.");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorResponse);
        }


    }

}
