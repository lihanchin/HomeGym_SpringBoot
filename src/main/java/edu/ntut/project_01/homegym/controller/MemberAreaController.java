package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.OrderService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderService orderService;



    //會員資料
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> showMemberInfo(HttpServletRequest httpServletRequest) {
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        Member member = memberService.findMemberByToken(authorizationHeader);
        Map<String,Object> memberInfo = new HashMap<>();
        memberInfo.put("memberId" ,member.getMemberId());
        memberInfo.put("name" ,member.getName());
        memberInfo.put("email" ,member.getEmail());
        memberInfo.put("birthday" ,member.getBirthday());
        memberInfo.put("phone" ,member.getPhone());
        memberInfo.put("mimeType" ,member.getMimeType());
        memberInfo.put("memberImage" ,member.getMemberImage());
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
    public ResponseEntity<Map<String, Object>> findNgOrderByMemberId(@RequestParam(required = false) Integer page) {
//        authorizationHeader = httpServletRequest.getHeader(HEADER);
//        memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        //之後這裡應該是要從綠界抓
        Set<String> failReason = new HashSet<>(Arrays.asList("付款失敗", "信用卡餘額不足", "網路中斷交易"));
        totalPage = orderService.totalPageByStatus(7, failReason, orderPageSize);
        if (page != null) {
            if (page > 0 && page <= totalPage) {
                indexPage = page - 1;
                return ResponseEntity.ok().body(orderService.orderPage(7, failReason, indexPage, orderPageSize));
            }
            throw new QueryException("頁數不可大於總頁數");
        }
        indexPage = 0;
        return ResponseEntity.ok().body(orderService.orderPage(7, failReason, indexPage, orderPageSize));
    }

    //會員資料更新
    @PutMapping("/edit")
    public ResponseEntity<Member> editMyMemberInfo(@RequestBody Map<String,Object> memberInfo, HttpServletRequest httpServletRequest){
        authorizationHeader = httpServletRequest.getHeader(HEADER);
        Member member = memberService.findMemberByToken(authorizationHeader);
        memberId = memberService.findMemberByToken(authorizationHeader).getMemberId();
        String base64 =  memberInfo.get("memberImage").toString();
        int startIndex = base64.indexOf(",")+1;
        String mimeType = base64.substring(base64.indexOf(":")+1,base64.indexOf(";"));
        byte[] bytes = Base64.getDecoder().decode(base64.substring(startIndex));
        String name = memberInfo.get("name").toString();
        String phone = memberInfo.get("phone").toString();
        member.setPhone(phone);
        member.setName(name);
        member.setMimeType(mimeType);
        member.setMemberImage(bytes);
        memberService.update(member);
        return ResponseEntity.ok().body(member);
    }

}
