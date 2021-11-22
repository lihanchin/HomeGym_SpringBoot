package edu.ntut.project_01.homegym.controller;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.repository.OrdersRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.util.GlobalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
public class ShoppingCartController {

    @Value("${jwt.header}")
    private String HEADER;
    @Value("${hg.url}")
    private String ourUrl;
    private static final Calendar currentTime = Calendar.getInstance(Locale.CHINESE);

    private final CourseRepository courseRepository;
    private final MemberService memberService;
    private final OrdersRepository ordersRepository;

    @Autowired
    public ShoppingCartController(CourseRepository courseRepository, MemberService memberService, OrdersRepository ordersRepository) {
        this.courseRepository = courseRepository;
        this.memberService = memberService;
        this.ordersRepository = ordersRepository;
    }

    @PostMapping("/checkout")
    ResponseEntity<Map<String, String>> useECPAY(HttpServletRequest httpServletRequest, @RequestBody String[] checkOut) {

//        綠界產生訂單需要的參數
        Integer orderPriceAmount = 0;
        StringBuilder orderItems = new StringBuilder();
        UUID uid = UUID.randomUUID();
        String orderId = "HG" + uid.toString().replace("-", "").substring(0, 6);
        Member member = memberService.findMemberByToken(httpServletRequest.getHeader(HEADER));
        Set<Course> orderCourses = new HashSet<>();

        for (int i = 0; i < checkOut.length; i++) {
//      courseId >> 課程資料 （ 抓課程名跟價錢 ）
            Optional<Course> course = courseRepository.findById(Integer.valueOf(checkOut[i]));
            orderCourses.add(course.orElseThrow());
            String courseName = course.get().getCourseName();
            Integer coursePrice = course.get().getPrice();
            orderPriceAmount += coursePrice;

            if (i == 0) {
                orderItems.append(courseName)
                        .append(" 價錢：")
                        .append(coursePrice);
            } else {
                orderItems.append(" # ")
                        .append(courseName)
                        .append(" 價錢：")
                        .append(coursePrice);
            }
        }

//      建訂單資料
        Orders newOrder = new Orders(orderId, orderPriceAmount, null, GlobalService.getNowDatetime(), member, orderCourses);
        ordersRepository.save(newOrder);

        for (Course course : orderCourses) {
            ordersRepository.insertOrderItem(course.getCourseId(), orderId);
        }

        Map<String, String> response = new HashMap<>();
        String paymentPage = genAioCheckOutALL(orderId, orderPriceAmount.toString(), orderItems.toString(), ourUrl);
        if (paymentPage != null) {
            response.put("paymentPage", paymentPage);
            log.info("綠界回傳網頁 ： " + paymentPage);
            return ResponseEntity.ok().body(response);
        }
        response.put("message", "交易失敗");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PostMapping("/ecpayResponse")
    public String ecpayResponse(@RequestBody String ecPayResponse) {

        String[] responses = ecPayResponse.split("&");
        Map<String, String> detail = new HashMap<>();

        for (int i = 1; i < responses.length; i++) {
            String[] detailKeyAndValue = responses[i].split("=");
            if (detailKeyAndValue.length == 1) {
                detail.put(detailKeyAndValue[0], "none");
            } else {
                detail.put(detailKeyAndValue[0], detailKeyAndValue[1]);
            }

        }
        log.info("ecpayResponse: (Map)" + detail);

        String orderId = detail.get("MerchantTradeNo");
        int paymentStatus = Integer.parseInt(detail.get("RtnCode"));

        Optional<Orders> order = ordersRepository.findById(orderId);
        if (paymentStatus == 1) {
            order.orElseThrow().setOrderStatus("付款成功");
        } else {
            order.orElseThrow().setOrderStatus("付款失敗" + paymentStatus);
        }
        ordersRepository.save(order.get());
        return "1|OK";
    }

    public String genAioCheckOutALL(String orderId, String price, String orderItems, String ourUrl) {
        AllInOne all = new AllInOne("");
        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(orderId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        obj.setMerchantTradeDate(sdf.format(currentTime.getTime()));
        obj.setTotalAmount(price);
        obj.setTradeDesc("HomeGym");
        obj.setItemName(orderItems);
        obj.setReturnURL(ourUrl + "/ecpayResponse");
        obj.setNeedExtraPaidInfo("Y");
        obj.setClientBackURL(ourUrl + "/OKOrder");
        return all.aioCheckOut(obj, null);
    }
}

