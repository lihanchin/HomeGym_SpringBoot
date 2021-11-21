package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.OrdersRepository;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/memberArea")
public class MemberAreaController {

    @Value("${jwt.header}")
    private String HEADER;
    @Value("${order.countsPerPage}")
    private Integer orderPageSize;
    private Integer totalPage;
    private Integer memberId;
    private Map<String, Object> response;

    private final MemberService memberService;
    private final OrderService orderService;
    private final OrdersRepository ordersRepository;

    @Autowired
    public MemberAreaController(MemberService memberService, OrderService orderService, OrdersRepository ordersRepository) {
        this.memberService = memberService;
        this.orderService = orderService;
        this.ordersRepository = ordersRepository;
    }

    //會員資料
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> showMemberInfo(HttpServletRequest httpServletRequest) {
        Member member = memberService.findMemberByToken(httpServletRequest.getHeader(HEADER));
        response = new HashMap<>();
        response.put("memberImage", member.getMemberImage());
        response.put("mimeType", member.getMimeType());
        response.put("memberId", member.getMemberId());
        response.put("name", member.getName());
        response.put("email", member.getEmail());
        response.put("birthday", member.getBirthday());
        response.put("phone", member.getPhone());

        return ResponseEntity.ok().body(response);
    }

    //更改密碼(OK)
    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(HttpServletRequest httpServletRequest, @RequestBody Map<String, String> oldAndNewPassword) {
        return ResponseEntity
                .ok()
                .body(
                        memberService.changePassword(httpServletRequest.getHeader(HEADER),
                                oldAndNewPassword.get("oldPassword"),
                                oldAndNewPassword.get("newPassword"),
                                oldAndNewPassword.get("newPasswordCheck"))
                );
    }

    //綠界回來的按鍵(OK)
    @GetMapping("/backFromECPay")
    public ResponseEntity<String> ecPayResponse(HttpServletRequest request) {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("orderTime").descending());
        Integer memberId = memberService.findMemberByToken(request.getHeader(HEADER)).getMemberId();
        return ResponseEntity.ok().body(ordersRepository.findOrdersByMember_MemberId(memberId, pageRequest).getContent().get(0).getOrderStatus());
    }

    //我的訂單OK(OK)
    @GetMapping("/OKOrder")
    public ResponseEntity<Map<String, Object>> findOkOrderByMemberId(@RequestParam(required = false, defaultValue = "1") Integer page, HttpServletRequest httpServletRequest) {
        memberId = memberService.findMemberByToken(httpServletRequest.getHeader(HEADER)).getMemberId();
        totalPage = orderService.totalPageByStatus(memberId, "付款成功", orderPageSize);

        if (page <= 0 || page > totalPage) {
            page = 1;
        }

        return ResponseEntity.ok().body(orderService.orderPage(memberId, "付款成功", page - 1, orderPageSize));
    }

    //我的訂單NG(OK)
    @GetMapping("/NGOrder")
    public ResponseEntity<Map<String, Object>> findNgOrderByMemberId(HttpServletRequest httpServletRequest, @RequestParam(required = false, defaultValue = "1") Integer page) {
        memberId = memberService.findMemberByToken(httpServletRequest.getHeader(HEADER)).getMemberId();
        totalPage = orderService.totalPageByStatus(memberId, "付款失敗", orderPageSize);

        if (page <= 0 || page > totalPage) {
            page = 1;
        }

        return ResponseEntity.ok().body(orderService.orderPage(memberId, "付款失敗", page - 1, orderPageSize));
    }

    //會員資料更新
    @PutMapping("/edit")
    public ResponseEntity<Map<String, Object>> editMyMemberInfo(@RequestBody Map<String, Object> memberInfo, HttpServletRequest httpServletRequest) {
        memberId = memberService.findMemberByToken(httpServletRequest.getHeader(HEADER)).getMemberId();
        response = memberService.updateMemberInfo(memberId,
                memberInfo.get("name").toString(),
                memberInfo.get("memberImage").toString(),
                memberInfo.get("phone").toString());

        return ResponseEntity.ok().body(response);
    }

}
