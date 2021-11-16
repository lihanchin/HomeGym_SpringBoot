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

        return "notPurchasedCourse" ; //未購買課程畫面
    }

    @GetMapping("/course{id}")
    public String showCourse() {

        return "purchasedCourse" ; //課程畫面
    }

    @GetMapping("/shop")
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

        return "memberAreaApplyForCoach"; //成為教練
    }

    @GetMapping("/check")
    public String checkout() {

        return "checkout" ; //金流結帳畫面
    }

    @GetMapping("/NGOrder")
    public String ngOrder() {
        return "memberAreaMyOrderNg"; //訂單交易失敗
    }

	@GetMapping("/OKOrder")
    public String okOrder() {
        return "memberAreaMyOrderOk"; //訂單交易成功
    }

    @GetMapping("/member")
    public String showMemberInfo() {

        return "memberAreaIntroduction" ; // 要導入的html
    }

    @GetMapping("/revisePassword")
    public String revisePassword() {
        return "memberAreaRevisePassword";
    }

    //驗證(OK)
    @GetMapping("/memberVerification")
    public String updateMemberStatus(@RequestParam String code) {
        authService.updateStatus(code);
		return "registated";
	}

    @GetMapping("/coachArea")
    public String coachAreasIntroduction() {

        return "coachAreaIntroduction"; //教練專區
    }


    @GetMapping("/CoachCourse")
    public String coach() {

        return "coachAreaUploadedCourse"; //教練專區
    }


    @GetMapping("/myCourse")
    public String showMyCourse() {
        return "myCoursesAreaPurchasedCourse";
    }



}
