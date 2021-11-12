package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.OrderService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/memberArea")
public class MemberAreaController {

    @Value("${jwt.header}")
    private String HEADER;
    private String authorizationHeader;
    @Value("${order.countsPerPage}")
    private Integer orderPageSize;
    private Integer indexPage;
    private Integer totalPage;
    private Integer memberId;

    private MemberService memberService;
    private OrderService orderService;

    @Autowired
    public MemberAreaController(MemberService memberService, OrderService orderService) {
        this.memberService = memberService;
        this.orderService = orderService;
    }

    //更改密碼(OK)
    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> oldAndNewPassword, HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        String oldPassword = oldAndNewPassword.get("oldPassword");
        String newPassword = oldAndNewPassword.get("newPassword");
        String newPasswordCheck = oldAndNewPassword.get("newPasswordCheck");

        return memberService.changePassword(authorizationHeader, oldPassword, newPassword, newPasswordCheck);
    }

    //我的訂單OK(OK)
    @GetMapping("/OKOrder")
    public ResponseEntity<Map<String, Object>> findOkOrderByMemberId(@RequestParam(required = false) Integer page, HttpServletRequest httpServletRequest) {

        authorizationHeader = httpServletRequest.getHeader(HEADER);
        memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        totalPage = orderService.totalPageByStatus(memberId, Arrays.asList("付款完成"), orderPageSize);

        if (page != null) {
            if (page > 0 && page <= totalPage) {
                indexPage = page - 1;
                return ResponseEntity.ok().body(orderService.orderPage(memberId, Arrays.asList("付款完成"), indexPage, orderPageSize));
            }
            throw new QueryException("頁數不可大於總頁數");
        }
        indexPage = 0;
        return ResponseEntity.ok().body(orderService.orderPage(memberId, Arrays.asList("付款完成"), indexPage, orderPageSize));
    }

    //我的訂單NG(OK)
    @GetMapping("/NGOrder")
    public ResponseEntity<Map<String, Object>> findNgOrderByMemberId(@RequestParam(required = false) Integer page, HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        //之後這裡應該是要從綠界抓
        Set<String> failReason = new HashSet<>(Arrays.asList("付款失敗", "信用卡餘額不足", "網路中斷交易"));
        totalPage = orderService.totalPageByStatus(memberId, failReason, orderPageSize);
        if (page != null) {
            if (page > 0 && page <= totalPage) {
                indexPage = page - 1;
                return ResponseEntity.ok().body(orderService.orderPage(memberId, failReason, indexPage, orderPageSize));
            }
            throw new QueryException("頁數不可大於總頁數");
        }
        indexPage = 0;
        return ResponseEntity.ok().body(orderService.orderPage(memberId, failReason, indexPage, orderPageSize));
    }
}
