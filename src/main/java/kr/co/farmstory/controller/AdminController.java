package kr.co.farmstory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    @GetMapping("/admin/index")
    public String index(){
        return "/admin/index";
    }

    @GetMapping("/admin/product/list")
    public String productlsit(){
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
    public String userLsit(){
        return "/admin/user/list";
    }


}
