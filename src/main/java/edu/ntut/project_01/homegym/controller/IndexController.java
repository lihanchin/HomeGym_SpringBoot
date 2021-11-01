package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.AuthRequest;
import edu.ntut.project_01.homegym.model.Member;

import edu.ntut.project_01.homegym.service.AuthService;
import edu.ntut.project_01.homegym.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
public class IndexController {

    private AuthService authService;
    private MemberService memberService;

    @Autowired
    public IndexController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
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

    //註冊(加入Security)
    @PostMapping("/registrations")
    public ResponseEntity<String> registrations(@RequestBody Member member) {
        return authService.register(member);
    }

    //驗證
    @GetMapping("/registration/memberVerification")
    public ResponseEntity<String> updateMemberStatus(@RequestParam String code) {
        return memberService.updateStatus(code);
    }

    //登入(加入Security)
    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws AuthenticationException {
        return authService.login(authRequest.getUsername(), authRequest.getPassword());
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
