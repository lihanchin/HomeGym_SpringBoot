package edu.ntut.project_01.homegym.controller;

import ecpay.payment.integration.AllInOne;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.repository.OrdersRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import example.ExampleAllInOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class ShoppingCartController {

    private static AllInOne all;
    @Value("${jwt.header}")
    private String HEADER;
    private String authorizationHeader;
    private CourseRepository courseRepository;
    private MemberService memberService;
    private OrdersRepository ordersRepository;

    @Autowired
    public ShoppingCartController(CourseRepository courseRepository, MemberService memberService, OrdersRepository ordersRepository) {
        this.courseRepository = courseRepository;
        this.memberService = memberService;
        this.ordersRepository = ordersRepository;
    }

    //目前只差接不到綠界傳回來的參數
    @PostMapping("/checkout")
    ResponseEntity<Map> useECPAY(@RequestBody String[] checkOut, HttpServletRequest httpServletRequest) {

//        綠界產生訂單需要的參數
        Integer orderPriceAmount = 0;
        StringBuilder orderItems = new StringBuilder();
        UUID uid = UUID.randomUUID();
        String orderId = "HG" + uid.toString().replace("-", "").substring(0, 6);

        authorizationHeader = httpServletRequest.getHeader(HEADER);
        Member member = memberService.findMemberByToken(authorizationHeader);

        Set<Course> orderCourses = new HashSet<>();


        for (int i = 0; i < checkOut.length; i++) {
//      courseId >> 課程資料 （ 抓課程名跟價錢 ）
            Optional<Course> course = courseRepository.findById(Integer.valueOf(checkOut[i]));
            orderCourses.add(course.orElseThrow());

                String courseName = course.get().getCourseName();
                Integer coursePrice = course.get().getPrice();
                orderPriceAmount += coursePrice;
                if (i == 0) {
                    orderItems.append(courseName + " 價錢：" + coursePrice);
                } else {
                    orderItems.append(" # " + courseName + " 價錢：" + coursePrice);
                }

        }


//      建訂單資料
        Orders newOrder = new Orders(orderId, orderPriceAmount, member, orderCourses);
        ordersRepository.save(newOrder);

        ExampleAllInOne exampleAllInOne = new ExampleAllInOne();
        ExampleAllInOne.initial();
        Map<String, String> map = new HashMap<>();
        String paymentPage = exampleAllInOne.genAioCheckOutALL(orderId, orderPriceAmount.toString(), orderItems.toString());
        if (paymentPage != null) {
            map.put("paymentPage", paymentPage);
            System.out.println(paymentPage);
            return ResponseEntity.ok().body(map);
        }
        map.put("message", "交易失敗");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }


    @PostMapping("/ecpayResponse")
    public String ecpayResponse(Map<String, Object> ecPayResponse) {
        String orderId = ecPayResponse.get("MerchantTradeNo").toString();
        Integer paymentStatus = Integer.valueOf(ecPayResponse.get("RtnCode").toString());

        Optional<Orders> order = ordersRepository.findById(orderId);
        if (paymentStatus == 1) {
            order.orElseThrow().setOrderStatus("付款成功");
        } else {
            order.orElseThrow().setOrderStatus("付款失敗");
        }
        ordersRepository.save(order.get());
        return "OK";
    }
}

