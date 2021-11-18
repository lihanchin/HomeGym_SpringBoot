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
    @GetMapping("/myCourse/allCourse")
    ResponseEntity<Map<String, Object>> myCourse(@RequestParam(required = false) Integer page, HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(requestHeader);
        Integer memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        Integer totalPage = orderService.totalPageByStatus(memberId,Arrays.asList("付款完成"),pageSize);
        Map<String, Object> courseResponse = new HashMap<>();
        PageRequest pageRequest;
        Page<Orders> ordersPage;
        if (page != null) {
            if (page > 0 && page<= totalPage) {
                pageRequest = PageRequest.of(page-1 ,pageSize);
                ordersPage =orderService.findOrdersByMemberIdAndStatus(memberId, Arrays.asList("付款完成"),pageRequest);
                courseResponse.put("totalPage", totalPage);
                courseResponse.put("courseList", orderService.statusOrderDetail(ordersPage));
                return ResponseEntity.ok().body(courseResponse);
            }
            throw new QueryException("頁數不得小於、等於0");
        }
        pageRequest = PageRequest.of(0 ,pageSize);
        ordersPage =orderService.findOrdersByMemberIdAndStatus(memberId, Arrays.asList("付款完成"),pageRequest);

        courseResponse.put("totalPage", totalPage);
        courseResponse.put("courseList", orderService.statusOrderDetail(ordersPage));
        return ResponseEntity.ok().body(courseResponse);

    }
}
