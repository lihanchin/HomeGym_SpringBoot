package edu.ntut.project_01.homegym.controller;

import ecpay.payment.integration.AllInOne;
import example.ExampleAllInOne;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartController {

    private static AllInOne all;

    //測試中
//    @PostMapping("/test/buySoomething")
//    ResponseEntity<String> useECPAY(@RequestBody String price, String orderItems) {
//        ExampleAllInOne exampleAllInOne = new ExampleAllInOne();
//        ExampleAllInOne.initial();
//        String paymentPage = exampleAllInOne.genAioCheckOutALL(price, orderItems);
//        if (paymentPage != null) {
//            return ResponseEntity.ok().body(paymentPage);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("交易失敗");
//    }
}
