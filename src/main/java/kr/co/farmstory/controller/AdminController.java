package kr.co.farmstory.controller;

import kr.co.farmstory.dto.*;
import kr.co.farmstory.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/index")
    public String index(){
        return "/admin/index";
    }

    @GetMapping("/admin/product/list")
    public String productlsit(Model model, ProductPageRequestDTO productPageRequestDTO){

        ProductPageResponseDTO pageResponseDTO = null;
        pageResponseDTO = adminService.selectProductsForAdmin(productPageRequestDTO);

        model.addAttribute(pageResponseDTO);
        log.info("here....!!!"+pageResponseDTO);


        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String productregister(){
        return "/admin/product/register";
    }

    @GetMapping("/admin/order/list")
    public String orderLsit(){
        return "/admin/order/list";
    }

    @GetMapping("/admin/user/list")
    public String userLsit(Model model, UserPageRequestDTO userPageRequestDTO){

        UserPageResponseDTO pageResponseDTO = null;
        pageResponseDTO = adminService.selectsUserForAdmin(userPageRequestDTO);

        model.addAttribute(pageResponseDTO);
        log.info(pageResponseDTO.toString());
        return "/admin/user/list";
    }


}
