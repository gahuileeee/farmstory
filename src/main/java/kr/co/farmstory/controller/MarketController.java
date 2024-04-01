package kr.co.farmstory.controller;

import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.repository.ProductsRepository;
import kr.co.farmstory.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor

public class MarketController {
    @Autowired
    private MarketService marketService;

    //상품 정보 리스트
    @GetMapping("/market/list")
    public  String marketList(Model model){
        List<ProductsDTO> products = marketService.selectProducts();
        model.addAttribute("products", products);
        log.info(products.toString());
        return "/market/list";
    }

    //상품 상세 리스트
    @GetMapping("/market/view")
    public  String marketView(Model model, @RequestParam("prodNum") int prodNum){
        log.info("여기ㅣ기!!");
        model.addAttribute("product",marketService.selectProduct(prodNum));
        return "/market/view";
    }

}
