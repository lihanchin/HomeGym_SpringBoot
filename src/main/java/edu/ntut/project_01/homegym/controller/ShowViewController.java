package edu.ntut.project_01.homegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ShowViewController {


    @GetMapping("/product{id}")
    public String showDetil() {

        return "notPurchasedCourse" ; //未購買課程畫面
    }

    @GetMapping("/course{id}")
    public String showCourse() {

        return "purchasedCourse" ; //課程畫面
    }

    @GetMapping("/shopping")
    public String show() {

        return "shopping" ; //商城
    }
    @GetMapping("/shoppingCart")
    public String checkOut() {

        return "shoppingCart" ; //購物車
    }

    @GetMapping("/waiting")
    public String showWaitForApplyingForCoach() {

        return "waitForApplyingForCoach" ; //成為教練_顯示申請中
    }

    @GetMapping("/apply")
    public String showEmptyForm() {

        return "memberAreasApplicationCoach" ; //成為教練
    }

    @GetMapping("/check")
    public String checkout() {

        return "checkout" ; //金流結帳畫面
    }

    @GetMapping("/NGOrder")
    public String ngOrder() {

        return "memberAreasMyOrderNg" ; //訂單交易失敗
    }


    @GetMapping("/OKOrderShow")
    public String okOrder() {

        return "memberAreasMyOrderOk" ; //訂單交易成功
    }




}
