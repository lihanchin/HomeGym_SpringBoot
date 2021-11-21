package edu.ntut.project_01.homegym.controller;


import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MyCourseController {

    @Value("${jwt.header}")
    private String requestHeader;

    private MemberService memberService;
    private OrderService orderService;

    @Autowired
    public MyCourseController(MemberService memberService, OrderService orderService) {
        this.memberService = memberService;
        this.orderService = orderService;
    }

    //已買課程分頁(OK)
    @GetMapping("/myCourses/allCourse")
    ResponseEntity<Map<String, Object>> myCourse(HttpServletRequest request) {
        Integer memberId = memberService.findMemberByToken(request.getHeader(requestHeader)).getMemberId();
        Map<String, Object> courseResponse = new HashMap<>();
        courseResponse.put("courseList", orderService.okStatusCourses(orderService.findOrdersByMemberIdAndOKStatus(memberId, "付款成功")));

        return ResponseEntity.ok().body(courseResponse);

    }
}
