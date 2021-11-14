package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShowViewController {

    @Autowired
    private AuthService authService;

    @GetMapping("/product{id}")
    public String showDetil() {

        return "notPurchasedCourse" ;
    }

    @GetMapping("/course{id}")
    public String showCourse() {

        return "purchasedCourse" ;
    }

    @GetMapping("/shop")
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

    @GetMapping("/check")
    public String checkout() {

        return "checkout" ; // 要導入的html
    }

    @GetMapping("/member")
    public String showMemberInfo() {

        return "memberAreasIntroduction" ; // 要導入的html
    }

    //驗證(OK)
    @GetMapping("/memberVerification")
    public String updateMemberStatus(@RequestParam String code) {
        authService.updateStatus(code);

        return "registated";
    }

    @GetMapping("/NGOrder")
    public String ngOrder() {
        return "memberAreasMyOrderNg";
    }

    @GetMapping("/coachArea")
    public String uploadCourse() {
        return "coachAreasIntroduction";
    }

    @GetMapping("/revisePassword")
    public String revisePassword() {
        return "revisePassword";
    }
}
