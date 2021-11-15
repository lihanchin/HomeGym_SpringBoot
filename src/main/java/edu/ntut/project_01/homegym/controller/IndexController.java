package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.AuthRequest;
import edu.ntut.project_01.homegym.model.Member;

import edu.ntut.project_01.homegym.model.Visitor;
import edu.ntut.project_01.homegym.service.AuthService;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
public class IndexController {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private VisitorService visitorService;


    @Value("${jwt.header}")
    private String authorization;

//    //檢查JWT
//    @GetMapping("/checkStatus")
//    public String checkStatus( HttpServletRequest request) {
//
//        String header= request.getHeader(authorization);
//        Member member = memberService.findMemberByToken(header);
//        return "registated";
//    }

    //註冊(加入Security)(OK)
    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> registrations(@RequestBody Member member) {
        return authService.register(member);
    }



    //重寄驗證信(OK)
    @GetMapping("/registrations/memberVerification/sendAgain/{memberId}")
    public ResponseEntity<String> sendVerificationAgain(@PathVariable Integer memberId) {
        return authService.resendMail(memberId);
    }

    //登入(加入Security)(OK)
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws AuthenticationException {
       log.info("準備傳回會員資料");
        System.out.println(authRequest.getUsername());
        System.out.println(authRequest.getPassword());
        return authService.login(authRequest.getUsername(), authRequest.getPassword());
    }



    //測試用(如果沒得到驗證TOKEN是無法訪問此路徑)
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    //測試用(身份驗證阻擋測試)
    @GetMapping("/hello/RoleAuthorizationTest1")
    @PreAuthorize("hasRole('MEMBER') OR hasRole('COACH')")
    public String bothOfRole() {
        return "教練、會員都看得到喔～～～";
    }

    //測試用(身份驗證阻擋測試)
    @GetMapping("/hello/RoleAuthorizationTest2")
    @PreAuthorize("hasRole('MEMBER')")
    public String onlyMember() {
        return "只有會員進得來";
    }

    //測試用(身份驗證阻擋測試)
    @GetMapping("/hello/RoleAuthorizationTest3")
    @PreAuthorize("hasRole('COACH')")
    public String onlyCoach() {
        return "只有教練進得來";
    }

    @PostMapping("/addMessage")
    public void addMessage(@RequestBody Visitor visitor){
        visitorService.addMessage(visitor);
    }
    //註冊(舊版)
//    @PostMapping("/registration")
//    public ResponseEntity<String> createMember(@RequestBody Member member) {
//        return memberService.createMember(member);
//    }

    //登入(舊版)
//    @PostMapping("/login")
//    public ResponseEntity<Member> memberLogin(@RequestBody Member member){
//        String email = member.getEmail();
//        String password = member.getPassword();
//        return memberService.login(email,password);
//    }

    //註冊資料傳來非Json值
//    @PostMapping("/memberSave")
//    public Member insert(@RequestParam Map<String, String> param, Model model){
//        Member member = new Member();
//        member.setName(param.get("name"));
//        member.setEmail(param.get("email"));
//        member.setPassword(param.get("password"));
//        SimpleDateFormat sdf  = new  SimpleDateFormat("yyyy-MM-dd");
//        try {
//            member.setBirthday(new java.sql.Date(sdf.parse(param.get("birthday")).getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        member.setPhone(param.get("phone"));
//        memberRepository.save(member);
//        model.addAttribute("member",member);
//        return member;
//    }
}
