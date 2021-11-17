package edu.ntut.project_01.homegym.controller;


import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.OrderService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyCourseController {

    @Value("${course.countsPerPage}")
    private Integer pageSize;
    @Value("${jwt.header}")
    private String requestHeader;

    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderService orderService;

    //已買課程分頁(OK)
    @GetMapping("/myCourses/allCourse")
    ResponseEntity<Map<String, Object>> myCourse(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(requestHeader);
        Integer memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        Map<String, Object> courseResponse = new HashMap<>();

        List<Orders> okOrders =orderService.findOrdersByMemberIdAndOKStatus(memberId, "付款成功");


        courseResponse.put("courseList", orderService.okStatusCourses(okOrders));
        return ResponseEntity.ok().body(courseResponse);

    }
}
