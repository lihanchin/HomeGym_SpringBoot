package edu.ntut.project_01.homegym.controller;

import edu.ntut.project_01.homegym.model.AuthRequest;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;

import edu.ntut.project_01.homegym.model.Visitor;
import edu.ntut.project_01.homegym.service.AuthService;
import edu.ntut.project_01.homegym.service.CourseService;
import edu.ntut.project_01.homegym.service.MemberService;
import edu.ntut.project_01.homegym.service.VisitorService;
import edu.ntut.project_01.homegym.util.JwtUtil;
import edu.ntut.project_01.homegym.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
public class IndexController {

    private AuthService authService;
    private MemberService memberService;
    private VisitorService visitorService;
    private CourseService courseService;
    private JwtUtil jwtUtil;
    private MailUtil mailUtil;

    @Value("${jwt.header}")
    private String authorization;
    @Value("${course.countsPerPage}")
    private Integer SIZE;

    public IndexController(AuthService authService, MemberService memberService, VisitorService visitorService, CourseService courseService, JwtUtil jwtUtil, MailUtil mailUtil) {
        this.authService = authService;
        this.memberService = memberService;
        this.visitorService = visitorService;
        this.courseService = courseService;
        this.jwtUtil = jwtUtil;
        this.mailUtil = mailUtil;
    }

    @Autowired


    //檢查JWT
    @GetMapping("/checkStatus")
    public Map<String, Object> checkStatus(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String header = request.getHeader(authorization);
        Member member = memberService.findMemberByToken(header);
        map.put("name", member.getName());
        map.put("mimeType", member.getMimeType());
        map.put("memberImage", member.getMemberImage());
        return map;
    }

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
    public ResponseEntity<Map<String, Object>> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws AuthenticationException {

        System.out.println(authRequest.getUsername());
        System.out.println(authRequest.getPassword());
        return authService.login(authRequest.getUsername(), authRequest.getPassword());
    }

    //關鍵字查詢
    @GetMapping("/keyword")
    public ResponseEntity<Map<String, Object>> keyword(@RequestParam String keyword, @RequestParam(required = false) Integer page) {
        Map<String, Object> response = new HashMap<>();
        if (page != null && page != 0) {
            response.put("courseList", courseService.findCoursesByKeyword(keyword, page - 1, SIZE).getContent());
            response.put("totalPage", courseService.findCoursesByKeyword(keyword, page - 1, SIZE).getTotalPages());
        } else {
            response.put("courseList", courseService.findCoursesByKeyword(keyword, 0, SIZE).getContent());
            response.put("totalPage", courseService.findCoursesByKeyword(keyword, 0, SIZE).getTotalPages());
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/addMessage")
    public void addMessage(@RequestBody Visitor visitor) {
        visitorService.addMessage(visitor);
    }

    //忘記密碼-驗證帳號、寄信
    @PostMapping("/forget/checkMail")
    public ResponseEntity<String> checkMailAndSend(@RequestBody String memberEmail) {
        if (memberService.findMemberByEmail(memberEmail).isPresent()) {
            mailUtil.sendResetPassword(memberEmail);
            return ResponseEntity.ok().body("已寄信");
        }
        return ResponseEntity.ok().body("您尚未成為我們的會員");
    }

    //新密碼設定
    @PostMapping("/forget/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,20}$";
        String memberEmail = password.get("memberEmail");
        String newPassword = password.get("newPassword");
        String newPasswordCheck = password.get("newPasswordCheck");
        if (newPassword.equals(newPasswordCheck)){
            if(newPassword.matches(regex)){
                Member member = memberService.findMemberByEmail(memberEmail).orElseThrow();
                member.setPassword(newPassword);
                memberService.update(member);
                return ResponseEntity.ok().body("修改成功");
            }
            return ResponseEntity.ok().body("您的密碼必須超過5個字元且包含英文字母大小寫和數字");
        }
        return ResponseEntity.ok().body("您輸入的新密碼和確認密碼不相符");
    }


//    //測試用(如果沒得到驗證TOKEN是無法訪問此路徑)
//    @GetMapping("/hello")
//    public String hello() {
//        return "hello world";
//    }
//
//    //測試用(身份驗證阻擋測試)
//    @GetMapping("/hello/RoleAuthorizationTest1")
//    @PreAuthorize("hasRole('MEMBER') OR hasRole('COACH')")
//    public String bothOfRole() {
//        return "教練、會員都看得到喔～～～";
//    }
//
//    //測試用(身份驗證阻擋測試)
//    @GetMapping("/hello/RoleAuthorizationTest2")
//    @PreAuthorize("hasRole('MEMBER')")
//    public String onlyMember() {
//        return "只有會員進得來";
//    }
//
//    //測試用(身份驗證阻擋測試)
//    @GetMapping("/hello/RoleAuthorizationTest3")
//    @PreAuthorize("hasRole('COACH')")
//    public String onlyCoach() {
//        return "只有教練進得來";
//    }
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
