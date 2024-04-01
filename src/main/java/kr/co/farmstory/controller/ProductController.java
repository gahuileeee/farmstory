package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;


    public void productList(){}

    @PostMapping("/product/register")
    public String productRegister(Model model, HttpServletRequest req, ProductsDTO productsDTO){

        log.info("Controller.productsDTO....:" + productsDTO.toString());

        Products products = productService.productRegister(productsDTO);

        return "redirect:/admin/product/list";

    }

}
