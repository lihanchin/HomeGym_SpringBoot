package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.OrdersRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.OrderService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
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
    private OrdersRepository ordersRepository;

    @Autowired
    public MemberAreaController(MemberService memberService, OrderService orderService, OrdersRepository ordersRepository) {
        this.memberService = memberService;
        this.orderService = orderService;
        this.ordersRepository = ordersRepository;
    }

    //會員資料
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> showMemberInfo(HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        Member member = memberService.findMemberByToken(authorizationHeader);
        Map<String, Object> memberInfo = new HashMap<>();
        memberInfo.put("memberImage", member.getMemberImage());
        memberInfo.put("mimeType", member.getMimeType());
        memberInfo.put("memberId", member.getMemberId());
        memberInfo.put("name", member.getName());
        memberInfo.put("email", member.getEmail());
        memberInfo.put("birthday", member.getBirthday());
        memberInfo.put("phone", member.getPhone());

        return ResponseEntity.ok().body(memberInfo);
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

//    //綠界回來的按鍵(OK)
//    @GetMapping("/backFromECPay")
//    public ResponseEntity<String> ecPayResponse(HttpServletRequest request) {
//        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("orderTime").descending());
//        authorizationHeader = request.getHeader(HEADER);
//        Integer memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
//        String message = ordersRepository.findOrdersByMember_MemberId(memberId, pageRequest).getContent().get(0).getOrderStatus();
//        return ResponseEntity.ok().body(message);
//    }

    //我的訂單OK(OK)
    @GetMapping("/OKOrder")
    public ResponseEntity<Map<String, Object>> findOkOrderByMemberId(@RequestParam(required = false) Integer page, HttpServletRequest httpServletRequest) {

        authorizationHeader = httpServletRequest.getHeader(HEADER);
        memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();

        totalPage = orderService.totalPageByStatus(memberId, "付款成功", orderPageSize);

        if (page != null) {
            if (page > 0 && page <= totalPage) {
                indexPage = page - 1;
                return ResponseEntity.ok().body(orderService.orderPage(memberId, "付款成功", indexPage, orderPageSize));
            }
            throw new QueryException("頁數不可大於總頁數");
        }
        indexPage = 0;
        return ResponseEntity.ok().body(orderService.orderPage(memberId, "付款成功", indexPage, orderPageSize));
    }

    //我的訂單NG(OK)
    @GetMapping("/NGOrder")
    public ResponseEntity<Map<String, Object>> findNgOrderByMemberId(@RequestParam(required = false) Integer page, HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        //之後這裡應該是要從綠界抓
        totalPage = orderService.totalPageByStatus(memberId, "付款失敗", orderPageSize);
        if (page != null) {
            if (page > 0 && page <= totalPage) {
                indexPage = page - 1;
                return ResponseEntity.ok().body(orderService.orderPage(memberId, "付款失敗", indexPage, orderPageSize));
            }
            throw new QueryException("頁數不可大於總頁數");
        }
        indexPage = 0;
        return ResponseEntity.ok().body(orderService.orderPage(memberId, "付款失敗", indexPage, orderPageSize));
    }

    //會員資料更新
    @PutMapping("/edit")
    public ResponseEntity<Map<String, Object>> editMyMemberInfo(@RequestBody Map<String, Object> memberInfo, HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        Member member = memberService.findMemberByToken(authorizationHeader);
        memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        String name = memberInfo.get("name").toString();
        String phone = memberInfo.get("phone").toString();
        String base64 = memberInfo.get("memberImage").toString();
        Map<String, Object> updateMemberInfoResponse = memberService.updateMemberInfo(memberId, name, base64, phone);

        return ResponseEntity.ok().body(updateMemberInfoResponse);
    }

}
