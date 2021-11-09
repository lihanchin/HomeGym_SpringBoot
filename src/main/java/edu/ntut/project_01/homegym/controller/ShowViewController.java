package edu.ntut.project_01.homegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ShowViewController {

//    @GetMapping("/product/{id}")
    @GetMapping("/product{id}")
    public String showDetil() {

        return "notPurchasedCourse" ;
    }

    @GetMapping("/course{id}")
    public String showCourse() {

        return "purchasedCourse" ;
    }

    @GetMapping("/shopping")
    public String show() {

        return "shopping" ;
    }
    @GetMapping("/shoppingCart")
    public String checkOut() {

        return "shoppingCart" ; // 要導入的html
    }

    @GetMapping("/waiting")
    public String showWaitForApplyingForCoach() {

        return "waitForApplyingForCoach" ; // 要導入的html
    }

    @GetMapping("/apply")
    public String showEmptyForm() {

        return "memberAreasApplicationCoach" ; // 要導入的html
    }
}
