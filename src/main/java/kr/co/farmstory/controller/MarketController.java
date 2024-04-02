package kr.co.farmstory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.farmstory.dto.CartsDTO;
import kr.co.farmstory.dto.CategoriesDTO;
import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.repository.ProductsRepository;
import kr.co.farmstory.service.MarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.angus.mail.imap.protocol.MODSEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        List<CategoriesDTO> cates = marketService.selectCategoriesByProduct(products);
        log.info(cates.toString());
        model.addAttribute("cates", cates);
        return "/market/list";
    }


    //상품 상세 리스트
    @GetMapping("/market/view")
    public  String marketView(Model model, @RequestParam("prodNum") int prodNum){
        model.addAttribute("product",marketService.selectProduct(prodNum));
        return "/market/view";
    }


    //장바구니 구현

    //장바구니 페이지 조회
    @GetMapping("/market/cart")
    public  String marketCart(String user , Model model){
        log.info(user+"!!");
        //user에 있는 장바구니 조회
        List<CartsDTO> carts = marketService.selectCartsByUser(user);
        model.addAttribute("carts", carts);
        log.info(carts+"!!");

        //그 상품들 조회
        List<ProductsDTO> products = marketService.selectProductsByCart(carts);
        model.addAttribute("products" , products);
        //카테고리 조회 (아무리 생각해도 비효율적..)
        List<CategoriesDTO> cates = marketService.selectCategoriesByProduct(products);
        model.addAttribute("cates", cates);

        return "/market/cart";
    }


    //장바구니 넣기
    @ResponseBody
    @PostMapping("/market/cart")
    public ResponseEntity marketCart(@RequestBody Map<String, String> map){
        int no = Integer.parseInt(map.get("prodNo"));
        int count = Integer.parseInt(map.get("count"));
        String id = map.get("userId");
        CartsDTO cart = CartsDTO.builder()
                                .userId(id)
                                .prodNo(no)
                                .cartProdCount(count)
                                .build();
        return marketService.insertCarts(cart);
    }

    //장바구니 삭제하기
    @ResponseBody
    @PutMapping("/market/cart/delete")
    public ResponseEntity marketCartDelete(@RequestBody Map<String, List<Integer>> map){
        log.info(map+"카트 삭제!!");
        List<Integer> cartNos = map.get("list");
        log.info(cartNos+"!!");
        return  marketService.deleteCarts(cartNos);
    }

    //결제 페이지 구현

    //결제 페이지 조회
    @PostMapping("/market/order")
    public String marketBuy(@RequestParam("jsonData") String jsonData, Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("!!!!");
        log.info(jsonData);

            Map<String, List<String>> dataMap = objectMapper.readValue(jsonData, Map.class);
            List<Integer> carts = dataMap.get("cartCountsList").stream().map(a -> Integer.parseInt(a)).toList();
            model.addAttribute("carts" , carts);
            log.info(carts.toString() );
            log.info("here2");

        List<Integer> prodNos = dataMap.get("prodNosList").stream().map(a -> Integer.parseInt(a)).toList();
            List<ProductsDTO> products = marketService.selectProductsByProdNum(prodNos);
            model.addAttribute("products" , products);
            //카테고리 조회 (아무리 생각해도 비효율적..)
            List<CategoriesDTO> cates = marketService.selectCategoriesByProduct(products);
            log.info(cates.toString());
            log.info("here!!");
            model.addAttribute("cates", cates);

        return "/market/order";
    }

    //최종 결제 구현 (장바구니 삭제까지)

}
